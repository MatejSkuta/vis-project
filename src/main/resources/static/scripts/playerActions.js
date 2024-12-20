async function loadPlayers() {
    const response = await fetch("/api/players");
    if (response.ok) {
        const players = await response.json();
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
                                : ''
                        }
                    </tr>
                `;
            });
        }
    } else {
        alert("Failed to load players.");
    }
}


async function deletePlayer(id) {
    const response = await fetch(`/api/players/${id}`, { method: "DELETE" });

    if (response.ok) {
        alert("Player deleted successfully!");
        loadPlayers();
    } else {
        alert("Failed to delete player.");
    }
}