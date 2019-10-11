package couchbase.connection.operate;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import couchbase.convert.N1qlQueryToObject;
import couchbase.entity.BucketTime;
import couchbase.entity.Document;
import couchbase.entity.Indexes;
import couchbase.exception.DataBaseOperateException;
import couchbase.standard.CodeConductStandard;
import dispose.DateDispose;
import encrypt.UUIDUtil;

import java.lang.reflect.Field;
import java.util.*;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

/**
 * CouchBase操作类
 */
public class CouchbaseOperate {
    /**
     * 数据桶
     */
    private Bucket bucket;
    /**
     * 转换工具类对象
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 数据桶各项时间配置类
     */
    private BucketTime bucketTime;

    /**
     * 创建数据库库操作对象
     *
     * @param b              数据库库
     * @param isIgnoreGetSet 是否忽略get.set
     */
    public CouchbaseOperate(Bucket b, boolean isIgnoreGetSet, BucketTime time) {
        bucket = b;
        bucketTime = time;
        if (isIgnoreGetSet) {
            //忽略get.set
            mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
            mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        }
        //有属性不能映射的时候不报错   比如json中多一个属性...或者是对象中多几个属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //检查是否有索引
//        checkIndex();
    }

    //------------------------------------------------------------------------------------------------------------------数据操作

    /**
     * 添加
     *
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean add(T doc) {
        try {
            if (doc != null) {
                bucket.insert(
                        documentInitialize(doc.getClass().getSimpleName() + "-" + UUIDUtil.getUUID32(), doc),
                        bucketTime.TIME_ADD,
                        bucketTime.TIME_UNIT
                );
                return true;
            } else {
                throw new DataBaseOperateException("诊断：传入对象为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加
     *
     * @param id  自定义主键
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean add(String id, T doc) {
        try {
            if (id != null && !id.isEmpty() && doc != null) {
                bucket.insert(
                        documentInitialize(id, doc),
                        bucketTime.TIME_ADD,
                        bucketTime.TIME_UNIT
                );
                return true;
            } else {
                throw new DataBaseOperateException("诊断：传入对象为空或者主键为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID修改对应文档
     *
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean update(T doc) {
        try {
            if (doc != null) {
                Field idField = getIdField(doc);
                if (idField != null) {
                    if (idField.get(doc) == null) {
                        throw new DataBaseOperateException("诊断：传入对象主键错误！", new NullPointerException());
                    } else {
                        JsonDocument jsonDocument = bucket.get(idField.get(doc).toString());
                        if (jsonDocument != null) {
                            documentAssignment(jsonDocument, doc);
                            bucket.replace(objectToT(doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                            return true;
                        } else {
                            throw new DataBaseOperateException("诊断：传入对象根据主键未查询到匹配数据！", new NullPointerException());
                        }
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入对象未继承Document的对象！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入对象为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID修改对应文档
     *
     * @param id  文档ID
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean update(String id, T doc) {
        try {
            if (id == null || id.isEmpty()) {
                throw new DataBaseOperateException("诊断：传入对象主键错误！", new NullPointerException());
            } else {
                if (doc != null) {
                    Field idField = getIdField(doc);
                    if (idField != null) {
                        JsonDocument jsonDocument = bucket.get(id);
                        if (jsonDocument != null) {
                            documentAssignment(jsonDocument, doc);
                            bucket.replace(objectToT(doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                            return true;
                        } else {
                            throw new DataBaseOperateException("诊断：传入对象根据主键未查询到匹配数据！", new NullPointerException());
                        }
                    } else {
                        throw new DataBaseOperateException("诊断：传入对象未继承Document的对象！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入对象为空！", new NullPointerException());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据语句修改
     *
     * @param statement 修改语句couchbase-client写法
     * @return 受影响行数
     */
    public int update(Statement statement) {
        try {
            if (statement != null && !statement.toString().isEmpty()) {
                N1qlQueryResult result = bucket.query(N1qlQuery.simple(statement), bucketTime.TIME_UPDATE_CONDITION, bucketTime.TIME_UNIT);
                if (result.status().equalsIgnoreCase(CodeConductStandard.Success)) {
                    return result.info().mutationCount();
                } else if (result.status().equalsIgnoreCase(CodeConductStandard.Errors)) {
                    return 0;
                } else {
                    throw new DataBaseOperateException("诊断：传入的n1ql语法错误！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入语句对象为空或者未能将传入的对象转换成n1ql！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据语句修改
     *
     * @param n1ql 修改语句n1ql写法
     * @return 受影响行数
     */
    public int update(String n1ql) {
        try {
            if (n1ql != null && !n1ql.isEmpty()) {
                N1qlQueryResult result = bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_UPDATE_CONDITION, bucketTime.TIME_UNIT);
                if (result.status().equalsIgnoreCase(CodeConductStandard.Success)) {
                    return result.info().mutationCount();
                } else if (result.status().equalsIgnoreCase(CodeConductStandard.Errors)) {
                    return 0;
                } else {
                    throw new DataBaseOperateException("诊断：传入的n1ql语法错误！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的n1ql语句为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据ID修改对应文档
     *
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean save(T doc) {
        try {
            if (doc != null) {
                Field idField = getIdField(doc);
                if (idField != null) {
                    Object Id = idField.get(doc);
                    if (Id != null && !Id.toString().isEmpty()) {
                        JsonDocument jsonDocument = bucket.get(idField.get(doc).toString());
                        if (jsonDocument != null) {
                            documentAssignment(jsonDocument, doc);
                            bucket.upsert(objectToT(doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                            return true;
                        } else {
                            bucket.upsert(documentInitialize(doc.getClass().getSimpleName() + "-" + UUIDUtil.getUUID32(), doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                            return true;
                        }
                    } else {
                        bucket.upsert(documentInitialize(doc.getClass().getSimpleName() + "-" + UUIDUtil.getUUID32(), doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                        return true;
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入对象未继承Document的对象！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入对象为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID修改对应文档
     *
     * @param id  文档ID
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean save(String id, T doc) {
        try {
            if (doc != null) {
                Field idField = getIdField(doc);
                if (idField != null) {
                    JsonDocument jsonDocument = bucket.get(id);
                    if (jsonDocument != null) {
                        documentAssignment(jsonDocument, doc);
                        bucket.upsert(objectToT(doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                        return true;
                    } else {
                        bucket.upsert(documentInitialize(id != null && !id.isEmpty() ? id : doc.getClass().getSimpleName() + "-" + UUIDUtil.getUUID32(), doc), bucketTime.TIME_UPDATE, bucketTime.TIME_UNIT);
                        return true;
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入对象未继承Document的对象！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入对象为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文档删除
     *
     * @param doc 文档内容
     * @param <T> 文档类型
     * @return true | false
     */
    public <T extends Document> boolean delete(T doc) {
        try {
            if (doc != null) {
                Field idField = getIdField(doc);
                if (idField != null) {
                    bucket.remove(idField.get(doc).toString(), bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                    return true;
                } else {
                    throw new DataBaseOperateException("诊断：未找到公共基类Document的主键！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入对象为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID删除
     *
     * @param id 自定义主键
     * @return true | false
     */
    public boolean delete(String id) {
        try {
            if (id != null && !id.isEmpty()) {
                bucket.remove(id, bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                return true;
            } else {
                throw new DataBaseOperateException("诊断：传入参数值为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文档集合删除
     *
     * @param docs   文档集合
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return true | false
     */
    public <T extends Document> boolean delete(List<T> docs, Class<T> tClass) {
        try {
            if (docs != null && docs.size() > 0) {
                if (tClass != null) {
                    Field idField = getIdField(tClass);
                    if (idField != null) {
                        for (T t : docs) {
                            bucket.remove(idField.get(t).toString(), bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                        }
                        return true;
                    } else {
                        throw new DataBaseOperateException("诊断：传入对象未继承Document的对象！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的处理对象集合为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID集合删除
     *
     * @param ids 文档id集合
     * @return true | false
     */
    public boolean delete(List<String> ids) {
        try {
            if (ids != null && ids.size() > 0) {
                for (int i = 0; i < ids.size(); i++) {
                    if (ids.get(i) != null && !ids.get(i).isEmpty()) {
                        bucket.remove(ids.get(i), bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                    } else {
                        throw new DataBaseOperateException("诊断：集合中第 " + i + " 位为空！", new NullPointerException());
                    }
                }
                return true;
            } else {
                throw new DataBaseOperateException("诊断：传入集合参数值为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID集合删除
     *
     * @param ids 文档id集合
     * @return true | false
     */
    public boolean delete(String[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    if (ids[i] != null && !ids[i].isEmpty()) {
                        bucket.remove(ids[i], bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                    } else {
                        throw new DataBaseOperateException("诊断：集合中第 " + i + " 位为空！", new NullPointerException());
                    }
                }
                return true;
            } else {
                throw new DataBaseOperateException("诊断：传入集合参数值为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据语句删除
     *
     * @param statement 删除语句couchbase-client写法
     * @param tClass    文档类型
     * @param <T>       文档类型
     * @return 受影响行数
     */
    public <T extends Document> int delete(Statement statement, Class<T> tClass) {
        try {
            if (statement != null && !statement.toString().isEmpty()) {
                if (tClass != null) {
                    List<T> result = n1qlQueryToT(bucket.query(N1qlQuery.simple(statement), bucketTime.TIME_DELETE_CONDITION, bucketTime.TIME_UNIT), tClass);
                    for (T t : result) {
                        bucket.remove(t.Id, bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                    }
                    return result.size();
                } else {
                    throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入语句对象为空或者未能将传入的对象转换成n1ql！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据语句删除
     *
     * @param n1ql   删除语句n1ql写法
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return 受影响行数
     */
    public <T extends Document> int delete(String n1ql, Class<T> tClass) {
        try {
            if (n1ql != null && !n1ql.isEmpty()) {
                String behaviour = n1ql.substring(0, 7);
                if (behaviour.equalsIgnoreCase(CodeConductStandard.Delete)) {
                    N1qlQueryResult result = bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_DELETE_CONDITION, bucketTime.TIME_UNIT);
                    if (result.status().equalsIgnoreCase(CodeConductStandard.Success)) {
                        return result.info().mutationCount();
                    } else if (result.status().equalsIgnoreCase(CodeConductStandard.Errors)) {
                        return 0;
                    } else {
                        throw new DataBaseOperateException("诊断：传入的n1ql语法错误！", new NullPointerException());
                    }
                } else if (behaviour.equalsIgnoreCase(CodeConductStandard.Select)) {
                    if (tClass != null) {
                        List<T> result = n1qlQueryToT(bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_DELETE_CONDITION, bucketTime.TIME_UNIT), tClass);
                        for (T t : result) {
                            bucket.remove(t.Id, bucketTime.TIME_DELETE, bucketTime.TIME_UNIT);
                        }
                        return result.size();
                    } else {
                        throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的n1ql非DELETE语句也非SELECT语句无法处理！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的n1ql语句为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询集合
     *
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return 文档集合
     */
    public <T extends Document> List<T> query(Class<T> tClass) {
        try {
            if (tClass != null) {
                if (bucket.name() != null && !bucket.name().isEmpty()) {
                    return n1qlQueryToT(bucket.query(N1qlQuery.simple(
                            select("*").from(bucket.name()).where(
                                    x(CodeConductStandard.Document_ClassTypeName).eq(s(tClass.getName()))
                            )
                    ), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), tClass);
                } else {
                    throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据语句查询
     *
     * @param statement 删除语句couchbase-client写法
     * @param tClass    文档类型
     * @param <T>       文档类型
     * @return 文档集合
     */
    public <T extends Document> List<T> query(Statement statement, Class<T> tClass) {
        try {
            if (statement != null && !statement.toString().isEmpty()) {
                if (tClass != null) {
                    if (bucket.name() != null && !bucket.name().isEmpty()) {
                        List<T> list = n1qlQueryToT(bucket.query(N1qlQuery.simple(statement), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), tClass);
                        if (list != null && list.size() > 0) {
                            return list;
                        } else {
                            return null;
                        }
                    } else {
                        throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入语句对象为空或者未能将传入的对象转换成n1ql！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据语句查询
     *
     * @param n1ql   删除语句n1ql写法
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return 文档集合
     */
    public <T extends Document> List<T> query(String n1ql, Class<T> tClass) {
        try {
            if (n1ql != null && !n1ql.isEmpty()) {
                if (tClass != null) {
                    if (bucket.name() != null && !bucket.name().isEmpty()) {
                        List<T> list = n1qlQueryToT(bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), tClass);
                        if (list != null && list.size() > 0) {
                            return list;
                        } else {
                            return null;
                        }
                    } else {
                        throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的n1ql语句为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询对象
     *
     * @param id     文档id
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return 文档集合
     */
    public <T extends Document> T getByID(String id, Class<T> tClass) {
        try {
            if (tClass != null) {
                if (bucket.name() != null && !bucket.name().isEmpty()) {
                    List<T> list = n1qlQueryToT(bucket.query(N1qlQuery.simple(
                            select("*").from(bucket.name()).where(
                                    x(CodeConductStandard.Document_Id).eq(s(id))
                                            .and(x(CodeConductStandard.Document_ClassTypeName).eq(s(tClass.getName())))
                            )
                    ), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), tClass);
                    if (list != null && list.size() > 0) {
                        return list.get(0);
                    } else {
                        return null;
                    }
                } else {
                    throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据语句查询
     *
     * @param statement 删除语句couchbase-client写法
     * @param tClass    文档类型
     * @param <T>       文档类型
     * @return 文档对象
     */
    public <T extends Document> T get(Statement statement, Class<T> tClass) {
        try {
            if (statement != null && !statement.toString().isEmpty()) {
                if (tClass != null) {
                    if (bucket.name() != null && !bucket.name().isEmpty()) {
                        List<T> list = n1qlQueryToT(bucket.query(N1qlQuery.simple(statement), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), tClass);
                        if (list != null && list.size() > 0) {
                            return list.get(0);
                        } else {
                            return null;
                        }
                    } else {
                        throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入语句对象为空或者未能将传入的对象转换成n1ql！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据语句查询
     *
     * @param n1ql   删除语句n1ql写法
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return 文档对象
     */
    public <T extends Document> T get(String n1ql, Class<T> tClass) {
        try {
            if (n1ql != null && !n1ql.isEmpty()) {
                if (tClass != null) {
                    if (bucket.name() != null && !bucket.name().isEmpty()) {
                        List<T> list = n1qlQueryToT(bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), tClass);
                        if (list != null && list.size() > 0) {
                            return list.get(0);
                        } else {
                            return null;
                        }
                    } else {
                        throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的n1ql语句为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询数
     *
     * @param statement 删除语句couchbase-client写法
     * @return 内容中的数
     */
    public Number find(Statement statement) {
        try {
            if (statement != null && !statement.toString().isEmpty()) {
                N1qlQueryResult result = bucket.query(N1qlQuery.simple(statement), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT);
                if (result.status().equalsIgnoreCase(CodeConductStandard.Success)) {
                    for (N1qlQueryRow row : result) {
                        for (String name : row.value().getNames()) {
                            return (Number) row.value().get(name);
                        }
                    }
                    return 0;
                } else if (result.status().equalsIgnoreCase(CodeConductStandard.Errors)) {
                    throw new DataBaseOperateException("诊断：传入的n1ql查询结果错误！", new NullPointerException());
                } else {
                    throw new DataBaseOperateException("诊断：传入的n1ql语法错误！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询数
     *
     * @param n1ql 删除语句n1ql写法
     * @return 内容中的数
     */
    public Number find(String n1ql) {
        try {
            if (n1ql != null && !n1ql.isEmpty()) {
                N1qlQueryResult result = bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT);
                if (result.status().equalsIgnoreCase(CodeConductStandard.Success)) {
                    for (N1qlQueryRow row : result) {
                        for (String name : row.value().getNames()) {
                            return (Number) row.value().get(name);
                        }
                    }
                    return 0;
                } else if (result.status().equalsIgnoreCase(CodeConductStandard.Errors)) {
                    throw new DataBaseOperateException("诊断：传入的n1ql查询结果错误！", new NullPointerException());
                } else {
                    throw new DataBaseOperateException("诊断：传入的n1ql语法错误！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的处理对象类型为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //------------------------------------------------------------------------------------------------------------------数据锁处理

    /**
     * 查询对象并且带锁
     *
     * @param Id        主键
     * @param isRestart 是否启用自动重新加载直到上一个锁释放读取到
     * @return 根据主键查询后的json文档对象
     */
    public JsonDocument getAndLock(String Id, boolean isRestart) {
        try {
            return bucket.getAndLock(Id, bucketTime.TIME_LOCK);
        } catch (Exception e) {
            if (isRestart) {
                try {
                    Thread.sleep(bucketTime.TIME_LOCK_RESTART);
                } catch (InterruptedException e1) {
                    return getAndLock(Id, true);
                }
                return getAndLock(Id, true);
            } else {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 解除带锁的json文档对象
     *
     * @param jsonDocument json文档对象
     * @return 是否解锁成功
     */
    public boolean unlock(JsonDocument jsonDocument) {
        return bucket.unlock(jsonDocument);
    }

    /**
     * 数据带锁时保存
     *
     * @param jsonDocument json文档对象
     * @return 是否保存成功
     */
    public boolean replace(JsonDocument jsonDocument) {
        try {
            bucket.replace(jsonDocument);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //------------------------------------------------------------------------------------------------------------------主动建索引

    /**
     * 查询当前数据桶所有索引
     *
     * @return 所有索引集合
     */
    public List<Indexes> queryIndexes() {
        try {
            if (bucket.name() != null && !bucket.name().isEmpty()) {
                List<Indexes> indexes = N1qlQueryToObject.getInstance(mapper).n1qlQueryToT(
                        bucket.query(N1qlQuery.simple("SELECT * FROM system:indexes WHERE keyspace_id = \"" + bucket.name() + "\"")
                                , bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), Indexes.class);
                if (indexes != null && indexes.size() > 0) {
                    return indexes;
                } else {
                    return null;
                }
            } else {
                throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询某个索引
     *
     * @param indexName 索引名称
     * @return 索引
     */
    public Indexes queryIndexes(String indexName) {
        try {
            if (indexName != null && !indexName.isEmpty()) {
                if (bucket.name() != null && !bucket.name().isEmpty()) {
                    List<Indexes> indexes = N1qlQueryToObject.getInstance(mapper).n1qlQueryToT(
                            bucket.query(N1qlQuery.simple("SELECT * FROM system:indexes WHERE keyspace_id = \"" + bucket.name() + "\" AND name = \"" + indexName + "\"")
                                    , bucketTime.TIME_QUERY, bucketTime.TIME_UNIT), Indexes.class);
                    if (indexes != null && indexes.size() > 0) {
                        return indexes.get(0);
                    } else {
                        return null;
                    }
                } else {
                    throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的索引名称为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建索引
     *
     * @param indexName  索引名称
     * @param fieldNames 字段名称集合
     * @return true | false
     */
    public boolean createIndex(String indexName, String[] fieldNames) {
        try {
            if (indexName != null && !indexName.isEmpty()) {
                if (fieldNames != null && fieldNames.length > 0) {
                    if (bucket.name() != null && !bucket.name().isEmpty()) {
                        StringBuilder n1ql = new StringBuilder("CREATE INDEX ")
                                .append(indexName)
                                .append(" on `")
                                .append(bucket.name())
                                .append("` (");
                        for (String fieldName : fieldNames) {
                            n1ql.append(fieldName);
                            n1ql.append(",");
                        }
                        n1ql.delete(n1ql.length() - 1, n1ql.length());
                        n1ql.append(") USING GSI");
                        bucket.query(N1qlQuery.simple(n1ql.toString()), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT);
                        return true;
                    } else {
                        throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                    }
                } else {
                    throw new DataBaseOperateException("诊断：传入的需建索引的属性名称集合为空！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的索引名称为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @return true | false
     */
    public boolean dropIndex(String indexName) {
        try {
            if (indexName != null && !indexName.isEmpty()) {
                if (bucket.name() != null && !bucket.name().isEmpty()) {
                    String n1ql = "DROP INDEX `" + bucket.name() + "`.`" + indexName + "` USING GSI";
                    bucket.query(N1qlQuery.simple(n1ql), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT);
                    return true;
                } else {
                    throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
                }
            } else {
                throw new DataBaseOperateException("诊断：传入的索引名称为空！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //------------------------------------------------------------------------------------------------------------------内部方法

    /**
     * 检查是否有基础索引没有则创建
     */
    private void checkIndex() {
        try {
            if (bucket.name() != null && !bucket.name().isEmpty()) {
                N1qlQueryResult result = bucket.query(N1qlQuery.simple(
                        "SELECT * FROM system:indexes where keyspace_id = \"" + bucket.name() + "\""
                ), bucketTime.TIME_QUERY, bucketTime.TIME_UNIT);
                if (!(result != null && result.allRows() != null && result.allRows().size() > 0)) {
                    bucket.query(N1qlQuery.simple("CREATE PRIMARY INDEX  ON `" + bucket.name() + "` USING GSI")
                            , bucketTime.TIME_QUERY, bucketTime.TIME_UNIT);
                }
            } else {
                throw new DataBaseOperateException("诊断：数据库连接初始化后数据库名称丢失！", new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取主键字段
     *
     * @param doc 文档
     * @param <T> 文档类型
     * @return 主键字段
     */
    private <T extends Document> Field getIdField(T doc) {
        Field idField = null;
        for (Field field : doc.getClass().getFields()) {
            if (field.getDeclaringClass().equals(Document.class)) {
                if (field.getName().equals(CodeConductStandard.Document_Id)) {
                    idField = field;
                }
            }
        }
        return idField;
    }

    /**
     * 读取主键字段
     *
     * @param tClass 文档类型
     * @param <T>    文档类型
     * @return 主键字段
     */
    private <T extends Document> Field getIdField(Class<T> tClass) {
        Field idField = null;
        for (Field field : tClass.getFields()) {
            if (field.getDeclaringClass().equals(Document.class)) {
                if (field.getName().equals(CodeConductStandard.Document_Id)) {
                    idField = field;
                }
            }
        }
        return idField;
    }

    /**
     * 读取主键字段
     *
     * @param doc 文档
     * @return 主键字段
     */
    private <T extends Document> String getIdFieldValue(T doc) {
        Field idField = null;
        for (Field field : doc.getClass().getFields()) {
            if (field.getDeclaringClass().equals(Document.class)) {
                if (field.getName().equals(CodeConductStandard.Document_Id)) {
                    idField = field;
                }
            }
        }
        try {
            return idField != null ? idField.get(doc) != null ? idField.get(doc).toString() : null : null;
        } catch (IllegalAccessException e) {
            throw new DataBaseOperateException("诊断：传入对象为空或者未找到公共基类Document的主键！", new NullPointerException());
        }
    }

    /**
     * 文档转换成json文档
     *
     * @param doc 文档
     * @param <T> 文档类型
     * @return json形式文档
     */
    private <T extends Document> JsonDocument objectToT(T doc) {
        HashMap<String, Object> docHashMap = mapper.convertValue(doc, new TypeReference<HashMap<String, Object>>() {
        });
        return JsonDocument.create(Objects.requireNonNull(getIdFieldValue(doc)), JsonObject.from(docHashMap));
    }

    /**
     * 赋值基类的Document属性
     *
     * @param jsonDocument json形式的文档
     * @param doc          文档
     * @param <T>          文档类型
     */
    private <T extends Document> void documentAssignment(JsonDocument jsonDocument, T doc) throws NoSuchFieldException, IllegalAccessException {
        doc.getClass().getField(CodeConductStandard.Document_Id)
                .set(doc, jsonDocument.content().getString(CodeConductStandard.Document_Id));
        doc.getClass().getField(CodeConductStandard.Document_ClassTypeName)
                .set(doc, jsonDocument.content().getString(CodeConductStandard.Document_ClassTypeName));
        doc.getClass().getField(CodeConductStandard.Document_CreateTime)
                .set(doc, DateDispose.formatting_Date(jsonDocument.content().getString(CodeConductStandard.Document_CreateTime), CodeConductStandard.Document_DateFormatType));
        doc.getClass().getField(CodeConductStandard.Document_UpdateTime)
                .set(doc, new Date());
        doc.getClass().getField(CodeConductStandard.Document_CreateManId)
                .set(doc, jsonDocument.content().getString(CodeConductStandard.Document_CreateManId));
        doc.getClass().getField(CodeConductStandard.Document_UpdateManId)
                .set(doc, jsonDocument.content().getString(CodeConductStandard.Document_UpdateManId));
        doc.getClass().getField(CodeConductStandard.Document_IsValidity)
                .set(doc, jsonDocument.content().getInt(CodeConductStandard.Document_IsValidity));
    }

    /**
     * 给文档赋初始值
     *
     * @param id  文档主键
     * @param doc 文档
     * @param <T> 文档类型
     * @return json形式的文档
     */
    private <T extends Document> JsonDocument documentInitialize(String id, T doc) throws IllegalAccessException, IllegalArgumentException, DataBaseOperateException {
        JsonDocument jsonDocument;
        Field idField = null;
        Field classTypeNameField = null;
        Field updateTimeField = null;
        Field createTimeField = null;
        for (Field field : doc.getClass().getFields()) {
            if (field.getDeclaringClass().equals(Document.class)) {
                switch (field.getName()) {
                    case CodeConductStandard.Document_Id:
                        idField = field;
                        break;
                    case CodeConductStandard.Document_ClassTypeName:
                        classTypeNameField = field;
                        break;
                    case CodeConductStandard.Document_UpdateTime:
                        updateTimeField = field;
                        break;
                    case CodeConductStandard.Document_CreateTime:
                        createTimeField = field;
                        break;
                }
            }
        }
        if (idField != null && classTypeNameField != null && updateTimeField != null && createTimeField != null) {
            if (idField.get(doc) == null || "".equals(idField.get(doc))) {
                idField.set(doc, id);
            }
            if (classTypeNameField.get(doc) == null) {
                classTypeNameField.set(doc, doc.getClass().getName());
            }
            if (createTimeField.get(doc) == null) {
                createTimeField.set(doc, new Date());
            }
            updateTimeField.set(doc, new Date());

            HashMap<String, Object> docHashMap = mapper.convertValue(doc, new TypeReference<HashMap<String, Object>>() {
            });
            jsonDocument = JsonDocument.create(id, JsonObject.from(docHashMap));
        } else {
            throw new DataBaseOperateException("诊断：对象未继承公共基类Document无法存入数据库请继承后重试！", new NullPointerException());
        }
        return jsonDocument;
    }

    /**
     * 读取数据返回对象中的值
     *
     * @param n1qlQueryResult 数据返回对象
     * @param tClass          文档类型
     * @param <T>             文档类型
     * @return 文档集合
     */
    private <T extends Document> List<T> n1qlQueryToT(N1qlQueryResult n1qlQueryResult, Class<T> tClass) {
        return n1qlQueryToT(n1qlQueryResult.allRows(), tClass);
    }

    /**
     * 读取数据返回对象中的值
     *
     * @param n1qlQueryRows 数据返回对象行集合
     * @param tClass        文档类型
     * @param <T>           文档类型
     * @return 文档集合
     */
    private <T extends Document> List<T> n1qlQueryToT(List<N1qlQueryRow> n1qlQueryRows, Class<T> tClass) {
        if (n1qlQueryRows == null || n1qlQueryRows.size() <= 0) {
            return null;
        }
        try {
            List<T> list = new ArrayList<>();
            for (N1qlQueryRow n1qlQueryRow : n1qlQueryRows) {
                JsonObject jsonObject = n1qlQueryRow.value();
                String value = "";
                for (String name : jsonObject.getNames()) {
                    value = jsonObject.get(name).toString();
                }
                T obj = mapper.readValue(value, tClass);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
