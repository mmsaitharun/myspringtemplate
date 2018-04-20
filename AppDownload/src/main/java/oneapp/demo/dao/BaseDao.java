package oneapp.demo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import oneapp.demo.dto.BaseDto;
import oneapp.demo.entity.BaseDo;
import oneapp.demo.exception.ExecutionFault;
import oneapp.demo.exception.InvalidInputFault;
import oneapp.demo.exception.NoResultFault;
import oneapp.demo.exception.dto.MessageUIDto;
import oneapp.demo.util.EnOperation;
import oneapp.demo.util.ServicesUtil;



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
public abstract class BaseDao<E extends BaseDo, D extends BaseDto>  {
	
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
	
	// private Integer batchSize;
	// private Integer startIndex = 0;
	// protected final Integer noOfRetries = 5;
	protected final boolean isNotQuery = false;
	protected final boolean isQuery = true;

	// Message
	private final String noRecordFound = "No record found: "; // NOT USED



	// <WRAPPER OVER BASIC CRUD ONES, WITH IMPORT AND EXPORT FUNCTIONS>

	/**
	 * @param dto
	 *            the record to be created
	 * @throws ExecutionFault
	 *             in case for fatal error
	 * @throws InvalidInputFault
	 *             wrong inputs
	 * @throws NoResultFault
	 */
	public void create(D dto) throws ExecutionFault, InvalidInputFault,
			NoResultFault {
		persist(importDto(EnOperation.CREATE, dto));
	}

	/**
	 * @param dto
	 *            input object
	 * @return single record based on the objects primary key
	 * @throws ExecutionFault
	 *             in case for fatal error
	 * @throws InvalidInputFault
	 *             even key is missing
	 * @throws NoResultFault
	 *             when record could be retrieved
	 */
	public D getByKeys(D dto) throws ExecutionFault, InvalidInputFault,
			NoResultFault {
		return exportDto(getByKeysForFK(dto));
	}

	/**
	 * @return the entity, mainly used for setting FK
	 */
	public E getByKeysForFK(D dto) throws ExecutionFault, InvalidInputFault,
			NoResultFault {
		return find(importDto(EnOperation.RETRIEVE, dto));
	}

	/**
	 * @param dto
	 *            the record to be updated
	 * @return the updated record
	 * @throws ExecutionFault
	 *             in case for fatal error
	 * @throws InvalidInputFault
	 *             wrong inputs
	 * @throws NoResultFault
	 */
	public void update(D dto) throws ExecutionFault, InvalidInputFault,
			NoResultFault {
		// return exportDto(merge(importDto(EnOperation.UPDATE, dto)));
		getByKeysForFK(dto);
		 merge(importDto(EnOperation.UPDATE, dto));
	}

	public void delete(D dto) throws ExecutionFault, InvalidInputFault,
			NoResultFault {
		// remove(importDto(EnOperation.DELETE, dto));
		remove(getByKeysForFK(dto));
	}

	// </WRAPPER OVER BASIC CRUD ONES, WITH IMPORT AND EXPORT FUNCTIONS>

	// <BASIC CRUD OPERATIONS>
	protected void persist(E pojo) throws ExecutionFault {
		try {
		//	 getSession().getTransaction().begin();
			 getSession().persist(pojo);
		//	 getSession().getTransaction().commit();
		} catch (Exception e) {
			MessageUIDto faultInfo = new MessageUIDto();
			System.err.println(e.getMessage());
			faultInfo.setMessage(e.getMessage());
			String message = "Create of " + pojo.getClass().getSimpleName()
					+ " with keys " + pojo.getPrimaryKey() + " failed!";
			throw new ExecutionFault(message, faultInfo, e);
		}
	}

	@SuppressWarnings("unchecked")
	protected E find(E pojo) throws ExecutionFault, NoResultFault {
		E result = null;
		try {
			String primaryKey = (String) pojo.getPrimaryKey();
			result = (E) getSession().load(pojo.getClass(),primaryKey );
		} catch (Exception e) {
			// In case of connection or other JPA ones.
			MessageUIDto faultInfo = new MessageUIDto();
			faultInfo.setMessage(e.getMessage());
			String message = "Retrieve of " + pojo.getClass().getSimpleName()
					+ " by keys " + pojo.getPrimaryKey() + " failed!";
			throw new ExecutionFault(message, faultInfo, e);
		}
		if (result == null) {
			throw new NoResultFault(noRecordFound
					+ pojo.getClass().getSimpleName() + "#"
					+ pojo.getPrimaryKey());
			// TODO: Re-think noRecordFound message
		}
		return result;
	}

	protected void merge(E pojo) throws ExecutionFault {
		try {
			 getSession().update(pojo);
			// getSession().saveOrUpdate(pojo);
		} catch (Exception e) {
			MessageUIDto faultInfo = new MessageUIDto();
			faultInfo.setMessage(e.getMessage());
			String message = "Update of " + pojo.getClass().getSimpleName()
					+ " having keys " + pojo.getPrimaryKey() + " failed!";
			throw new ExecutionFault(message, faultInfo, e);
		}
	}

	protected void remove(E pojo) throws ExecutionFault {
		try {
			 getSession().delete(pojo);
			// getSession().remove(pojo);
		} catch (Exception e) {
			MessageUIDto faultInfo = new MessageUIDto();
			faultInfo.setMessage(e.getMessage());
			String message = "Delete of " + pojo.getClass().getSimpleName()
					+ " having keys " + pojo.getPrimaryKey() + " failed!";
			throw new ExecutionFault(message, faultInfo, e);
		}
	}

	// </BASIC CRUD OPERATIONS>
	// <SIGNATURE FOR DATA TRANSFER FUNCTIONS>
	private E importDto(EnOperation operation, D fromDto)
			throws InvalidInputFault, ExecutionFault, NoResultFault {
		if (fromDto != null) {
			fromDto.validate(operation);
			return importDto(fromDto);
		}
		throw new InvalidInputFault("Empty DTO passed");
	}

	/**
	 * @param fromDto
	 *            Data object from which data needs to be copied to a new entity
	 */
	protected abstract E importDto(D fromDto) throws InvalidInputFault,
			ExecutionFault, NoResultFault;

	// /**
	// * To be used this if copy over existing entity is needed
	// */
	// protected abstract E importDto(D fromDto, E toEntity)
	// throws InvalidInputFault, ExecutionFault;

	/**
	 * @param entity
	 *            Copies data back to a new transfer object from entity
	 */
	protected abstract D exportDto(E entity);

	protected List<D> exportDtoList(Collection<E> listDo) {
		List<D> returnDtos = null;
		if (!ServicesUtil.isEmpty(listDo)) {
			returnDtos = new ArrayList<D>(listDo.size());
			for (Iterator<E> iterator = listDo.iterator(); iterator.hasNext();) {
				returnDtos.add(exportDto(iterator.next()));
			}
		}
		return returnDtos;
	}

}