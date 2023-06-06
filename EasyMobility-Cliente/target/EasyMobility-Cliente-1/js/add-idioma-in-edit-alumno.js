// Enviar solicitud post a servlet para añadir el nuevo idioma guardando el id del alumno editado
var modal = document.getElementById("addIdiomaModal");
var addLanguageOption = document.getElementById("add-language-option");
var idAlumnoEditado = document.getElementById("studentIdentification").value;

addLanguageOption.onclick = function () {
    modal.style.display = "block";
};

// Añade el idioma y redirecciona a editarAlumno.jsp
var addLanguageButtonInEditarAlumno = document.getElementById("addIdiomaButtonInEditarAlumno");
addLanguageButtonInEditarAlumno.onclick = function () {

    var languageName = document.getElementById("newLanguage").value;

    // Crear la solicitud AJAX para agregar el nuevo idioma
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "addIdioma");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Si la solicitud se realizó correctamente, redirigir de vuelta a la página principal, manteniendo los valores del formulario principal
                window.location.href = "editarAlumno.jsp?numIdentificacionAlumnoEditar=" + idAlumnoEditado;
            }
        }
    };
    xhr.send("languageName=" + languageName);
};
