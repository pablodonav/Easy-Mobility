
/*******************************************************************************
 *                WEBSOCKETS FUNCTIONS AND VARIABLES                           *
 *******************************************************************************/
var webSocket;

openSocket();

// Crea el socket y gestiona los mensajes recibidos del servidor
function openSocket() {
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        closeSocket();
        console.log("WEBSOCKET CLOSED");
    }

    webSocket = new WebSocket(wsUri);
    console.log("Abriendo socket => ");


    // eventos del websocket ====

    webSocket.onopen = function (event) {
        if (event.data === undefined)
            return;
    };

    webSocket.onmessage = function (event) {
        var msg = JSON.parse(event.data); //Parsear string a JSON

        console.log("onMessage: " + event.data);

        switch (msg.cmd) {
            case "new-alumno":
                var jsonNewAlumnoData = JSON.parse(msg.json);
                var coordinadorId = jsonNewAlumnoData.coordinadorDestinoId;

                // Añaden al nuevo alumno a los coordinadores que van a gestionar a dicho alumno
                if (coordinadorId === userId) {
                    addNewAlumnoToTable(jsonNewAlumnoData);
                }
                break;

            case "remove-alumno":
                var jsonAlumnoRemovedData = JSON.parse(msg.json);
                var studentRemovedId = jsonAlumnoRemovedData.alumnoEliminadoId;
                var coordinadorOrigenId = jsonAlumnoRemovedData.coordinadorOrigenId;
                var coordinadorDestinoId = jsonAlumnoRemovedData.coordinadorDestinoId;

                // Elimina el alumno de los coordinadores que lo gestionan
                if ((userId === coordinadorOrigenId) || (userId === coordinadorDestinoId)) {
                    removeStudentFromTable(studentRemovedId);
                }
                break;

            case "edit-alumno":
                var jsonAlumnoEditedData = JSON.parse(msg.json);
                var coordinadorOrigenId = jsonAlumnoEditedData.coordinadorOrigenId;
                var coordinadorDestinoId = jsonAlumnoEditedData.coordinadorDestinoId;
                var coordinadorDestinoIdBeforeUpdate = jsonAlumnoEditedData.coordinadorDestinoIdBeforeUpdate;
                var studentId = jsonAlumnoEditedData.alumno.numId;

                // Verifica si se ha modificado el estudio de destino de un alumno a partir de sus coordinadores. 
                // Para el coordinador del destino almacenado antes de la edición se procederá a borrar al alumno de la tabla.
                // Para el coordinador del destino almacenado después de la edición se procederá a insertar al alumna a la tabla.
                if ((coordinadorDestinoIdBeforeUpdate !== coordinadorDestinoId) && (userId === coordinadorDestinoIdBeforeUpdate)) {
                    removeStudentFromTable(studentId);
                } else if ((coordinadorDestinoIdBeforeUpdate !== coordinadorDestinoId) && (userId === coordinadorDestinoId)) {
                    addNewAlumnoToTable(jsonAlumnoEditedData);
                }

                // Edita el alumno de los coordinadores que lo gestionan
                if ((userId === coordinadorOrigenId) || (userId === coordinadorDestinoId)) {
                    editStudentFromTable(jsonAlumnoEditedData);
                }

                break;

            case "new-file":
                var jsonNewFileData = JSON.parse(msg.json);
                var studentId = jsonNewFileData.alumnoId;
                var coordinadorOrigenId = jsonNewFileData.coordinadorOrigenId;
                var coordinadorDestinoId = jsonNewFileData.coordinadorDestinoId;

                // Añade el nuevo fichero a los coordinadores que gestionan al alumno propietario del fichero y al propio alumno
                if ((userId === coordinadorOrigenId) || (userId === coordinadorDestinoId) || (userId === studentId)) {
                    addNewFileToTable(jsonNewFileData);
                }
                break;

            case "remove-file":
                var jsonFileRemovesData = JSON.parse(msg.json);
                var studentId = jsonFileRemovesData.alumnoId;
                var coordinadorOrigenId = jsonFileRemovesData.coordinadorOrigenId;
                var coordinadorDestinoId = jsonFileRemovesData.coordinadorDestinoId;
                var fileRemovedId = jsonFileRemovesData.ficheroEliminadoId;

                // Elimnia el fichero de los coordinadores que gestionan al alumno propietario del fichero y del propio alumno
                if ((userId === coordinadorOrigenId) || (userId === coordinadorDestinoId) || (userId === studentId)) {
                    removeFileFromTable(fileRemovedId);
                }
                break;
        }
    };

    webSocket.onclose = function (event) {
    };

    webSocket.onerror = function (event) {
    };
} //openSocket

// Envía mensajes al servidor
function enviarMensaje(_userId) {
    var msg = '{"cmd": "newUser", "userId": "' + _userId + '"}';
    webSocket.send(msg);
    console.log("TX: " + msg);
}

// Cierra el socket creado
function closeSocket() {
    webSocket.close();
}

/*******************************************************************************
 *                    NEW STUDENT MANAGEMENT                                   *
 *******************************************************************************/

// Creación e inserción de una nueva fila con información de un nuevo alumno en la tabla con alumnos 
function addNewAlumnoToTable(_newAlumnoJson) {
    const tableAlumnos = document.getElementById("tabla-alumnos");

    // Comprueba si la tabla está vacía y elimina el mensaje informativo
    const informativeRow = tableAlumnos.rows[1];
    const contenidoFirstRow = informativeRow.cells[0].textContent;
    if (contenidoFirstRow.trim() === "No data available in table") {
        informativeRow.remove(); // Elimina fila con mensaje informativo
    }

    const fila = document.createElement("tr");

    const celdaDocumentoIdentificacion = document.createElement("td");
    const celdaNumIdentificacion = document.createElement("td");
    const celdaCursoAcademico = document.createElement("td");
    const celdaAreaEstudio = document.createElement("td");
    const celdaTipoAcuerdo = document.createElement("td");
    const celdaIdioma = document.createElement("td");
    const celdaPeriodoIntercambio = document.createElement("td");
    const celdaPaisDestino = document.createElement("td");
    const celdaUniversidadDestino = document.createElement("td");
    const celdaOperaciones = document.createElement("td");
    const operacionesButtonsRow = document.createElement("div");
    operacionesButtonsRow.classList.add("row");

    celdaDocumentoIdentificacion.textContent = _newAlumnoJson.alumno.tipoId;
    celdaNumIdentificacion.textContent = _newAlumnoJson.alumno.numId;
    celdaCursoAcademico.textContent = _newAlumnoJson.acuerdo.cursoAcademico;
    celdaAreaEstudio.textContent = _newAlumnoJson.acuerdo.areaEstudio;
    celdaTipoAcuerdo.textContent = _newAlumnoJson.acuerdo.tipoEstudio;
    celdaIdioma.textContent = _newAlumnoJson.acuerdo.idioma;
    celdaPeriodoIntercambio.textContent = _newAlumnoJson.acuerdo.periodoIntercambio;
    celdaPaisDestino.textContent = _newAlumnoJson.acuerdo.paisAcuerdo;
    celdaUniversidadDestino.textContent = _newAlumnoJson.acuerdo.universidadAcuerdo;

    fila.appendChild(celdaDocumentoIdentificacion);
    fila.appendChild(celdaNumIdentificacion);
    fila.appendChild(celdaCursoAcademico);
    fila.appendChild(celdaAreaEstudio);
    fila.appendChild(celdaTipoAcuerdo);
    fila.appendChild(celdaIdioma);
    fila.appendChild(celdaPeriodoIntercambio);
    fila.appendChild(celdaPaisDestino);
    fila.appendChild(celdaUniversidadDestino);
    createButtonVer(operacionesButtonsRow, _newAlumnoJson.alumno.numId);
    createButtonEditar(operacionesButtonsRow, _newAlumnoJson.alumno.numId);
    createButtonBorrar(operacionesButtonsRow, _newAlumnoJson.alumno.numId);
    celdaOperaciones.appendChild(operacionesButtonsRow);
    fila.appendChild(celdaOperaciones);

    tableAlumnos.querySelector("tbody").appendChild(fila);
}

// Creación del botón ver que representa la operación ver documentación de un alumno   
function createButtonVer(_operacionesButtonsRow, _alumnoId) {
    const verButtonCol = document.createElement("div");
    verButtonCol.classList.add("col", "col-lg-2");
    const verButtonForm = document.createElement("form");

    verButtonForm.action = "verDocumentacionAlumno.jsp";
    verButtonForm.method = "POST";
    verButtonForm.title = "View";
    verButtonForm.dataset.toggle = "tooltip";

    const numIdentificacionUsuario = document.createElement("input");
    numIdentificacionUsuario.type = "hidden";
    numIdentificacionUsuario.name = "numIdentificacionAlumnoFichero";
    numIdentificacionUsuario.value = _alumnoId;

    const verButton = document.createElement("button");
    verButton.style.background = "none";
    verButton.style.border = "none";
    verButton.style.color = "#03A9F4";

    const svgCode = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-fill" viewBox="0 0 16 16">' +
            '<path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>' +
            '<path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>' +
            '</svg>';
    const verButtonIcon = document.createElement("div");
    verButtonIcon.innerHTML = svgCode;

    verButton.appendChild(verButtonIcon);
    verButtonForm.appendChild(numIdentificacionUsuario);
    verButtonForm.appendChild(verButton);
    verButtonCol.appendChild(verButtonForm);

    _operacionesButtonsRow.appendChild(verButtonCol);
}

// Creación del botón editar que representa la operación editar información de un alumno 
function createButtonEditar(_operacionesButtonsRow, _alumnoId) {
    const editarButtonCol = document.createElement("div");
    editarButtonCol.classList.add("col", "col-lg-2");
    const editarButtonForm = document.createElement("form");

    editarButtonForm.action = "editarAlumno.jsp";
    editarButtonForm.method = "POST";
    editarButtonForm.title = "Edit";
    editarButtonForm.dataset.toggle = "tooltip";

    const numIdentificacionUsuario = document.createElement("input");
    numIdentificacionUsuario.type = "hidden";
    numIdentificacionUsuario.name = "numIdentificacionAlumnoEditar";
    numIdentificacionUsuario.value = _alumnoId;

    const editarButton = document.createElement("button");
    editarButton.style.background = "none";
    editarButton.style.border = "none";
    editarButton.style.color = "#FFC107";
    editarButton.type = "submit";

    const svgCode = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">' +
            '<path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 \n\
                        6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 \n\
                        .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 \n\
                        1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>' +
            '</svg>';
    const editarButtonIcon = document.createElement("div");
    editarButtonIcon.innerHTML = svgCode;

    editarButton.appendChild(editarButtonIcon);
    editarButtonForm.appendChild(numIdentificacionUsuario);
    editarButtonForm.appendChild(editarButton);
    editarButtonCol.appendChild(editarButtonForm);

    _operacionesButtonsRow.appendChild(editarButtonCol);
}

// Creación del botón borrar que representa la operación borrar un alumno 
function createButtonBorrar(_operacionesButtonsRow, _alumnoId) {
    const borrarButtonCol = document.createElement("div");
    borrarButtonCol.classList.add("col", "col-lg-2");
    const borrarButtonForm = document.createElement("form");

    borrarButtonForm.action = "deleteAlumno";
    borrarButtonForm.method = "POST";
    borrarButtonForm.title = "Delete";
    borrarButtonForm.dataset.toggle = "tooltip";


    const numIdentificacionUsuario = document.createElement("input");
    numIdentificacionUsuario.type = "hidden";
    numIdentificacionUsuario.name = "numIdentificacionAlumnoBorrar";
    numIdentificacionUsuario.value = _alumnoId;

    const borrarButton = document.createElement("button");
    borrarButton.style.background = "none";
    borrarButton.style.border = "none";
    borrarButton.style.color = "#E34724";
    borrarButton.type = "button";
    borrarButton.setAttribute("data-bs-toggle", "modal");
    borrarButton.setAttribute("data-bs-target", `#removeAlumnoModal-${_alumnoId}`);
    borrarButton.setAttribute("data-id", _alumnoId);

    const svgCode = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">' +
            '<path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 \n\
                        1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 \n\
                        5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>' +
            '</svg>';
    const borrarButtonIcon = document.createElement("div");
    borrarButtonIcon.innerHTML = svgCode;

    const modalDialogCode =
            `<div class="modal" id="removeAlumnoModal-${_alumnoId}" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title"><b>Confirmación borrado</b></h5>
                            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group row">
                                        <p>¿Desea eliminar el alumno con número de identificación ${_alumnoId}?</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-primary" id="removeAlumnoBtn" style="background-color:#4B49AC;">Borrar</button>
                        </div>
                    </div>
                </div>
            </div>`;
    const borrarButtonModalDialog = document.createElement("div");
    borrarButtonModalDialog.innerHTML = modalDialogCode;

    borrarButton.appendChild(borrarButtonIcon);
    borrarButtonForm.appendChild(numIdentificacionUsuario);
    borrarButtonForm.appendChild(borrarButton);
    borrarButtonForm.appendChild(borrarButtonModalDialog);
    borrarButtonCol.appendChild(borrarButtonForm);

    _operacionesButtonsRow.appendChild(borrarButtonCol);
}

/*******************************************************************************
 *                    STUDENT REMOVED MANAGEMENT                               *
 *******************************************************************************/

// Eliminación de la fila que posee los datos de un alumno eliminado
function removeStudentFromTable(_studentId) {
    const tableAlumnos = document.getElementById("tabla-alumnos");

    //Almacena el índice de la segunda columna con el id de los alumnos
    const studentIdColIndex = 1;

    for (let i = 0; i < tableAlumnos.rows.length; i++) {
        const tableAlumnosRow = tableAlumnos.rows[i]; //Almacena cada fila
        const studentIdValue = tableAlumnosRow.cells[studentIdColIndex].textContent; //Almacena id del estudiante de la fila correspondiente

        if (studentIdValue === _studentId) {
            tableAlumnosRow.remove(); // Elimina fila si coinciden los ids
        }
    }

    // Si la tabla se queda vacía muestra el mensaje informativo
    if (tableAlumnos.rows.length == 1) {
        const fila = document.createElement("tr");

        const celdaDocumentoIdentificacion = document.createElement("td");
        const celdaNumIdentificacion = document.createElement("td");
        const celdaCursoAcademico = document.createElement("td");
        const celdaAreaEstudio = document.createElement("td");
        const celdaTipoAcuerdo = document.createElement("td");
        const celdaIdioma = document.createElement("td");
        const celdaPeriodoIntercambio = document.createElement("td");
        const celdaPaisDestino = document.createElement("td");
        const celdaUniversidadDestino = document.createElement("td");
        const celdaOperaciones = document.createElement("td");

        celdaDocumentoIdentificacion.textContent = "No data available in table";
        celdaDocumentoIdentificacion.setAttribute("colspan", "10");
        celdaDocumentoIdentificacion.style.textAlign = "center";
        celdaNumIdentificacion.textContent = "";
        celdaCursoAcademico.textContent = "";
        celdaAreaEstudio.textContent = "";
        celdaTipoAcuerdo.textContent = "";
        celdaIdioma.textContent = "";
        celdaPeriodoIntercambio.textContent = "";
        celdaPaisDestino.textContent = "";
        celdaUniversidadDestino.textContent = "";
        celdaOperaciones.textContent = "";

        fila.appendChild(celdaDocumentoIdentificacion);
        fila.appendChild(celdaNumIdentificacion);
        fila.appendChild(celdaCursoAcademico);
        fila.appendChild(celdaAreaEstudio);
        fila.appendChild(celdaTipoAcuerdo);
        fila.appendChild(celdaIdioma);
        fila.appendChild(celdaPeriodoIntercambio);
        fila.appendChild(celdaPaisDestino);
        fila.appendChild(celdaUniversidadDestino);
        fila.appendChild(celdaOperaciones);
        tableAlumnos.querySelector("tbody").appendChild(fila);
    }
}

/*******************************************************************************
 *                    STUDENT EDITED MANAGEMENT                                *
 *******************************************************************************/

// Modificación de los datos de la fila que posee información del alumno editado
function editStudentFromTable(_editedStudentData) {
    const tableAlumnos = document.getElementById("tabla-alumnos");

    //Almacena el índice de la segunda columna con el id de los alumnos
    const studentIdColIndex = 1;
    //Almacena el índice de todas las columnas que se pueden modificar
    const cursoAcademicoColIndex = 2;
    const areaEstudioColIndex = 3;
    const tipoAcuerdoColIndex = 4;
    const idiomaColIndex = 5;
    const periodoIntercambioColIndex = 6;
    const paisAcuedroColIndex = 7;
    const universidadAcuerdoColIndex = 8;

    for (let i = 0; i < tableAlumnos.rows.length; i++) {
        const tableAlumnosRow = tableAlumnos.rows[i]; //Almacena cada fila
        const studentIdValue = tableAlumnosRow.cells[studentIdColIndex].textContent; //Almacena id del estudiante de la fila correspondiente

        if (studentIdValue === _editedStudentData.alumno.numId) {
            tableAlumnosRow.cells[cursoAcademicoColIndex].textContent = _editedStudentData.acuerdo.cursoAcademico;
            tableAlumnosRow.cells[areaEstudioColIndex].textContent = _editedStudentData.acuerdo.areaEstudio;
            tableAlumnosRow.cells[tipoAcuerdoColIndex].textContent = _editedStudentData.acuerdo.tipoEstudio;
            tableAlumnosRow.cells[idiomaColIndex].textContent = _editedStudentData.acuerdo.idioma;
            tableAlumnosRow.cells[periodoIntercambioColIndex].textContent = _editedStudentData.acuerdo.periodoIntercambio;
            tableAlumnosRow.cells[paisAcuedroColIndex].textContent = _editedStudentData.acuerdo.paisAcuerdo;
            tableAlumnosRow.cells[universidadAcuerdoColIndex].textContent = _editedStudentData.acuerdo.universidadAcuerdo;
        }
    }
}

/*******************************************************************************
 *                    NEW FILE MANAGEMENT                                      *
 *******************************************************************************/

// Creación de una nueva fila con información del nuevo fichero insertado
function addNewFileToTable(_newFileData) {
    const tableFicheros = document.getElementById("tabla-ficheros");

    // Comprueba si la tabla está vacía y elimina el mensaje informativo
    const informativeRow = tableFicheros.rows[1];
    const contenidoFirstRow = informativeRow.cells[0].textContent;
    if (contenidoFirstRow.trim() === "No data available in table") {
        informativeRow.remove(); // Elimina fila con mensaje informativo
    }

    const fila = document.createElement("tr");

    const celdaIdFichero = document.createElement("td");
    const celdaNombreFichero = document.createElement("td");
    const celdaMimeType = document.createElement("td");
    const celdaOperaciones = document.createElement("td");
    const operacionesButtonsRow = document.createElement("div");
    operacionesButtonsRow.classList.add("row");

    celdaIdFichero.textContent = _newFileData.file.fileId;
    celdaNombreFichero.textContent = _newFileData.file.fileName;
    celdaMimeType.textContent = _newFileData.file.fileMimeType;

    fila.appendChild(celdaIdFichero);
    fila.appendChild(celdaNombreFichero);
    fila.appendChild(celdaMimeType);
    createButtonDescargarFichero(operacionesButtonsRow, _newFileData.file.fileId);
    createButtonVerFichero(operacionesButtonsRow, _newFileData.file.fileId);
    createButtonBorrarFichero(operacionesButtonsRow, _newFileData.file.fileId);
    celdaOperaciones.appendChild(operacionesButtonsRow);
    fila.appendChild(celdaOperaciones);

    tableFicheros.querySelector("tbody").appendChild(fila);
}

// Creación del botón descargar que representa la operación descargar documento de un alumno   
function createButtonDescargarFichero(_operacionesButtonsRow, _ficheroId) {
    const descargarButtonCol = document.createElement("div");
    descargarButtonCol.classList.add("col", "col-lg-2");
    const descargarButtonForm = document.createElement("form");

    descargarButtonForm.action = "getFile";
    descargarButtonForm.method = "POST";
    descargarButtonForm.title = "Descargar";
    descargarButtonForm.dataset.toggle = "tooltip";

    const idFicheroDescargar = document.createElement("input");
    idFicheroDescargar.type = "hidden";
    idFicheroDescargar.name = "idFicheroDescargar";
    idFicheroDescargar.value = _ficheroId;

    const descargarButton = document.createElement("button");
    descargarButton.style.background = "none";
    descargarButton.style.border = "none";
    descargarButton.style.color = "#00FF00";

    const svgCode = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cloud-download" viewBox="0 0 16 16">' +
            '<path d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383z"/>' +
            '<path d="M7.646 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V5.5a.5.5 0 0 0-1 0v8.793l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>'
    '</svg>';
    const descargarButtonIcon = document.createElement("div");
    descargarButtonIcon.innerHTML = svgCode;

    descargarButton.appendChild(descargarButtonIcon);
    descargarButtonForm.appendChild(idFicheroDescargar);
    descargarButtonForm.appendChild(descargarButton);
    descargarButtonCol.appendChild(descargarButtonForm);

    _operacionesButtonsRow.appendChild(descargarButtonCol);
}

// Creación del botón ver que representa la operación ver documento de un alumno   
function createButtonVerFichero(_operacionesButtonsRow, _ficheroId) {
    const verButtonCol = document.createElement("div");
    verButtonCol.classList.add("col", "col-lg-2");
    const verButtonForm = document.createElement("form");

    verButtonForm.action = "visualizarFichero.jsp";
    verButtonForm.method = "POST";
    verButtonForm.title = "Visualizar";
    verButtonForm.dataset.toggle = "tooltip";

    const idFicheroVer = document.createElement("input");
    idFicheroVer.type = "hidden";
    idFicheroVer.name = "idFicheroVer";
    idFicheroVer.value = _ficheroId;

    const verButton = document.createElement("button");
    verButton.style.background = "none";
    verButton.style.border = "none";
    verButton.style.color = "#03A9F4";

    const svgCode = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-fill" viewBox="0 0 16 16">' +
            '<path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>' +
            '<path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>' +
            '</svg>';
    const verButtonIcon = document.createElement("div");
    verButtonIcon.innerHTML = svgCode;

    verButton.appendChild(verButtonIcon);
    verButtonForm.appendChild(idFicheroVer);
    verButtonForm.appendChild(verButton);
    verButtonCol.appendChild(verButtonForm);

    _operacionesButtonsRow.appendChild(verButtonCol);
}

// Creación del botón borrar que representa la operación borrar el documento de un alumno 
function createButtonBorrarFichero(_operacionesButtonsRow, _ficheroId) {
    const borrarButtonCol = document.createElement("div");
    borrarButtonCol.classList.add("col", "col-lg-2");
    const borrarButtonForm = document.createElement("form");

    borrarButtonForm.action = "removeFile";
    borrarButtonForm.method = "POST";
    borrarButtonForm.title = "Borrar";
    borrarButtonForm.dataset.toggle = "tooltip";

    const idFicheroBorrar = document.createElement("input");
    idFicheroBorrar.type = "hidden";
    idFicheroBorrar.name = "idFicheroBorrar";
    idFicheroBorrar.value = _ficheroId;

    const borrarButton = document.createElement("button");
    borrarButton.style.background = "none";
    borrarButton.style.border = "none";
    borrarButton.style.color = "#E34724";
    borrarButton.type = "button";
    borrarButton.setAttribute("data-bs-toggle", "modal");
    borrarButton.setAttribute("data-bs-target", `#removeFicheroModal-${_ficheroId}`);
    borrarButton.setAttribute("data-id", _ficheroId);

    const svgCode = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">' +
            '<path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 \n\
                        1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 \n\
                        5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>' +
            '</svg>';
    const borrarButtonIcon = document.createElement("div");
    borrarButtonIcon.innerHTML = svgCode;

    const modalDialogCode =
            `<div class="modal" id="removeFicheroModal-${_ficheroId}" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title"><b>Confirmación borrado</b></h5>
                            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group row">
                                        <p>¿Desea eliminar el fichero con número de identificación ${_ficheroId}?</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-primary" id="removeFicheroBtn" style="background-color:#4B49AC;">Borrar</button>
                        </div>
                    </div>
                </div>
            </div>`;
    const borrarButtonModalDialog = document.createElement("div");
    borrarButtonModalDialog.innerHTML = modalDialogCode;

    borrarButton.appendChild(borrarButtonIcon);
    borrarButtonForm.appendChild(idFicheroBorrar);
    borrarButtonForm.appendChild(borrarButton);
    borrarButtonForm.appendChild(borrarButtonModalDialog);
    borrarButtonCol.appendChild(borrarButtonForm);

    _operacionesButtonsRow.appendChild(borrarButtonCol);
}


/*******************************************************************************
 *                    FILE REMOVED MANAGEMENT                               *
 *******************************************************************************/

// Eliminación de la fila que posee los datos de un fichero eliminado
function removeFileFromTable(_fileId) {
    const tableFicheros = document.getElementById("tabla-ficheros");

    //Almacena el índice de la segunda columna con el id de los ficheros
    const fileIdColIndex = 0;

    for (let i = 0; i < tableFicheros.rows.length; i++) {
        const tableFicherosRow = tableFicheros.rows[i]; //Almacena cada fila
        const fileIdValue = tableFicherosRow.cells[fileIdColIndex].textContent; //Almacena id del fichero de la fila correspondiente

        if (fileIdValue === _fileId) {
            tableFicherosRow.remove(); // Elimina fila si coinciden los ids
        }
    }

    // Si la tabla se queda vacía muestra el mensaje informativo
    if (tableFicheros.rows.length == 1) {
        const fila = document.createElement("tr");

        const celdaIdFichero = document.createElement("td");
        const celdaNombreFichero = document.createElement("td");
        const celdaMimeType = document.createElement("td");
        const celdaOperaciones = document.createElement("td");

        celdaIdFichero.textContent = "No data available in table";
        celdaIdFichero.setAttribute("colspan", "4");
        celdaIdFichero.style.textAlign = "center";
        celdaNombreFichero.textContent = "";
        celdaMimeType.textContent = "";
        celdaOperaciones.textContent = "";

        fila.appendChild(celdaIdFichero);
        fila.appendChild(celdaNombreFichero);
        fila.appendChild(celdaMimeType);
        fila.appendChild(celdaOperaciones);
        tableFicheros.querySelector("tbody").appendChild(fila);
    }
}