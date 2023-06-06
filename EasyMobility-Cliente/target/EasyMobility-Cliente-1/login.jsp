<%-- 
    Document   : login.jsp
    Created on : 5 abr. 2023, 13:43:05
    Author     : Adnana Dragut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>EasyMobility Login</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="vendors/feather/feather.css">
        <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="css/vertical-layout-light/style.css">
        <!-- endinject -->
        <link rel="shortcut icon" href="images/favicon.png" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <style>
            input[type=submit]{
                padding: 16px 32px;
                text-decoration: none;
                margin: 4px 2px;
                cursor: pointer;
                color: #fff;
                background-color: #7369bd;
                border-color: #4B49AC;
                border-radius: 10px;
                width: 100%;
            }
        </style>
    </head>
    <body onload="getcookiedata()">
        <div class="container-scroller">
            <div class="container-fluid page-body-wrapper full-page-wrapper">
                <div class="content-wrapper d-flex align-items-center auth px-0">
                    <div class="row w-100 mx-0">
                        <div class="col-lg-6 mx-auto">
                            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                                <div class="brand-logo" style="text-align:center">
                                    <img src="images/logo-completo2.png" alt="logo" style="width:230px;height:100px;">
                                </div>
                                <div>
                                    <% if (session.getAttribute("errorAlumno") != null) {%>
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("errorAlumno")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("errorAlumno", null); %>
                                    <% }else if (session.getAttribute("logoutExitoso") != null) {%>
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("logoutExitoso")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("logoutExitoso", null); %>
                                    <% }else if (session.getAttribute("errorCoordinador") != null) {%>
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("errorCoordinador")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("errorCoordinador", null); %>
                                    <% }else if (session.getAttribute("errorLogin") != null) {%>
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("errorLogin")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("errorLogin", null); %>
                                    <% }else if (session.getAttribute("errorSesion") != null) {%>
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("errorSesion")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("errorSesion", null); %>
                                    <% }%>
                                </div>
                                <h4>Inicio de sesión</h4>
                                <br>
                                <h6 class="font-weight-light">Por favor, introduce tus credenciales de acceso</h6>
                                <form id="login-form" class="pt-3" method=POST action="login">
                                    <div class="form-group">
                                        <div class="dropdown row">
                                            <div class="form-group col">
                                                <select class="form-select" style="border-radius: 7px;height:50px;border:1px solid #04467E;background-color:#7369bd;color:#ffffff;font-size:13px" required>
                                                    <option value="" selected>--Selecciona documento de identificación--</option>
                                                    <option value="dni">DNI</option>
                                                    <option value="pasaporte">Pasaporte</option>
                                                </select>
                                            </div>
                                            <div class="form-group col">
                                                <input type="text" class="form-control form-control-lg" id="userIdentification" name="userIdentification" placeholder="Número de identificación" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-lg" id="userPassword" name="userPassword" placeholder="Contraseña" required>
                                    </div>
                                    <br>
                                    <div class="form-group row">
                                        <div class="form-group col">
                                            <b>Tipo de usuario</b>
                                        </div>
                                        <div class="form-group col">
                                            <input type="radio" class="form-check-input" name="userType" id="userAlumno" value="userAlumno" checked>
                                            Alumno
                                        </div>
                                        <div class="form-group col">
                                            <input type="radio" class="form-check-input" name="userType" id="userCoordinator" value="userCoordinator">
                                            Coordinador de centro
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <input type="submit" value="Iniciar sesión">
                                    </div>
                                    <div class="my-2 d-flex justify-content-between align-items-center">
                                        <div class="form-check">
                                            <label class="form-check-label text-muted">
                                                <input type="checkbox" class="form-check-input" name="rememberme" id="rememberme" onclick="setcookie()">
                                                Recordar mis credenciales para futuros accesos
                                            </label>
                                        </div>
                                        <a href="passwordOlvidada.jsp" class="text-primary">He olvidado mi contraseña</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- content-wrapper ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <!-- container-scroller -->
        <!-- plugins:js -->
        <script src="vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="js/off-canvas.js"></script>
        <script src="js/hoverable-collapse.js"></script>
        <script src="js/template.js"></script>
        <script src="js/settings.js"></script>
        <script src="js/todolist.js"></script>
        <!-- endinject -->

        <script>
            function setcookie() {
                var u = document.getElementById('userIdentification').value;
                var p = document.getElementById('userPassword').value;
                var userAlumnoRadio = document.getElementById('userAlumno');
                var userCoordinatorRadio = document.getElementById('userCoordinator');
                var selectElement = document.querySelector('.form-select').value; // Selecciona el elemento <select>

                document.cookie = "identificationType=" + selectElement + ";path=http://localhost:8080/EasyMobility-Cliente/";
                document.cookie = "myusrname=" + u + ";path=http://localhost:8080/EasyMobility-Cliente/";
                document.cookie = "mypswd=" + p + ";path=http://localhost:8080/EasyMobility-Cliente/";
                if (userAlumnoRadio.checked) {
                    document.cookie = "userType=" + userAlumnoRadio.id + ";path=http://localhost:8080/EasyMobility-Cliente/";
                } else {
                    document.cookie = "userType=" + userCoordinatorRadio.id + ";path=http://localhost:8080/EasyMobility-Cliente/";
                }
                
            }

            function getcookiedata() {
                var user = getcookie('myusrname');
                var password = getcookie('mypswd');
                var userType = getcookie('userType');
                var identificationType = getcookie('identificationType');

                var userTypeRadios = document.getElementsByName('userType');

                document.querySelector('#userIdentification').value = user;
                document.querySelector('#userPassword').value = password;
                var dropdownIdentificationType = document.querySelector('.form-select');
                
                for (var i = 0; i < dropdownIdentificationType.options.length; i++) {
                    if (dropdownIdentificationType.options[i].value === identificationType) {
                        dropdownIdentificationType.options[i].selected = true; // Marca la opción como seleccionada
                        break; // Sal del bucle una vez se haya encontrado la opción correspondiente
                    }
                }

                // Recorre los elementos de radio y verifica si coinciden con el valor de la cookie
                for (var i = 0; i < userTypeRadios.length; i++) {
                    var radio = userTypeRadios[i];

                    // Comprueba si el valor del radio coincide con el valor de la cookie
                    if (radio.value === userType) {
                        radio.checked = true; // Selecciona el radio correspondiente
                        break; // Sal del bucle una vez que encuentres el radio adecuado
                    }
                }
            }

            function getcookie(cname) {
                var name = cname + "=";
                var decodedCookie = decodeURIComponent(document.cookie);
                var ca = decodedCookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(name) == 0) {
                        return c.substring(name.length, c.length);
                    }
                }
                return "";
            }

            window.onload = function () {
                getcookiedata();
            };
        </script>

    </body>

</html>
