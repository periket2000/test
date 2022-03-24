package es.mybi.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

class Worker1 implements Runnable {

    private BlockingQueue<Integer> queue;

    public Worker1(BlockingQueue<Integer> queue) {
	this.queue = queue;
    }
    
    @Override
    public void run() {
	int counter = 0;
	System.out.println("Worker1 started");	
	while(true) {
	    try {
		System.out.println("Putting in the queue ... " + counter);
		queue.put(counter);
		counter++;
		Thread.sleep(100);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}

class Worker2 implements Runnable {
    
    private BlockingQueue<Integer> queue;

    public Worker2(BlockingQueue<Integer> queue) {
	this.queue = queue;
    }
    
    @Override
    public void run() {
	System.out.println("Worker2 started");
	while(true) {
	    try {
		int counter = queue.take();
		System.out.println("Taking item from queue ... " + counter);
		Thread.sleep(300);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}


/**
 Compile: javac -d . ConsumerProducer.java
 Run:     java es/mybi/test/ConsumerProducer
 */
public class ConsumerProducer {
    public static void main(String[] args) {
	System.out.println("Start");
	BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
	Worker1 w1 = new Worker1(queue);
	Worker2 w2 = new Worker2(queue);
	new Thread(w1).start();
	new Thread(w2).start();
    }
}
