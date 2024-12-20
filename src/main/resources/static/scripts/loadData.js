async function loadLeagues() {
    const leagueSelectors = [
        document.querySelector("#teamLeague"),
        document.querySelector("#leagueFilter"),
        document.querySelector("#leaguePlayerFilter")
    ];

    if (!leagueSelectors.some(selector => selector)) return;

    try {
        const response = await fetch("/api/leagues");
        if (response.ok) {
            const leagues = await response.json();
            leagueSelectors.forEach(selector => {
                if (selector) {
                    selector.innerHTML = '<option value="">Select League</option>';
                    leagues.forEach(league => {
                        selector.innerHTML += `<option value="${league.id}">${league.name}</option>`;
                    });
                }
            });
        } else {
            console.error("Failed to load leagues:", response.status);
            alert("Failed to load leagues.");
        }
    } catch (error) {
        console.error("Error fetching leagues:", error);
        alert("An error occurred while loading leagues.");
    }
}

function populatePlayersTable(players) {
    const playersTable = document.querySelector("#playersTable tbody");
    if (playersTable) {
        playersTable.innerHTML = "";

        players.forEach(player => {
            playersTable.innerHTML += `
                <tr>
                    <td>${player.id}</td>
                    <td>${player.firstName}</td>
                    <td>${player.lastName}</td>
                    <td>${player.position}</td>
                    <td>${player.teamId}</td>
                    ${
                        currentUser && currentUser.role === 'admin'
                            ? `<td><button onclick="deletePlayer(${player.id})" class="btn btn-danger">Delete</button></td>`
                            : '<td></td>'
                    }
                </tr>
            `;
        });
    }
}


async function loadTeamsPlayers(leagueId = null, teamId = null) {
    let endpoint = "/api/players";
    if (teamId) {
        endpoint = `/api/players/team/${teamId}`;
    } else if (leagueId) {
        endpoint = `/api/players/league/${leagueId}`;
    }

    const response = await fetch(endpoint);
    if (response.ok) {
        const players = await response.json();
        populatePlayersTable(players);
    } else {
        alert("Failed to load players.");
    }
}

async function loadTeamsForPlayersFilter(leagueId) {
    const teamPlayerFilter = document.querySelector("#teamPlayerFilter");
    if (!teamPlayerFilter) return;

    try {
        const response = await fetch(`/api/teams/league/${leagueId}`);
        if (response.ok) {
            const teams = await response.json();

            teamPlayerFilter.innerHTML = '<option value="">All Teams</option>';
            teams.forEach(team => {
                teamPlayerFilter.innerHTML += `<option value="${team.id}">${team.name}</option>`;
            });
        } else {
            console.error(`Failed to load teams for league ${leagueId}:`, response.status);
            alert("Failed to load teams for the selected league.");
        }
    } catch (error) {
        console.error("Error loading teams for players filter:", error);
        alert("An error occurred while loading teams for players filter.");
    }
}
