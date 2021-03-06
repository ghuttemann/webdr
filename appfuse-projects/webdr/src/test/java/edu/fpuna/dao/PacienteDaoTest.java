/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.Constants;
import edu.fpuna.model.Address;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.Role;
import edu.fpuna.model.TipoSangre;
import edu.fpuna.service.GenericManager;
import java.util.Date;

/**
 *
 * @author Fernando
 */
public class PacienteDaoTest extends BaseDaoTestCase {
    
    // Objeto DAO de prueba
    private PacienteDao pacienteDao = null;
    private RoleDao roleDao = null;
    private GenericManager<TipoSangre, Long> tipoSangreManager = null;
    
    public void setPacienteDao(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
    }
    
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setTipoSangreManager(GenericManager<TipoSangre, Long> tipoSangreManager) {
        this.tipoSangreManager = tipoSangreManager;
    }
    
    public void testObtenerPaciente() throws Exception {
        log.debug("Iniciando Obtener Paciente...");
        Paciente res = pacienteDao.getPaciente(-4L);
        assertTrue(res != null && res.getId() == -4L);
        log.debug("Fin Obtener Paciente...");
    }
    
    /*
     * Test n�1. 
     */
    public void testGuardarPaciente() throws Exception {
        log.debug("Iniciando Guardar paciente...");

        /* Datos de Usuario */
        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setUsername("usuario_nuevo");
        nuevoPaciente.setPassword("usuario_nuevo");
        nuevoPaciente.setFirstName("Usuario");
        nuevoPaciente.setLastName("Nuevo");
        Address address = new Address();
        address.setCity("AS");
        address.setProvince("Central");
        address.setCountry("PY");
        address.setPostalCode("80210");
        nuevoPaciente.setAddress(address);
        nuevoPaciente.setEmail("aausuario_nuevo@appfuse.org");
        nuevoPaciente.setWebsite("http://fmancia.rasibledesigns.com");
        nuevoPaciente.setAccountExpired(false);
        nuevoPaciente.setAccountLocked(false);
        nuevoPaciente.setCredentialsExpired(false);
        nuevoPaciente.setEnabled(true);
        /*<--- Datos del usuario */
        
        /*---> Datos propios del Paciente */
        nuevoPaciente.setCedula(88112233);
        
        Date fechaIngreso = new Date(System.currentTimeMillis());
        nuevoPaciente.setFechaIngreso(fechaIngreso);
        
        Date fechaNacimiento = new Date(System.currentTimeMillis());
        nuevoPaciente.setFechaNacimiento(fechaNacimiento);
        
        log.debug("Agregando el Rol " + Constants.USER_ROLE + "...");        
        Role role = roleDao.getRoleByName(Constants.USER_ROLE);
        assertNotNull(role.getId());
        log.debug("---> role = " + role.getName());
        nuevoPaciente.addRole(role);
        /*<--- Datos del paciente */
        
        log.debug("Agregando el Tipo de Sangre ");
        TipoSangre tiposangre = tipoSangreManager.get(-1L);
        
        log.debug("---> tiposangre = " + tiposangre.getNombre());
        nuevoPaciente.setTipoSangre(tiposangre);
        
        log.debug("Guardando el Paciente...");
        Paciente p = pacienteDao.guardar(nuevoPaciente);
        flush();
        
        log.debug("Paciente Guardado...");
        
        assertNotNull(p.getId());
        Paciente retornado = pacienteDao.get(p.getId());
        assertEquals("usuario_nuevo", retornado.getPassword());

        log.debug("Guardar Confirmado...");
    }
}
