/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author sabol
 */
public class VypisMienA_Hodnot {
    
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Timer t = new Timer( );
    public boolean run = true;
    
    
    VypisMienA_Hodnot(int pocet_sekund,Funkcie fn){
        
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                
                if(run) {
                   vypisZoznamMienA_Ich_Hodnot(fn);
                } else {
                   t.cancel();
                   t.purge();
                }
            }
        }, 1000,pocet_sekund*1000);
    }

    
        
    
    
    private void vypisZoznamMienA_Ich_Hodnot(Funkcie fn) {
        
        double pridavny_usd_kurz = 0;
        String textovy_pridavny_kurz = "";
        
        System.out.println("Výpis zoznamu mien a ich hodnôt ["+dateFormat.format(new Date())+"],\nak ho chcete zastavit stlačte nejakú klávesu a potom enter.");        
        for (Map.Entry<String, Double> entry : fn.meny_a_sumy.entrySet()){
            textovy_pridavny_kurz = "";
            pridavny_usd_kurz = 0;
            if(entry.getValue()!=0){
                if(fn.kurz_usd>0 && !entry.getKey().equals("USD")){
                    pridavny_usd_kurz = entry.getValue()/fn.kurz_usd;
                    textovy_pridavny_kurz = "(USD "+fn.nf.format(pridavny_usd_kurz)+")";
                }
                System.out.println(entry.getKey()+" "+fn.nf.format(entry.getValue()) + " " + textovy_pridavny_kurz);
            }
        }
        System.out.println("\n\n");
    }

   
     
}
