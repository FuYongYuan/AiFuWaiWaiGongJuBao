package enumerate;

public enum HttpState {
    HTTP_100(100),
    HTTP_101(101),
    HTTP_200(200),
    HTTP_201(201),
    HTTP_202(202),
    HTTP_203(203),
    HTTP_204(204),
    HTTP_205(205),
    HTTP_206(206),
    HTTP_300(300),
    HTTP_301(301),
    HTTP_302(302),
    HTTP_303(303),
    HTTP_304(304),
    HTTP_305(305),
    HTTP_307(307),
    HTTP_400(400),
    HTTP_401(401),
    HTTP_403(403),
    HTTP_404(404),
    HTTP_405(405),
    HTTP_406(406),
    HTTP_407(407),
    HTTP_408(408),
    HTTP_409(409),
    HTTP_410(410),
    HTTP_411(411),
    HTTP_412(412),
    HTTP_413(413),
    HTTP_414(414),
    HTTP_415(415),
    HTTP_416(416),
    HTTP_417(417),
    HTTP_499(499),
    HTTP_500(500),
    HTTP_501(501),
    HTTP_502(502),
    HTTP_503(503),
    HTTP_504(504),
    HTTP_505(505),
    HTTP_610(610),
    HTTP_611(611),
    HTTP_613(613),
    HTTP_615(615),
    HTTP_631(631),
    HTTP_650(650),
    HTTP_681(681),;


    private int value;

    HttpState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
