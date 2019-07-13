package couchbase.dao;

import com.couchbase.client.java.document.JsonDocument;
import couchbase.CouchbasePool;
import couchbase.entity.Document;
import couchbase.exception.DataBaseOperateException;
import couchbase.standard.CodeConductStandard;
import dispose.CopyClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

public class CouchbaseBaseDao<T extends Document> {

    /**
     * 泛型对象
     */
    private Class<T> entityClass;

    /**
     * 日志
     */
    private Log logger;

    /**
     * 无参构造
     */
    @SuppressWarnings("unchecked")
    public CouchbaseBaseDao() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            entityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
            logger = LogFactory.getLog(this.getClass());
        } else {
            entityClass = null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------基本应用

    /**
     * 新增对象
     *
     * @param document 泛型对象
     * @return 是否成功 true | false
     */
    public boolean add(T document) {
        return CouchbasePool.getConnection().operate.add(document);
    }

    /**
     * 修改对象
     *
     * @param document 泛型对象
     * @return 是否成功 true | false
     */
    public boolean update(T document) {
        return update(document, true);
    }

    /**
     * 修改对象
     *
     * @param document 泛型对象
     * @return 是否成功 true | false
     */
    public boolean update(T document, boolean isUpdateNotNull) {
        if (isUpdateNotNull) {
            String Id = getIdField(document);
            if (Id == null || Id.isEmpty()) {
                return false;
            }

            T doc = CouchbasePool.getConnection().operate.getByID(Id, entityClass);

            try {
                CopyClass.copyClass(document, doc, entityClass, false);
            } catch (IllegalAccessException e) {
                throw new DataBaseOperateException("诊断：修改对象传值失败");
            }

            return CouchbasePool.getConnection().operate.update(document);
        } else {
            return CouchbasePool.getConnection().operate.update(document);
        }
    }

    /**
     * 保存或修改对象
     *
     * @param document 泛型对象
     * @return 是否成功 true | false
     */
    public boolean save(T document) {
        return save(document, true);
    }

    /**
     * 保存或修改对象
     *
     * @param document 泛型对象
     * @return 是否成功 true | false
     */
    public boolean save(T document, boolean isUpdateNotNull) {
        String Id = getIdField(document);
        if (Id == null || Id.isEmpty()) {
            return CouchbasePool.getConnection().operate.save(document);
        } else {
            if (isUpdateNotNull) {
                T doc = CouchbasePool.getConnection().operate.getByID(Id, entityClass);

                try {
                    CopyClass.copyClass(document, doc, entityClass, false);
                } catch (IllegalAccessException e) {
                    throw new DataBaseOperateException("诊断：保存对象传值失败");
                }

                return CouchbasePool.getConnection().operate.save(document);
            } else {
                return CouchbasePool.getConnection().operate.save(document);
            }
        }
    }

    /**
     * 删除对象
     *
     * @param Id 泛型对象ID值
     * @return 是否成功 true | false
     */
    public boolean delete(String Id) {
        return CouchbasePool.getConnection().operate.delete(Id);
    }

    /**
     * 删除对象
     *
     * @param document 泛型对象
     * @return 是否成功 true | false
     */
    public boolean delete(T document) {
        return CouchbasePool.getConnection().operate.delete(document);
    }

    /**
     * 删除对象
     *
     * @param Ids 泛型对象ID值集合
     * @return 是否成功 true | false
     */
    public boolean delete(String[] Ids) {
        return CouchbasePool.getConnection().operate.delete(Ids);
    }

    /**
     * 删除对象
     *
     * @param Ids 泛型对象ID值集合
     * @return 是否成功 true | false
     */
    public boolean delete(List<String> Ids) {
        return CouchbasePool.getConnection().operate.delete(Ids);
    }

    /**
     * 根据主键查询
     *
     * @param Id 主键
     * @return 根据主键查询后的泛型对象
     */
    public T getByID(String Id) {
        return CouchbasePool.getConnection().operate.getByID(Id, entityClass);
    }

    /**
     * 查询单个实体所有数据
     *
     * @return 泛型对象的集合
     */
    public List<T> query() {
        return CouchbasePool.getConnection().operate.query(entityClass);
    }

    /**
     * 查询对象并且带锁
     *
     * @param Id 主键
     * @return 根据主键查询后的json文档对象
     */
    public JsonDocument getAndLock(String Id) {
        return CouchbasePool.getConnection().operate.getAndLock(Id, true);
    }

    /**
     * 查询对象并且带锁
     *
     * @param Id        主键
     * @param isRestart 是否启用自动重新加载直到上一个锁释放读取到
     * @return 根据主键查询后的json文档对象
     */
    public JsonDocument getAndLock(String Id, boolean isRestart) {
        return CouchbasePool.getConnection().operate.getAndLock(Id, isRestart);
    }

    /**
     * 解除带锁的json文档对象
     *
     * @param jsonDocument json文档对象
     * @return 是否解锁成功
     */
    public boolean unlock(JsonDocument jsonDocument) {
        return CouchbasePool.getConnection().operate.unlock(jsonDocument);
    }

    /**
     * 数据带锁时保存
     *
     * @param jsonDocument json文档对象
     * @return 是否保存成功
     */
    public boolean replace(JsonDocument jsonDocument) {
        return CouchbasePool.getConnection().operate.replace(jsonDocument);
    }

    /**
     * 查询库的总数
     *
     * @return 泛型对象的总数
     */
    public int query_count() {
        return (int) CouchbasePool.getConnection().operate.find(
                select("count(*)").
                        from(CouchbasePool.getConnection().getBucket_name()).
                        where(x(CodeConductStandard.Document_ClassTypeName).eq(s(entityClass.getName())))
        );
    }


    //------------------------------------------------------------------------------------------------------------------内部方法

    /**
     * 读取主键字段
     *
     * @param document 文档
     * @return 主键字段
     */
    private String getIdField(T document) {
        Field idField = null;
        for (Field field : document.getClass().getFields()) {
            if (field.getDeclaringClass().equals(Document.class)) {
                if (field.getName().equals(CodeConductStandard.Document_Id)) {
                    idField = field;
                }
            }
        }
        try {
            return idField != null ? idField.get(document) != null ? idField.get(document).toString() : null : null;
        } catch (IllegalAccessException e) {
            throw new DataBaseOperateException("诊断：传入对象为空或者未找到公共基类Document的主键");
        }
    }
}
