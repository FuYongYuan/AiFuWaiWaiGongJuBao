package schedule.service;

/**
 * 完成设置
 *
 * @author fyy
 */
class FiniteNumberSet {

    public FiniteNumberSet(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    private final int lower;

    public int getLower() {
        return lower;
    }

    private final int upper;

    public int getUpper() {
        return upper;
    }

    public AddResult add(int value, int add) {

        AddResult result = new AddResult();
        result.value = ((value - lower) + add) % (upper - lower + 1) + lower;
        result.overflow = (int) Math.floor((float) ((value - lower) + add) / (upper - lower + 1));


        return result;
    }
}
