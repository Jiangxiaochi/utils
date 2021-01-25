package org.xc.utils.spring.data;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class QueryUtils {

    private final EntityManager entityManager;

    public QueryUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 分页查询，不支持Sort
     *
     * @param sql
     * @param clazz
     * @param pageable
     * @param parameters
     * @param <T>
     * @return
     */
    public <T> PageImpl<T> pageResults(String sql, Class<T> clazz, Pageable pageable, Object... parameters) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, parameters);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
        List<T> pageResult = query.getResultList();
        return new PageImpl<T>(pageResult, pageable, count(sql, parameters));
    }

    /**
     * 查询多条数据
     *
     * @param sql
     * @param clazz
     * @param parameters
     * @param <T>
     * @return
     */
    public <T> List<T> listResults(String sql, Class<T> clazz, Object... parameters) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, parameters);
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
        return query.getResultList();
    }

    /**
     * 查询单条数据
     *
     * @param sql
     * @param clazz
     * @param parameters
     * @param <T>
     * @return
     */
    public <T> T singleResult(String sql, Class<T> clazz, Object... parameters) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, parameters);
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
        return (T) query.getSingleResult();
    }

    private void setParameters(Query query, Object[] parameters) {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                query.setParameter(i + 1, parameters[i]);//parameters start whit 1
            }
        }
    }

    private int count(String sql, Object... parameters) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, parameters);
        return query.getResultList().size();
    }


}
