package couchbase.queue;

import com.couchbase.client.java.document.JsonDocument;
import couchbase.CouchbasePool;
import couchbase.connection.CouchbaseConnection;
import schedule.Job;
import schedule.Timer;
import schedule.service.JobService;

import java.time.Duration;
import java.util.LinkedList;

public class CouchbaseQueue {
    /**
     * 消息队列值队列
     */
    private LinkedList<JsonDocument> queue;

    /**
     * 执行模式
     */
    private PerformMode performMode;

    /**
     * 数据库主连接对象
     */
    private CouchbaseConnection connection;

    /**
     * 自动注入模式和数据库连接
     */
    public CouchbaseQueue() {
        this.queue = new LinkedList<>();
        this.performMode = PerformMode.FirstIn_FirstOut;
        this.connection = CouchbasePool.getConnection();
    }

    /**
     * 自动注入模式
     *
     * @param connection 数据库连接
     */
    public CouchbaseQueue(CouchbaseConnection connection) {
        this.queue = new LinkedList<>();
        this.performMode = PerformMode.FirstIn_FirstOut;
        this.connection = connection;
    }

    /**
     * 自动注入数据库连接
     *
     * @param performMode 执行模式
     */
    public CouchbaseQueue(PerformMode performMode) {
        this.queue = new LinkedList<>();
        this.performMode = performMode;
        this.connection = CouchbasePool.getConnection();
    }

    /**
     * 手动注入
     *
     * @param connection  数据库连接
     * @param performMode 执行模式
     */
    public CouchbaseQueue(CouchbaseConnection connection, PerformMode performMode) {
        this.queue = new LinkedList<>();
        this.performMode = performMode;
        this.connection = connection;
    }

    /**
     * 添加要执行的文档对象
     *
     * @param jsonDocument 文档对象
     */
    public void add(JsonDocument jsonDocument) {
        queue.add(jsonDocument);
    }

    /**
     * 读取一个队列中的对象
     *
     * @return 队列中的前或者后对象
     */
    public JsonDocument get() {
        if (PerformMode.FirstIn_FirstOut == performMode) {
            return queue.getFirst();
        } else if (PerformMode.FirstIn_LastOut == performMode) {
            return queue.getLast();
        } else {
            return queue.getLast();
        }
    }

    /**
     * 删除一个队列中的对象
     */
    public void remove() {
        if (PerformMode.FirstIn_FirstOut == performMode) {
            queue.removeFirst();
        } else if (PerformMode.FirstIn_LastOut == performMode) {
            queue.removeLast();
        } else {
            queue.removeLast();
        }
    }

    /**
     * 判断队列是否是空的
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * 读取一个队列中的对象
     *
     * @return 队列中的前或者后对象
     */
    private JsonDocument get(LinkedList<JsonDocument> perform) {
        if (PerformMode.FirstIn_FirstOut == performMode) {
            return perform.getFirst();
        } else if (PerformMode.FirstIn_LastOut == performMode) {
            return perform.getLast();
        } else {
            return perform.getLast();
        }
    }

    /**
     * 删除一个队列中的对象
     */
    private void remove(LinkedList<JsonDocument> perform) {
        if (PerformMode.FirstIn_FirstOut == performMode) {
            perform.removeFirst();
        } else if (PerformMode.FirstIn_LastOut == performMode) {
            perform.removeLast();
        } else {
            perform.removeLast();
        }
    }

    /**
     * 创建执行线程
     *
     * @param second 每隔多少秒执行一次
     */
    public void perform(int second) {
        JobService jobService = new JobService();
        Job job = Job.with(
                () -> {
                    LinkedList<JsonDocument> perform = queue;
                    queue = new LinkedList<>();
                    while (!perform.isEmpty()) {
                        connection.getBucket().upsert(get(perform));
                        remove(perform);
                    }
                }
        ).when(
                Timer.duration(Duration.ofSeconds(second))
        );
        jobService.addJob(job);
        jobService.start();
    }

    /**
     * 创建执行线程
     *
     * @param number 一共创建多少个
     * @param second 每隔多少秒执行一次
     */
    public void perform(int number, int second) {
        JobService jobService = new JobService();
        for (int i = 0; i <= number; i++) {
            Job job = Job.with(
                    () -> {
                        LinkedList<JsonDocument> perform = queue;
                        queue = new LinkedList<>();
                        while (!perform.isEmpty()) {
                            connection.getBucket().upsert(get(perform));
                            remove(perform);
                        }
                    }
            ).when(
                    Timer.duration(Duration.ofSeconds(second))
            );
            jobService.addJob(job);
        }
        jobService.start();
    }
}
