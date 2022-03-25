package es.mybi.test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

class WorkerPut implements Runnable {
    private ConcurrentMap<String, Integer> map;

    public WorkerPut(ConcurrentMap<String, Integer> map) {
	this.map = map;
    }

    @Override
    public void run() {
	try {
	    int val = (int)(Math.random() * ((15 - 2) + 1)) + 2;
	    map.put("B", val);
	    System.out.println("Thread " + Thread.currentThread().getName() + " setting B " + val);
	    Thread.sleep((int)(Math.random() * ((5 - 2) + 1)) + 2);
    	    val = (int)(Math.random() * ((15 - 2) + 1)) + 2;
	    map.put("Z", val);
	    System.out.println("Thread " + Thread.currentThread().getName() + " setting Z " + val);	    
    	    val = (int)(Math.random() * ((15 - 2) + 1)) + 2;	   
	    map.put("A", val);
	    System.out.println("Thread " + Thread.currentThread().getName() + " setting A " + val);	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}

class WorkerGet implements Runnable {
    private ConcurrentMap<String, Integer> map;

    public WorkerGet(ConcurrentMap<String, Integer> map) {
	this.map = map;
    }

    @Override
    public void run() {
	try {
	    Thread.sleep(5000);
	    System.out.println(map.get("A"));
	    Thread.sleep(2000);
	    System.out.println(map.get("B"));
	    System.out.println(map.get("Z"));
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}

/**
   Compile: javac -d . ConcurrentMapApp.java
   Run:     java es/mybi/test/ConcurrentMapApp
 */
public class ConcurrentMapApp {
    public static void main(String[] args) {
	ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
	WorkerPut p1 = new WorkerPut(map);
	WorkerGet g1 = new WorkerGet(map);

	new Thread(p1).start();
	new Thread(p1).start();
	new Thread(g1).start();
    }
}
