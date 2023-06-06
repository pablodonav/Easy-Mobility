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
        clearForm();
        return false;
    }
    // Otherwise, display the correct tab:
    showTab(currentTab);
}

function checkPattern() {
    var elem = document.getElementById("academicYear");

    // Curso académico con formato ####-##
    var re = /^[0-9]{4}-[0-9]{2}$/;

    return re.test(elem.value);
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
        if ((y1[i].value === "") && !(y1[i].id === "newLanguage")) {
            // add an "invalid" class to the field:
            y1[i].className += " invalid";
            // and set the current valid status to false
            valid = false;
        }

        if ((y1[i].name === "academicYear") && (!checkPattern(y1[i].value))) {
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

/*********************************************************************************************
 *                                                                                           *
 * Funciones para crear drop down lists con los datos del estudio seleccionado por el alumno *
 *                                                                                           *
 *********************************************************************************************/
function configureDropDownUniversityList(selectedUniversity) {
    var countrySelect = document.getElementById('country-select');
    var universitySelect = document.getElementById('university-select');
    var centerSelect = document.getElementById('center-select');
    var studySelect = document.getElementById('study-select');
    var universidadesDePaisSeleccionado = [];

    universitySelect.innerHTML = "";
    createOption(universitySelect, "Selecciona tu universidad de destino", "-1", "selected", "disabled");
    centerSelect.innerHTML = "";
    createOption(centerSelect, "Selecciona tu centro de destino", "-1", "selected", "disabled");
    studySelect.innerHTML = "";
    createOption(studySelect, "Selecciona el estudio deseado", "-1", "selected", "disabled");

    for (var i = 0; i < estudiosJSON.length; i++) {
        if (estudiosJSON[i].centro.universidad.localizacion.pais === countrySelect.value) {
            universidadesDePaisSeleccionado.push(estudiosJSON[i].centro.universidad);
        }
    }

    universitySelect.options.length = 1;
    for (var i = 0; i < universidadesDePaisSeleccionado.length; i++) {
        if (selectedUniversity == universidadesDePaisSeleccionado[i].id) { //Comprueba si ya había una universidad seleccionada (útil para el caso de editar o seguir rellenando la información del formulario donde ya debería haber un valor seleccionado)
            createOption(universitySelect, universidadesDePaisSeleccionado[i].nombre, universidadesDePaisSeleccionado[i].id, "selected", "");
        } else {
            createOption(universitySelect, universidadesDePaisSeleccionado[i].nombre, universidadesDePaisSeleccionado[i].id, "", "");
        }
    }
}

function configureDropDownCenterList(selectedCenter) {
    var universitySelect = document.getElementById('university-select');
    var centerSelect = document.getElementById('center-select');
    var centrosDeUniversidadSeleccionada = [];

    for (var i = 0; i < estudiosJSON.length; i++) {
        if (estudiosJSON[i].centro.universidad.id == universitySelect.value) {
            console.log("CENTRO: " + estudiosJSON[i].centro.nombre);
            centrosDeUniversidadSeleccionada.push(estudiosJSON[i].centro);
        }
    }

    centerSelect.options.length = 1;
    for (var i = 0; i < centrosDeUniversidadSeleccionada.length; i++) {
        if (selectedCenter == centrosDeUniversidadSeleccionada[i].id.idCentro) { //Comprueba si ya había un centro seleccionado (útil para el caso de editar o seguir rellenando la información del formulario donde ya debería haber un valor seleccionado)
            createOption(centerSelect, centrosDeUniversidadSeleccionada[i].nombre, centrosDeUniversidadSeleccionada[i].id.idCentro, "selected", "");
        } else {
            createOption(centerSelect, centrosDeUniversidadSeleccionada[i].nombre, centrosDeUniversidadSeleccionada[i].id.idCentro, "", "");
        }
    }
}

function configureDropDownStudyList(selectedStudy) {
    var universitySelect = document.getElementById('university-select');
    var centerSelect = document.getElementById('center-select');
    var studySelect = document.getElementById('study-select');
    var estudiosDeCentroSeleccionado = [];

    for (var i = 0; i < estudiosJSON.length; i++) {
        if ((estudiosJSON[i].centro.id.idCentro == centerSelect.value) && (estudiosJSON[i].centro.universidad.id == universitySelect.value)) {
            console.log("ESTUDIO: " + estudiosJSON[i].nombre);
            estudiosDeCentroSeleccionado.push(estudiosJSON[i]);
        }
    }

    studySelect.options.length = 1;
    for (var i = 0; i < estudiosDeCentroSeleccionado.length; i++) {
        if (selectedStudy == estudiosDeCentroSeleccionado[i].id.idEstudio) { //Comprueba si ya había un estudio seleccionado (útil para el caso de editar o seguir rellenando la información del formulario donde ya debería haber un valor seleccionado)
            createOption(studySelect, estudiosDeCentroSeleccionado[i].nombreEstudio, estudiosDeCentroSeleccionado[i].id.idEstudio, "selected", "");
        } else {
            createOption(studySelect, estudiosDeCentroSeleccionado[i].nombreEstudio, estudiosDeCentroSeleccionado[i].id.idEstudio, "", "");
        }
    }
}

function createOption(ddl, text, value, isSelected, isDisabled) {
    var opt = document.createElement('option');
    opt.value = value;
    opt.text = text;
    opt.selected = isSelected;
    opt.disabled = isDisabled;
    ddl.options.add(opt);
}

function fillDropDownLists(selectedUniversity, selectedCenter, selectedStudy) {
    configureDropDownUniversityList(selectedUniversity);
    configureDropDownCenterList(selectedCenter);
    configureDropDownStudyList(selectedStudy);
}

/******************************************
 *                                        *
 * Funciones para añadir un nuevo idioma  *
 *                                        *
 ******************************************/
// Abre el diálogo modal
function changeFunc(id) {
    if (id == "addIdiomaModal") {
        $("#addIdiomaModal").modal('show');
    }
}


