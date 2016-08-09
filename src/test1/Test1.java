/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.io.Console;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sabol
 */
public class Test1 {

    /**
     * @param args the command line arguments
     */
    boolean bezi_s=false;
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Funkcie fn = new Funkcie();

        if(args.length==1){
        try {
            fn.nacitanieSuboru(args[0]);
        } catch (Exception ex) {
            Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        Console vstup_z_okna = System.console();

        fn.vypisMenu();


        
        while (true) {
   
            if (vstup_z_okna == null) {
                System.err.println("Nenašla sa žiadna konzola z ktorej by šlo čítať !");
                System.exit(1);
            }
                        

            String vlozene_parametre = vstup_z_okna.readLine(fn.vratPopisVstupu()); 
            
            if(vlozene_parametre==null){
                fn.vypisChybnaMoznost();                
            }else{
                vlozene_parametre = vlozene_parametre.replaceAll("( )+", " ");                
            }
            
            fn.spracujVlozeneParametre(vlozene_parametre);
                                                      
        }
        
    }
    
   
    
}
