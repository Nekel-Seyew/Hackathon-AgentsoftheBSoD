/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import Utilities.Animation;
import Utilities.Image2D;
import Utilities.Vector2;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import raycaster.Camera;

/**
 *
 * @author RomulusAaron
 */
public class LevelMaster {
    public static Hashtable<Color, String> exits=new Hashtable<Color, String>();
    public static Hashtable<String, ArrayList<Exit>> rooms=new Hashtable<>();
    
    public static Hashtable<Color, Object> walls=new Hashtable<Color, Object>();
    public static Hashtable<Integer, Color> w=new Hashtable<>();
    public static Hashtable<Color, Integer> rw=new Hashtable<>();
    
    public static Hashtable<Color, String> items=new Hashtable<>();
    public static Hashtable<Color, String> NPC=new Hashtable<>();
    public static Hashtable<Color, Integer> story=new Hashtable<>();
    
    public static Hashtable<String, Level> levels=new Hashtable<>();
    public static String startLevel=null;
    
    private static int wallNum=1;
    
    private static int exitThread=1;
    private static int wallThread=1;
    
    static String a;
    static String aa;
    static String aaa;
    static String aaaa;
    
    public static void makeLevels(){
        try{
            Scanner reader = new Scanner(new File("Resources/Dungeons/Levels.level"));
            ArrayList<String> maps=null;
            String levelName=null;
            while(reader.hasNext()){
                String string=reader.nextLine();
                if(string.startsWith("$")){
                    if(maps !=null){
                        levels.put(levelName, new Level(maps));
                    }else{
                        maps=new ArrayList<String>();
                    }
                    levelName=string.substring(string.indexOf("$")+1,string.indexOf("="));
                }else if(string.contains("@") && !string.contains("#")){
                    String map=string.substring(string.indexOf("@")+1,string.indexOf(";"));
                    maps.add("Resources/Dungeons/"+map);
                }else if(string.contains("&START&")){
                    if(startLevel != null){
                        throw new Exception("More than One Start Indicated in file! Level: "+levelName);
                    }
                    startLevel=levelName;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
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
                    String sprite=string.substring(string.indexOf("@")+1,string.indexOf("}"));
                    Color rgb=new Color(r,g,b);
                    w.put(wallNum, rgb);
                    rw.put(rgb, wallNum);
                    exits.put(rgb, next);
                    wallNum+=1;
                    Thread t=new Thread(new loadExits(sprite, next, rgb));
                    t.setName("Exit Thread "+exitThread++);
                    t.start();
                    System.out.println("Wall Thread "+t.getName()+" Started, color: "+rgb);
//                    Image2D[] w2=new Image2D[Camera.rayCount];
//                    for(int i=0; i<Camera.rayCount; i++){
//                        aaa="Resources/Sprites/Walls/"+sprite;
//                        w2[i]=new Image2D("Resources/Sprites/Walls/"+sprite);
//                    }
//                    walls.put(rgb, w2);
//                    exits.put(rgb, next);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void makeWalls(){
        try{
            Scanner reader = new Scanner(new File("Resources/Sprites/Walls/Wall.wall"));
            while(reader.hasNext()){
                String string=reader.nextLine();
                if(string.contains("#Wall:")){
                    String next=string.substring(string.indexOf("@")+1, string.indexOf('}'));
                    a=next;
                    String rS=string.substring(string.indexOf("{")+1);
                    int r=Integer.parseInt(rS.substring(0, rS.indexOf(',')));
                    String gS=rS.substring(rS.indexOf(',')+1);
                    int g=Integer.parseInt(gS.substring(0, gS.indexOf(',')));
                    String bS=gS.substring(gS.indexOf(',')+1);
                    aaaa=bS;
                    int b=Integer.parseInt(bS.substring(0, bS.indexOf(',')));
                    Color rgb;
                    if(r==0 && b==0 && g==0){
                        rgb=Color.black;
                    }else{
                        rgb=new Color(r,g,b);
                    }
                    w.put(wallNum, rgb);
                    rw.put(rgb, wallNum);
                    wallNum+=1;
                    Thread t=new Thread(new loadWalls(next, rgb,false));
                    t.setName("Wall Thread "+wallThread++);
                    t.start();
                    System.out.println("Wall Thread "+t.getName()+" Started, color: "+rgb);
//                    Image2D[] w2=new Image2D[Camera.rayCount];
//                    for(int i=0; i<Camera.rayCount; i++){
//                        aaa="Resources/Sprites/Walls/"+next;
//                        w2[i]=new Image2D("Resources/Sprites/Walls/"+next);
//                    }
//                    walls.put(rgb, w2);
                }
                if(string.contains("#WallAnim:")){
                    String next=string.substring(string.indexOf("@")+1, string.indexOf('}'));
                    a=next;
                    String rS=string.substring(string.indexOf("{")+1);
                    int r=Integer.parseInt(rS.substring(0, rS.indexOf(',')));
                    String gS=rS.substring(rS.indexOf(',')+1);
                    int g=Integer.parseInt(gS.substring(0, gS.indexOf(',')));
                    String bS=gS.substring(gS.indexOf(',')+1);
                    aaaa=bS;
                    int b=Integer.parseInt(bS.substring(0, bS.indexOf(',')));
                    Color rgb;
                    if(r==0 && b==0 && g==0){
                        rgb=Color.black;
                    }else{
                        rgb=new Color(r,g,b);
                    }
                    w.put(wallNum, rgb);
                    rw.put(rgb, wallNum);
                    wallNum+=1;
                    Thread t=new Thread(new loadWalls(next, rgb,true));
                    t.setName("Wall Thread "+wallThread++);
                    t.start();
                    
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void makeItemsAndNPC(){
        try{
            Scanner reader = new Scanner(new File("Resources/Sprites/Sprites.sprite"));
            while(reader.hasNext()){
                String string=reader.nextLine();
                if(string.contains("#NPC:")){
                    String next=string.substring(string.indexOf("@")+1, string.indexOf('}'));
                    a=next;
                    String rS=string.substring(string.indexOf("{")+1);
                    int r=Integer.parseInt(rS.substring(0, rS.indexOf(',')));
                    String gS=rS.substring(rS.indexOf(',')+1);
                    int g=Integer.parseInt(gS.substring(0, gS.indexOf(',')));
                    String bS=gS.substring(gS.indexOf(',')+1);
                    aaaa=bS;
                    int b=Integer.parseInt(bS.substring(0, bS.indexOf(',')));
                    Color rgb=new Color(r,g,b);
                    NPC.put(rgb, next);
                }
                else if(string.contains("#Item")){
                    String next=string.substring(string.indexOf("@")+1, string.indexOf('}'));
                    a=next;
                    String rS=string.substring(string.indexOf("{")+1);
                    int r=Integer.parseInt(rS.substring(0, rS.indexOf(',')));
                    String gS=rS.substring(rS.indexOf(',')+1);
                    int g=Integer.parseInt(gS.substring(0, gS.indexOf(',')));
                    String bS=gS.substring(gS.indexOf(',')+1);
                    aaaa=bS;
                    int b=Integer.parseInt(bS.substring(0, bS.indexOf(',')));
                    Color rgb=new Color(r,g,b);
                    items.put(rgb, next);
                }
                else if(string.contains("#Story")){
                    int next=Integer.parseInt(string.substring(string.indexOf("@")+1, string.indexOf('}')));
                    String rS=string.substring(string.indexOf("{")+1);
                    int r=Integer.parseInt(rS.substring(0, rS.indexOf(',')));
                    String gS=rS.substring(rS.indexOf(',')+1);
                    int g=Integer.parseInt(gS.substring(0, gS.indexOf(',')));
                    String bS=gS.substring(gS.indexOf(',')+1);
                    int b=Integer.parseInt(bS.substring(0, bS.indexOf(',')));
                    Color rgb=new Color(r,g,b);
                    story.put(rgb, next);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static int getNum(Color c){
        if(rw.containsKey(c)){
            return rw.get(c);
        }else{
            return 0;
        }
    }
    
    public static boolean isWall(Color c){
        return rw.containsKey(c);
    }
    
    
    public static boolean isExit(Color c) {
        return exits.containsKey(c);
    }
    
    private static class loadWalls implements Runnable{

        private String next;
        private Color rgb;
        private boolean anim;

        public loadWalls(String n, Color c, boolean anim) {
            this.rgb = c;
            this.next = new String(n);
            this.anim=anim;
        }

        @Override
        public void run() {
            if (anim) {
                WallAnimation w2 = new WallAnimation("Resources/Sprites/Walls/"+next, 10,Camera.rayCount);
                for(int i=0; i<Camera.rayCount; i++){
                    w2.add(i,"Resources/Sprites/Walls/"+next);
                }
                walls.put(rgb, w2);
            } else {
                Image2D[] w2 = new Image2D[Camera.rayCount];
                for (int i = 0; i < Camera.rayCount; i++) {
                    w2[i] = new Image2D("Resources/Sprites/Walls/" + next);
                }
                walls.put(rgb, w2);
            }
            
            System.out.println("Thread color: "+rgb+" Done!");
        }
    }
    
    private static class loadExits implements Runnable {

        private String sprite;
        private String next;
        private Color rgb;

        public loadExits(String s, String n, Color c) {
            this.sprite = new String(s);
            this.next = new String(n);
            this.rgb = c;
        }

        @Override
        public void run() {
            Image2D[] w2 = new Image2D[Camera.rayCount];
            for (int i = 0; i < Camera.rayCount; i++) {
                w2[i] = new Image2D("Resources/Sprites/Walls/" + sprite);
            }
            walls.put(rgb, w2);
            exits.put(rgb, next);
            System.out.println("Thread color: "+rgb+" Done!");
        }
    }
}
