package schedule;

class ScheduleCrosscut {

    public static int getOrder(Schedule s) {

        if (s instanceof Second || s instanceof Timer) {
            return 0;
        } else if (s instanceof Minute) {
            return 1;
        } else if (s instanceof Hour) {
            return 2;
        } else if (s instanceof WeekDay) {
            return 3;
        } else if (s instanceof MonthDay) {
            return 4;
        } else if (s instanceof Month) {
            return 5;
        }


        return 0;
    }
}
