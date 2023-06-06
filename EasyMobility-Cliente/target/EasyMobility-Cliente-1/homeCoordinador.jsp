<%-- 
    Document   : homeCoordinador.jsp
    Created on : 5 abr. 2023, 16:55:48
    Author     : Pablo Doñate
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="em.common.dto.CoordinadorDTO"%>
<%@page import="em.common.dto.EstudioDTO"%>
<%@page import="em.common.dto.CentroDTO"%>
<%@page import="em.common.dto.AlumnoDTO"%>
<%@page import="em.common.dto.AcuerdoEstudiosDTO"%>
<%@page import="em.service.StudyAgreementsServiceManager"%>
<%@page import="em.service.StudyServiceManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.google.gson.Gson"%>
<!DOCTYPE html>
<%   
    CoordinadorDTO coordinador = (CoordinadorDTO) session.getAttribute("coordinator");
    StudyAgreementsServiceManager studyAgreementsServiceManager = StudyAgreementsServiceManager.getInstance();
    StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
    
    EstudioDTO estudioCoordinador = (EstudioDTO) studyServiceManager.getEstudioByCoordinator(coordinador.getNumIdentificacion(), session);
    AlumnoDTO[] alumnos = studyAgreementsServiceManager.getAlumnosByCoordinator(coordinador.getNumIdentificacion(), session);
    List<AcuerdoEstudiosDTO> acuerdosEntrada = new ArrayList<>();
    List<AcuerdoEstudiosDTO> acuerdosSalida = new ArrayList<>();
    session.setAttribute("userType", "Coordinator");
%>
<html lang="es">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Coordinador View</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="vendors/feather/feather.css">
        <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
        <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" type="text/css" href="js/select.dataTables.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="css/vertical-layout-light/style.css">
        <!-- endinject -->
        <link rel="shortcut icon" href="images/favicon.png" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container-scroller">
            <!-- partial:partials/_navbar.html -->
            <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
                    <a class="navbar-brand brand-logo mr-5" href="homeCoordinador.jsp"><img src="images/logo-completo2.png" class="mr-2" alt="logo"/></a>
                    <a class="navbar-brand brand-logo-mini" href="homeCoordinador.jsp"><img src="images/logo-completo2.png" alt="logo"/></a>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
                    <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
                        <span class="icon-menu"></span>
                    </button>
                    <ul class="navbar-nav navbar-nav-right">
                        <li class="nav-item nav-profile dropdown">
                            <b><%=coordinador.getNombre()%> <%=coordinador.getApellido1()%> &nbsp;</b>
                            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                </svg>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
                                <a class="dropdown-item" href="editarPerfil.jsp">
                                    <i class="ti-settings text-primary"></i>
                                    Ajustes
                                </a>
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
                            <a class="nav-link" href="homeCoordinador.jsp">
                                <i class="icon-grid menu-icon"></i>
                                <span class="menu-title">Dashboard</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="gestionAlumnos.jsp">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                                <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
                                </svg>
                                <span class="menu-title px-3">Gestión Alumnos</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- partial -->
                <div class="main-panel">
                    <div class="content-wrapper">
                        <div class="row">
                            <% if (session.getAttribute("errorAlumno") != null) {%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorAlumno")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorAlumno", null); %>
                            <% } else if (session.getAttribute("errorCoordinador") != null) {%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorCoordinador")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorCoordinador", null); %>
                            <% }%>
                            <% if (session.getAttribute("warning") != null) {%>
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("warning")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("warning", null); }%>
                        </div>
                        <div class="row">
                            <div class="col-md-12 grid-margin">
                                <div class="row">
                                    <div class="col-12 col-xl-8 mb-4 mb-xl-0">
                                        <h3 class="font-weight-bold">¡ Bienvenid@ <%=coordinador.getNombre()%> !</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                        for (AlumnoDTO alumno : alumnos) {
                            AcuerdoEstudiosDTO acuerdoAlumno = (AcuerdoEstudiosDTO) studyAgreementsServiceManager.getAcuerdoByStudent(alumno.getNumIdentificacion(), session);
                            if (studyAgreementsServiceManager.getTipoAcuerdo(alumno, estudioCoordinador, session) == "in") {
                                acuerdosEntrada.add(acuerdoAlumno);
                            } else {
                                acuerdosSalida.add(acuerdoAlumno);
                            }
                        }%>
                        <div class="row">
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between">
                                            <p class="card-title">Alumnos IN / Alumnos OUT</p>
                                        </div>
                                        <p class="font-weight-500">Número total de alumnos de entrada y salida por años.</p>
                                        <canvas id="alumnosinoutchart"></canvas>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between">
                                            <p class="card-title">Países favoritos IN</p>
                                        </div>
                                        <p class="font-weight-500">Países con más alumnos que viajan a <%=estudioCoordinador.getCentro().getLocalizacion().getPais()%>.</p>
                                        <canvas id="paisesfavoritosInchart" width="10" height="15"></canvas>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between">
                                            <p class="card-title">Países favoritos OUT</p>
                                        </div>
                                        <p class="font-weight-500">Países con más alumnos que viajan desde <%=estudioCoordinador.getCentro().getLocalizacion().getPais()%>.</p>
                                        <canvas id="paisesfavoritosOutchart" width="10" height="15"></canvas>
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
        <script>
            var acuerdosEntrada = <%= new Gson().toJson(acuerdosEntrada) %>;
            var acuerdosSalida = <%= new Gson().toJson(acuerdosSalida) %>;
        </script>  
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

        <script src="https://cdn.jsdelivr.net/npm/chart.js@4.2.1/dist/chart.umd.min.js"></script>
        <script src="js/alumnosinoutchart.js"></script>
        <script src="js/paisesfavoritosIn.js"></script>
        <script src="js/paisesfavoritosOut.js"></script>
    </body>

</html>

