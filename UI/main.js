function getAllValues() {
    fetch('http://localhost:8080/') 
        .then((resp) => resp.json())
        .then(function(data) {
            var tableBody = document.getElementById("table_body");
            for(var i=0; i<data.length; i++) {
                var elem = data[i];
                var row = tableBody.insertRow(i);
                var c1 = row.insertCell(0);
                var c2 = row.insertCell(1);
                c1.innerHTML = elem.id;
                c2.innerHTML = elem.encrypted;
            }
        });
}