/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: StudentServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.AlumnoDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.PasswordGeneratorRestClient;
import em.rest.client.ServerAlumnosRestService;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpSession;

public class StudentServiceManager {

    private static StudentServiceManager moiMeme = null; //Singleton

    /**
     * Construye un StudentServiceManager.
     *
     */
    public StudentServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return UserServiceManager
     */
    public static StudentServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new StudentServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve la lista de alumnos dados de alta.
     * 
     * @param _session
     * @return 
     */
    public AlumnoDTO[] getAlumnos(HttpSession _session) {
        AlumnoDTO[] alumnos = new AlumnoDTO[0];

        try {
            ServerAlumnosRestService emclient = new ServerAlumnosRestService();

            alumnos = emclient.findAll(AlumnoDTO[].class);
            emclient.close();

            for (AlumnoDTO alumno : alumnos) {
                System.out.println("ALUMNO --> " + alumno.toString());
            }

            if (alumnos == null) {
                _session.setAttribute("errorAlumno", ErrorMessages.ERROR_OBTENER_ALUMNOS);
                alumnos = new AlumnoDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            alumnos = new AlumnoDTO[0];
        }

        return alumnos;
    }

    /**
     * Devuelve un alumno con numIdentificacion = _numIdentificacion.
     * 
     * @param _numIdentificacion
     * @param _session
     * @return 
     */
    public AlumnoDTO getAlumno(String _numIdentificacion, HttpSession _session) {
        AlumnoDTO alumno = new AlumnoDTO();

        try {
            ServerAlumnosRestService emclient = new ServerAlumnosRestService();

            alumno = emclient.find(AlumnoDTO.class, _numIdentificacion);
            emclient.close();

            if (alumno == null) {
                _session.setAttribute("errorAlumno", ErrorMessages.ERROR_OBTENER_ALUMNO);
                alumno = new AlumnoDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            alumno = new AlumnoDTO();
        }

        return alumno;
    }

    /**
     * Da de alta un nuevo alumno.
     * 
     * @param _alumno
     * @param _session
     * @return 
     */
    public int addAlumno(AlumnoDTO _alumno, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAlumnosRestService emclient = new ServerAlumnosRestService();
            statusServer = emclient.create(_alumno);
            emclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println(statusServer);
                System.out.println("ALUMNO --> " + _alumno.toJson());
                _session.setAttribute("errorAlumno", ErrorMessages.ERROR_ADD_ALUMNO.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_ADD_ALUMNO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Elimina el alumno con identificador = _numIdentificacionAlumno.
     * 
     * @param _numIdentificacionAlumno
     * @param _session
     * @return 
     */
    public int deleteAlumno(String _numIdentificacionAlumno, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAlumnosRestService emclient = new ServerAlumnosRestService();
            statusServer = emclient.remove(_numIdentificacionAlumno);
            emclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorAlumno", ErrorMessages.ERROR_DELETE_ALUMNO.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_DELETE_ALUMNO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Modifica un alumno existente.
     * 
     * @param _alumno
     * @param _session
     * @return 
     */
    public int editAlumno(AlumnoDTO _alumno, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAlumnosRestService emclient = new ServerAlumnosRestService();
            statusServer = emclient.edit(_alumno, _alumno.getNumIdentificacion());
            emclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println(statusServer);
                System.out.println("ALUMNO --> " + _alumno.toJson());
                _session.setAttribute("errorAlumno", ErrorMessages.ERROR_ADD_ALUMNO.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_EDIT_ALUMNO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Genera una contraseña aleatoria para el nuevo alumno.
     * 
     * @param _session
     * @return 
     */
    public String generateAlumnoPwd(HttpSession _session) {
        String generatedPWD = "";

        PasswordGeneratorRestClient pwdclient = new PasswordGeneratorRestClient();
        String json = pwdclient.getRandomPassword();
        pwdclient.close();

        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject root = reader.readObject();

        generatedPWD = root.getString("password");

        System.out.println("PWD GENERATED --> " + generatedPWD);

        return generatedPWD;
    }

    /**
     * Devuelve el alumno con correo = _email.
     * 
     * @param _email
     * @param _session
     * @return 
     */
    public AlumnoDTO getAlumnoByEmail(String _email, HttpSession _session) {
        AlumnoDTO alumno = new AlumnoDTO();

        ServerAlumnosRestService alumclient = new ServerAlumnosRestService();

        alumno = alumclient.findByEmail(AlumnoDTO.class, _email);
        alumclient.close();

        return alumno;
    }

}
