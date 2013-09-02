/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import MenuAndStory.StoryJournal;
import Utilities.Animation;
import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import objects.Pillar;
import objects.Player;
import objects.Sprite;
import objects.Zombie;
import raycaster.Camera;
import raycaster.Main;

/**
 *
 * @author pcowal15
 */
public class Level {
    public static Level inst;
    
    Color[][] wall;
    int[][] walls;//I'm representing the walls as integers here, with the integers corresponding to textures/colors
    double cellSize=64;//The cells in the game are 64 by 64, as are the wall textures.
    
    public ArrayList<Exit> exits;
    public ArrayList<Sprite> sprites;
    public ArrayList<Sprite> setForRemove;
    
    String map;
    Player player;
    ArrayList<String> maps;
    Vector2 playerStartPos;
    boolean hasBeenVisited=false;
    
    public Level(ArrayList<String> maps){
        this.maps=maps;
        map=maps.get(0);//TEMP
        this.make();
    }
    
    public Level(String map,Player player,ArrayList<Sprite>objects) throws IOException{
        inst=this;
        this.player=player;
        this.map=new String(map);
        this.make();
    }
    
    public void make(){
        inst=this;
        try {
            exits=new ArrayList<>();
            sprites=new ArrayList<>();
            setForRemove=new ArrayList<>();
            //This code draws an image to a graphics thingy and gets the BufferedImage off of it
            BufferedImage mapImage = ImageIO.read(new File(map));
            int type = mapImage.getType();
            if(type!=BufferedImage.TYPE_INT_RGB){
                BufferedImage tempImage= new BufferedImage(mapImage.getWidth(),mapImage.getHeight(),BufferedImage.TYPE_INT_RGB);
                Graphics g = tempImage.createGraphics();
                g.drawImage(mapImage,0,0,null);
                g.dispose();
                mapImage=tempImage;
            }
            //Initializes the walls array
            walls=new int[mapImage.getWidth()][mapImage.getHeight()];
            wall=new Color[mapImage.getWidth()][mapImage.getHeight()];
            int[] color=new int[3];
            //The following loop parses through the buffered image, checking the colors
            for(int i=0; i<mapImage.getWidth(); i++){
                for(int j=0; j<mapImage.getHeight(); j++){
                    mapImage.getRaster().getPixel(i, j, color);
                    wall[i][j]=new Color(color[0], color[1],color[2]);
                    //depending on the color, it sets the wall value and/or adds an object
                    if(LevelMaster.isWall(wall[i][j]) && !isExit(color[0],color[1],color[2])){
                        walls[i][j]=LevelMaster.getNum(wall[i][j]);
                    }else if (color[0]==255 && color[1]==255 && color[2]==0){
                        walls[i][j]=0;//blank spot
                        //sets the player's position to the current cell
                        this.playerStartPos=new Vector2(i*64+32,j*64+32);
//                        player.setPos(i*64+32,j*64+32);
                    }else if(isCD(color)){
                        this.makeCD(color, i, j);
                    }
                    else if(isExit(color[0],color[1],color[2])){
                        addDoor(i,j,color,map);
                    }
                    else{
                        this.Zombie(color, i, j);
                        walls[i][j]=0;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        for(int i=0; i<wall.length; i++){
            for(int j=0; j<wall[i].length; j++){
                Color c=wall[i][j];
                double angleToExit=0;
                Vector2 exitLocation=new Vector2();
                if(isExit(c.getRed(), c.getGreen(), c.getBlue())){
                    if(this.isEmptySpace(i+1, j)){
                        angleToExit=Math.PI;
                        exitLocation.setX((i+1)*64+32);
                        exitLocation.setY((j)*64+32);
                    }else if(this.isEmptySpace(i-1, j)){
                        angleToExit=0;
                        exitLocation.setX((i-1)*64+32);
                        exitLocation.setY((j)*64+32);
                    }else if(this.isEmptySpace(i, j+1)){
                        angleToExit=-Math.PI/2;
                        exitLocation.setX((i)*64+32);
                        exitLocation.setY((j+1)*64+32);
                    }else if(this.isEmptySpace(i, j-1)){
                        angleToExit=Math.PI/2;
                        exitLocation.setX((i)*64+32);
                        exitLocation.setY((j-1)*64+32);
                    }
                    for(Exit e: exits){
                        if(e.color.equals(c)){
                            e.setAngle(angleToExit);
                            e.playerExitPosition=exitLocation;
                        }
                    }
                }
            }
        }
        
    }
    
    public void givePlayer(Player p, Vector2 pos, double dir){
        this.player=p;
        p.setPos(pos.getX(), pos.getY());
        
    }
    
    public void givePlayer(Player p){
        this.player=p;
        p.setPos(this.playerStartPos.getX(), this.playerStartPos.getY());
    }
    
    public int[][] getWalls(){
        //returns the array
        return walls;
    }
    
    public Color[][] getWall(){
        return wall;
    }
    
    public boolean rectCell(double x, double y, double r){
        //I think this computes whether or not the given square with sidelength 2r intersects with a wall
        return (wall(x,y)||wall(x-r,y)||wall(x+r,y)||wall(x,y-r)||wall(x,y+r)
                ||wall(x-r,y-r)||wall(x+r,y+r)||wall(x+r,y-r)||wall(x-r,y+r));
    }
    public boolean wall(double X, double Y) {
        //returns whether or not the cell value at the given point is greater than zero
        //i.e. is a solid wall
        int cellX = (int) Math.floor(X / cellSize);
        int cellY = (int) Math.floor(Y / cellSize);
        try{
            if (walls[cellX][cellY]>0) return true;
            else return false;
        }
        catch(Exception e){
            return true;
        }
    }
    
    public void update(Player p, Main game){
        for(Exit e : exits){
            e.update(p, game);
        }
        for(Sprite s: sprites){
            s.Update(p);
        }
        for(Sprite s: setForRemove){
            sprites.remove(s);
        }
    }
    
    public void Draw(Camera camera, Player player, ImageCollection batch) {
        for (Sprite o : sprites) {
            if(o instanceof Zombie){
                Zombie z=(Zombie)o;
                camera.drawImage(batch, player, z.getSprite(), z.x(), z.y(), true);
                continue;
            }
            camera.drawImage(batch, player, o.sprite(), o.x(), o.y(), true);
        }
    }
    
    public void addDoor(int i, int j, int[] color, String s){
        Color c=new Color(color[0], color[1], color[2]);
        Color player=new Color(255,255,0);
        exits.add(new Exit(LevelMaster.exits.get(wall[i][j]),new Vector2(i*64+32,j*64+32),wall[i][j],s));
        walls[i][j]=LevelMaster.getNum(c);
    }
    
    public boolean isEmptySpace(int i, int j){
        try{
            if(wall[i][j].equals(Color.white)){
                return true;
            }else{
                return false;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }
    
    public boolean isExit(int r, int g, int b){
        return LevelMaster.isExit(new Color(r,g,b));
    }
    
    public boolean colorEqual(int[] color, Color c){
        return color[0]==c.getRed() && color[1]==c.getGreen() && color[2]==c.getBlue();
    }
    
    public void randomItem(){
        //TODO: finish randomItem function.
    }
    
    public boolean isCD(int[] color){
        return color[0]==32 && color[0]==32 && color[2]<10;
    }
    public void makeCD(int[] color, int i, int j){
        walls[i][j]=0;
        StoryJournal sj=StoryJournal.story.get(LevelMaster.story.get(new Color(color[0],color[1],color[2])).intValue());
        sprites.add(sj);
        sj.givePos(new Vector2(i*64+32,j*64+32));
    }
    
    public void Zombie(int[] color, int i, int j){
        if(colorEqual(color, new Color(123,123,0))){
            walls[i][j]=0;
            String s=LevelMaster.NPC.get(new Color(123,123,0));
            sprites.add(new Zombie(s,new Vector2(i*64+32,j*64+32)));
        }else if(colorEqual(color, new Color(123,0,123))){
            walls[i][j]=0;
            String s=LevelMaster.NPC.get(new Color(123,0,123));
            sprites.add(new Zombie(s,new Vector2(i*64+32,j*64+32)));
        }else if(colorEqual(color, new Color(32,123,150))){
            walls[i][j]=0;
            String s=LevelMaster.NPC.get(new Color(32,123,150));
            sprites.add(new Zombie(s,new Vector2(i*64+32,j*64+32)));
        }else if(colorEqual(color, new Color(122,35,111))){
            walls[i][j]=0;
            String s=LevelMaster.NPC.get(new Color(122,35,111));
            sprites.add(new Zombie(s,new Vector2(i*64+32,j*64+32)));
        }
    }
    
    public void setVisited(boolean b){
        this.hasBeenVisited=b;
    }
    
    public boolean hasVisited(){
        return this.hasBeenVisited;
    }
}
