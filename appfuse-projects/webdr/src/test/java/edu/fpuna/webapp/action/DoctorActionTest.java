/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.dao.EspecialidadDao;
import edu.fpuna.model.Address;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.EspecialidadManager;
import edu.fpuna.service.RoleManager;
import java.util.Date;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Clase de prueba del Action de Doctor.
 * @author ghuttemann
 */
public class DoctorActionTest extends BaseActionTestCase {
    
    private DoctorAction action;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        log.debug("Realizando SetUp...");
        super.onSetUpInTransaction();
        
        log.debug("Recuperando DoctorManager...");
        DoctorManager manager = (DoctorManager) applicationContext.getBean("doctorManager");
        RoleManager roleManager = (RoleManager) applicationContext.getBean("roleManager");
        EspecialidadManager especialidadManager = (EspecialidadManager) applicationContext.getBean("especialidadManager");
        
        action = new DoctorAction();
        action.setDoctorManager(manager);
        action.setRoleManager(roleManager);
        action.setEspecialidadManager(especialidadManager);
        
        // Agregar un doctor de prueba
        log.debug("Creando Doctor...");
        Doctor doctor = crearDoctor();
        
        log.debug("Guardando Doctor...");
        manager.guardarDoctor(doctor);
    }
    
    public void testList() throws Exception {
        log.debug("Probando lista de Doctores...");
        assertEquals(action.list(), DoctorAction.SUCCESS);
        
        log.debug("Probando cantidad de Doctores...");
        assertTrue(action.getDoctores().size() >= 1);
    }
    
    public void testListPorEspecialidad() throws Exception {
        log.debug("Probando lista de Doctores por Especialidad...");
        
        log.debug("\tRecuperando Especialidad...");
        Especialidad especialidad = obtenerEspecialidad();
        
        log.debug("\tRecuperando Doctor...");
        assertEquals(action.list(), DoctorAction.SUCCESS);
        assertFalse(action.getDoctores().isEmpty());
        Doctor doctor = action.getDoctores().get(0);
        
        log.debug("\tAgregando Especialidad al Doctor...");
        doctor.agregarEspecialidad(especialidad);
        
        log.debug("\tListando Doctores con Especialidad '" + especialidad + "'");
        action.setEspecialidad(especialidad.getNombre());
        assertEquals(action.list(), DoctorAction.SUCCESS);
        
        log.debug("\tProbando cantidad de Doctores recuperados...");
        assertTrue(action.getDoctores().size() >= 1);
    }
    
    public void testEdit() throws Exception {
        log.debug("Probando edit...");
        action.setId(-3L);
        assertNull(action.getDoctor());
        assertEquals("success", action.edit());
        assertNotNull(action.getDoctor());
        assertFalse(action.hasActionErrors());
    }
    
    public void testSave() throws Exception {
        log.debug("Probando save...");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setId(-3L);
        assertEquals("success", action.edit());
        assertNotNull(action.getDoctor());
        
        // update last name and save
        action.getDoctor().setLastName("Nuevo apellido");
        assertEquals("input", action.save());
        assertEquals("Nuevo apellido", action.getDoctor().getLastName());
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());
        assertNotNull(request.getSession().getAttribute("messages"));
    }

    public void testRemove() throws Exception {
        log.debug("Probando delete...");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setDelete("");
        Doctor doctor = new Doctor();
        doctor.setId(-3L);
        action.setDoctor(doctor);
        assertEquals("success", action.delete());
        assertNotNull(request.getSession().getAttribute("messages"));
    }
    
    /*
     * Metodo para crear un doctor
     */
    private Doctor crearDoctor() {
        Address address = new Address();
        address.setCity("Asunci�n");
        address.setPostalCode("123456");
        
        Doctor doctor = new Doctor();
        doctor.setUsername("chapatin");
        doctor.setEmail("chapatin@gmail.com");
        doctor.setFirstName("Roberto");
        doctor.setLastName("G�mez Bola�os");
        doctor.setAccountExpired(false);
        doctor.setAccountLocked(false);
        doctor.setCredentialsExpired(false);
        doctor.setPassword(".cambiar.");
        doctor.setAddress(address);
        doctor.setFechaNacimiento(new Date(System.currentTimeMillis()));
        doctor.setRegistro(12340000);
        
        Especialidad esp = obtenerEspecialidad();
        doctor.agregarEspecialidad(esp);
        
        return doctor;
    }

    private Especialidad obtenerEspecialidad() {
        log.debug("Recuperando EspecialidadDao...");
        EspecialidadDao espDao = (EspecialidadDao) applicationContext.getBean("especialidadDao");
        
        log.debug("Recuperando especialidades...");
        List result = espDao.getAll();
        assertFalse(result.isEmpty());
        
        return (Especialidad) result.get(0);
    }
}
