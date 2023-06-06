<%-- 
    Document   : gestionCoordinadores.jsp
    Created on : 5 abr. 2023, 16:55:48
    Author     : Adnana Dragut
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="em.common.dto.CoordinadorDTO"%>
<%@page import="em.common.dto.AdministradorDTO"%>
<%@page import="em.common.dto.EstudioDTO"%>
<%@page import="em.common.dto.CentroDTO"%>
<%@page import="em.common.dto.AcuerdoEstudiosDTO"%>
<%@page import="em.service.StudyServiceManager"%>
<%@page import="em.service.StudyAgreementsServiceManager"%>
<%@page import="em.service.CoordinatorServiceManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<%
    AdministradorDTO administrador = (AdministradorDTO) session.getAttribute("admin");
    
    CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
    StudyAgreementsServiceManager studyAgreementsServiceManager = StudyAgreementsServiceManager.getInstance();
    StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
    
    CoordinadorDTO[] coordinadores = coordinatorServiceManager.getCoordinadores(session);
%>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Gestión Alumnos</title>
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
            a.addAlumn:link, a.addAlumn:visited {
                background-color: #4B49AC;
                color: white;
                padding: 15px 25px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                border-radius: 8px;
            }
            a.addAlumn:hover, a.addAlumn:active {
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
                            <% if (session.getAttribute("errorAlumno") != null) {%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorAlumno")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorAlumno", null); %>
                            <% } else if(session.getAttribute("errorCoordinador") != null){%>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("errorCoordinador")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("errorCoordinador", null); %>
                            <% } %>
                            <% if(session.getAttribute("exito") != null){%>
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong><%=session.getAttribute("exito")%></strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <% session.setAttribute("exito", null); %>
                            <% }%>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <h4 class="card-title">Lista de coordinadores</h4>
                                            </div>
                                            <div class="col-md-7 mb-3 text-right">
                                                <a id="addCoordinadorBtn" href="nuevoCoordinador.jsp" class="addAlumn">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-person-add" viewBox="0 0 16 16">
                                                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0Zm-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0ZM8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4Z"/>
                                                    <path d="M8.256 14a4.474 4.474 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10c.26 0 .507.009.74.025.226-.341.496-.65.804-.918C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4s1 1 1 1h5.256Z"/>
                                                    </svg>
                                                    Nuevo Coordinador
                                                </a>
                                            </div>
                                        </div>
                                        <div class="table-responsive" >
                                            <table id="example" class="table table-hover table-striped" cellspacing="0" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Tipo Id</th>
                                                        <th>Id</th>
                                                        <th>Nombre</th>
                                                        <th>Teléfono</th>
                                                        <th>Correo</th>
                                                        <th>Estudio</th>
                                                        <th>Centro</th>
                                                        <th>Universidad</th>
                                                        <th>Operaciones</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% if(coordinadores.length > 0) {%>
                                                    <% for (CoordinadorDTO coordinador: coordinadores) { %>
                                                    <% EstudioDTO estudioCoordinador = studyServiceManager.getEstudioByCoordinator(coordinador.getNumIdentificacion(), session); %>
                                                    <tr>
                                                        <td><%=coordinador.getDocumentoIdentificacion()%></td>
                                                        <td><%=coordinador.getNumIdentificacion()%></td>
                                                        <td><%=coordinador.getNombre()%> <%=coordinador.getApellido1()%> <%=coordinador.getApellido2()%></td>
                                                        <td><%=coordinador.getTelefono()%></td>
                                                        <td><%=coordinador.getCorreo()%></td>
                                                        <% if(estudioCoordinador.getNombreEstudio().length() > 0) {%> <!-- para coordinadores que son nuevos se asigna el símbolo "-" -->
                                                        <td><%=estudioCoordinador.getNombreEstudio()%></td>
                                                        <td><%=estudioCoordinador.getCentro().getNombre()%></td>
                                                        <td><%=estudioCoordinador.getCentro().getUniversidad().getNombre()%></td>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col col-lg-2">
                                                                    <form action="editarCoordinador.jsp" method="POST" title="Edit" data-toggle="tooltip">
                                                                        <input type="hidden" id="numIdentificacionCoordinadorEditar" name="numIdentificacionCoordinadorEditar" value="<%=coordinador.getNumIdentificacion()%>">
                                                                        <button style="background: none;border: none;color: #FFC107;" type="submit">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                                                            <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <%}else{%>
                                                        <td>-</td>
                                                        <td>-</td>
                                                        <td>-</td>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col col-lg-2">
                                                                    <form action="editarCoordinador.jsp" method="POST" title="Edit" data-toggle="tooltip">
                                                                        <input type="hidden" id="numIdentificacionCoordinadorEditar" name="numIdentificacionCoordinadorEditar" value="<%=coordinador.getNumIdentificacion()%>">
                                                                        <button style="background: none;border: none;color: #FFC107;" type="submit">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                                                            <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                                <div class="col col-lg-2">
                                                                    <form action="deleteCoordinador" method="POST" title="Delete" data-toggle="tooltip">
                                                                        <input type="hidden" id="numIdentificacionCoordinadorBorrar" name="numIdentificacionCoordinadorBorrar" value="<%=coordinador.getNumIdentificacion()%>">
                                                                        <button style="background: none;border: none;color: #E34724;" type="button" data-bs-toggle="modal" data-bs-target="#removeCoordinadorModal-<%=coordinador.getNumIdentificacion()%>" data-id="<%=coordinador.getNumIdentificacion()%>">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                                                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                                                                            </svg>
                                                                        </button>
                                                                        <div class="modal" id="removeCoordinadorModal-<%=coordinador.getNumIdentificacion()%>" tabindex="-1" role="dialog">
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
                                                                                                    <p>¿Desea eliminar el coordinador con número de identificación <%=coordinador.getNumIdentificacion()%> ?</p>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                                                                        <button type="submit" class="btn btn-primary" id="removeCoordinadorBtn" style="background-color:#4B49AC;">Borrar</button>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <%}%>
                                                    </tr>
                                                    <%}%>
                                                    <%} else{ %>
                                                    <tr>
                                                        <td colspan="9" style="text-align: center;">No data available in table</td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
                                                        <td style="display:none;"></td>
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

        <!-- Añade paginación a la tabla -->
        <script type="text/javascript">
            $(document).ready(function () {
                $('#example').DataTable();
                $('.dataTables_length').addClass('bs-select');
            });
            var table = $('#example').DataTable({
                pageLength: 5,
                lengthMenu: [[5, 10, 20, -1], [5, 10, 20, 'All']]
            });
        </script>
    </body>
</html>

