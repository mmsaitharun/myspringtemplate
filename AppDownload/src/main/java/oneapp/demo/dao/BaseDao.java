package oneapp.demo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import oneapp.demo.dto.BaseDto;
import oneapp.demo.entity.BaseDo;

/**
 * The <code>BaseDao</code> abstract class comprise abstract functions for CRUD
 * operations and a few utility functions for child
 * <code>Data Access Objects<code>
 * 
 * @version 2, 21-June-2012
 * @since CR8313
 */
@Repository("baseDao")
@Transactional
public abstract class BaseDao<E extends BaseDo, D extends BaseDto> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e){
			e.printStackTrace();
			return sessionFactory.openSession();
		}
	}
	
	protected abstract D exportDto(E entity);
	
	protected abstract E importDto(D fromDto);
}