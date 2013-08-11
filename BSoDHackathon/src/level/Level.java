/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import Utilities.Animation;
import Utilities.Rect;
import Utilities.Vector2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import objects.Pillar;
import objects.Player;
import objects.Sprite;
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
    ArrayList<Sprite> sprites;
    
    
    public Level(String map,Player player,ArrayList<Sprite>objects) throws IOException{
        inst=this;
        exits=new ArrayList<>();
        sprites=objects;
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
                if (color[0]==0 && color[1]==0 && color[2]==0){
                    walls[i][j]=1;//probably brick
                    LevelMaster.w.put(1, wall[i][j]);
                }     
                else if (color[0]==128 && color[1]==0 && color[2]==0){
                    walls[i][j]=2;//metal
                    LevelMaster.w.put(2, wall[i][j]);
                }
                else if (color[0]==0 && color[1]==128 && color[2]==0){
                    walls[i][j]=3;//another metal
                    LevelMaster.w.put(3, wall[i][j]);
                }
                else if (color[0]==0 && color[1]==0 && color[2]==128){
                    walls[i][j]=4;//I'm not even sure
                    LevelMaster.w.put(4, wall[i][j]);
                }else if(color[0]==200 && color[1]==200 && color[2]==200){
                    walls[i][j]=15;
                    LevelMaster.w.put(15, wall[i][j]);
                }else if(color[0]==100 && color[1]==100 && color[2]==100){
                    walls[i][j]=16;
                    LevelMaster.w.put(16, wall[i][j]);
                }else if(color[0]==100 && color[1]==50 && color[2]==25){
                    walls[i][j]=17;
                    LevelMaster.w.put(17, wall[i][j]);
                }else if(color[0]==150 && color[1]==150 && color[2]==150){
                    walls[i][j]=18;
                    LevelMaster.w.put(18, wall[i][j]);
                }else if(colorEqual(color, new Color(200,200,0))){
                    walls[i][j]=19;
                    LevelMaster.w.put(19, wall[i][j]);
                }else if(colorEqual(color, new Color(255,1,1))){
                    walls[i][j]=20;
                    LevelMaster.w.put(20, wall[i][j]);
                }else if(colorEqual(color, new Color(127,127,127))){
                    walls[i][j]=22;
                    LevelMaster.w.put(22, wall[i][j]);
                }else if(colorEqual(color, new Color(100,234,123))){
                    walls[i][j]=23;
                    LevelMaster.w.put(23, wall[i][j]);
                }else if(colorEqual(color, new Color(0,20,255))){
                    walls[i][j]=24;
                    LevelMaster.w.put(24, wall[i][j]);
                }else if(colorEqual(color, new Color(20,20,20))){
                    walls[i][j]=25;
                    LevelMaster.w.put(25, wall[i][j]);
                }else if(colorEqual(color, new Color(100,25,50))){
                    walls[i][j]=26;
                    LevelMaster.w.put(26, wall[i][j]);
                }else if(colorEqual(color, new Color(200,0,0))){
                    walls[i][j]=27;
                    LevelMaster.w.put(27, wall[i][j]);
                }else if(colorEqual(color, new Color(32,45,54))){
                    walls[i][j]=0;
                    
                } else if (color[0]==255 && color[1]==0 && color[2]==0){
                    walls[i][j]=0;//blank spot
                    //adds a pixelated column
                    objects.add(new Pillar(new Vector2(i*64+32,j*64+32)));
                }
                else if (color[0]==0 && color[1]==255 && color[2]==0){
                    walls[i][j]=0;//another blank spot
                }
                else if (color[0]==0 && color[1]==0 && color[2]==255){
                    walls[i][j]=0;//blank spot
                }
                else if (color[0]==255 && color[1]==255 && color[2]==0){
                    walls[i][j]=0;//blank spot
                    //sets the player's position to the current cell
                    player.setPos(i*64+32,j*64+32);
                }else if(isExit(color[0],color[1],color[2])){
                    addDoor(i,j,color,player,map);
//                    walls[i][j]=5;
//                    LevelMaster.w.put(5, wall[i][j]);
//                    exits.add(new Exit(LevelMaster.exits.get(wall[i][j]),new Vector2(i*64+32,j*64+32),wall[i][j]));
                }
                else{
                    walls[i][j]=0;
                }
            }
        }
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
//    public int cell(double X, double Y) {
//        //returns the cell value at the cell containing the given point
//        int cellX = (int) Math.floor(X / cellSize);
//        int cellY = (int) Math.floor(Y / cellSize);
//        try{
//            return walls[cellX][cellY];
//        }
//        catch(Exception e){
//            return 1;
//        }
//    }
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
            s.Update();
        }
    }
    
    public void addDoor(int i, int j, int[] color, Player p, String s){
        Color c=new Color(color[0], color[1], color[2]);
        Color player=new Color(255,255,0);
        exits.add(new Exit(LevelMaster.exits.get(wall[i][j]),new Vector2(i*64+32,j*64+32),wall[i][j],s));
        if(color[0]==128 && color[1]==60 && color[2]==60){
            walls[i][j]=5;
            LevelMaster.w.put(5, wall[i][j]);
        }else if(color[0]==128 && color[1]==10 && color[2]==60){
            walls[i][j]=6;
            LevelMaster.w.put(6, wall[i][j]);
        }else if(color[0]==60 && color[1]==10 && color[2]==60){
            walls[i][j]=7;
            LevelMaster.w.put(7, wall[i][j]);
        }else if(color[0]==255 && color[1]==255 && color[2]==10){
            walls[i][j]=8;
            LevelMaster.w.put(8, wall[i][j]);
        }else if(color[0]==10 && color[1]==248 && color[2]==255){
            walls[i][j]=9;
            LevelMaster.w.put(9, wall[i][j]);
        }else if(color[0]==255 && color[1]==255 && color[2]==128){
            walls[i][j]=10;
            LevelMaster.w.put(10, wall[i][j]);
        }else if(color[0]==60 && color[1]==60 && color[2]==50){
            walls[i][j]=11;
            LevelMaster.w.put(11, wall[i][j]);
        }else if(color[0]==128 && color[1]==234 && color[2]==126){
            walls[i][j]=12;
            LevelMaster.w.put(12, wall[i][j]);
        }else if(color[0]==255 && color[1]==5 && color[2]==5){
            walls[i][j]=13;
            LevelMaster.w.put(13, wall[i][j]);
        }else if(color[0]==101 && color[1]==101 && color[2]==101){
            walls[i][j]=14;
            LevelMaster.w.put(14, wall[i][j]);
        }
    }
    
    public boolean isExit(int r, int g, int b){
        return LevelMaster.isExit(new Color(r,g,b));
    }
    
    public boolean colorEqual(int[] color, Color c){
        return color[0]==c.getRed() && color[1]==c.getGreen() && color[2]==c.getBlue();
    }
}
