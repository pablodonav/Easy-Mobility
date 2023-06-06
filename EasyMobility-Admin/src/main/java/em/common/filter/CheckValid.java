/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CheckValid.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.filter;

import em.common.enums.ErrorMessages;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "CheckValid", urlPatterns = {"/*"})
public class CheckValid implements Filter {

    private static final boolean debug = false;

    private FilterConfig filterConfig = null;

    public CheckValid() {
    }

    /**
     * 
     * @param _request
     * @param _response
     * @throws IOException
     * @throws ServletException 
     */
    private void doBeforeProcessing(ServletRequest _request, ServletResponse _response)
            throws IOException, ServletException {
    }

    /**
     * 
     * @param _request
     * @param _response
     * @throws IOException
     * @throws ServletException 
     */
    private void doAfterProcessing(ServletRequest _request, ServletResponse _response)
            throws IOException, ServletException {

    }

    /**
     *
     * @param _request The servlet request we are processing
     * @param _response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest _request, ServletResponse _response,
            FilterChain _chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) _request;
        HttpServletResponse res = (HttpServletResponse) _response;
        String uri = req.getRequestURI();

        doBeforeProcessing(_request, _response);

        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId != null) {
            _chain.doFilter(_request, _response);
        } else {
            if ((uri.endsWith("login.jsp"))
                    || (uri.endsWith("login"))) {
                //System.out.println("Estoy en la página de login no hace falta autenticación");
                _chain.doFilter(_request, _response); // Authentication valid continues request processing

            } else {
                if ((!uri.endsWith("passwordOlvidada.jsp"))
                        && (!uri.endsWith("reset_password"))) {

                    session.setAttribute("errorSesion", ErrorMessages.ERROR_SESION_CADUCADA.toString());
                    res.sendRedirect(res.encodeRedirectURL("login.jsp"));
                } else {
                    _chain.doFilter(_request, _response);
                }
            }
        }

        doAfterProcessing(_request, _response);
    }

    /**
     * Return the filter configuration object for this filter.
     * 
     * @return 
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param _filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig _filterConfig) {
        this.filterConfig = _filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     * 
     * @param _filterConfig
     */
    @Override
    public void init(FilterConfig _filterConfig) {
        this.filterConfig = _filterConfig;
        if (_filterConfig != null) {
            if (debug) {
                log("UserIdValid:Initializing filter");
            }
        }
    }

    /**
     * 
     * @param _msg 
     */
    public void log(String _msg) {
        filterConfig.getServletContext().log(_msg);
    }

}
