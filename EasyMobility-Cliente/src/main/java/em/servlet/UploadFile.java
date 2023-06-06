/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: UploadFile.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.AcuerdoEstudiosDTO;
import em.common.dto.FicheroDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerAcuerdoEstudiosRestService;
import em.rest.client.ServerFicherosRestService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadFile", urlPatterns = {"/FileUploader"})
public class UploadFile extends HttpServlet {

    protected void processRequest(HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException {
        try {
            HttpSession session = _request.getSession(false);
            ServerFicherosRestService serverFicheroRestService = new ServerFicherosRestService();
            ServerAcuerdoEstudiosRestService serverAcuerdoEstudiosRestService = new ServerAcuerdoEstudiosRestService();

            boolean isMultipart = ServletFileUpload.isMultipartContent(_request);

            String numIdentificacionAlumno = "";
            String userType = "";

            if (isMultipart) {
                DiskFileItemFactory dfiFactory = new DiskFileItemFactory();
                dfiFactory.setSizeThreshold(500 * 1024);

                ServletFileUpload upload = new ServletFileUpload(dfiFactory);

                // Parse the request
                List<FileItem> items = upload.parseRequest(_request);
                Iterator<FileItem> it = items.iterator();
                FileItem item;
                String fileName = "";
                String mimeType = "";
                byte[] buffer = new byte[0];

                while (it.hasNext()) {
                    item = it.next();

                    if (item.isFormField()) {
                        numIdentificacionAlumno = item.getString();
                        if (session.getAttribute("userType") == "Alumn") {
                            userType = "Alumn";
                        } else {
                            userType = "Coordinator";
                        }
                    } else {
                        fileName = item.getName();
                        mimeType = item.getContentType();
                        String path = getServletContext().getRealPath("/");
                        File directorio = new File(path + "/uploads");
                        if (!directorio.exists()) {
                            directorio.mkdirs();
                        }

                        File uploadedFile = new File(directorio + "/" + fileName);
                        if (uploadedFile.exists()) {
                            uploadedFile.delete();
                        }

                        item.write(uploadedFile);

                        if (uploadedFile.exists() && uploadedFile.length() > 0) {
                            try ( FileInputStream fis = new FileInputStream(uploadedFile);  ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                                int bytesRead;
                                byte[] tempBuffer = new byte[4096]; // Tamaño del bloque de lectura

                                while ((bytesRead = fis.read(tempBuffer)) != -1) {
                                    bos.write(tempBuffer, 0, bytesRead);
                                }

                                buffer = bos.toByteArray();
                            }

                            uploadedFile.delete();
                            directorio.delete();
                        }
                    }
                }

                FicheroDTO fichero = new FicheroDTO();
                fichero.setFileName(fileName);
                fichero.setMimeType(mimeType);
                fichero.setFile(buffer);
                fichero.setAcuerdo((AcuerdoEstudiosDTO) serverAcuerdoEstudiosRestService.findAcuerdoAlumno(numIdentificacionAlumno));
                serverAcuerdoEstudiosRestService.close();

                serverFicheroRestService.create(fichero);
                serverFicheroRestService.close();
            } else {
                // NO es multipart
                if ("Alumn".equals(userType)) {
                    _response.sendRedirect("homeAlumno.jsp");
                } else {
                    _response.sendRedirect("homeCoordinador.jsp");
                }
            }

            session.setAttribute("exitoCargaFichero", SuccessMessages.EXITO_ADD_FILE.toString());
            if ("Alumn".equals(userType)) {
                _response.sendRedirect("homeAlumno.jsp");
            } else {
                _response.sendRedirect("verDocumentacionAlumno.jsp");
            }
        } catch (Exception e) {
            HttpSession session = _request.getSession(false);
            session.setAttribute("errorCargaFichero", ErrorMessages.ERROR_CARGA_FICHERO.toString());
            if (session.getAttribute("userType") == "Alumn") {
                _response.sendRedirect("homeAlumno.jsp");
            } else {
                _response.sendRedirect("verDocumentacionAlumno.jsp");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(response.encodeRedirectURL("error.jsp"));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
