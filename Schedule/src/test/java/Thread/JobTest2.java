package Thread;

import schedule.*;
import schedule.service.JobService;

import java.util.Date;


//造剧的已发布路演3个月自动结束需求
public class JobTest2 {
	//当前时间
	private static Date d=new Date();
	
	public static void main(String[]  age){
		//创建框架的job对象.with里面写要执行的方法
		Job job = Job.with(() -> {
			JobTest2 jobtest2=new JobTest2();
			//例数据库查出来的实体对象
			projectDB[] dbs=new projectDB[]{jobtest2.new projectDB("1","2014-06-17"),jobtest2.new projectDB("2","2015-10-17"),jobtest2.new projectDB("3","2016-06-17")};
			//循环数据库读到的对象
			for (projectDB db:dbs) {
				//执行业务逻辑
				updateMoon(db);
				//业务逻辑执行完成
				System.out.println("id:"+db.id+"\t已停止\t 时间为:"+db.time+"\n");
			}
		}).when(
				//月份.1到12月
				Month.at(1, 12).with(
					//小时.0点
					Hour.at(0).with(
						//分钟.0分钟
						Minute.at(0).with(
							//秒.0秒
						    Second.at(0)
						)
					)
				)
			);
		//创建线程服务
		JobService s = new JobService();
		//添加线程方法
		s.addJob(job);
		//启动线程
		s.start();
	}
	
	//业务逻辑类     传进项目对象
	public static void updateMoon(projectDB db) {
		//判断项目的时间是否大于当前时间
		if (DateDispose.compareDateSize(DateDispose.formatting_Date(db.time, DateType.Year_Month_Day), d)) {
			//加上3个月
			String s1 = DateDispose.formatting_Date(DateDispose.month_calculate_Date(db.time, 3, DateType.Year_Month_Day),DateType.Year_Month_Day);
			//判断加上3个月后是否大于当前时间
			if (!DateDispose.compareDateSize(DateDispose.formatting_Date(s1, DateType.Year_Month_Day),d)) {
				//如果大于理论上来说要转成已完成状态
				System.out.println(db.id + "\t"+db.time+"\t加上3个月:"+s1+"\t大于当前时间:"+DateDispose.formatting_Date(d, DateType.Year_Month_Day));
			}else{
				//如果小于理论上来说不动还是未完成状态
				System.out.println(db.id + "\t"+db.time+"\t加上3个月:"+s1+"\t小于当前时间:"+DateDispose.formatting_Date(d, DateType.Year_Month_Day));
			}
		}else{
			//如果大于当前时间 理论上来说要已完成状态如果不是.转成已完成.如果已经是不做处理
			System.out.println(db.id + "\t"+db.time+"不小于当前时间所以不进行加月份");
		}
	}
	
	//假定的项目对象
	class projectDB {
		//初始化项目
		public projectDB (String id,String time){
			this.id=id;
			this.time=time;
		}
		//项目id;
		public String id;
		//项目时间
		public String time;
	}
}
