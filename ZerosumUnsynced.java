import java.lang.Thread;


public class ZerosumUnsynced extends Thread {
	protected int calories, inc, delay;

	public ZerosumUnsynced(int init, int inc, int delay) {
		this.calories = init;
		this.inc = inc;
		this.delay = delay; 
	}

	public void run() {
		try {
			for(int i = 0; i < 1000; i++) {
				System.out.println(calories + " ");
				calories += inc;
				sleep(delay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new ZerosumUnsynced(0, 1, 1).start();
		new ZerosumUnsynced(0, -1, 1).start();
	}
} 