package couchbase.entity;

import java.util.concurrent.TimeUnit;

//设置的超时时间
public class BucketTime {

    //单位
    public TimeUnit TIME_UNIT;
    //根据上方单位设定的值
    public long TIME_ADD;
    public long TIME_UPDATE;
    public long TIME_UPDATE_CONDITION;
    public long TIME_QUERY;
    public long TIME_DELETE;
    public long TIME_DELETE_CONDITION;
    public long TIME_BUCKET;
    public int TIME_LOCK;
    public long TIME_LOCK_RESTART;

    public BucketTime() {
        this.TIME_UNIT = TimeUnit.SECONDS;  //单位秒
        this.TIME_ADD = 60L;                //添加---1分钟
        this.TIME_UPDATE = 180L;            //修改---3分钟
        this.TIME_UPDATE_CONDITION = 600L;  //修改带查询条件---10分钟
        this.TIME_QUERY = 600L;             //查询---10分钟
        this.TIME_DELETE = 180L;            //删除---3分钟
        this.TIME_DELETE_CONDITION = 600L;  //删除带查询条件---10分钟
        this.TIME_BUCKET = 300L;            //数据桶---5分钟
        this.TIME_LOCK = 0;                 //数据锁过期时间---0只要本次访问方法没返回就永不过期
        this.TIME_LOCK_RESTART = 100;       //数据锁被占用重启时间---100毫秒
    }

    public BucketTime(TimeUnit TIME_UNIT, long TIME_ADD, long TIME_UPDATE, long TIME_UPDATE_CONDITION, long TIME_QUERY, long TIME_DELETE, long TIME_DELETE_CONDITION, long TIME_BUCKET, int TIME_LOCK, long TIME_LOCK_RESTART) {
        this.TIME_UNIT = TIME_UNIT == null ? TimeUnit.SECONDS : TIME_UNIT;
        this.TIME_ADD = TIME_ADD;
        this.TIME_UPDATE = TIME_UPDATE;
        this.TIME_UPDATE_CONDITION = TIME_UPDATE_CONDITION;
        this.TIME_QUERY = TIME_QUERY;
        this.TIME_DELETE = TIME_DELETE;
        this.TIME_DELETE_CONDITION = TIME_DELETE_CONDITION;
        this.TIME_BUCKET = TIME_BUCKET;
        this.TIME_LOCK = TIME_LOCK;
        this.TIME_LOCK_RESTART = TIME_LOCK_RESTART;
    }

    public BucketTime(BucketTime bucketTime) {
        this.TIME_UNIT = bucketTime.TIME_UNIT == null ? TimeUnit.SECONDS : bucketTime.TIME_UNIT;
        this.TIME_ADD = bucketTime.TIME_ADD;
        this.TIME_UPDATE = bucketTime.TIME_UPDATE;
        this.TIME_UPDATE_CONDITION = bucketTime.TIME_UPDATE_CONDITION;
        this.TIME_QUERY = bucketTime.TIME_QUERY;
        this.TIME_DELETE = bucketTime.TIME_DELETE;
        this.TIME_DELETE_CONDITION = bucketTime.TIME_DELETE_CONDITION;
        this.TIME_BUCKET = bucketTime.TIME_BUCKET;
        this.TIME_LOCK = bucketTime.TIME_LOCK;
        this.TIME_LOCK_RESTART = bucketTime.TIME_LOCK_RESTART;
    }
}
