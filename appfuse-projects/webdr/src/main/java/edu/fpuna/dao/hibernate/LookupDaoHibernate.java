package edu.fpuna.dao.hibernate;

import java.util.List;

import edu.fpuna.dao.LookupDao;
import edu.fpuna.model.Especialidad;
import edu.fpuna.model.Role;

/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupDaoHibernate extends UniversalDaoHibernate implements LookupDao {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return getHibernateTemplate().find("from Role order by name");
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Especialidad> getEspecialidades() {
        log.debug("Retrieving all especialidades names...");

        return getHibernateTemplate().find("from Especialidad order by nombre");
    }
}
