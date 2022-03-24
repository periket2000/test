package es.mybi.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

class Task implements Callable<String> {
    private int id;

    public Task(int id) {
	this.id = id;
    }

    @Override
    public String call() throws Exception {
	System.out.println(String.format("Task with id %s is working in thread %s", id, Thread.currentThread().getName()));
	try {
	    // Random between 2 and 5
	    TimeUnit.SECONDS.sleep((int)(Math.random() * ((5 - 2) + 1)) + 2);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	return "Id: " + id + " thread: " + Thread.currentThread().getName();
    }
}

/**
    Compile: javac -d . CallableApp.java
    Run: java es/mybi/test/CallableApp
 */
public class CallableApp {
    public static void main(String[] args) {
	List<Future<String>> list = new ArrayList<>();
	// Fixed thread executor that will distribute tasks between threads.
	ExecutorService executorFixed = Executors.newFixedThreadPool(4);
	for(int i = 0; i < 15; i++) {
	    Future<String> s = executorFixed.submit(new Task(i));
	    list.add(s);
	}

	for(Future<String> f : list) {
	    try {
		//We'll get some timeouts
		System.out.println("Result: " + f.get(2, TimeUnit.SECONDS));
	    } catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	    } catch (TimeoutException e1) {
		System.out.println("Timeout!");
	    }
	}
	executorFixed.shutdown();	
    }
}
