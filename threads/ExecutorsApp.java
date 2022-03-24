package es.mybi.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    private int id;

    public Task(int id) {
	this.id = id;
    }

    @Override
    public void run() {
	System.out.println(String.format("Task with id %s is working in thread %s", id, Thread.currentThread().getName()));
	long duration = (long)Math.random()*50;
	try {
	    TimeUnit.SECONDS.sleep(duration);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}

/**
    Compile: javac -d . ExecutorsApp.java
    Run: java es/mybi/test/ExecutorsApp
 */
public class ExecutorsApp {
    public static void main(String[] args) {
	// Single thread that will execute task secuentially 
	ExecutorService executor = Executors.newSingleThreadExecutor();
	for(int i = 0; i < 5; i++) {
	    executor.execute(new Task(i));
	}
	executor.shutdown();
	
	try {
	    Thread.sleep(2000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println("===================================================");

	// Fixed thread executor that will distribute tasks between threads.
	ExecutorService executorFixed = Executors.newFixedThreadPool(4);
	for(int i = 0; i < 15; i++) {
	    executorFixed.execute(new Task(i));
	}
	executorFixed.shutdown();	
    }
}
