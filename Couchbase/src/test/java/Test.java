import com.couchbase.client.java.query.Statement;
import couchbase.connection.CouchbaseConnection;

import static com.couchbase.client.java.query.Select.select;

public class Test {

    public static void main(String[] age) throws Exception {

//        String s = "{\"name\":\"test\",\"companyId\":\"test\",\"areas\":[{\"name\":\"a\",\"areas\":[{\"name\":\"a1\",\"areas\":[],\"id\":\"a1\"},{\"name\":\"a2\",\"areas\":[],\"id\":\"a2\"}],\"id\":\"a\"},{\"name\":\"b\",\"areas\":[{\"name\":\"b1\",\"areas\":[],\"id\":\"b1\"}],\"id\":\"b\"}],\"_class\":\"com.couchbase.entity.Building\",\"phoneNumbers\":[]}";


//        List<String> list = new ArrayList<>();
//        list.add("localhost");
//        CouchbaseEnvironment c = DefaultCouchbaseEnvironment.builder().build();
//        CouchbaseCluster cluster = CouchbaseCluster.create(c, list);

//        cluster.authenticate("admin","123456");

//        cluster.openBucket("Test");

//        String json = "{\"CreateManId\":null,\"CreateTime\":\"2018-10-29 15:24:58\",\"UpdateTime\":\"2018-10-29 15:24:58\",\"UpdateManId\":null,\"ClassTypeName\":\"CentextTest\",\"Id\":\"CentextTest-18c2f60514004cb49a83c38917607937\",\"IsValidity\":null,\"Name\":\"1\"}";
//        /**
//         * 转换工具类对象
//         */
//        ObjectMapper mapper = new ObjectMapper();
//        //忽略get.set
//        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
//        mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
//        //有属性不能映射的时候不报错   比如json中多一个属性...或者是对象中多几个属性
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        CentextTest obj = mapper.readValue(json, CentextTest.class);
//
//        System.out.println(obj.toString());

        CouchbaseConnection cb = new CouchbaseConnection("http://localhost:8091", "FYY", "fyyain77", "Test");
        Statement statement = select("*").from(cb.getBucket().name());
        System.out.println(statement);
        System.out.println(cb.getBucket().query(statement).allRows());
        CentextTest test = new CentextTest("1");

//        cb.operate.add(test);

        System.out.println(cb.operate.getByID("CentextTest-34ece62c6f804f1d92a8304602ef38c7", CentextTest.class));


        //        cb.getBucket().replace(N1qlQueryToObject.getInstance().objectToT("2X", new CentextTest("2X")));
//        cb.getBucket().get("");

//        CouchbaseQueue queue = new CouchbaseQueue();
//        queue.perform(10,1);
//
//        for (int i = 1; i <= 1000000; i++) {
////            if (i % 3 == 0) {
////                Long x = Long.parseLong(RandomNumberUtil.getRandomByLength(3));
////                Thread.sleep(x);
////            } else if (i % 2 == 0) {
////                Long x = Long.parseLong(RandomNumberUtil.getRandomByLength(2));
////                Thread.sleep(x);
////            }
////              else {
////                Long x = Long.parseLong(RandomNumberUtil.getRandomByLength(1));
////                Thread.sleep(x);
////            }
//            queue.add(N1qlQueryToObject.getInstance().objectToT("" + i, new CentextTest("" + i)));
//        }


//        LinkedList<Document> queue1 = new LinkedList<>();
//        queue1.add(new CentextTest("1"));
//        queue1.add(new CentextTest("2"));
//        queue1.add(new CentextTest("3"));
//        queue1.add(new CentextTest("4"));
//        queue1.add(new CentextTest("5"));
//
//        while (!queue1.isEmpty()){
//            CentextTest centextTest = (CentextTest) queue1.getFirst();
//            System.out.println(centextTest.Name);
//            queue1.removeFirst();
//        }


//        for (int i = 0; i < 10000; i++) {
//            if (i % 3 == 2 && i % 5 == 4 && i % 7 == 6 && i % 9 == 8 && i % 11 == 0) {
//                System.out.println(i);
//            }
//        }

//        CouchbaseConnection cb = new CouchbaseConnection("http://localhost:8091", "FYY", "fyyain77", "Test");
//        Statement statement=
//                select("count(*)").from(
//                        cb.getBucket_name()).where(
//                        x(CodeConductStandard.Document_ClassTypeName).eq(s("Test")));
//        System.out.println(statement.toString());
//        System.out.println(cb.operate.queryNumber(statement));

//        Thread.sleep(1000);
//
//        System.out.println("后续逻辑");

//        throw new DataBaseConnectionException("连接错误");

//        cb.operate.queryIndexes().forEach(s -> {
//            System.out.println(s.id);
//            System.out.println(s.name);
//            System.out.println(s.datastore_id);
//            System.out.println(s.keyspace_id);
//            System.out.println(s.namespace_id);
//            System.out.println(Arrays.toString(s.index_key));
//            System.out.println(s.is_primary);
//            System.out.println(s.using);
//            System.out.println(s.state);
//        });

//
//        CentextTest test =cb.operate.query("TestID",CentextTest.class);
//
//        System.out.println(DateDispose.formatting_Date(test.UpdateTime, DateType.Year_Month_Day_Hour_Minute_Second));

//
//        cb.operate.queryList(select("*").from("CentextTest").where(""), Document.class).forEach(s->System.out.println(s.ClassTypeName));
//
//        String n1ql = "SELECT * FROM system:indexes where keyspace_id = \"default\"";
//
//        long startTime = System.currentTimeMillis();
//
//        N1qlQueryResult n1qlQueryRows = cb.getBucket().query(N1qlQuery.simple(n1ql));
//
//        for (N1qlQueryRow row : n1qlQueryRows) {
//            System.out.println(row.toString());
//        }
//        ObjectMapper mapper = new ObjectMapper();
//
//        List<Indexes> indexes = N1qlQueryToObject.getInstance(mapper).n1qlQueryToT(n1qlQueryRows.allRows(), Indexes.class);
//
//        long endTime = System.currentTimeMillis();
//        System.out.println("方法运行时间：" + (endTime - startTime) + "ms");
//
//
//        Statement statement = select("*").from("drug").where(x("TypeClassName").eq(s("DIH.Base.Types.BinaryDoc")).and(x("Id").eq(s("DIH.Base.Types.BinaryDoc-kbnhATa8QQSKm1qvyg0s9g"))));
//        System.out.println(statement.toString());
//
//
//        statement = select("*").from("drug").where(x("TypeClassName").eq(s("DXY.Insert"))).orderBy(Sort.asc("Id")).limit(5).offset(0);
//        System.out.println(statement.toString());
    }
}
