/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Test.java
    Date: 23 abr. 2023
  
    Authors: Pablo Doñate & Adnana Dragut.
*/
package em.dummy;

import em.bd.AcuerdoEstudios;
import em.bd.Administrador;
import em.bd.Alumno;
import em.bd.AreaEstudios;
import em.bd.Centro;
import em.bd.Coordinador;
import em.bd.Estudio;
import em.bd.Idioma;
import em.bd.Localizacion;
import em.bd.Universidad;
import em.common.enums.PeriodoIntercambio;
import em.common.enums.TipoIdentificacion;
import static em.dummy.Hasher.getSHA512;
import em.rest.server.AcuerdoEstudiosFacadeRest;
import em.rest.server.AdministradorFacadeRest;
import em.rest.server.AlumnoFacadeRest;
import em.rest.server.AreaEstudiosFacadeRest;
import em.rest.server.CentroFacadeRest;
import em.rest.server.CoordinadorFacadeRest;
import em.rest.server.EstudioFacadeRest;
import em.rest.server.IdiomaFacadeRest;
import em.rest.server.LocalizacionFacadeRest;
import em.rest.server.UniversidadFacadeRest;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Ususario
 */
//@Singleton
//@Startup
//public class Test {
//
//    @EJB
//    LocalizacionFacadeRest locFac;
//    @EJB
//    UniversidadFacadeRest unFac;
//    @EJB
//    CentroFacadeRest ceFac;
//    @EJB
//    IdiomaFacadeRest idFac;
//    @EJB
//    AlumnoFacadeRest alumFac;
//    @EJB
//    AcuerdoEstudiosFacadeRest acEstFac;
//    @EJB
//    AreaEstudiosFacadeRest areaEstFac;
//    @EJB
//    CoordinadorFacadeRest coordFac;
//    @EJB
//    EstudioFacadeRest estuFac;
//    @EJB
//    AdministradorFacadeRest admFac;
//
//    @PostConstruct
//    public void init() {
//
//        // Crear una instancia de Localizacion
//        Localizacion localizacion = new Localizacion("Calle Principal 123", "Madrid", "España");
//        locFac.create(localizacion);
//
//        // Crear una instancia de Universidad y agregarla a la lista de centros adscritos
//        Universidad universidad = new Universidad("Universidad Complutense de Madrid", localizacion);
//        unFac.create(universidad);
//        
//        Centro centro = new Centro(1L, "Escuela Universitaria Politécnica", universidad, localizacion);
//        ceFac.create(centro);
//        
//        
//        Localizacion localizacion2 = new Localizacion("St. London Bridge", "London", "United Kingdom");
//        locFac.create(localizacion2);
//
//        // Crear una instancia de Universidad y agregarla a la lista de centros adscritos
//        Universidad universidad2 = new Universidad("University of Cambridge", localizacion2);
//        unFac.create(universidad2);
//        
//        Centro centro2 = new Centro(2L, "School of Science", universidad2, localizacion2);
//        ceFac.create(centro2);
//        
//        Coordinador coor1 = new Coordinador("11111111A", TipoIdentificacion.DNI, 123456789, "Juan", "Pérez", "García", "juan.perez@example.com", getSHA512("password"), false);
//        coordFac.create(coor1);
//        
//        //Crear una instancia de Alumno y Coordinador
//        Coordinador coor2 = new Coordinador("22222222A", TipoIdentificacion.DNI, 123456789, "Ana", "Prueba", "Prueba", "ana.prueba@example.com", getSHA512("password"), false);
//        coordFac.create(coor2);
//        
//        AreaEstudios ae = new AreaEstudios("Ingenierías");
//        areaEstFac.create(ae);
//        
//        
//        Estudio est = new Estudio(1L, "Ingeniería Informática", centro, ae, coor1);
//        estuFac.create(est);
//        Estudio est2 = new Estudio(2L, "Computer Science", centro2, ae, coor2);
//        estuFac.create(est2);
//
//        // Crear una instancia de Idioma
//        Idioma idioma = new Idioma(1L, "Inglés");
//        idFac.create(idioma);
//
//        Alumno alumno = new Alumno("22222222B", TipoIdentificacion.DNI, 987654321, "María", "Gómez", "Pérez", "maria.gomez@example.com", getSHA512("password"), false);
//        alumFac.create(alumno);
//
//        AcuerdoEstudios acuerdo = new AcuerdoEstudios(est, est2, "2022-23", PeriodoIntercambio.COMPLETO, alumno, idioma);
//        acEstFac.create(acuerdo);
//        
//        // Crear una instancia de Idioma
//        Idioma idioma2 = new Idioma(2L, "Español");
//        idFac.create(idioma2);
//        
//        Alumno alumno2 = new Alumno("33333333B", TipoIdentificacion.DNI, 987654321, "Peter", "Smith", "Johansson", "pter.smth@example.com", getSHA512("password"), false);
//        alumFac.create(alumno2);
//        
//        AcuerdoEstudios acuerdo2 = new AcuerdoEstudios(est2, est, "2022-23", PeriodoIntercambio.COMPLETO, alumno2, idioma2);
//        acEstFac.create(acuerdo2);
//        
//        Administrador admin = new Administrador("12345678A", TipoIdentificacion.DNI, 987654321, "Pablo", "Doñate", "Navarro", "pablo@gmail.com", getSHA512("password"), false);
//        admFac.create(admin);
//    }
//}
