/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ViewFile.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.FicheroDTO;
import em.rest.client.ServerFicherosRestService;
import java.io.BufferedOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewFile", urlPatterns = {"/viewFile"})
public class ViewFile extends HttpServlet {

    protected void processRequest(HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException {

        ServerFicherosRestService serverFicheroRestService = new ServerFicherosRestService();
        Long id;

        if (_request.getParameter("idFicheroDescargar") != null) {
            id = Long.valueOf(_request.getParameter("idFicheroDescargar"));
        } else {
            id = Long.valueOf(_request.getParameter("id"));
        }

        FicheroDTO fdb = serverFicheroRestService.find(FicheroDTO.class, id);

        if (fdb != null) {
            _response.setContentType(fdb.getMimeType());

            // Establecer el encabezado para mostrar el archivo en línea
            _response.setHeader("Content-Disposition", "inline;filename=\"" + fdb.getFileName() + "\"");

            byte[] buffer = fdb.getFile();
            BufferedOutputStream out = new BufferedOutputStream(_response.getOutputStream());
            out.write(buffer);
            out.flush();
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
        processRequest(request, response);
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
