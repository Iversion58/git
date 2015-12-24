package cn.itcast.yycg.base.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.yycg.base.dao.BaseDao;

public class BaseDaoImpl<T extends Serializable> extends HibernateDaoSupport implements BaseDao<T> {

	
	
	//使用构造 方法注入entityClass
	private Class<T> entityClass;
	public BaseDaoImpl(Class entityClass){
		this.entityClass = entityClass;
	}
	
	@Override
	public T findById(Serializable id) throws Exception {
		
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public void update(T entity) throws Exception {
		this.getHibernateTemplate().update(entity);
		
	}

	@Override
	public void insert(T entity) throws Exception {
		this.getHibernateTemplate().save(entity);
		
	}
	
	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}
	@Override
	public List<T> findListByCriteria(DetachedCriteria criteria){
		return this.getHibernateTemplate().findByCriteria(criteria);
	}


	@Override
	public List<T> findListPageByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) throws Exception {
		return this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}

	@Override
	public T findSingleObjByCriteria(DetachedCriteria criteria){
		List<T> list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
	}


	@Override
	public long findListCountByCriteria(DetachedCriteria detachedCriteria)
			throws Exception {
		//设置投影，查询记录总数
		detachedCriteria.setProjection(Projections.rowCount());
		long totalCount = (Long) this.getHibernateTemplate().findByCriteria(detachedCriteria).get(0);
		// 清除投影，防止下次查询还使用原来的投影设置
		detachedCriteria.setProjection(null);
		// 重置结果策略,如果使用QBC进行关联查询时，设置setResultTransformer(DetachedCriteria.ROOT_ENTITY)，否则 返回是Object[]而不是List<pojo>
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		return totalCount;
	}

	@Override
	public DetachedCriteria createCriteria() throws Exception {
		return DetachedCriteria.forClass(entityClass);
	}


}
