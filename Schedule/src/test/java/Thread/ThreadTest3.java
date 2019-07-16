package Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;


//造剧的已发布路演3个月自动结束需求
public class ThreadTest3 {
    //当前时间
    private static Date d = new Date();

    //执行
    public static void main(String[] a) {
        try {
            //new下当前类准备执行线程
            ThreadTest3 tt = new ThreadTest3();
            //创建线程类
            um um = tt.new um();
            //创建线程
            Thread t = new Thread(um);
            //执行线程
            t.start();
            //等待线程结束
            t.join();
            //结束
            System.out.println("线程已结束");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //业务逻辑类     传进项目对象
    public static void updateMoon(projectDB db) {
        //判断项目的时间是否大于当前时间
        if (DateDispose.compareDateSize(DateDispose.formatting_Date(db.time, DateType.Year_Month_Day), d)) {
            //加上3个月
            String s1 = DateDispose.formatting_Date(DateDispose.month_calculate_Date(db.time, 3, DateType.Year_Month_Day), DateType.Year_Month_Day);
            //判断加上3个月后是否大于当前时间
            if (!DateDispose.compareDateSize(DateDispose.formatting_Date(s1, DateType.Year_Month_Day), d)) {
                //如果大于理论上来说要转成已完成状态
                System.out.println(db.id + "\t" + db.time + "\t加上3个月:" + s1 + "\t大于当前时间:" + DateDispose.formatting_Date(d, DateType.Year_Month_Day));
            } else {
                //如果小于理论上来说不动还是未完成状态
                System.out.println(db.id + "\t" + db.time + "\t加上3个月:" + s1 + "\t小于当前时间:" + DateDispose.formatting_Date(d, DateType.Year_Month_Day));
            }
        } else {
            //如果大于当前时间 理论上来说要已完成状态如果不是.转成已完成.如果已经是不做处理
            System.out.println(db.id + "\t" + db.time + "不小于当前时间所以不进行加月份");
        }
    }

    //线程类
    class um implements Runnable {
        //执行方法
        @Override
        public void run() {
            //循环来控制这个线程一直执行
            while (true) {
                //例数据库查出来的实体对象
                projectDB[] dbs = new projectDB[]{new projectDB("1", "2014-06-17"), new projectDB("2", "2015-10-17"), new projectDB("3", "2016-06-17")};
                //循环数据库读到的对象
                for (projectDB db : dbs) {
                    //执行业务逻辑
                    updateMoon(db);
                    //业务逻辑执行完成
                    System.out.println("id:" + db.id + "\t已停止\t 时间为:" + db.time + "\n");
                }
                try {
                    //准备休眠线程
                    System.out.println("线程已休眠\n");
                    //输入休眠时间10秒
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //假定的项目对象
    class projectDB {
        //初始化项目
        public projectDB(String id, String time) {
            this.id = id;
            this.time = time;
        }

        //项目id;
        public String id;
        //项目时间
        public String time;
    }
}
