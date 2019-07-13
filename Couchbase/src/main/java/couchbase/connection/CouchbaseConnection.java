package couchbase.connection;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import couchbase.connection.operate.CouchbaseOperate;
import couchbase.entity.BucketTime;
import couchbase.exception.DataBaseConnectionException;

/**
 * CouchBase链接类
 */
public class CouchbaseConnection {
    /**
     * 连接
     */
    private Cluster cluster;

    /**
     * 数据库地址
     */
    private String data_url;

    /**
     * 数据库账号
     */
    private String data_name;

    /**
     * 数据库密码
     */
    private String data_password;

    /**
     * 连接数据桶
     */
    private Bucket bucket;

    /**
     * 数据桶名称
     */
    private String bucket_name;

    /**
     * 数据桶密码
     */
    private String bucket_password;

    /**
     * 数据桶各项时间配置类
     */
    private BucketTime bucketTime;

    /**
     * 封装操作类
     */
    public CouchbaseOperate operate;

    //------------------------------------------------------------------------------------------------------------------get连接配置内容

    /**
     * 数据库地址
     *
     * @return 数据库地址
     */
    public String getDataUrl() {
        return data_url;
    }

    /**
     * 数据库账号名称
     *
     * @return 数据库账号名称
     */
    public String getDataName() {
        return data_name;
    }

    /**
     * 数据库密码
     *
     * @return 数据库密码
     */
    public String getDataPassword() {
        return data_password;
    }

    /**
     * 数据桶名称
     *
     * @return 数据桶名称
     */
    public String getBucket_name() {
        return bucket_name;
    }

    /**
     * 数据桶密码
     *
     * @return 数据桶密码
     */
    public String getBucket_password() {
        return bucket_password;
    }

    /**
     * 数据桶各项时间配置类
     *
     * @return 数据桶各项时间配置类
     */
    public BucketTime getBucketTime() {
        return bucketTime;
    }

    //------------------------------------------------------------------------------------------------------------------初始化连接

    /**
     * 创建连接
     *
     * @param url        数据库地址
     * @param name       数据库名称
     * @param password   数据库密码
     * @param bucketName 数据桶名称
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucketTime = new BucketTime();
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), true, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url            数据库地址
     * @param name           数据库名称
     * @param password       数据库密码
     * @param bucketName     数据桶名称
     * @param isIgnoreGetSet 是否忽略Get Set
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, boolean isIgnoreGetSet) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucketTime = new BucketTime();
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), isIgnoreGetSet, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url            数据库地址
     * @param name           数据库名称
     * @param password       数据库密码
     * @param bucketName     数据桶名称
     * @param bucketPassword 数据桶密码
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, String bucketPassword) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucket_password = bucketPassword;
        bucketTime = new BucketTime();
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), true, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url            数据库地址
     * @param name           数据库名称
     * @param password       数据库密码
     * @param bucketName     数据桶名称
     * @param bucketPassword 数据桶密码
     * @param isIgnoreGetSet 是否忽略Get Set
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, String bucketPassword, boolean isIgnoreGetSet) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucket_password = bucketPassword;
        bucketTime = new BucketTime();
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), isIgnoreGetSet, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url        数据库地址
     * @param name       数据库名称
     * @param password   数据库密码
     * @param bucketName 数据桶名称
     * @param time       数据桶各项时间配置类
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, BucketTime time) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucketTime = time;
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), true, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url            数据库地址
     * @param name           数据库名称
     * @param password       数据库密码
     * @param bucketName     数据桶名称
     * @param isIgnoreGetSet 是否忽略Get Set
     * @param time           数据桶各项时间配置类
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, boolean isIgnoreGetSet, BucketTime time) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucketTime = time;
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), isIgnoreGetSet, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url            数据库地址
     * @param name           数据库名称
     * @param password       数据库密码
     * @param bucketName     数据桶名称
     * @param bucketPassword 数据桶密码
     * @param time           数据桶各项时间配置类
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, String bucketPassword, BucketTime time) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucket_password = bucketPassword;
        bucketTime = time;
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), true, getBucketTime());
        }
    }

    /**
     * 创建连接
     *
     * @param url            数据库地址
     * @param name           数据库名称
     * @param password       数据库密码
     * @param bucketName     数据桶名称
     * @param bucketPassword 数据桶密码
     * @param isIgnoreGetSet 是否忽略Get Set
     * @param time           数据桶各项时间配置类
     */
    public CouchbaseConnection(String url, String name, String password, String bucketName, String bucketPassword, boolean isIgnoreGetSet, BucketTime time) {
        data_url = url;
        data_name = name;
        data_password = password;
        bucket_name = bucketName;
        bucket_password = bucketPassword;
        bucketTime = time;
        if (operate == null) {
            operate = new CouchbaseOperate(getBucket(), isIgnoreGetSet, getBucketTime());
        }
    }

    //------------------------------------------------------------------------------------------------------------------数据桶操作

    /**
     * 获取创建数据桶
     *
     * @return 数据桶
     */
    public Bucket getBucket() {
        if (bucket == null) {
            if (data_url == null || data_url.isEmpty()) {
                throw new DataBaseConnectionException("<<<<<=====-----数据库连接地址为空!-----=====>>>>>");
            }
            if (data_name == null || data_name.isEmpty()) {
                throw new DataBaseConnectionException("<<<<<=====-----数据库连接名称为空!-----=====>>>>>");
            }
            if (data_password == null || data_password.isEmpty()) {
                throw new DataBaseConnectionException("<<<<<=====-----数据库连接密码为空!-----=====>>>>>");
            }
            if (bucket_name == null || bucket_name.isEmpty()) {
                throw new DataBaseConnectionException("<<<<<=====-----数据库连接库名称为空!-----=====>>>>>");
            }

            cluster = CouchbaseCluster.create(data_url);
            cluster.authenticate(data_name, data_password);

            if (bucket_password == null || bucket_password.isEmpty()) {
                bucket = cluster.openBucket(bucket_name, bucketTime.TIME_BUCKET, bucketTime.TIME_UNIT);
                //如果没有默认索引则创建
                bucket.bucketManager().createN1qlPrimaryIndex(true, false);
                return bucket;
            } else {
                bucket = cluster.openBucket(bucket_name, bucket_password, bucketTime.TIME_BUCKET, bucketTime.TIME_UNIT);
                //如果没有默认索引则创建
                bucket.bucketManager().createN1qlPrimaryIndex(true, false);
                return bucket;
            }
        } else {
            return bucket;
        }
    }

    /**
     * 销毁连接
     */
    public void closeConnection() {
        bucket.close();
        cluster.disconnect();
    }

}
