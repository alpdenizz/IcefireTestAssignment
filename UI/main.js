function putIntoTextArea(text) {
    document.getElementById("encrypted").value = text;
}

function buildLink(text,id) {
    var link = document.createElement("a");
    link.href = "#";
    link.id = `link${id}`;
    link.onclick = function() {
        putIntoTextArea(text);
    };
    link.innerHTML = text;
    return link;
}

function addToTable(elem) {
    var tableBody = document.getElementById("table_body");
    var size = tableBody.rows.length;
    var row = tableBody.insertRow(size);
    var c1 = row.insertCell(0);
    var c2 = row.insertCell(1);
    c1.innerHTML = elem.id;
    c2.appendChild(buildLink(elem.encrypted, size));
}

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
                c2.appendChild(buildLink(elem.encrypted, i));
            }
        });
}

function encrypt() {
    var text = document.getElementById("encrypted");
    var encrypted = text.value;
    if(encrypted.trim() === '') {
        return;
    }
    var req = `{"encrypted": "${encrypted}"}`;
    fetch('http://localhost:8080/encrypt', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: req
    }).then((resp) => resp.json())
    .then((body) => {
        addToTable(body);
    })
}

function decrypt() {
    var text = document.getElementById("encrypted");
    var encrypted = text.value;
    if(encrypted.trim() === '') {
        return;
    }
    var url = `http://localhost:8080/decrypt?text=${encrypted}`;
    fetch(url)
    .then((r) => r.json())
    .then((body) => {
        console.log(body);
        putIntoTextArea(body.encrypted);
    });
}