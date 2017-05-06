package com.rephen.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 基础DAO操作
 * 
 * @author Rephen
 *
 * @param <T>
 */
public class GenericDAO<T> {

    private static final Logger logger = Logger.getLogger(GenericDAO.class);
    private static final String SQL_ID_TEMPLATE = "%s.%s";

    private SqlMapClient sqlMapClient;
    private SqlMapClientTemplate sqlMapClientTemplate;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    SqlMapClientTemplate getSqlMapClientTemplate() {
        if (null == sqlMapClientTemplate) {
            sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
        }
        return sqlMapClientTemplate;
    }

    /**
     * 获取完整的SQL_ID<br>
     * 规则：sql映射文件对应的namespace必须是对应的DAO接口完整名称
     *
     * @param sqlId sqlId
     * @return 完整的SQL ID
     */
    protected String getWholeSqlId(String sqlId) {
        return String.format(SQL_ID_TEMPLATE, getClass().getInterfaces()[0].getName(), sqlId);
    }

    protected Object insert(String statement) {
        return insert(statement, null);
    }

    protected Object insert(String statement, Object param) {
        try {
            if (null == param) {
                return getSqlMapClientTemplate().insert(getWholeSqlId(statement));
            } else {
                return getSqlMapClientTemplate().insert(getWholeSqlId(statement), param);
            }
        } catch (Exception e) {
           throw new DAOException(e);
        }
    }

    protected List<T> selectList(String statement) {
        return selectList(statement, null);
    }

    @SuppressWarnings("unchecked")
    protected List<T> selectList(String statement, Object param) {
        if (null == param) {
            return getSqlMapClientTemplate().queryForList(getWholeSqlId(statement));
        } else {
            return getSqlMapClientTemplate().queryForList(getWholeSqlId(statement), param);
        }
    }

    protected T selectOne(String statement) {
        return selectOne(statement, null);
    }

    protected T selectOne(String statement, Object param) {
        List<T> results;
        if (null == param) {
            results = selectList(statement);
        } else {
            results = selectList(statement, param);
        }

        if (!CollectionUtils.isEmpty(results)) {
            return results.get(0);
        } else {
            return null;
        }
    }

    protected int update(String statement) {
        return update(statement, null);
    }

    protected int update(String statement, Object param) {
        try {
            if (null == param) {
                return getSqlMapClientTemplate().update(getWholeSqlId(statement));
            } else {
                return getSqlMapClientTemplate().update(getWholeSqlId(statement), param);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    protected boolean isExist(String statement) {
        return isExist(statement, null);
    }

    protected boolean isExist(String statement, Object param) {
        Object o;
        if (null == param) {
            o = getSqlMapClientTemplate().queryForObject(getWholeSqlId(statement));
        } else {
            o = getSqlMapClientTemplate().queryForObject(getWholeSqlId(statement), param);
        }
        return o != null;
    }

    protected int delete(String statement) {
        return delete(statement, null);
    }

    protected int delete(String statement, Object param) {
        try {

            if (null == param) {
                return getSqlMapClientTemplate().delete(getWholeSqlId(statement));
            } else {
                return getSqlMapClientTemplate().delete(getWholeSqlId(statement), param);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    protected int count(String statement) {
        return count(statement, null);
    }

    protected int count(String statement, Object parameter) {
        Object result;
        if (parameter == null) {
            result = getSqlMapClientTemplate().queryForObject(getWholeSqlId(statement));
        } else {
            result = getSqlMapClientTemplate().queryForObject(getWholeSqlId(statement), parameter);
        }
        if (result == null)
            logger.error(getWholeSqlId(statement) + " return null");
        try {
            return Integer.parseInt(result.toString());
        } catch (NumberFormatException e) {
            throw new DAOException(e);
        }
    }

}
