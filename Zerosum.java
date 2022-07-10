import java.lang.Thread;
import java.lang.InterruptedException;


public class Zerosum extends Thread {
	protected int dailyCalories, delay;
	private String gainOrBurn;
	private static int balance = 0;

	public Zerosum(int dailyCalories, String gainOrBurn, int delay) {
		this.dailyCalories = dailyCalories;
		this.gainOrBurn = gainOrBurn;
		this.delay = delay; 
	}

	public void run() {
		for(int i = 0; i < dailyCalories; i++) {
			try {
				synchronized(this) {
					if (gainOrBurn.equals("gain")) {
						gainCalories();
						System.out.println(Thread.currentThread().getName() + " - Gain calories balance" + i + ": " + balance + ", synchronized " + Thread.holdsLock(this));
					} else if (gainOrBurn.equals("burn")) {
						burnCalories();
						System.out.println(Thread.currentThread().getName() + " - Burn calories balance" + i + ": " + balance + ", synchronized " + Thread.holdsLock(this));
					} else {
						System.out.println("Not gaining or burning any calories today");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		Thread gainCalories = new Zerosum(1000, "gain", 10);
		Thread burnCalories = new Zerosum(1000, "burn", 10);
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

	public void gainCalories() throws InterruptedException {
		balance += 1;
		sleep(delay);
	}

	public void burnCalories() throws InterruptedException {
		balance -= 1;
		sleep(delay);
	}
} 