/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuAndStory;

import Utilities.Image2D;
import Utilities.Vector2;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import level.Level;
import objects.Player;
import objects.Sprite;

/**
 *
 * @author RomulusAaron
 */
public class StoryJournal extends Sprite{
    public static ArrayList<StoryJournal>story=new ArrayList<>();
    int i;
    ArrayList<String> sayings;
    
    public StoryJournal(ArrayList s, int i){
        super("Resources/Sprites/cd.png",new Vector2());
        sayings=s;
        this.i=i;
    }
    
    public void givePos(Vector2 pos){
        this.pos=pos;
    }
    
    public static void makeStories(){
        try{
            Scanner reader = new Scanner(new File("Resources/Story.story"));
            String build="";
            int num=0;
            ArrayList<String> mys=new ArrayList<>();
            while(reader.hasNextLine()){
                String string=reader.nextLine();
                if(string.startsWith("$")){
                    num=Integer.parseInt(string.substring(string.indexOf('$')+1,string.indexOf(':')));
                    build=string.substring(string.indexOf(':')+1, string.indexOf(';'));
                    mys.add(build);
                }
                if(string.startsWith("#")){
                    build=string.substring(string.indexOf('#')+1, string.indexOf(';'));
                    mys.add(build);
                }
                if(string.contains("&end;")){
                    story.add(num, new StoryJournal(mys,num));
                    mys=new ArrayList<>();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void Update(Player p) {
        if(p.collides(pos)){
            p.giveEntry(this);
            Level.inst.setForRemove.add(this);
        }
    }
    
    public boolean equals(Object o){
        if(o instanceof StoryJournal){
            StoryJournal sj=(StoryJournal)o;
            return sj.i==this.i;
        }else{
            return false;
        }
    }
}
