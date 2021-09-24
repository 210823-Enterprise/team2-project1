window.onload = function(){
    findAll();
}
function operationSelection(str)
{
    switch(str)
    {
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
function getCommandLine()
{
    let field = document.getElementById("command");
    let str = field.value;
    field.value = '';
    return str;
}
function findAll() {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            let content = "";
            let array = JSON.parse(this.responseText);
            array.forEach(account => {
                content += `<tr>
                <th scope="row">${account.id}</th>
                <td>${account.accountName}</td>
                <td>${account.ownerId}</td>
                <td>${account.balance}</td>
              </tr>`;
            });
            document.getElementById("accountsTable").innerHTML = content;
        }
    }
    xhttp.open("GET", "http://localhost:8080/T2-Demo/findAll", true);
    xhttp.send();
}
function insertAccount() {

    const arr = getCommandLine().split(",");
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            findAll();
        }
    }
    xhttp.open("POST","http://localhost:8080/T2-Demo/insertAccount",true);
    xhttp.setRequestHeader('Content-Type','application/json');

    let account = {
        accountName: arr[0],
        ownerId: arr[1],
        balance: arr[2]
    };
    
    xhttp.send(JSON.stringify(account));
}
function updateAccount()
{
    const arr = getCommandLine().split(",");
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            findAll();
        }
    }
    xhttp.open("PUT","http://localhost:8080/T2-Demo/updateAccount",true);
    xhttp.setRequestHeader('Content-Type','application/json');

    let account = {
        id: arr[0],
        accountName: arr[1],
        ownerId: arr[2],
        balance: arr[3]
    };  
    xhttp.send(JSON.stringify(account));
}
function deleteAccount()
{
    let a_id = getCommandLine();
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            findAll();
        }
    }
    xhttp.open("DELETE","http://localhost:8080/T2-Demo/deleteAccount?id=" + a_id,true);   
    xhttp.send();
}
