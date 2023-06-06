/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// Obtener el formulario y los campos
const myForm = document.getElementById("regForm");
const nameField = document.getElementById("studentName");
const surname1Field = document.getElementById("studentSurname1");
const surname2Field = document.getElementById("studentSurname2");
const emailField = document.getElementById("studentEmail");
const passwordField = document.getElementById("studentPassword");
const phoneField = document.getElementById("phone");
const identificationTypeField = document.getElementById("identificationType");
const identificationField = document.getElementById("studentIdentification");
const countryField = document.getElementById("country-select");
const universityField = document.getElementById("university-select");
const centerField = document.getElementById("center-select");
const studyField = document.getElementById("study-select");
const academicYearField = document.getElementById("academicYear");
const exchangePeriodField = document.getElementById("exchangePeriod");

// Obtener los valores de los campos y guardarlos en el LocalStorage
function saveForm() {
    localStorage.setItem('name', nameField.value);
    localStorage.setItem('surname1', surname1Field.value);
    localStorage.setItem('surname2', surname2Field.value);
    localStorage.setItem('email', emailField.value);
    localStorage.setItem('password', passwordField.value);
    localStorage.setItem('phone', phoneField.value);
    localStorage.setItem('identificationType', identificationTypeField.value);
    localStorage.setItem('identification', identificationField.value);
    localStorage.setItem('country', countryField.value);
    localStorage.setItem('university', universityField.value);
    localStorage.setItem('center', centerField.value);
    localStorage.setItem('study', studyField.value);
    localStorage.setItem('academicYear', academicYearField.value);
    localStorage.setItem('exchangePeriod', exchangePeriodField.value);

    console.log("ESTUDIO GUARDADO --> " + localStorage.getItem('study'));
}

// Limpiar valores guardados en el LocalStorage
function clearForm() {
    localStorage.setItem('name', "");
    localStorage.setItem('surname1', "");
    localStorage.setItem('surname2', "");
    localStorage.setItem('email', "");
    localStorage.setItem('password', "");
    localStorage.setItem('phone', "");
    localStorage.setItem('identificationType', "");
    localStorage.setItem('identification', "");
    localStorage.setItem('country', "");
    localStorage.setItem('university', "");
    localStorage.setItem('center', "");
    localStorage.setItem('study', "");
    localStorage.setItem('academicYear', "");
    localStorage.setItem('exchangePeriod', "");
}

// Cargar los valores guardados en el LocalStorage en los campos del formulario
function loadForm() {
    console.log("ESTUDIO GUARDADO --> " + localStorage.getItem('study'));
    const name = localStorage.getItem('name');
    const surname1 = localStorage.getItem('surname1');
    const surname2 = localStorage.getItem('surname2');
    const email = localStorage.getItem('email');
    const password = localStorage.getItem('password');
    const phone = localStorage.getItem('phone');
    const identificationType = localStorage.getItem('identificationType');
    const identification = localStorage.getItem('identification');
    const country = localStorage.getItem('country');
    const university = localStorage.getItem('university');
    const center = localStorage.getItem('center');
    const study = localStorage.getItem('study');
    const academicYear = localStorage.getItem('academicYear');
    const exchangePeriod = localStorage.getItem('exchangePeriod');

    if (name) {
        nameField.value = name;
    }

    if (surname1) {
        surname1Field.value = surname1;
    }

    if (surname2) {
        surname2Field.value = surname2;
    }

    if (email) {
        emailField.value = email;
    }

    if (password) {
        passwordField.value = password;
    }

    if (phone) {
        phoneField.value = phone;
    }

    if (identificationType) {
        identificationTypeField.value = identificationType;
    }

    if (identification) {
        identificationField.value = identification;
    }

    if (country) {
        countryField.value = country;
    }

    if (university) {
        for (var i = 0; i < estudiosJSON.length; i++) {
            if (estudiosJSON[i].centro.universidad.id == university) {
                createOption(universityField, estudiosJSON[i].centro.universidad.nombre, estudiosJSON[i].centro.universidad.id, "selected", "");
            } else if (estudiosJSON[i].centro.universidad.localizacion.pais === country) { //Mostrar solamente universidades del país seleccionado
                createOption(universityField, estudiosJSON[i].centro.universidad.nombre, estudiosJSON[i].centro.universidad.id, "", "");
            }
        }
    }

    if (center) {
        for (var i = 0; i < estudiosJSON.length; i++) {
            if (estudiosJSON[i].centro.id.idCentro == center) {
                createOption(centerField, estudiosJSON[i].centro.nombre, estudiosJSON[i].centro.id.idCentro, "selected", "");
            } else if (estudiosJSON[i].centro.universidad.id == universidad) { //Mostrar solamente centros de la universidad seleccionada
                createOption(centerField, estudiosJSON[i].centro.nombre, estudiosJSON[i].centro.id.idCentro, "", "");
            }
        }
    }

    if (study) {
        for (var i = 0; i < estudiosJSON.length; i++) {
            if (estudiosJSON[i].id.idEstudio == study) {
                createOption(studyField, estudiosJSON[i].nombreEstudio, estudiosJSON[i].id.idEstudio, "selected", "");
            } else if ((estudiosJSON[i].centro.id.idCentro == center) && (estudiosJSON[i].centro.universidad.id == university)) { //Mostrar solamente estudios del centro y universidad seleccionados
                createOption(studyField, estudiosJSON[i].nombreEstudio, estudiosJSON[i].id.idEstudio, "", "");
            }
        }
    }

    if (academicYear) {
        academicYearField.value = academicYear;
    }

    if (exchangePeriod) {
        exchangePeriodField.value = exchangePeriod;
    }
}

// Cargar los valores guardados en el LocalStorage al cargar la página
loadForm();

// Enviar solicitud post a servlet para añadir el nuevo idioma guardando los campos del formulario rellenados
var modal = document.getElementById("addIdiomaModal");
var addLanguageOption = document.getElementById("add-language-option");

addLanguageOption.onclick = function () {
    modal.style.display = "block";

    // Guardar los valores del formulario principal antes de abrir el diálogo modal
    saveForm();
};

// Añade el idioma y redirecciona a nuevoAlumno.jsp
var addLanguageButtonInNuevoAlumno = document.getElementById("addIdiomaButtonInNuevoAlumno");
addLanguageButtonInNuevoAlumno.onclick = function () {

    var languageName = document.getElementById("newLanguage").value;

    // Crear la solicitud AJAX para agregar el nuevo idioma
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "addIdioma");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Si la solicitud se realizó correctamente, redirigir de vuelta a la página principal, manteniendo los valores del formulario principal
                window.location.href = "nuevoAlumno.jsp";
            }
        }
    };
    xhr.send("languageName=" + languageName);
};