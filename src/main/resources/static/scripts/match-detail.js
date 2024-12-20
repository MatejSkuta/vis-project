let homeTeamId, awayTeamId, currentMatchId;

const MIN_PLAYERS = 7;
const MAX_PLAYERS = 18;

async function validateSquadSize(teamId, matchId) {
    try {
        console.log("matchId:", matchId, "teamId:", teamId);
        const response = await fetch(`/api/squads/match/${matchId}/team/${teamId}`);
        if (response.ok) {
            const players = await response.json();
            console.log("Players JSON:", players);
            console.log("Players Length:", players.length);
            return players.length;
        } else {
            throw new Error("Failed to validate squad size.");
        }
    } catch (error) {
        console.error("Error validating squad size:", error);
        return 0; // Pokud dojde k chybě, vrátíme 0
    }
}

document.addEventListener("DOMContentLoaded", async () => {

    const pathSegments = window.location.pathname.split("/");
    currentMatchId = pathSegments[pathSegments.length - 2];
    console.log("Loaded matchId:", currentMatchId);

    if (currentMatchId) {
        await loadMatchDetails(currentMatchId);
    } else {
        console.error("Match ID is missing in the URL");
    }
});

async function loadMatchDetails(matchId) {
    try {
        const response = await fetch(`/api/matches/${matchId}`);
        if (response.ok) {
            const matchDetails = await response.json();
            console.log("Match details:", matchDetails);

            homeTeamId = matchDetails.homeTeamId;
            awayTeamId = matchDetails.awayTeamId;

            document.querySelector("#homeTeamName").textContent = matchDetails.homeTeamName || "N/A";
            document.querySelector("#awayTeamName").textContent = matchDetails.awayTeamName || "N/A";

            await loadMatchSquad(matchId);
            await loadAvailablePlayers(homeTeamId, awayTeamId);

            await loadGoals(matchId);

        } else {
            throw new Error("Failed to load match details");
        }
    } catch (error) {
        console.error("Error loading match details:", error);
    }
}

async function loadMatchSquad(matchId) {
    try {
        const response = await fetch(`/api/squads/match/${matchId}`);
        if (response.ok) {
            const squad = await response.json();
            console.log("Loaded squad:", squad);

            const homeSquadTable = document.querySelector("#homeSquad tbody");
            const awaySquadTable = document.querySelector("#awaySquad tbody");
            const homeGoalScorerSelect = document.getElementById("homeGoalScorerSelect");
            const awayGoalScorerSelect = document.getElementById("awayGoalScorerSelect");

            homeSquadTable.innerHTML = "";
            awaySquadTable.innerHTML = "";
            homeGoalScorerSelect.innerHTML = "";
            awayGoalScorerSelect.innerHTML = "";

            squad.forEach(player => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${player.firstName} ${player.lastName}</td>
                    <td>${player.jerseyNumber}</td>
                    <td>
                        <a href="#" class="btn btn-danger" onclick="removePlayerFromSquad(${player.playerId}, ${player.matchId}, ${player.teamId}); return false;">
                            Remove
                        </a>
                    </td>
                `;

                if (player.teamId === homeTeamId) {
                    homeSquadTable.appendChild(row);

                    const option = document.createElement("option");
                    option.value = player.playerId;
                    option.textContent = `${player.firstName} ${player.lastName} (#${player.jerseyNumber})`;
                    homeGoalScorerSelect.appendChild(option);
                } else if (player.teamId === awayTeamId) {
                    awaySquadTable.appendChild(row);

                    const option = document.createElement("option");
                    option.value = player.playerId;
                    option.textContent = `${player.firstName} ${player.lastName} (#${player.jerseyNumber})`;
                    awayGoalScorerSelect.appendChild(option);
                }
            });
        } else {
            throw new Error("Failed to load squad");
        }
    } catch (error) {
        console.error("Error loading squad:", error);
    }
}

async function loadAvailablePlayers(homeTeamId, awayTeamId) {
    try {
        console.log("Loading available players...");
        const homePlayerSelect = document.getElementById("homePlayerSelect");
        const awayPlayerSelect = document.getElementById("awayPlayerSelect");

        homePlayerSelect.innerHTML = "";
        awayPlayerSelect.innerHTML = "";

        await loadPlayersForTeam(homeTeamId, homePlayerSelect);

        await loadPlayersForTeam(awayTeamId, awayPlayerSelect);
    } catch (error) {
        console.error("Error loading available players:", error);
    }
}

async function loadPlayersForTeam(teamId, selectElement) {
    try {
        const response = await fetch(`/api/players/team/${teamId}`);
        if (response.ok) {
            const players = await response.json();
            console.log(`Players for team ${teamId}:`, players);

            players.forEach(player => {
                const option = document.createElement("option");
                option.value = player.id;
                option.textContent = `${player.firstName} ${player.lastName}`;
                selectElement.appendChild(option);
            });
        } else {
            console.error(`Failed to load players for team ${teamId}`);
        }
    } catch (error) {
        console.error(`Error loading players for team ${teamId}:`, error);
    }
}

document.getElementById("addHomePlayer").addEventListener("click", async () => {
    const playerId = document.getElementById("homePlayerSelect").value;
    const jerseyNumber = document.getElementById("homeJerseyNumber").value;
    if (playerId && jerseyNumber) {
        try {
            const currentSquadSize = await validateSquadSize(homeTeamId, currentMatchId);
            if (currentSquadSize >= MAX_PLAYERS) {
                alert(`Cannot add more players. Maximum squad size is ${MAX_PLAYERS}.`);
                return;
            }
            await addPlayerToSquad(playerId, currentMatchId, jerseyNumber);
        } catch (error) {
            console.error("Error validating squad size or adding player:", error);
            alert("An error occurred while processing the request.");
        }
    } else {
        alert("Please select a player and enter a jersey number.");
    }
});

document.getElementById("addAwayPlayer").addEventListener("click", async () => {
    const playerId = document.getElementById("awayPlayerSelect").value;
    const jerseyNumber = document.getElementById("awayJerseyNumber").value;
    if (playerId && jerseyNumber) {
        try {
            const currentSquadSize = await validateSquadSize(awayTeamId, currentMatchId);
            if (currentSquadSize >= MAX_PLAYERS) {
                alert(`Cannot add more players. Maximum squad size is ${MAX_PLAYERS}.`);
                return;
            }
            await addPlayerToSquad(playerId, currentMatchId, jerseyNumber);
        } catch (error) {
            console.error("Error validating squad size or adding player:", error);
            alert("An error occurred while processing the request.");
        }
    } else {
        alert("Please select a player and enter a jersey number.");
    }
});

async function addPlayerToSquad(playerId, matchId, jerseyNumber) {
    try {
        console.log("Sending data:", { playerId, matchId, jerseyNumber });
        const response = await fetch(`/api/squads`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                playerId,
                matchId,
                jerseyNumber
            })
        });

        if (response.ok) {
            alert("Player added successfully!");
            location.reload();
        } else {
            console.error("Failed to add player:", await response.text());
            alert("Failed to add player.");
        }
    } catch (error) {
        console.error("Error adding player:", error);
    }
}

async function removePlayerFromSquad(playerId, matchId, teamId) {
    const currentSquadSize = await validateSquadSize(teamId, matchId);
    console.log("Current player count: " + currentSquadSize);
    if (currentSquadSize <= MIN_PLAYERS) {
        alert(`Cannot remove player. Minimum squad size is ${MIN_PLAYERS}.`);
        return;
    }
    console.log(playerId);
    console.log(matchId)
    try {
        const response = await fetch(`/api/squads`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                playerId,
                matchId,
                teamId
            })
        });

        if (response.ok) {
            alert("Player removed successfully!");
            location.reload();
        } else {
            alert("Failed to remove player.");
        }
    } catch (error) {
        console.error("Error removing player:", error);
    }
}

async function loadMatchSquadForGoals(matchId) {
    try {
        const response = await fetch(`/api/squads/match/${matchId}`);
        if (response.ok) {
            const squad = await response.json();

            const homeGoalScorerSelect = document.getElementById("homeGoalScorerSelect");
            const awayGoalScorerSelect = document.getElementById("awayGoalScorerSelect");

            homeGoalScorerSelect.innerHTML = "";
            awayGoalScorerSelect.innerHTML = "";

            squad.forEach(player => {
                const option = document.createElement("option");
                option.value = player.playerId;
                option.textContent = `${player.firstName} ${player.lastName}`;

                if (player.teamId === homeTeamId) {
                    homeGoalScorerSelect.appendChild(option);
                } else if (player.teamId === awayTeamId) {
                    awayGoalScorerSelect.appendChild(option);
                }
            });
        } else {
            console.error("Failed to load squad for goals.");
        }
    } catch (error) {
        console.error("Error loading squad for goals:", error);
    }
}

async function addGoal(playerId, matchId, goalMinute) {
    try {
        const response = await fetch("/api/goals", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                playerId,
                matchId,
                goalMinute
            })
        });

        if (response.ok) {
            alert("Goal added successfully!");
            loadGoals(matchId);
        } else {
            alert("Failed to add goal.");
        }
    } catch (error) {
        console.error("Error adding goal:", error);
    }
}

async function loadGoals(matchId) {
    try {
        const response = await fetch(`/api/goals/match/${matchId}`);
        if (response.ok) {
            const goals = await response.json();
            console.log("Loaded goals:", goals);

            const homeScorersList = document.getElementById("homeScorersList");
            const awayScorersList = document.getElementById("awayScorersList");

            const homeScoreElement = document.getElementById("homeScore");
            const awayScoreElement = document.getElementById("awayScore");

            homeScorersList.innerHTML = "";
            awayScorersList.innerHTML = "";

            const homeGoals = goals.filter(goal => goal.teamId === homeTeamId);
            const awayGoals = goals.filter(goal => goal.teamId === awayTeamId);

            homeScoreElement.textContent = homeGoals.length;
            awayScoreElement.textContent = awayGoals.length;

            homeGoals.forEach(goal => {
                const listItem = document.createElement("li");
                listItem.className = "list-group-item d-flex justify-content-between align-items-center";

                listItem.textContent = `${goal.firstName} ${goal.lastName} (${goal.goalMinute}')`;

                const removeButton = document.createElement("button");
                removeButton.className = "btn btn-danger btn-sm";
                removeButton.textContent = "Remove";
                removeButton.onclick = async () => {
                    if (confirm("Are you sure you want to delete this goal?")) {
                        await deleteGoal(goal.id, matchId);
                        loadGoals(matchId);
                    }
                };

                listItem.appendChild(removeButton);
                homeScorersList.appendChild(listItem);
            });

            awayGoals.forEach(goal => {
                const listItem = document.createElement("li");
                listItem.className = "list-group-item d-flex justify-content-between align-items-center";

                listItem.textContent = `${goal.firstName} ${goal.lastName} (${goal.goalMinute}')`;

                const removeButton = document.createElement("button");
                removeButton.className = "btn btn-danger btn-sm";
                removeButton.textContent = "Remove";
                removeButton.onclick = async () => {
                    if (confirm("Are you sure you want to delete this goal?")) {
                        await deleteGoal(goal.id, matchId);
                        loadGoals(matchId);
                    }
                };

                listItem.appendChild(removeButton);
                awayScorersList.appendChild(listItem);
            });
        } else {
            throw new Error("Failed to load goals.");
        }
    } catch (error) {
        console.error("Error loading goals:", error);
    }
}

async function deleteGoal(goalId, matchId) {
    try {
        const response = await fetch(`/api/goals/${goalId}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (response.ok) {
            console.log("Goal deleted successfully!");
        } else {
            throw new Error("Failed to delete goal.");
        }
    } catch (error) {
        console.error("Error deleting goal:", error);
    }
}

async function deleteGoal(goalId, matchId) {
    try {
        const response = await fetch(`/api/goals/${goalId}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (response.ok) {
            console.log("Goal deleted successfully!");
        } else {
            throw new Error("Failed to delete goal.");
        }
    } catch (error) {
        console.error("Error deleting goal:", error);
    }
}

document.getElementById("addHomeGoal").addEventListener("click", () => {
    const playerId = document.getElementById("homeGoalScorerSelect").value;
    const goalMinute = document.getElementById("homeGoalMinuteInput").value;
    if (playerId) {
        addGoal(playerId, currentMatchId, goalMinute);
    } else {
        alert("Please select a player.");
    }
});

document.getElementById("addAwayGoal").addEventListener("click", () => {
    const playerId = document.getElementById("awayGoalScorerSelect").value;
    const goalMinute = document.getElementById("awayGoalMinuteInput").value;
    if (playerId) {
        addGoal(playerId, currentMatchId, goalMinute);
    } else {
        alert("Please select a player.");
    }
});
