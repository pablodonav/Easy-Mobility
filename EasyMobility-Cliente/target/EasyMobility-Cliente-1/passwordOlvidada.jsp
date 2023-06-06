<%-- 
    Document   : passwordOlvidada.jsp
    Created on : 5 abr. 2023, 13:43:05
    Author     : Pablo Doñate
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Contraseña olvidada</title>
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
            <div class="container-fluid page-body-wrapper full-page-wrapper">
                <div class="content-wrapper d-flex align-items-center auth px-0">
                    <div class="row w-100 mx-0">
                        <div class="col-lg-6 mx-auto">
                            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                                <div class="brand-logo" style="text-align:center">
                                    <img src="images/logo-completo2.png" alt="logo" style="width:230px;height:100px;">
                                </div>
                                <div>
                                    <% if (session.getAttribute("error") != null) {%>
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("error")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("error", null); %>
                                    <% }else if (session.getAttribute("exitoReset") != null) {%>
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <strong><%=session.getAttribute("exitoReset")%></strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <% session.setAttribute("exitoReset", null); %>
                                    <% }%>
                                </div>
                                <h4>Contraseña olvidada</h4>
                                <div class="text-right">
                                    <a href="login.jsp" class="volver">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-arrow-left" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
                                        </svg>
                                        Volver
                                    </a>
                                </div>
                                <h6 class="font-weight-light">Por favor, introduce tu correo electrónico dado de alta:</h6>
                                <div class="row">
                                    <form class="pt-3" id="resetForm" action="reset_password" method="post">
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-lg" id="email" name="email" placeholder="Email" required>
                                        </div>
                                        <div class="mt-3">
                                            <input type="submit" value="Iniciar sesión">
                                        </div>
                                    </form>
                                </div>
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
        <!-- endinject -->
    </body>

</html>
