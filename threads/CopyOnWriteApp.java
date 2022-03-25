package es.mybi.test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.Random;

class Worker implements Runnable {
    private List<Integer> list;
    private Random random;

    public Worker(List<Integer> list) {
	this.list = list;
	this.random = new Random();
    }
    
    @Override
    public void run() {
	while(true) {
	    try {
		Thread.sleep(50);	      
	    } catch(InterruptedException e) {
		e.printStackTrace();
	    }
	    list.set(random.nextInt(list.size()), random.nextInt(10));
	}
    }
}

class Reader implements Runnable {
    private List<Integer> list;

    public Reader(List<Integer> list) {
	this.list = list;
    }

    @Override
    public void run() {
	while(true) {
	    try {
		Thread.sleep(500);		
	    } catch(InterruptedException e) {
		e.printStackTrace();
	    }
	    System.out.println(list);
	}
    }
}

/**
  Compile: javac -d . CopyOnWriteApp.java
  Run:     java es.mybi.test.CopyOnWriteApp
 */
public class CopyOnWriteApp {
    public static void main(String[] args) {
	System.out.println("Start!");
	List<Integer> list = new CopyOnWriteArrayList<>();
	list.addAll(Arrays.asList(0,0,0,0,0,0,0,0,0,0));

	Thread t1 = new Thread(new Worker(list));
	Thread t2 = new Thread(new Worker(list));
	Thread t3 = new Thread(new Worker(list));
	Thread t4 = new Thread(new Reader(list));

	t1.start();
	t2.start();
	t3.start();
	t4.start();
    }
}
