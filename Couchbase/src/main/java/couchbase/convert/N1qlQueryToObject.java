package couchbase.convert;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * N1ql查询结果转换成对象帮助类(对象必须继承自Document)
 */
public class N1qlQueryToObject {

    /**
     * 转换工具类对象
     */
    private static ObjectMapper mapper;

    /**
     * 转换单例
     */
    private static N1qlQueryToObject n1qlQueryToObject;

    /**
     * N1ql查询结果转换成对象帮助类(对象必须继承自Document)
     *
     * @param m 提供一些功能将转换成Java对象匹配JSON结构.的工具对象
     */
    private N1qlQueryToObject(ObjectMapper m) {
        mapper = m;
    }

    /**
     * 初始化静态方法
     *
     * @return N1qlQueryToObject对象
     */
    public static N1qlQueryToObject getInstance() {
        if (mapper != null) {
            if (n1qlQueryToObject == null) {
                n1qlQueryToObject = new N1qlQueryToObject(mapper);
            }
        } else {
            mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
            mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            if (n1qlQueryToObject == null) {
                n1qlQueryToObject = new N1qlQueryToObject(mapper);
            }
        }
        return n1qlQueryToObject;
    }

    /**
     * 初始化静态方法
     *
     * @param m 提供一些功能将转换成Java对象匹配JSON结构.的工具对象
     * @return N1qlQueryToObject对象
     */
    public static N1qlQueryToObject getInstance(ObjectMapper m) {
        if (m != null) {
            if (mapper != null) {
                if (m.hashCode() != mapper.hashCode()) {
                    n1qlQueryToObject = new N1qlQueryToObject(m);
                    mapper = m;
                } else {
                    if (n1qlQueryToObject == null) {
                        n1qlQueryToObject = new N1qlQueryToObject(m);
                        mapper = m;
                    }
                }
            } else {
                if (n1qlQueryToObject == null) {
                    n1qlQueryToObject = new N1qlQueryToObject(m);
                    mapper = m;
                }
            }
        } else {
            return getInstance();
        }
        return n1qlQueryToObject;
    }

    /**
     * 将 N1qlQueryResult 转换成 T
     *
     * @param n1qlQueryResult N1qlQueryResult对象
     * @param tClass          转换结果类型
     * @param <T>             对象类型
     * @return T类型的List
     */
    public <T> List<T> n1qlQueryToT(N1qlQueryResult n1qlQueryResult, Class<T> tClass) {
        return n1qlQueryToT(n1qlQueryResult.allRows(), tClass);
    }

    /**
     * 将 N1qlQueryRow 转换成 T
     *
     * @param n1qlQueryRows N1qlQueryRow对象
     * @param tClass        转换结果类型
     * @param <T>           对象类型
     * @return T类型的List
     */
    public <T> List<T> n1qlQueryToT(List<N1qlQueryRow> n1qlQueryRows, Class<T> tClass) {
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
        }
        return null;
    }

    /**
     * 转成Bucket可用的文档类型
     *
     * @param Id  文档id
     * @param doc 文档内容
     * @param <T> 类型
     * @return 文档格式的内容
     */
    public <T> JsonDocument objectToT(String Id, T doc) {
        HashMap<String, Object> docHashMap = mapper.convertValue(doc, new TypeReference<HashMap<String, Object>>() {
        });
        return JsonDocument.create(Id, JsonObject.from(docHashMap));
    }

    /**
     * 将 JsonObject 转成T
     *
     * @param jsonObject json对象
     * @param tClass     需要转出的类型
     * @param <T>        类型
     * @return 转换后的对象
     */
    public <T> T objectToT(JsonObject jsonObject, Class<T> tClass) {
        if (jsonObject == null || jsonObject.size() <= 0) {
            return null;
        }
        try {
            return mapper.readValue(jsonObject.toString(), tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给文档赋初始值
     *
     * @param id  文档主键
     * @param doc 文档
     * @param <T> 文档类型
     * @return json形式的文档
     */
    public <T> JsonDocument giveDocumentId(String id, T doc) {
        JsonDocument jsonDocument;
        HashMap<String, Object> docHashMap = mapper.convertValue(doc, new TypeReference<HashMap<String, Object>>() {
        });
        jsonDocument = JsonDocument.create(id, JsonObject.from(docHashMap));
        return jsonDocument;
    }
}
