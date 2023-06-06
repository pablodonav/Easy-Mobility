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
<%@page import="em.common.dto.FicheroDTO"%>
<%@page import="em.common.dto.AcuerdoEstudiosDTO"%>
<%@page import="em.service.StudyServiceManager"%>
<%@page import="em.service.StudyAgreementsServiceManager"%>
<%@page import="em.service.StudentServiceManager"%>
<%@page import="em.service.FileServiceManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.eclipse.microprofile.config.Config"%>
<%@page import="org.eclipse.microprofile.config.ConfigProvider"%>
<!DOCTYPE html>
<%
    String numIdentificacionAlumno = (String) request.getParameter("numIdentificacionAlumnoFichero");
    if (numIdentificacionAlumno == null) {
        numIdentificacionAlumno = (String) session.getAttribute("numIdentificacionAlumnoFichero");
    } else {
        session.setAttribute("numIdentificacionAlumnoFichero", numIdentificacionAlumno);
    }
    String coordinadorId = (String) session.getAttribute("userId");
    CoordinadorDTO coordinador = (CoordinadorDTO) session.getAttribute("coordinator");
    StudentServiceManager studentServiceManager = StudentServiceManager.getInstance();
    AlumnoDTO alumno = studentServiceManager.getAlumno(numIdentificacionAlumno, session);
    FileServiceManager fileServiceManager = FileServiceManager.getInstance();
    List<FicheroDTO> ficheros = fileServiceManager.getFicheroByAlumn(numIdentificacionAlumno, session);
    
    Config configP = ConfigProvider.getConfig();
    String wsUri = configP.getValue("em.urlServerWebSocket", String.class);
%>
<script>
    function checkFileSize(inputFile) {
        var MAXSIZE = 10 * 1024 * 1024; // 10MB

        if (inputFile.files && inputFile.files[0].size > MAXSIZE) {
            alert("File too large."); // Do your thing to handle the error.
            inputFile.value = null; // Clear the field.
        }
    }

</script>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Ver Documentación Alumno</title>
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
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="css/vertical-layout-light/style.css">
        <!-- endinject -->
        <link rel="shortcut icon" href="images/favicon.png" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <style>
            table.table td a {
                color: #a0a5b1;
                display: inline-block;
                margin: 0 5px;
            }
            table.table td a.edit {
                color: #03A9F4;
            }
            table.table td a.download {
                color: #00FF00;
            }
            table.table td a.delete {
                color: #E34724;
            }
        </style>
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
                            <% if (session.getAttribute("exitoBorradoFichero") != null) {%>
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("exitoBorradoFichero")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("exitoBorradoFichero", null);%>
                            <% } else if (session.getAttribute("errorFicheros") != null) {%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorFicheros")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorFicheros", null);%>
                            <% } else if (session.getAttribute("errorCargaFichero") != null) {%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorCargaFichero")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorCargaFichero", null);%>
                            <% } else if (session.getAttribute("exitoCargaFichero") != null) {%>
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("exitoCargaFichero")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("exitoCargaFichero", null); }%>
                            <div class="alert alert-danger alert-dismissible fade show d-none" id="errorAlert" role="alert">
                                <strong id="errorMessage"></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <h4 class="card-title">Documentación: <%=alumno.getNombre()%></h4>
                                            </div>
                                            <div class="col-md-7 mb-3 text-right">
                                                <form method="post" action="<%=response.encodeURL("FileUploader")%>" enctype="multipart/form-data" onsubmit="return validateForm()">
                                                    Fichero a enviar:</td>
                                                    <input type="file" name="fichero" id="fichero" size="70" onchange="checkFileSize(this)">
                                                    <input type="hidden" name="alumnId" id="alumnId" value="<%=numIdentificacionAlumno%>" />
                                                    <input type="submit" value="Enviar nuevo documento">
                                                </form>
                                            </div>
                                        </div>
                                        <div class="table-responsive" >
                                            <table id="tabla-ficheros" class="table table-hover table-striped" cellspacing="0" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Id</th>
                                                        <th>Nombre</th>
                                                        <th>Mime Type</th>
                                                        <th>Operaciones</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%for (FicheroDTO fichero: ficheros) {%>
                                                    <tr>
                                                        <td><%=fichero.getId()%></td>
                                                        <td><%=fichero.getFileName()%></td>
                                                        <td><%=fichero.getMimeType()%></td>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col col-lg-2">
                                                                    <form action="getFile" method="POST" title="Descargar" data-toggle="tooltip">
                                                                        <input type="hidden" id="idFicheroDescargar" name="idFicheroDescargar" value="<%=fichero.getId()%>">
                                                                        <button style="background: none;border: none;color: #00FF00;" type="submit">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cloud-download" viewBox="0 0 16 16">
                                                                            <path d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383z"/>
                                                                            <path d="M7.646 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V5.5a.5.5 0 0 0-1 0v8.793l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                                <div class="col col-lg-2">
                                                                    <form action="visualizarFichero.jsp" method="POST" title="Visualizar" data-toggle="tooltip">
                                                                        <input type="hidden" id="idFicheroVer" name="idFicheroVer" value="<%=fichero.getId()%>">
                                                                        <input type="hidden" id="nameFichero" name="nameFichero" value="<%=fichero.getFileName()%>">
                                                                        <input type="hidden" id="mimeFichero" name="mimeFichero" value="<%=fichero.getMimeType()%>">
                                                                        <button style="background: none;border: none;color: #03A9F4;" type="submit">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-fill" viewBox="0 0 16 16">
                                                                            <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>
                                                                            <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                                <div class="col col-lg-2">
                                                                    <form action="removeFile" method="POST" title="Borrar" data-toggle="tooltip">
                                                                        <input type="hidden" id="idFicheroBorrar" name="idFicheroBorrar" value="<%=fichero.getId()%>">
                                                                        <button style="background: none;border: none;color: #E34724;" type="button" data-bs-toggle="modal" data-bs-target="#removeFicheroModal-<%=fichero.getId()%>" data-id="<%=fichero.getId()%>">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                                                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                                                                            </svg>
                                                                        </button>
                                                                        <div class="modal" id="removeFicheroModal-<%=fichero.getId()%>" tabindex="-1" role="dialog">
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
                                                                                                    <p>¿Desea eliminar el fichero con número de identificación <%=fichero.getId()%> ?</p>
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
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
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
            </div>
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
        <script type="text/javascript">
            $(document).ready(function () {
                $('#tabla-ficheros').DataTable();
                $('.dataTables_length').addClass('bs-select');
            });
            var table = $('#tabla-ficheros').DataTable({
                pageLength: 5,
                lengthMenu: [[5, 10, 20, -1], [5, 10, 20, 'All']]
            });
        </script>

        <script>
            function validateForm() {
                var fileInput = document.getElementById("fichero");
                if (fileInput.files.length === 0) {
                    var errorAlert = document.getElementById("errorAlert");
                    var errorMessage = document.getElementById("errorMessage");
                    errorMessage.textContent = "Por favor, selecciona un archivo.";
                    errorAlert.classList.remove("d-none");
                    return false; // Cancelar el envío del formulario si no se selecciona un archivo
                }
                return true; // Permitir el envío del formulario si se selecciona un archivo
            }
        </script>

        <script>
            var userId = '<%=coordinadorId%>';
            var wsUri = '<%=wsUri%>';
        </script>
        
        <script src="js/serverWebsocket.js"></script>

    </body>

</html>

