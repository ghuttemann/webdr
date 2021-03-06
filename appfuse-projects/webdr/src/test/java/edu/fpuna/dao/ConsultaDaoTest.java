package edu.fpuna.dao;

import edu.fpuna.model.Consulta;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Notas;
import edu.fpuna.model.MedidasPaciente;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Clase que realiza las pruebas sobre el DAO de las 
 * Consulta (ConsultaDao)
 * @author mrodas
 */
public class ConsultaDaoTest extends BaseDaoTestCase {

    // Objeto DAO de prueba
    private ConsultaDao consultaDao = null;
    private DoctorDao doctorDao = null;
    private PacienteDao pacienteDao = null;

    public void setConsultaDao(ConsultaDao consultaDao) {
        this.consultaDao = consultaDao;
    }

    public void setDoctorDao(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    public void setPacienteDao(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    /*
     * Test n�1. Prueba de busqueda de una Consulta por su id
     */
    public void testObtenerConsultaId() throws Exception {
        log.debug("Testing(1) ObtenerConsultaId...");

        Consulta consulta = consultaDao.obtenerConsultaId(-1L);

        assertTrue(consulta.getId() == -1);
        log.debug("Testing ObtenerConsultaId ha Finalizado.");
    }

    /*
     * Test n�2. Prueba de busqueda de Consulta por Paciente
     */
    public void testObtenerConsultasPaciente() throws Exception {
        log.debug("Testing(2) ObtenerConsultasPaciente...");

        List<Consulta> consultas = consultaDao.obtenerConsultasPaciente("fmancia");

        assertTrue(consultas.isEmpty() != true);
        log.debug("Cantidad de consultas del Paciente: " + consultas.size());
        log.debug("Testing ObtenerConsultasPaciente ha Finalizado.");
    }

    /*
     * Test n�3. Prueba de busqueda de Consulta por Doctor
     */
    public void testObtenerConsultasDoctor() throws Exception {
        log.debug("Testing(3) ObtenerConsultasDoctor...");

        List<Consulta> consultas = consultaDao.obtenerConsultasDoctor("ghuttemann");

        assertTrue(consultas.isEmpty() != true);
        log.debug("Cantidad de consultas del Doctor: " + consultas.size());
        log.debug("Testing ObtenerConsultasDoctor ha Finalizado.");
    }

    /*
     * Test n�4. Prueba de busqueda de Consulta por Fecha
     */
    public void testObtenerConsultasFecha() throws Exception { 
        log.debug("Testing(4) ObtenerConsultasFecha...");
        Date fecha1 = new Date(1, 1, 1);
        Date fecha2 = new Date();
        
        log.debug("Recuperando doctor(id = -3)...");
        Doctor doctor = doctorDao.get(-3L);
        
        log.debug("Recuperando consultas del doctor(id = -3)...");
        List<Consulta> consultas = consultaDao.obtenerConsultasFecha(doctor.getUsername(), fecha1, fecha2);
        log.debug("Cantidad de consultas del doctor(" + doctor.getUsername() + "): " + consultas.size());
        
        assertFalse(consultas.isEmpty());
        log.debug("Testing ObtenerConsultasFecha ha Finalizado.");
    }

    /*
     * Test n�5. Prueba de eliminar una consulta
     */
    public void testEliminarConsulta() throws Exception {
        log.debug("Testing(5) EliminarConsulta...");

        consultaDao.remove((long) -1);

        log.debug("Testing EliminarConsulta ha Finalizado.");
    }

    /*
     * Test n�6. Prueba de guardar una consulta
     */
    public void testGuardarConsulta() throws Exception {
        log.debug("Testing(6) GuardarConsulta...");

        Consulta consulta = new Consulta();
        consulta.setId(new Long(3));
        consulta.setFecha(new Timestamp(80,10,5,11,30,0,0));
        MedidasPaciente mpacientes = new MedidasPaciente(new Double(100), new Integer(200), new Integer(30), new Boolean(false));
        consulta.setMedidasPaciente(mpacientes);
        consulta.setNotas(new Notas("Irritaciones", "Alergia", "Alergin 100gr", "200ml cada 12hrs"));
        consulta.setDoctor(doctorDao.obtenerPorNombre("ghuttemann"));
        consulta.setPaciente(pacienteDao.getPaciente("fmancia"));

        consulta = consultaDao.save(consulta);

        log.debug("Testing GuardarConsulta ha Finalizado.");
    }
}
