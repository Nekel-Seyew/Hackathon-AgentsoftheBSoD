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

/**
 *
 * @author pcowal15
 */
public class Level {
    Color[] wall;
    int[][] walls;//I'm representing the walls as integers here, with the integers corresponding to textures/colors
    double cellSize=64;//The cells in the game are 64 by 64, as are the wall textures.
    public Level(String map,Player player,ArrayList<Sprite>objects) throws IOException{
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
        int[] color=new int[3];
        //The following loop parses through the buffered image, checking the colors
        for(int i=0; i<mapImage.getWidth(); i++){
            for(int j=0; j<mapImage.getHeight(); j++){
                mapImage.getRaster().getPixel(i, j, color);
                //depending on the color, it sets the wall value and/or adds an object
                if (color[0]==0 && color[1]==0 && color[2]==0){
                    walls[i][j]=1;//probably brick
                }     
                else if (color[0]==128 && color[1]==0 && color[2]==0){
                    walls[i][j]=2;//metal
                }
                else if (color[0]==0 && color[1]==128 && color[2]==0){
                    walls[i][j]=3;//another metal
                }
                else if (color[0]==0 && color[1]==0 && color[2]==128){
                    walls[i][j]=4;//I'm not even sure
                }    
                else if (color[0]==255 && color[1]==0 && color[2]==0){
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
    public boolean rectCell(double x, double y, double r){
        //I think this computes whether or not the given square with sidelength 2r intersects with a wall
        return (wall(x,y)||wall(x-r,y)||wall(x+r,y)||wall(x,y-r)||wall(x,y+r)
                ||wall(x-r,y-r)||wall(x+r,y+r)||wall(x+r,y-r)||wall(x-r,y+r));
    }
    public int cell(double X, double Y) {
        //returns the cell value at the cell containing the given point
        int cellX = (int) Math.floor(X / cellSize);
        int cellY = (int) Math.floor(Y / cellSize);
        try{
            return walls[cellX][cellY];
        }
        catch(Exception e){
            return 1;
        }
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
    
    public boolean isExit(int r, int g, int b){
        return LevelMaster.isExit(new Color(r,g,b));
    }
}
