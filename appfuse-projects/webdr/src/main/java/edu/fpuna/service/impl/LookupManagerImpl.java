package edu.fpuna.service.impl;

import edu.fpuna.dao.LookupDao;
import edu.fpuna.model.Especialidad;
import edu.fpuna.model.LabelValue;
import edu.fpuna.model.Role;
import edu.fpuna.service.LookupManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupManagerImpl extends UniversalManagerImpl implements LookupManager {
    private LookupDao dao;

    /**
     * Method that allows setting the DAO to talk to the data store with.
     * @param dao the dao implementation
     */
    public void setLookupDao(LookupDao dao) {
        super.dao = dao;
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllEspecialidades() {
        List<Especialidad> especialidades = dao.getEspecialidades();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Especialidad esp : especialidades) {
            list.add(new LabelValue(esp.getNombre(), esp.getNombre()));
        }

        return list;
    }
}
