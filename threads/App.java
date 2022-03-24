package es.mybi.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 Compile: javac -d . App.java
 Run:     java es/mybi/test/App (ok)

    - with ok param, works as expected due to synchronizedList.
    - without params, threads overlap values.
 */
public class App {
    public static void main(String[] args) {

	List<Integer> nums = Collections.synchronizedList(new ArrayList<>());
	List<Integer> nums1 = new ArrayList<>();

	String val = "";
	if(args.length > 0) {
	    val = args[0];
	}

	final String v = val;

	Thread t1 = new Thread(new Runnable() {
		public void run() {
		    for(int i = 0; i < 10000; i++)
			if("ok".equals(v))
			    nums.add(i);
		        else
			    nums1.add(i);
		}
	    });

	Thread t2 = new Thread(new Runnable() {
		public void run() {
		    for(int i = 0; i < 10000; i++)
			if("ok".equals(v))
			    nums.add(i);
		        else
			    nums1.add(i);
		}
	    });

	t1.start();
	t2.start();

	try {
	    t1.join();
	    t2.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	System.out.println(String.format("Size of array: %s", nums.size()));
	System.out.println(String.format("Size of array: %s", nums1.size()));
    }
}
