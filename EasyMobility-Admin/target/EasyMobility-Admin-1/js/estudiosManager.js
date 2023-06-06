// Cargar los valores guardados en el LocalStorage al cargar la página de nuevoEstudio.jsp
if (window.location.href.indexOf("nuevoEstudio.jsp") > -1) {
    
    // Obtener el formulario y los campos
    const myForm = document.getElementById("regForm");
    const nameField = document.getElementById("studyName");
    const coordinatorField = document.getElementById("coordinadorId");
    const areaEstudiosField = document.getElementById("areaEstudiosId");
    const universidadField = document.getElementById("university-select");
    const centroField = document.getElementById("center-select");
    
    loadForm();

    // Obtener los valores de los campos y guardarlos en el LocalStorage
    function saveForm1() {
        localStorage.setItem('studyName', nameField.value);
        localStorage.setItem('coordinadorId', coordinatorField.value);
        localStorage.setItem('university-select', universidadField.value);
        localStorage.setItem('center-select', centroField.value);
    }

    function saveForm2() {
        localStorage.setItem('studyName', nameField.value);
        localStorage.setItem('coordinadorId', coordinatorField.value);
        localStorage.setItem('areaEstudiosId', areaEstudiosField.value);
        localStorage.setItem('center-select', centroField.value);
    }

    function saveForm3() {
        localStorage.setItem('studyName', nameField.value);
        localStorage.setItem('coordinadorId', coordinatorField.value);
        localStorage.setItem('areaEstudiosId', areaEstudiosField.value);
        localStorage.setItem('university-select', universidadField.value);
    }

    // Limpiar valores guardados en el LocalStorage
    function clearForm() {
        localStorage.setItem('studyName', "");
        localStorage.setItem('coordinadorId', "");
        localStorage.setItem('areaEstudiosId', "");
        localStorage.setItem('university-select', "");
        localStorage.setItem('center-select', "");
    }

    // Cargar los valores guardados en el LocalStorage en los campos del formulario
    function loadForm() {
        const name = localStorage.getItem('studyName');
        const coordinator = localStorage.getItem('coordinadorId');
        const areaEstudios = localStorage.getItem('areaEstudiosId');
        const universidad = localStorage.getItem('university-select');
        const centro = localStorage.getItem('center-select');

        if (name) {
            nameField.value = name;
        }

        if (areaEstudios) {
            areaEstudiosField.value = areaEstudios;
        }

        if (coordinator) {
            coordinatorField.value = coordinator;
        }

        if (universidad) {
            universidadField.value = universidad;
        } else {
            document.getElementById('add-centerUniversity-option').disabled = true; // deshabilita la opción de nuevo centro hasta que se seleccione una universidad.
        }

        if (centro) {
            for (var i = 0; i < centrosJSON.length; i++) {
                if (centrosJSON[i].id.idCentro === parseInt(centro)) {
                    createOption(centroField, centrosJSON[i].nombre, centrosJSON[i].id, "selected", "");
                }
            }
        }
    }

    var modal1 = document.getElementById("addAreaEstudioModal");
    function onClickLoadForm1(selectedValue) {
        if(selectedValue === "addAreaEstudioModal") {
            // Si el valor es igual a "addAreaEstudioModal", se ha seleccionado la opción "++++ Nuevo Area de Estudio ++++"
            modal1.style.display = "block";
            saveForm1();
        }
    }

    var modal2 = document.getElementById("addUniversidadModal");
    function onClickLoadForm2(selectedValue) {
        if(selectedValue === "addUniversidadModal") {
            // Si el valor es igual a "addAreaEstudioModal", se ha seleccionado la opción "++++ Nuevo Area de Estudio ++++"
            modal2.style.display = "block";
            saveForm2();
        }
    }

    var modal3 = document.getElementById("addCentroUniversidadModal");
    function onClickLoadForm3(selectedValue) {
        if(selectedValue === "addCentroModal") {
            // Si el valor es igual a "addAreaEstudioModal", se ha seleccionado la opción "++++ Nuevo Area de Estudio ++++"
            modal3.style.display = "block";
            saveForm3();
        }
    }

    function closeModal(modalId, idSelect) {
        var modal = document.getElementById(modalId);
        var select = document.getElementById(idSelect); //Deselecciona la opción que había activa.

        modal.style.display = "none";
        select.selectedIndex = 0;
    }

    var addAreaEstudioButtonInNuevoEstudio = document.getElementById("addAreaEstudioButtonInNuevoEstudio");
    addAreaEstudioButtonInNuevoEstudio.onclick = function () {

        var areaEstudioName = document.getElementById("newAreaEstudio").value;

        // Crear la solicitud AJAX para agregar el nuevo idioma
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "addAreaEstudios");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Si la solicitud se realizó correctamente, redirigir de vuelta a la página principal, manteniendo los valores del formulario principal
                    window.location.href = "nuevoEstudio.jsp";
                }
            }
        };
        xhr.send("areaEstudiosName=" + areaEstudioName);
    };

    var addUniversidadButtonInNuevoEstudio = document.getElementById("addUniversidadButtonInNuevoEstudio");
    addUniversidadButtonInNuevoEstudio.onclick = function () {

        var nombreUniversidad = document.getElementById("nombreUniversidad").value;
        var paisUniversidad = document.getElementById("paisUniversidad").value;
        var ciudadUniversidad = document.getElementById("ciudadUniversidad").value;
        var direcUniversidad = document.getElementById("direcUniversidad").value;

        // Crear la solicitud AJAX para agregar el nuevo idioma
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "addUniversidad");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Si la solicitud se realizó correctamente, redirigir de vuelta a la página principal, manteniendo los valores del formulario principal
                    window.location.href = "nuevoEstudio.jsp";
                }
            }
        };
        var params = "nombreUniversidad="+nombreUniversidad+"&paisUniversidad="+paisUniversidad+"&ciudadUniversidad="+ciudadUniversidad+"&direcUniversidad="+direcUniversidad;
        xhr.send(params);
    };

    var addCentroButtonInNuevoEstudio = document.getElementById("addCentroButtonInNuevoEstudio");
    addCentroButtonInNuevoEstudio.onclick = function () {

        var nombreCentro = document.getElementById("nombreCentro").value;
        var paisCentro = document.getElementById("paisCentro").value;
        var ciudadCentro = document.getElementById("ciudadCentro").value;
        var direcCentro = document.getElementById("direcCentro").value;
        var universidadId = document.getElementById("universidad-seleccionada").textContent;

        // Crear la solicitud AJAX para agregar el nuevo idioma
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "addCentro");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Si la solicitud se realizó correctamente, redirigir de vuelta a la página principal, manteniendo los valores del formulario principal
                    window.location.href = "nuevoEstudio.jsp";
                }
            }
        };
        var params = "nombreCentro="+nombreCentro+"&paisCentro="+paisCentro+"&ciudadCentro="+ciudadCentro+"&direcCentro="+direcCentro+"&universidadId="+universidadId;
        xhr.send(params);
    };

    /*********************************************************************************************
     *                                                                                           *
     * Funciones para crear drop down lists con los datos de la universidad seleccionada         *
     *                                                                                           *
     *********************************************************************************************/
    function configureDropDownCenterList(selectedUniversity) {
        var universitySelect = document.getElementById('university-select');
        var centerSelect = document.getElementById('center-select');
        var centrosDeUniversidadSeleccionada = [];

        if(selectedUniversity !== "addUniversidadModal") {
            for (var i = 0; i < centrosJSON.length; i++) {
                if (centrosJSON[i].universidad.id == selectedUniversity) {
                    centrosDeUniversidadSeleccionada.push(centrosJSON[i]);
                }
            }

            centerSelect.options.length = 1;

            createOption(centerSelect, "++++ Nuevo Centro ++++", "addCentroModal", "", "");

            for (var i = 0; i < centrosDeUniversidadSeleccionada.length; i++) {
                if (selectedUniversity === centrosDeUniversidadSeleccionada[i].universidad.id) { //Comprueba si ya había un centro seleccionado (útil para el caso de editar o seguir rellenando la información del formulario donde ya debería haber un valor seleccionado)
                    createOption(centerSelect, centrosDeUniversidadSeleccionada[i].nombre, centrosDeUniversidadSeleccionada[i].id.idCentro, "selected", "");
                } else {
                    createOption(centerSelect, centrosDeUniversidadSeleccionada[i].nombre, centrosDeUniversidadSeleccionada[i].id.idCentro, "", "");
                }
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
}

if (window.location.href.indexOf("editarEstudio.jsp") > -1) {

    /*********************************************************************************************
     *                                                                                           *
     * Funciones para crear drop down lists con los datos de la universidad seleccionada         *
     *                                                                                           *
     *********************************************************************************************/
    function configureDropDownCenterList(selectedUniversity) {
        var universitySelect = document.getElementById('university-select');
        var centerSelect = document.getElementById('center-select');
        var centrosDeUniversidadSeleccionada = [];

        for (var i = 0; i < centrosJSON.length; i++) {
            if (centrosJSON[i].universidad.id == selectedUniversity) {
                centrosDeUniversidadSeleccionada.push(centrosJSON[i]);
            }
        }

        centerSelect.options.length = 1;

        for (var i = 0; i < centrosDeUniversidadSeleccionada.length; i++) {
            createOption(centerSelect, centrosDeUniversidadSeleccionada[i].nombre, centrosDeUniversidadSeleccionada[i].id.idCentro, "", "");
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
}

function clearForm() {
    localStorage.setItem('studyName', "");
    localStorage.setItem('coordinadorId', "");
    localStorage.setItem('areaEstudiosId', "");
    localStorage.setItem('university-select', "");
    localStorage.setItem('center-select', "");
}