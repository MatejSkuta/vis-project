 document.querySelector("#exportMatches").addEventListener("click", () => {
        const matchesTable = document.querySelector("#matchesTableBody");
        if (!matchesTable) return;

        const rows = matchesTable.querySelectorAll("tr");
        const csvData = [];

        csvData.push(["Date", "Home Team", "Away Team", "Score"].join(","));

        rows.forEach(row => {
                const cells = row.querySelectorAll("td");
                const rowData = Array.from(cells)
                    .filter((_, index) => [0, 1, 2, 3].includes(index))
                    .map(cell => cell.innerText.trim());
                csvData.push(rowData.join(","));
            });

        const csvContent = "\uFEFF" + csvData.join("\n");

        const encodedUri = encodeURI("data:text/csv;charset=utf-8," + csvContent);
        const link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "my_matches.csv");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    });