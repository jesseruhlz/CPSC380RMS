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
public class Timer {
    public Timer(){
        //
    }
    
    public double getTime(){
        return(System.nanoTime() / 1000000);
    }
    
    public void run(Semaphore s){
        double startTime = System.nanoTime();
        
        while((System.nanoTime() / 1000000) < (startTime / 1000000 + 10 * 16 * 100)){
            if (((System.nanoTime() - startTime)/100000) % 100 == 0){
                s.release();
            }
        }
    }
}
