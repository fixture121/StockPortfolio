// function getApiStock(ticker) {
//     if (document.getElementsByName("apiStock" + ticker).innerHTML == "") {
//         fetch('http://localhost:8080/getStock/' + ticker)
//             .then(response => response.json())
//             .then(function (apiStock) {
//                 var textToDisplay = "";
//                 textToDisplay += "Ticker: " + apiStock.ticker + "<br>";
//                 textToDisplay += "Company: " + apiStock.company + "<br>";
//                 textToDisplay += "Price: " + apiStock.price + "<br>";
//
//                 document.getElementsByName("apiStock" + ticker).innerHTML = textToDisplay;
//             });
//     } else {
//         document.getElementsByName("apiStock" + ticker).innerHTML = "";
//     }
// }

function getApiStock(ticker) {
    var element = document.getElementsByName("apiStock" + ticker)[0];

    if (element.innerHTML === "") {
        fetch('http://localhost:8080/getStock/' + ticker)
            .then(response => response.json())
            .then(function (apiStock) {
                var textToDisplay = "";
                textToDisplay += "Ticker: " + apiStock.ticker + "<br>";
                textToDisplay += "Company: " + apiStock.company + "<br>";
                textToDisplay += "Price: " + apiStock.price + "<br>";

                element.innerHTML = textToDisplay;
            });
    } else {
        element.innerHTML = "";
    }
}
