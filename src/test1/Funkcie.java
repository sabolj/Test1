/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author sabol
 */
public class Funkcie {
    
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);    
    VypisMienA_Hodnot vypis = null;
    int interval_vypisu=5;
    double kurz_usd=0;
    Map<String, Double> meny_a_sumy = new HashMap<String, Double>();
    int id_operacie;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    
    Funkcie(){
        id_operacie=0;
        nf.setMaximumFractionDigits(2);
    }
    
    public void vypisMenu(){
         System.out.println("\nVyberte si z menu:\n"+
                            "   1. Výpis mien a ich hodnôt\n"+
                            "   2. Zadanie meny a jej hodnoty\n"+
                            "   3. Nastavenie cyklu výpisu mien a ich hodnôt (aktuálne "+interval_vypisu+" sec.)\n"+
                            "   4. Zadanie kurzu USD (aktuálne "+kurz_usd+")\n"+
                            "   5. Ukončiť program\n"
                            );
    }
    
    public void vypisChybnaMoznost(){
        System.out.println(ANSI_RED +"Nevybrali ste žiadnu z ponúkaných možností, skúste znova.\n" +ANSI_RESET); 
    }
    
    public boolean spracujVlozeneParametre(String vlozene_parametre){
        
         if(vlozene_parametre==null){return false;}
         if(vlozene_parametre.trim().equals("")){return false;}
         
         String[] pole_parametrov=vlozene_parametre.toUpperCase().split(" ");
         
        
         if(pole_parametrov.length==1 && id_operacie==1 && !pole_parametrov[0].equals("1") && vypis!=null){
            
             vypis.run=false;
             vypis=null;
             id_operacie=0;
             vypisMenu();
             return true;             
         }
         if(id_operacie==2){vlozMenuA_JejHodnotu(pole_parametrov);}
         if(id_operacie==3){
             try{                
                interval_vypisu = Integer.parseInt(pole_parametrov[0]);
                System.out.println("\nBol nastavený nový interval zobrazovania zoznamu: "+interval_vypisu+" sec.\n");
                id_operacie=0;
                vypisMenu();                 
                return true;
             }catch(Exception e){
                System.out.println("\nChyba pri integer konverzii  počtu sekúnd pre interval zobrazovania zoznamu: "+e+"\n");
             }
         }
         if(id_operacie==4){
             try{                
                kurz_usd = Double.parseDouble(pole_parametrov[0].replace(",", "."));
                System.out.println("\nBol nastavený nový kurz pre USD: "+nf.format(kurz_usd)+"\n");
                id_operacie=0;
                vypisMenu(); 
                return true;
             }catch(Exception e){
                System.out.println("\nChyba pri double konverzii kurzu: "+e+"\n");
             }
         }
         if(pole_parametrov.length==1 && id_operacie==0){spustFunkcieZ_Menu(pole_parametrov[0]);} 
         return true;         
    }
    
    
    private boolean spustFunkcieZ_Menu(String volba_menu){
        
        id_operacie=0;
       
        try{
           id_operacie = Integer.parseInt(volba_menu);
        }catch(Exception e){
           
        }
        
      
        
        switch(id_operacie){
                case 1:                      
                      vypis = new VypisMienA_Hodnot(interval_vypisu,this);              
                break;   
                case 2:
                break;
                case 3:
                break;
                case 4:
                break;
                case 5:
                    System.out.println("Končím program, dovi :)");
                    System.exit(0);
                break;
                default:                    
                    vypisChybnaMoznost();
                    vypisMenu();
                    id_operacie=0;
        }
        
        return true;
    }
    
    private boolean vlozMenuA_JejHodnotu(String[] pole_parametrov){
        
        if(pole_parametrov.length<1 || pole_parametrov.length>2){return false;}
        
        if(pole_parametrov.length==1){
            if(pole_parametrov[0].equals("Q")){
                id_operacie=0;
                vypisMenu();
                return true;
            }
        }else{                
        
            double suma = 0;
            double povodna_suma = 0;
            
            try{
                suma = Double.parseDouble(pole_parametrov[1].replace(",", "."));
                System.out.println("Vkladam "+pole_parametrov[0]+" vo vyske: "+pole_parametrov[1]);
                try{
                    povodna_suma = meny_a_sumy.get(pole_parametrov[0]);
                    suma+=povodna_suma;
                }catch(NullPointerException e){
                        
                }
                
                meny_a_sumy.put(pole_parametrov[0], suma);
                System.out.println("Nový stav meny "+pole_parametrov[0]+" "+nf.format(meny_a_sumy.get(pole_parametrov[0])));
                return true;
            }catch(Exception e){
                System.out.println("\nChyba pri konverzii sumy: "+e+"\n");
            }            
        }
        
        return false;
    }
    
    public String vratPopisVstupu(){
        switch(id_operacie){
            case 1:
                return ""; // zobrazovanie zoznamu mien, zbytocne nieco pozadovat na vstup
            case 2:
                return "Kód meny a výšku sumy, príklad (USD 1000),\nvkladanie ukončite zadaním `q`:";
            case 3:
                return "Interval zobrazovania zoznamu,\naktuále("+interval_vypisu+" sec.): ";
            case 4:
                return "Kurz USD,\naktuále("+nf.format(kurz_usd)+"): ";
            case 5:
                return ""; // exit programu
            default:
                return "Číslo v menu:";
                    
                    
        }
 
    }
    
    public boolean nacitanieSuboru(String nazov_suboru) throws IOException{
      
        FileInputStream fstream = new FileInputStream(nazov_suboru);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        
        String strLine;

        while ((strLine = br.readLine()) != null)   {  
            id_operacie=2;
            spracujVlozeneParametre(strLine);
        }


        br.close();
        id_operacie=0;
        return true;
    }
    
}
