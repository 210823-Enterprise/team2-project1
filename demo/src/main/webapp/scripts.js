var spinner;
var currencyF;
window.onload = function () {
    spinner = document.getElementById("spinner");
    currencyF = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });   
    findAll();
}
function operationSelection(str) {
    switch (str) {
        case "insert":
            document.getElementById("command").placeholder = "Name, Owner, Balance";
            break;
        case "update":
            document.getElementById("command").placeholder = "ID, Name, Owner, Balance";
            break;
        case "delete":
            document.getElementById("command").placeholder = "ID";
            break;
    }
}
function getCommandLine() {
    let field = document.getElementById("command");
    let str = field.value;
    field.value = '';
    return str;
}
function makeRequest() {
    spinner.style.visibility = 'visible';
    let str = document.getElementById("selection").value;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            findAll();
        }
    }
    switch (str) {
        case "insert":
            const insertInput = getCommandLine().split(",");
            xhttp.open("POST", "http://localhost:8080/T2-Demo/insertAccount", true);
            xhttp.setRequestHeader('Content-Type', 'application/json');
            let insertedAccount = {
                accountName: insertInput[0],
                ownerId: insertInput[1],
                balance: insertInput[2]
            };
            xhttp.send(JSON.stringify(insertedAccount));
            break;
        case "update":
            const updateInput = getCommandLine().split(",");
            xhttp.open("PUT", "http://localhost:8080/T2-Demo/updateAccount", true);
            xhttp.setRequestHeader('Content-Type', 'application/json');
            let updatedAccount = {
                id: updateInput[0],
                accountName: updateInput[1],
                ownerId: updateInput[2],
                balance: updateInput[3]
            };
            xhttp.send(JSON.stringify(updatedAccount));
            break;
        case "delete":
            let a_id = getCommandLine();
            xhttp.open("DELETE", "http://localhost:8080/T2-Demo/deleteAccount?id=" + a_id, true);
            xhttp.send();
            break;
    }
}
function findAll() {

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                let content = "";
                let array = JSON.parse(this.responseText);
                array.forEach(account => {
                    content += `<tr>
                    <th scope="row">${account.id}</th>
                    <td>${account.accountName}</td>
                    <td>${account.ownerId}</td>
                    <td>${currencyF.format(account.balance)}</td>
                  </tr>`;
                });
                document.getElementById("accountsTable").innerHTML = content;
            }
            spinner.style.visibility = 'hidden';
        }
    }
    xhttp.open("GET", "http://localhost:8080/T2-Demo/findAll", true);
    xhttp.send();
}
