import java.lang.Thread;
import java.lang.InterruptedException;


public class Zerosum extends Thread {
	protected int dailyCalories, delay;
	private static int balance = 0;

	public Zerosum(int dailyCalories, int delay) {
		this.dailyCalories = dailyCalories;
		this.delay = delay; 
	}
	
	public void gainCalories() {
		synchronized(this) {
			for (int i = 0; i < dailyCalories; i++) {
				System.out.println(Thread.currentThread().getName() + " - Gain calories " + i + ", calorie balance " + balance);
				balance += 1;
				try {
					sleep(delay);
					if (balance > 0) {
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				notify();
			}
		}
	}

	public void burnCalories() {
		synchronized(this) {
			for (int i = 0; i < dailyCalories; i++) {
				System.out.println(Thread.currentThread().getName() + " - Burn calories " + i + ", calorie balance " + balance);
				balance -= 1;
				try {
					sleep(delay);
					if (balance < 0) {
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				notify();
			}
		}
	}
	
	public static void main(String args[]) {
		Zerosum zs = new Zerosum(1000, 10);

		Thread gainCalories = new Thread(new Runnable() {
			public void run() {
				zs.gainCalories();
			}
		});
		
		Thread burnCalories = new Thread(new Runnable() {
			public void run() {
				zs.burnCalories();
			}
		});

		gainCalories.start();
		burnCalories.start();

		try {
			gainCalories.join();
			burnCalories.join();
		} catch (Exception e) {
		  e.printStackTrace();	
		}
		System.out.println("Final Calorie Balance: " + balance);
	}
} 