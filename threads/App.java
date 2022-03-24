package es.mybi.test;

public class App {
    public static void main(String[] args) {
	List<Integer> nums = new ArrayList<>();

	Thread t1 = new Thread(new Runnable() {
		public void run() {
		    for(int i = 0; i < 1000; i++)
			nums.add(i);
		}
	    });

	Thread t2 = new Thread(new Runnable() {
		public void run() {
		    for(int i = 0; i < 1000; i++)
			nums.add(i);
		}
	    });

	t1.start();
	t2.start();
}
