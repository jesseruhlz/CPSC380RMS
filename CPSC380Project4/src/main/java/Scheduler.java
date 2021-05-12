/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author super
 */
import java.util.concurrent.Semaphore;
public class Scheduler {
    private int overrunsThread0;
    private int overrunsThread1;
    private int overrunsThread2;
    private int overrunsThread3;
    //public int time;

    Semaphore sem = new Semaphore(0); // only 1 thread may access at a time
    Semaphore mainSem = new Semaphore(0); // only 1 thread may access at a time

    busyThread BusyThread0 = new busyThread(1, 2);
    busyThread BusyThread1 = new busyThread(2, 4);
    busyThread BusyThread2 = new busyThread(4, 6);
    busyThread BusyThread3 = new busyThread(16, 8);
    //time = 0;

    Thread schedulerThread = new Thread(new Runnable(){
        @Override
        public void run(){
            BusyThread0.runThread();
            BusyThread1.runThread();
            BusyThread2.runThread();
            BusyThread3.runThread();
            schedule();
        }
    });

    public Scheduler(){
        schedulerThread.setPriority(Thread.MAX_PRIORITY);
        //time = 0;
    }

    public void schedule(){
        //BusyThread0.sem.release();
        //BusyThread1.sem.release();
        //BusyThread2.sem.release();
        //BusyThread3.sem.release();
        
        for (int i = 0; i < 160; ++i){
            try{
                
                sem.acquire();
            }catch(InterruptedException e){
                System.out.println("FAILED: Scheduler failed to acquire semaphore.");
            }
            //time++;
            //thread 0 overruns
            if(!BusyThread0.isDone()){
                overrunsThread0++;
            }

            //then schedule thread0
            BusyThread0.sem.release();

            //thread1
            if (i % 2 == 0){
                //check thread1 overrun
                if (!BusyThread1.isDone()){
                    overrunsThread1++;
                }
                //schedule thread 1
                BusyThread1.sem.release();
            }

            //thread 2
            if (i % 4 == 0){
                //check thread2 overrun
                if (!BusyThread2.isDone()){
                    overrunsThread2++;
                }
                //schedule thread 2
                BusyThread2.sem.release();
            }

            if (i % 16 == 0){
                //check thread3 overrun
                if (!BusyThread3.isDone()){
                    overrunsThread3++;
                }
                //schedule thread 3
                BusyThread3.sem.release();
            }

            //continue the thread by stopping from waiting for their semaphore
            BusyThread0.thread.interrupt();
            BusyThread1.thread.interrupt();
            BusyThread2.thread.interrupt();
            BusyThread3.thread.interrupt();
            //flag to the main semaphore to print the results later in a separate function
            mainSem.release();
        }
    }

    //join the individual threads
    public void joinThreads(){
        BusyThread0.joinThread();
        BusyThread1.joinThread();
        BusyThread2.joinThread();
        BusyThread3.joinThread();
        joinTheSchedulerThread();
    }

    //make a function here that will print out the number of completions and overruns
      public void printResults(){
         System.out.println("RESULTS");
         //thread 0
         System.out.println("THREAD 1 COMPLETIONS: " + BusyThread0.getNumberOfCompletions());
         System.out.println("THREAD 1 OVERRUNS: " + overrunsThread0 + "\n");
         //thread 1
         System.out.println("THREAD 2 COMPLETIONS: " + BusyThread1.getNumberOfCompletions());
         System.out.println("THREAD 2 OVERRUNS: " + overrunsThread1 + "\n");
         //thread 2
         System.out.println("THREAD 3 COMPLETIONS: " + BusyThread2.getNumberOfCompletions());
         System.out.println("THREAD 3 OVERRUNS: " + overrunsThread2 + "\n");
         //thread 3
         System.out.println("THREAD 4 COMPLETIONS: " + BusyThread3.getNumberOfCompletions());
         System.out.println("THREAD 4 OVERRUNS: " + overrunsThread3 + "\n");

     }


    //join the main thread
    private void joinTheSchedulerThread(){
        try{
            this.schedulerThread.join();
        }
        catch(Exception e){}
    }
}
