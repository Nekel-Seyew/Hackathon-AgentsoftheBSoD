/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuAndStory;

import Utilities.Image2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author RomulusAaron
 */
public class StoryJournal {
    public static ArrayList<StoryJournal>story=new ArrayList<>();
    
    Image2D sprite;
    String sayings;
    int i;
    
    public StoryJournal(String s, int i){
        sayings=new String(s);
        this.i=i;
    }
    
    
    
    public static void makeStories(){
        try{
            Scanner reader = new Scanner(new File("Resources/Story.story"));
            String build="";
            int num=0;
            while(reader.hasNextLine()){
                String string=reader.nextLine();
                if(string.startsWith("$")){
                    num=Integer.parseInt(string.substring(string.indexOf('$')+1,string.indexOf(':')));
                    build+=string.substring(string.indexOf(':')+1, string.indexOf(';'));
                    build+="\n";
                }
                if(string.startsWith("#")){
                    build+=string.substring(string.indexOf('#')+1, string.indexOf(';'));
                }
                if(string.contains("&end;")){
                    story.add(num, new StoryJournal(build,num));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
