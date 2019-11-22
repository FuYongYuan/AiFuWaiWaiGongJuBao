public class D {
    private D() {

    }


    private static D d;

    public static D newD() {
        if (d == null) {
            d = new D();
        }
        return d;
    }
}
