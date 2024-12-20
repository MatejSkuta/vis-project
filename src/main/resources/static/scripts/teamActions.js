async function loadTeams(leagueId = null) {
    const endpoint = leagueId ? `/api/teams/league/${leagueId}` : "/api/teams";
    const response = await fetch(endpoint);
    if (response.ok) {
        const teams = await response.json();

        const teamSelect = document.querySelector("#playerTeam");
        if (teamSelect) {
            teamSelect.innerHTML = '<option value="">Select Team</option>';
            teams.forEach(team => {
                teamSelect.innerHTML += `<option value="${team.id}">${team.name}</option>`;
            });
        }

        const teamsTable = document.querySelector("#teamsTable tbody");
        if (teamsTable) {
            teamsTable.innerHTML = "";
            teams.forEach(team => {
                teamsTable.innerHTML += `
                    <tr>
                        <td>${team.id}</td>
                        <td>${team.name}</td>
                        <td>${team.stadiumName}</td>
                        <td>${team.foundedYear}</td>
                        ${
                            currentUser && currentUser.role === 'admin'
                                ? `<td><button onclick="deleteTeam(${team.id})" class="btn btn-danger">Delete</button></td>`
                                : ''
                        }
                    </tr>
                `;
            });
        }
    } else {
        alert("Failed to load teams.");
    }
}
