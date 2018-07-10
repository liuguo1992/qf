package com.qFun.qFun.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable{

	public static ReentrantLock t = new ReentrantLock(true);
	public static int i = 0;
	@Override
	public void run() {
		for(int j=0;j<1000;j++){
            t.lock();
            try{
                i++;
            }finally{
                t.unlock();
            }
        }
    }
		
	 public static void main(String[] args) throws InterruptedException {
	        ReenterLock relock = new ReenterLock();
	        Thread t1 = new Thread(relock);
	        Thread t2 = new Thread(relock);
	        t1.start();
	        t2.start();     
	        t1.join();      
	        t2.join();
	        System.out.println(i);
	    }
}
