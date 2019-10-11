package couchbase;

import couchbase.connection.CouchbaseConnection;
import couchbase.exception.DataBasePoolException;

import java.util.HashMap;
import java.util.Map;

/**
 * CouchBase的数据连接池
 */
public class CouchbasePool {

    /**
     * 数据库连接对象MAP集合可以从这中间.get取到特定的数据库连接
     */
    private Map<String, CouchbaseConnection> connectionGroup;

    /**
     * 数据库主连接对象
     */
    private CouchbaseConnection connection;

    /**
     * 创建couchbase连接池
     *
     * @param connections        连接的map集合
     * @param mainConnectionName 主连接名称
     */
    private CouchbasePool(Map<String, CouchbaseConnection> connections, String mainConnectionName) {
        connectionGroup = connections;
        initMainConnection(mainConnectionName);
    }

    /**
     * 单例创建连接池
     */
    private static CouchbasePool couchBasePool;

    /**
     * 读取连接池
     *
     * @return 连接池
     */
    public static CouchbasePool get() {
        if (couchBasePool != null) {
            return couchBasePool;
        } else {
            throw new DataBasePoolException("诊断：连接池读取失败内存中没有连接池对象！", new NullPointerException());
        }
    }

    /**
     * 读取所有连接
     *
     * @return 连接的map集合
     */
    public static Map<String, CouchbaseConnection> getConnections() {
        if (couchBasePool != null && couchBasePool.connectionGroup != null) {
            return couchBasePool.connectionGroup;
        } else {
            throw new DataBasePoolException("诊断：连接池读取失败内存中没有连接池对象！", new NullPointerException());
        }
    }

    /**
     * 读取主连接
     *
     * @return 主链接
     */
    public static CouchbaseConnection getConnection() {
        if (couchBasePool != null && couchBasePool.connection != null) {
            return couchBasePool.connection;
        } else {
            throw new DataBasePoolException("诊断：连接池连接读取失败内存中没有连接对象！", new NullPointerException());
        }
    }

    /**
     * 读取指定连接
     *
     * @param connectionName 连接名称
     * @return 指定连接
     */
    public static CouchbaseConnection getConnection(String connectionName) {
        if (couchBasePool != null && couchBasePool.connection != null) {
            return couchBasePool.connectionGroup.get(connectionName);
        } else {
            throw new DataBasePoolException("诊断：连接池连接读取失败内存中没有连接对象！", new NullPointerException());
        }
    }

    /**
     * 修改连接池
     *
     * @param connections        连接的map集合
     * @param mainConnectionName 主连接名称
     * @return 刷新后的连接池
     */
    public static CouchbasePool set(Map<String, CouchbaseConnection> connections, String mainConnectionName) {
        return couchBasePool = new CouchbasePool(connections, mainConnectionName);
    }

    /**
     * 添加连接
     *
     * @param connectionName      连接名称
     * @param couchBaseConnection 连接
     * @return 连接池
     */
    public static CouchbasePool add(String connectionName, CouchbaseConnection couchBaseConnection) {
        if (couchBasePool == null) {
            couchBasePool = new CouchbasePool(new HashMap<>(), connectionName);
        }
        couchBasePool.connectionGroup.put(connectionName, couchBaseConnection);
        return couchBasePool;
    }

    /**
     * 初始化核心连接
     */
    private void initMainConnection(String mainConnectionName) {
        if (mainConnectionName != null && !mainConnectionName.isEmpty()) {
            CouchbaseConnection couchBaseConnection = connectionGroup.get(mainConnectionName);
            if (couchBaseConnection != null) {
                connection = couchBaseConnection;
            } else {
                throw new DataBasePoolException("诊断：可能因为没有主连接名称对应的数据库连接！", new NullPointerException());
            }
        }
    }

}
