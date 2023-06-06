/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

/*******************************************************************************
 *                                                                             *
 * Funciones para crear el drop down list con la lista de prefijos de teléfono *
 *                                                                             *
 *******************************************************************************/
const phoneInputField = document.querySelector("#phone");
const phoneInput = window.intlTelInput(phoneInputField, {
    utilsScript:
            "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js"
});

function process(event) {
    event.preventDefault();

    const phoneNumber = phoneInput.getNumber();
    console.log("Estoy dentro de javascript");
    console.log("Valor de la variable" + phoneNumber);

    document.getElementById('phone').value = phoneNumber;
    document.getElementById("register-form").submit();
}

/*******************************************************************************
 *                                                                             *
 * Funciones para crear el formulario con varias etapas                        *
 *                                                                             *
 *******************************************************************************/

var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the current tab

function showTab(n) {
    // This function will display the specified tab of the form...
    var x = document.getElementsByClassName("tab");
    x[n].style.display = "block";
    //... and fix the Previous/Next buttons:
    if (n == 0) {
        document.getElementById("prevBtn").style.display = "none";
    } else {
        document.getElementById("prevBtn").style.display = "inline";
    }
    if (n == (x.length - 1)) {
        document.getElementById("nextBtn").innerHTML = "Submit";
    } else {
        document.getElementById("nextBtn").innerHTML = "Next";
    }
    //... and run a function that will display the correct step indicator:
    fixStepIndicator(n)
}

function nextPrev(n) {
    // This function will figure out which tab to display
    var x = document.getElementsByClassName("tab");

    console.log("VALOR DE N --> " + n);
    // Exit the function if any field in the current tab is invalid:
    if (n == 1 && !validateForm())
        return false;
    // Hide the current tab:
    x[currentTab].style.display = "none";
    // Increase or decrease the current tab by 1:
    currentTab = currentTab + n;
    console.log("CURRENT TAB --> " + currentTab);
    // if you have reached the end of the form...
    if (currentTab >= x.length) {
        // ... the form gets submitted:
        console.log("HACE SUBMIT");
        document.getElementById("regForm").submit();
        return false;
    }
    // Otherwise, display the correct tab:
    showTab(currentTab);
}

function checkPatternEmail() {
    var studentEmail = document.getElementById("coordinatorEmail");

    // Email con formato xx@xx.xx
    var re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    console.log("EMAIL RECIBIDO --> " + studentEmail.value);

    return re.test(studentEmail.value);
}

function validateForm() {
    // This function deals with validation of the form fields
    var x, y1, y2, i, valid = true;
    x = document.getElementsByClassName("tab");
    y1 = x[currentTab].getElementsByTagName("input");
    // A loop that checks every input field in the current tab:
    for (i = 0; i < y1.length; i++) {
        console.log("INPUT VALUE -->" + y1[i].value);
        // If a field is empty...
        if (y1[i].value === "") {
            if(y1[i].id !== "coordinatorSurname2"){
                // add an "invalid" class to the field:
                y1[i].className += " invalid";
                // and set the current valid status to false
                valid = false;   
            }
        }

        if ((y1[i].name === "coordinatorEmail") && (!checkPatternEmail())) {
            console.log("ESTOY DENTRO VERIFICAR PATTERN EMAIL");
            // add an "invalid" class to the field:
            y1[i].className += " invalid";
            // and set the current valid status to false
            valid = false;
        }
    }

    //Check if dropdown list has selected value
    y2 = x[currentTab].getElementsByTagName("select");
    for (i = 0; i < y2.length; i++) {
        console.log("DROPDOWNLIST VALUE -->" + y2[i].value);
        // If a field is empty...
        if (y2[i].value === "-1") {
            // add an "invalid" class to the field:
            y2[i].className += " invalid";
            // and set the current valid status to false
            valid = false;
        }
    }

    // If the valid status is true, mark the step as finished and valid:
    if (valid) {
        document.getElementsByClassName("step")[currentTab].className += " finish";
    }
    return valid; // return the valid status
}

function fixStepIndicator(n) {
    // This function removes the "active" class of all steps...
    var i, x = document.getElementsByClassName("step");
    for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "");
    }
    //... and adds the "active" class on the current step:
    x[n].className += " active";
}
