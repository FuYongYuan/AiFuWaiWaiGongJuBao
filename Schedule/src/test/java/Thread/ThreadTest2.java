package Thread;

//Java中有两种实现多线程的方式。一是直接继承Thread类，二是实现Runnable接口。那么这两种实现多线程的方式在应用上有什么区别呢？ 

// 为了回答这个问题，我们可以通过编写一段代码来进行分析。我们用代码来模拟铁路售票系统，实现通过四个售票点发售某日某次列车的100张车票，一个售票点用一个线程表示。
public class ThreadTest2 {
	//创建线程第一种写法
	class thread1 extends Thread{
		//假设有100张票
		private int ticket = 100;
		//线程执行的方法
		public void run() {
			//线程需要循环否则只执行一次
			while (true) {
				//判断票数是否为0张
				if (ticket > 0) {
					//大于0就少1张
					System.out.println("线程名:"+Thread.currentThread().getName()	+ "\t 当前数量:" + ticket--);
				} else {
					//等于小于0就结束
					break;
				}
			}
		}
	}
	
	//创建线程第二种写法
	class thread2 implements Runnable{  
		//假设有100张票
		private int tickets = 100;
		//线程执行的方法
		public void run(){ 
			//线程需要循环否则只执行一次
			while(true){
				//判断票数是否为0张
				if(tickets > 0){  
					//大于0就少1张
					System.out.println("线程名:"+Thread.currentThread().getName()	+ "\t 当前数量:" + tickets--);
			    } else {
					//等于小于0就结束
					break;
				}  
			}  
		}
	} 
	
	public static void main(String[] args) {
		//创建对象
		ThreadTest2 thread=new ThreadTest2();
		//new个线程1的对象
		thread1 t1 = thread.new thread1();
		//写法1.启动4次当前new的线程   java.lang.IllegalThreadStateException 错误原因线程不能被多次调用.
		t1.start();
		t1.start();
		t1.start();
		t1.start();
		//上面的代码中，我们用ThreadTest类模拟售票处的售票过程，run方法中的每一次循环都将总票数减1，模拟卖出一张车票，同时该车票号打印出来，
		//直接剩余的票数到零为止。在ThreadDemo1类的main方法中，我们创建了一个线程对象，并重复启动四次，希望通过这种方式产生四个线程。
		//从运行的结果来看我们发现其实只有一个线程在运行，这个结果告诉我们：一个线程对象只能启动一个线程，无论你调用多少遍start()方法，结果只有一个线程。

		//写法2.创建4次线程对象.并执行
		thread.new thread1().start();
	    thread.new thread1().start();
	    thread.new thread1().start();
	    thread.new thread1().start();
	    //从结果上看每个票号都被打印了四次，即四个线程各自卖各自的100张票，而不去卖共同的100张票。这种情况是怎么造成的呢？我们需要的是，多个线程去处理同一个资源，
	    //一个资源只能对应一个对象，在上面的程序中，我们创建了四个ThreadTest对象，就等于创建了四个资源，每个资源都有100张票，每个线程都在独自处理各自的资源。

		//经过这些实验和分析，可以总结出，要实现这个铁路售票程序，我们只能创建一个资源对象，但要创建多个线程去处理同一个资源对象，并且每个线程上所运行的是相同的程序代码。在回顾一下使用接口编写多线程的过程。
		//写法3.使用thread2实现了Runnable接口的线程对象.
		//new个线程2的对象
		thread2 t2 = thread.new thread2();
	    new Thread(t2).start();
	    new Thread(t2).start();
	    new Thread(t2).start();
	    new Thread(t2).start();
		//上面的程序中，创建了四个线程，每个线程调用的是同一个ThreadTest对象中的run()方法，访问的是同一个对象中的变量（tickets）的实例，这个程序满足了我们的需求。
		//在Windows上可以启动多个记事本程序一样，也就是多个进程使用同一个记事本程序代码。
	}
	
	//可见，实现Runnable接口相对于继承Thread类来说，有如下显著的好处： 
	//(1)适合多个相同程序代码的线程去处理同一资源的情况，把虚拟CPU（线程）同程序的代码，数据有效的分离，较好地体现了面向对象的设计思想。 
	//(2)可以避免由于Java的单继承特性带来的局限。我们经常碰到这样一种情况，即当我们要将已经继承了某一个类的子类放入多线程中，由于一个类不能同时有两个父类，
	//所以不能用继承Thread类的方式，那么，这个类就只能采用实现Runnable接口的方式了。 
	//(3)有利于程序的健壮性，代码能够被多个线程共享，代码与数据是独立的。当多个线程的执行代码来自同一个类的实例时，即称它们共享相同的代码。多个线程操作相同的数据，与它们的代码无关。
	//当共享访问相同的对象是，即它们共享相同的数据。当线程被构造时，需要的代码和数据通过一个对象作为构造函数实参传递进去，这个对象就是一个实现了Runnable接口的类的实例。 
	
}
