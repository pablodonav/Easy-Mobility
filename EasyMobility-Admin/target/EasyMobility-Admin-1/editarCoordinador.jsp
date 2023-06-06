<%-- 
    Document   : editarCoordinador.jsp
    Created on : 5 abr. 2023, 16:55:48
    Author     : Adnana Dragut
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="em.common.dto.AdministradorDTO"%>
<%@page import="em.common.dto.CoordinadorDTO"%>
<%@page import="em.service.CoordinatorServiceManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.Gson"%>
<!DOCTYPE html>
<%
    AdministradorDTO administrador = (AdministradorDTO) session.getAttribute("admin");
    
    // Obtener datos del coordinador seleccionado que se va a editar
    String numIdCoordinadorEditar = request.getParameter("numIdentificacionCoordinadorEditar");
    CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
    CoordinadorDTO coordinador = coordinatorServiceManager.getCoordinador(numIdCoordinadorEditar, session);
%>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Nuevo Alumno</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="vendors/feather/feather.css">
        <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
        <link rel="stylesheet" href="css/data-tables-style/datatables.css" />
        <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" type="text/css" href="js/select.dataTables.min.css">

        <link rel="stylesheet" href="vendors/select2/select2.min.css">
        <link rel="stylesheet" href="vendors/select2-bootstrap-theme/select2-bootstrap.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="css/vertical-layout-light/style.css">
        <!-- endinject -->
        <link rel="shortcut icon" href="images/favicon.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/css/intlTelInput.css" />
        <style>
            table.table td a {
                color: #a0a5b1;
                display: inline-block;
                margin: 0 5px;
            }
            table.table td a.view {
                color: #03A9F4;
            }
            table.table td a.edit {
                color: #FFC107;
            }
            table.table td a.delete {
                color: #E34724;
            }

            .dropdownlist{
                padding: 10px 32px;
                text-decoration: none;
                cursor: pointer;
                width: 100%;
                justify-content: center;
                height:50px;
                border:1px solid #a09fdf;
                background-color:#a09fdf;
                color:white;
                font-size:13px
            }

            /* Mark input boxes that gets an error on validation: */
            input.invalid {
                background-color: #ffdddd;
            }

            select.invalid {
                border:3px solid red !important;
            }

            /* Hide all steps by default: */
            .tab {
                display: none;
            }

            button {
                background-color: #4B49AC;
                color: white;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                border-radius: 8px;
                border-color: #4B49AC;
            }

            button:hover {
                opacity: 0.8;
            }

            #prevBtn {
                background-color: #bbbbbb;
                border-color: #bbbbbb;
            }

            .required:after {
                content:" *";
                color: red;
            }

            /* Make circles that indicate the steps of the form: */
            .step {
                height: 15px;
                width: 15px;
                margin: 0 2px;
                background-color: #bbbbbb;
                border: none;
                border-radius: 50%;
                display: inline-block;
                opacity: 0.5;
            }

            .step.active {
                opacity: 1;
            }

            /* Mark the steps that are finished and valid: */
            .step.finish {
                background-color: #4B49AC;
            }

            a.volver:link, a.volver:visited {
                background-color: #4B49AC;
                color: white;
                padding: 15px 25px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                border-radius: 8px;
            }
            a.volver:hover, a.volver:active {
                background-color: #3f3da8;
            }
        </style>
    </head>
    <body>
        <div class="container-scroller">
            <!-- partial:partials/_navbar.html -->
            <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
                    <a class="navbar-brand brand-logo mr-5" href="gestionCoordinadores.jsp"><img src="images/logo-completo2.png" class="mr-2" alt="logo"/></a>
                    <a class="navbar-brand brand-logo-mini" href="gestionCoordinadores.jsp"><img src="images/logo-completo2.png" alt="logo"/></a>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
                    <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
                        <span class="icon-menu"></span>
                    </button>
                    <ul class="navbar-nav navbar-nav-right">
                        <li class="nav-item nav-profile dropdown">
                            <b><%=administrador.getNombre()%> <%=administrador.getApellido1()%> &nbsp;</b>
                            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                </svg>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
                                <a class="dropdown-item" href="logout">
                                    <i class="ti-power-off text-primary"></i>
                                    Cierre de sesión
                                </a>
                            </div>
                        </li>
                    </ul>
                    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                        <span class="icon-menu"></span>
                    </button>
                </div>
            </nav>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- partial -->
                <!-- partial:partials/_sidebar.html -->
                <nav class="sidebar sidebar-offcanvas" id="sidebar">
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link" href="gestionCoordinadores.jsp">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                                <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
                                </svg>
                                <span class="menu-title px-3">Coordinadores</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="gestionEstudios.jsp">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-book-fill" viewBox="0 0 16 16">
                                <path d="M8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                                </svg>
                                <span class="menu-title px-3">&nbsp;Estudios</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- partial -->
                <div class="main-panel">
                    <div class="content-wrapper">
                        <div class="row">
                            <% if(session.getAttribute("errorCoordinador") != null){%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorCoordinador")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorCoordinador", null); %>
                            <% } %>
                        </div>
                        <div class="row">
                            <div class="col-12 grid-margin">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <nav aria-label="breadcrumb">
                                                <ol class="breadcrumb" style="border: white;">
                                                    <li class="breadcrumb-item"><a href="gestionCoordinadores.jsp">Gestión coordinadores</a></li>
                                                    <li class="breadcrumb-item active" aria-current="page">Editar coordinador</li>
                                                </ol>
                                            </nav>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-5">
                                                <h4 class="card-title">Edición de un coordinador existente</h4>
                                            </div>
                                            <div class="col-md-7 mb-3 text-right">
                                                <a id="volverBtn" href="gestionCoordinadores.jsp" class="volver">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-arrow-left" viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
                                                    </svg>
                                                    Volver
                                                </a>
                                            </div>
                                        </div>
                                        <form class="form-sample" id="regForm" method=POST action="editCoordinador">
                                            <div class="tab">
                                                <p class="card-description">
                                                    Información personal
                                                </p>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label required">Nombre</label>
                                                            <div class="col-sm-9">
                                                                <div class="form-group">
                                                                    <input type="text" class="form-control" id="coordinatorName" name="coordinatorName" oninput="this.className = 'form-control'" value=<%=coordinador.getNombre()%>>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label required">Primer Apellido</label>
                                                            <div class="col-sm-9">
                                                                <div class="form-group">
                                                                    <input type="text" class="form-control" id="coordinatorSurname1" name="coordinatorSurname1" oninput="this.className = 'form-control'" value=<%=coordinador.getApellido1()%>>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label">Segundo Apellido</label>
                                                            <div class="col-sm-9">
                                                                <div class="form-group">
                                                                    <input type="text" class="form-control" id="coordinatorSurname2" name="coordinatorSurname2" oninput="this.className = 'form-control'" value=<%=coordinador.getApellido2()%>>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label required">Correo Electrónico</label>
                                                            <div class="col-sm-9">
                                                                <div class="form-group">
                                                                    <input type="email" class="form-control" id="coordinatorEmail" name="coordinatorEmail" title="El formato del correo electrónico es: xx@xx.xx" placeholder="example@example.com" oninput="this.className = 'form-control'" value=<%=coordinador.getCorreo()%>>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label required">Teléfono</label>
                                                            <div class="col-sm-9">
                                                                <div class="form-group" style="width:100%;">
                                                                    <input class="form-control" type="tel" name="phone" id="phone" oninput="this.className = 'form-control'" value=<%=coordinador.getTelefono()%>>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="dropdown row">
                                                            <div class="form-group col-5">
                                                                <select class="form-select dropdownlist" id="identificationType" name="identificationType" onchange="this.className = 'form-select dropdownlist'" readonly>
                                                                    <% if (coordinador.getDocumentoIdentificacion().toString().equals("dni")){%>
                                                                    <option value="dni" selected>DNI</option>
                                                                    <%}else if(coordinador.getDocumentoIdentificacion().toString().equals("passport")){%>
                                                                    <option value="pasaporte" selected>Pasaporte</option>
                                                                    <%}%>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col">
                                                                <input type="text" class="form-control" id="coordinatorIdentification" name="coordinatorIdentification" oninput="this.className = 'form-control'" value=<%=coordinador.getNumIdentificacion()%> readonly>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>  
                                            <div style="overflow:auto;">
                                                <div style="float:right;">
                                                    <button class="volver" type="button" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
                                                    <button class="volver" type="button" id="nextBtn" onclick="nextPrev(1)">Next</button>
                                                </div>
                                            </div>
                                            <!-- Circles which indicates the steps of the form: -->
                                            <div style="text-align:center;margin-top:40px;">
                                                <span class="step"></span>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- content-wrapper ends -->
                    <!-- partial:partials/_footer.html -->
                    <footer class="footer">
                        <div class="d-sm-flex justify-content-center justify-content-sm-between">
                            <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Pablo Doñate & Adnana Dragut</span>
                        </div>
                    </footer> 
                    <!-- partial -->
                </div>
                <!-- main-panel ends -->
            </div>   
            <!-- page-body-wrapper ends -->
        </div>
        <!-- container-scroller -->

        <!-- plugins:js -->
        <script src="vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->    
        <script src="vendors/chart.js/Chart.min.js"></script>
        <script src="vendors/datatables.net/jquery.dataTables.js"></script>
        <script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
        <script src="js/dataTables.select.min.js"></script>

        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="js/off-canvas.js"></script>
        <script src="js/hoverable-collapse.js"></script>
        <script src="js/template.js"></script>
        <script src="js/settings.js"></script>
        <script src="js/todolist.js"></script>
        <!-- endinject -->
        <!-- Custom js for this page-->
        <script src="js/dashboard.js"></script>
        <script src="js/Chart.roundedBarCharts.js"></script>
        <!-- End custom js for this page-->

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="js/alumnosinoutchart.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/jquery.dataTables.min.js"></script>
        <script src="js/datatables.js"></script>

        <script src="vendors/typeahead.js/typeahead.bundle.min.js"></script>
        <script src="vendors/select2/select2.min.js"></script>
        <script src="js/select2.js"></script>
        <script src="js/typeahead.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/intlTelInput.min.js"></script>
        <script src="js/coordinadores.js"></script>
    </body>
</html>

