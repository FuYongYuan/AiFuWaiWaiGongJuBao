package schedule;

import java.util.Arrays;

/**
 * 抽象的时间类
 *
 * @author fyy
 */
public abstract class AbstractSchedule {

    private AbstractSchedule[] abstractSchedules = new AbstractSchedule[]{};

    public AbstractSchedule[] getSchedules() {
        return Arrays.copyOf(abstractSchedules, abstractSchedules.length);
    }

    /**
     * 设置子周期
     *
     * @param abstractSchedules 子周期
     */
    public AbstractSchedule with(AbstractSchedule... abstractSchedules) {

        for (AbstractSchedule s : abstractSchedules) {

            if (ScheduleCrosscut.getOrder(this) <= ScheduleCrosscut.getOrder(s)) {
                throw new IllegalArgumentException(String.format("Cannot add %s to %s.", s.getClass(), getClass()));
            }
        }

        this.abstractSchedules = abstractSchedules;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractSchedule)) {
            return false;
        }

        AbstractSchedule that = (AbstractSchedule) o;

        return Arrays.equals(abstractSchedules, that.abstractSchedules);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(abstractSchedules);
    }
}
