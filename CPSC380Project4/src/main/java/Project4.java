/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author super
 */
import  java.util.concurrent.Semaphore;
public class Project4 {
    public static void main(String[] args){
        Scheduler RMS = new Scheduler();
        Semaphore mainSem = new Semaphore(0);
        
        RMS.schedulerThread.start();
        
        try{
            RMS.mainSem.acquire();
            RMS.schedulerThread.join();
        }catch(InterruptedException e){}
        
        try{
            RMS.joinThreads();
            RMS.printResults();
        }catch(Exception e){}
    }
}
