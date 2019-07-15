package Thread;

//线程简单入门
	//synchronized 
	//Java语言的关键字，可用来给对象和方法或者代码块加锁，当它锁定一个方法或者一个代码块的时候，同一时刻最多只有一个线程执行这段代码。
	//当两个并发线程访问同一个对象object中的这个加锁同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
	//然而，当一个线程访问object的一个加锁代码块时，另一个线程仍然可以访问该object中的非加锁代码块。
public class ThreadTest1 {
	//定义一个变量j之后用作计算
	private int j;
	
	//创建加线程
	class Inc implements Runnable {
		//线程执行方法
		public void run() {
			//循环10次
			for (int i = 0; i < 10; i++) {
				//执行加方法
				inc();
			}
		}
	}

	//创建减线程
	class Dec implements Runnable {
		//线程执行方法
		public void run() {
			//循环10次
			for (int i = 0; i < 10; i++) {
				//执行减线程
				dec();
			}
		}
	}

	public static void main(String args[]) {
		//创建当前类
		ThreadTest1 tt = new ThreadTest1();
		//创建加j的方法
		Inc inc = tt.new Inc();
		//创建减j的方法
		Dec dec = tt.new Dec();
		//循环
		for (int i = 0; i < 2; i++) {
			//创建一个加线程
			Thread t = new Thread(inc);
			//执行一次加方法
			t.start();
			//创建一个减线程
			t = new Thread(dec);
			//执行一次减方法
			t.start();
		}
	}
	
	//特殊字段类顶有注释     加方法
	private synchronized void inc() {
		//变量j加1
		j++;
		//输出
		System.out.println("线程名:"+Thread.currentThread().getName() + "\t -输出类:inc \t 输出数:" + j);
	}
	
	//特殊字段类顶有注释     减方法
	private synchronized void dec() {
		//变量j减1
		j--;
		//输出
		System.out.println("线程名:"+Thread.currentThread().getName() + "\t -输出类:dec \t 输出数:" + j);
	}
	
}