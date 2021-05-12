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
public class busyThread {
    public Semaphore sem = new Semaphore(0);
    public int period;
    public int numberOfCompletions;
    private double[][] doWorkMatrix = new double[10][10];
    private boolean finishedWork = true;
    
    Thread thread = new Thread(new Runnable(){
        @Override
        public void run(){
            try{
                for (int i = 0; i < 160; ++i){
                    sem.acquire();
                    finishedWork = false;
                    runWithCount();
                    finishedWork = true;
                }
            }
            catch(Exception e){
                
            }  
        }
    });
    
    public busyThread(int per, int prio){
        makeMatrix();
        this.period = per;
        this.thread.setPriority(Thread.MAX_PRIORITY - prio);
    }
    
    public void runThread(){
        this.thread.start();
    }
    
    public void joinThread(){
        try{
            this.thread.join();
        }catch(Exception e){
            
        }
    }
    
    public boolean isDone(){
        return this.finishedWork;
    }
    
    public int getNumberOfCompletions(){
        return this.numberOfCompletions;
    }
    
    private void makeMatrix(){
        for (int i = 0; i < 10; ++i){
            for (int j = 0; j < 10; ++j){
                doWorkMatrix[i][j] = 1;
            }
        }
    }
    
    private void doWork(){
        int production = 1;
        for (int i = 0; i < 10; ++i){
            for (int j = 0; j < 10; ++j){
                int row = (5 * (i%2)) + ((i%2) % 5);
                production *= doWorkMatrix[j][row];
            }
        }
    }
    
    private void runWithCount(){
        for (int i = 0; i < this.period; ++i){
            doWork();
            numberOfCompletions++;
        }
    }
}
