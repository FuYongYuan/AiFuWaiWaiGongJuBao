package schedule;

/**
 * 调度任务实体
 *
 * @author fyy
 */
public class Job {

    /**
     * 无需构造封闭 new 通过 with 创建
     */
    private Job() {
    }

    /**
     * 执行方法体
     *
     * @param action () -> 执行方法体
     */
    public static Job with(Runnable action) {
        Job instance = new Job();
        instance.action = action;
        return instance;
    }

    /**
     * 执行方法体
     *
     * @param actionClass () -> 执行方法体
     */
    public static Job with(Class<? extends Runnable> actionClass) {
        Job instance = new Job();
        instance.actionClass = actionClass;
        return instance;
    }


    private Runnable action;

    public Runnable getAction() {
        return action;
    }

    private Class<?> actionClass;

    public Class<?> getActionClass() {
        return actionClass;
    }


    private AbstractSchedule[] abstractSchedules = new AbstractSchedule[]{};

    public AbstractSchedule[] getSchedules() {
        return abstractSchedules;
    }

    /**
     * 设置执行周期
     *
     * @param abstractSchedules 执行周期
     */
    public Job when(AbstractSchedule... abstractSchedules) {
        this.abstractSchedules = abstractSchedules;
        return this;
    }
}