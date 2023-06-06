/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: RemoveFile.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.enums.SuccessMessages;
import em.rest.client.ServerFicherosRestService;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RemoveFile", urlPatterns = {"/removeFile"})
public class RemoveFile extends HttpServlet {

    protected void processRequest(HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException {

        HttpSession session = _request.getSession(false);

        Long id = Long.valueOf(_request.getParameter("idFicheroBorrar"));

        ServerFicherosRestService serverFicheroRestService = new ServerFicherosRestService();

        serverFicheroRestService.remove(id);
        serverFicheroRestService.close();

        String numIdentificacionAlumno = (String) session.getAttribute("numIdentificacionAlumnoFichero");
        session.setAttribute("exitoBorradoFichero", SuccessMessages.EXITO_REMOVE_FILE.toString());
        if (session.getAttribute("userType") == "Alumn") {
            _response.sendRedirect(_response.encodeRedirectURL("homeAlumno.jsp"));
        } else {
            _response.sendRedirect(_response.encodeRedirectURL("verDocumentacionAlumno.jsp"));
            
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
