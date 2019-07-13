package schedule;

public class Job {

    private Job() {
    }

    public static Job with(Runnable action) {
        Job instance = new Job();
        instance.action = action;
        return instance;
    }

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


    private Schedule[] schedules = new Schedule[]{};

    public Schedule[] getSchedules() {
        return schedules;
    }

    public Job when(Schedule... schedules) {
        this.schedules = schedules;
        return this;
    }
}