/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author RomulusAaron
 */
public class LevelMaster {
    public static Hashtable<Color, String> exits=new Hashtable<Color, String>();
    
    static String a;
    static String aa;
    static String aaa;
    static String aaaa;
    
    public static void makeExists(){
        try{
            Scanner reader = new Scanner(new File("Resources/Dungeons/Levels.level"));
            while(reader.hasNext()){
                String string=reader.nextLine();
                if(string.contains("#Exit:")){
                    String next=string.substring(string.indexOf("->")+2, string.indexOf(','));
                    a=next;
                    String rS=string.substring(string.indexOf(",")+1);
                    int r=Integer.parseInt(rS.substring(0, rS.indexOf(',')));
                    String gS=rS.substring(rS.indexOf(',')+1);
                    int g=Integer.parseInt(gS.substring(0, gS.indexOf(',')));
                    String bS=gS.substring(gS.indexOf(',')+1);
                    aaaa=bS;
                    int b=Integer.parseInt(bS.substring(0, bS.indexOf(',')));
                    Color rgb=new Color(r,g,b);
                    exits.put(rgb, next);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void make(){
        makeExists();
    }
    
    public static boolean isExit(Color c) {
        return exits.containsKey(c);
    }
}
