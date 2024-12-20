 document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const leagueId = urlParams.get("leagueId");
    if (leagueId) {
        loadTeamsByLeague(leagueId);
    } else {
        alert("League ID is missing in the URL!");
    }

    loadReferees();

    document.getElementById("addMatchForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const matchDate = document.getElementById("matchDate").value;
        const homeTeamId = document.getElementById("homeTeamSelect").value;
        const awayTeamId = document.getElementById("awayTeamSelect").value;
        const refereeId = document.getElementById("refereeSelect").value;

        if (homeTeamId === awayTeamId) {
            alert("Home and Away teams cannot be the same!");
            return;
        }

        try {
            const response = await fetch("/api/matches", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    matchDate,
                    leagueId,
                    homeTeamId,
                    awayTeamId,
                    refereeId
                })
            });

            if (response.ok) {
                alert("Match created successfully!");
                window.location.href = "/leagues";
            } else {
                const errorText = await response.text();
            alert(`Failed to create match: ${errorText}`);
            }
        } catch (error) {
            console.error("Error creating match:", error);
            alert("An error occurred. Please try again.");
        }
    });
});


async function loadReferees() {
    try {
        const response = await fetch("/api/referees");
        if (response.ok) {
            const referees = await response.json();
            console.log("Loaded referees:", referees);
            const refereeSelect = document.getElementById("refereeSelect");
            referees.forEach(referee => {
                const option = document.createElement("option");
                option.value = referee.id;

                option.textContent = referee.name || `${referee.firstName} ${referee.lastName}`;
                refereeSelect.appendChild(option);
            });
        } else {
            console.error("Failed to load referees.");
        }
    } catch (error) {
        console.error("Error loading referees:", error);
    }
}


async function loadTeamsByLeague(leagueId) {
    try {
        console.log(`Loading teams for league ID: ${leagueId}`); // Debug log
        const response = await fetch(`/api/teams/league/${leagueId}`);
        console.log(`Response status: ${response.status}`); // Debug log
        if (response.ok) {
            const teams = await response.json();
            console.log("Loaded teams:", teams); // Debug log
            const homeTeamSelect = document.getElementById("homeTeamSelect");
            const awayTeamSelect = document.getElementById("awayTeamSelect");

            homeTeamSelect.innerHTML = "";
            awayTeamSelect.innerHTML = "";

            teams.forEach(team => {
                const homeOption = document.createElement("option");
                homeOption.value = team.id;
                homeOption.textContent = team.name;
                homeTeamSelect.appendChild(homeOption);

                const awayOption = document.createElement("option");
                awayOption.value = team.id;
                awayOption.textContent = team.name;
                awayTeamSelect.appendChild(awayOption);
            });
        } else {
            console.error("Failed to load teams for league:", leagueId);
        }
    } catch (error) {
        console.error("Error loading teams for league:", error);
    }
}