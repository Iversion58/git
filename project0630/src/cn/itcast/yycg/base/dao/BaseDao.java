package cn.itcast.yycg.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {

	// 根据id查询查询对象的记录
	public T findById(Serializable id) throws Exception;

	// 更新对象信息
	public void update(T entity) throws Exception;
	
	//可以扩展其它方法
	public void insert(T entity) throws Exception;
	//删除
	public void delete(T entity);

	//创建criteria
	public DetachedCriteria createCriteria() throws Exception;
	//根据查询条件（综合查询条件，有多个查询条件），得到一个对象
	//通过criteria查询单个对象，只适用于单表查询
	public T findSingleObjByCriteria(DetachedCriteria detachedCriteria)throws Exception;
	//根据查询条件查询，得到一个列表（不分页）
	//通过criteria查询列表，只适用于单表查询
	public List<T> findListByCriteria(DetachedCriteria detachedCriteria)throws Exception;
	//根据查询条件查询，得到一个列表（分页）
	//通过criteria分页查询
	public List<T> findListPageByCriteria(DetachedCriteria detachedCriteria,int firstResult, int maxResults)throws Exception;
		
	//根据查询条件查询，得到符合查询条件记录总数
	//通过criteria查询列表总数
	public long findListCountByCriteria(DetachedCriteria detachedCriteria) throws Exception;

}
