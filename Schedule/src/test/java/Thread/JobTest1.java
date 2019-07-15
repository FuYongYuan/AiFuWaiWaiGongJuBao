package Thread;


import schedule.*;
import schedule.service.JobService;

//公司框架实现类//一个方法+一个方法- 对应ThreadTest1.java
public class JobTest1 {
	//定义变量之后用作计算
	private static int j;
	private static int i1;
	private static int i2;
	
	public static void main(String[]  age){
		//创建框架的job对象.with里面写要执行的方法
		Job job1 = Job.with(() -> {
			//执行次数
			i1++;
			//定义一个局部变量
			int x=0;
			//增加局部变量
			x++;
			//变量j加1
			j++;
			//输出
			System.out.println("线程名:"+Thread.currentThread().getName() + "\t -输出类:inc \t 输出j:" + j + "\t 输出x:" + x + "\t 输出i1:" + i1);
		}).when(
				//月份.1到12月
				Month.at(1, 12).with(
					//小时.0点到23点
					Hour.at(0, 23).with(
						//分钟.0分钟到59分钟
						Minute.at(0, 59).with(
							//秒.0秒到59秒 1秒1次可以写成多次固定第几秒执行
						    Second.at(0,59)
						)
					)
				)
			);
		//创建框架的job对象.with里面写要执行的方法
		Job job2 = Job.with(() -> {
			//执行次数
			i2++;
			//定义一个局部变量
			int x=0;
			//增加局部变量
			x--;
			//变量j减1
			j--;
			//输出
			System.out.println("线程名:"+Thread.currentThread().getName() + "\t -输出类:dec \t 输出j:" + j + "\t 输出x:" + x + "\t 输出i2:" + i2);
		}).when(
				//月份.1到12月
				Month.at(1, 12).with(
					//小时.0点到23点
					Hour.at(0, 23).with(
						//分钟.0分钟到59分钟
						Minute.at(0, 59).with(
							//秒.0秒到59秒 1秒1次可以写成多次固定第几秒执行
						    Second.at(0,59)
						)
					)
				)
			);
		//创建线程服务
		JobService s = new JobService();
		//添加线程方法
		s.addJob(job1);
		s.addJob(job2);
		//启动线程
		s.start();
	}
}
