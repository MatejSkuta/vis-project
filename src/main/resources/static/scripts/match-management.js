

document.addEventListener("DOMContentLoaded", () => {
    loadLeagues();

    document.getElementById("loadMatches").addEventListener("click", async () => {
        const leagueId = document.getElementById("leagueSelect").value;
        if (leagueId) {
            loadMatchesByLeague(leagueId);

            const createMatchButton = document.getElementById("createMatch");
            createMatchButton.addEventListener("click", () => {
                window.location.href = `/addMatch?leagueId=${leagueId}`;
            });
        } else {
            alert("Please select a league.");
        }
    });
});

async function loadLeagues() {
    try {
        const response = await fetch("/api/leagues");
        if (response.ok) {
            const leagues = await response.json();
            const leagueSelect = document.getElementById("leagueSelect");
            leagueSelect.innerHTML = "";
            leagues.forEach(league => {
                const option = document.createElement("option");
                option.value = league.id;
                option.textContent = league.name;
                leagueSelect.appendChild(option);
            });
        } else {
            console.error("Failed to load leagues.");
        }
    } catch (error) {
        console.error("Error loading leagues:", error);
    }
}

async function loadMatchesByLeague(leagueId) {
    try {
        const response = await fetch(`/api/matches/league/${leagueId}`);
        if (response.ok) {
            const matches = await response.json();
            const matchesTableBody = document.getElementById("matchesTableBody");
            matchesTableBody.innerHTML = "";
            matches.forEach(match => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${new Date(match.matchDate).toLocaleDateString()}</td>
                    <td>${match.homeTeamName}</td>
                    <td>${match.awayTeamName}</td>
                    <td>${match.homeTeamScore} - ${match.awayTeamScore}</td>
                    ${
                        currentRole === 'admin' || currentRole==='referee'
                        ? `<td><a class="btn btn-primary" href="/${match.id}/details" th:>Manage</a></td>`
                        : ''
                    }
                `;
                matchesTableBody.appendChild(row);
            });
            document.getElementById("matches").style.display = "block";
        } else {
            console.error("Failed to load matches.");
        }
    } catch (error) {
        console.error("Error loading matches:", error);
    }
}

const currentRole = document.getElementById("currentUsername").innerText;