/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raycaster;

import Hardware_Accelerated.AGame;
import Utilities.ImageCollection;
import Utilities.KeyBoard;
import bsodhackathon.BSoDHackathon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import level.Level;
import level.LevelMaster;
import objects.ParticleManager;
import objects.Player;
import objects.Sprite;

/**
 *
 * @author pcowal15
 */
public class Main extends AGame{
    Camera camera;
    Level level;
    Player player;
    ArrayList<Sprite> sprites;
    ParticleManager particles;
    public boolean isRebellionHappening;
    

    @Override
    public void InitializeAndLoad() {
        isRebellionHappening=false;
        player=new Player(500,500,0);
        sprites=new ArrayList<Sprite>();
        try {
            level=new Level("src\\Resources/ColorTest.bmp",player,sprites);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        camera=new Camera(Math.PI/4,320,640,480);//Field of View, Number of Rays, Width, Height
        //camera=new Camera(Math.PI/4,1279,640,480); //MAX RAY COUNT
        camera.setLevel(level.getWalls()); //
        particles=new ParticleManager(Color.BLUE,-0.1,0.5,0,false,300);//color,gravity,bounciness,air resistance,stickiness,lifetime
        player.setLevel(level.getWalls());
        this.setBackgroundColor(Color.BLACK);
        LevelMaster.makeExists();
    }

    @Override
    public void UnloadContent() {
        
    }

    @Override
    public void Update() {
        player.update(level); 
        //player movement
        if(keyboard.isKeyDown('w')||keyboard.isKeyDown(KeyEvent.VK_UP) || keyboard.isKeyDown(KeyBoard.Eight)){
            player.moveForwards(level);
        }
        if(keyboard.isKeyDown(KeyEvent.VK_LEFT)||keyboard.isKeyDown(KeyBoard.Four)){
            player.turnLeft();
            camera.screwFloor(0.5);
        }
        if(keyboard.isKeyDown('s')||keyboard.isKeyDown(KeyEvent.VK_DOWN)||keyboard.isKeyDown(KeyBoard.Two)){
            player.moveBackwards(level);
        }
        if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)||keyboard.isKeyDown(KeyBoard.Six)){
            player.turnRight();
            camera.screwFloor(0.5);
        }
        if(keyboard.isKeyDown('a')){
            player.moveLeft(level);
        }
        if(keyboard.isKeyDown('d')){
            player.moveRight(level);
        }
        camera.screwFloor(player.speed()/10);
        
        //scope
        if(keyboard.isKeyDown(KeyEvent.VK_SPACE)){
            camera.setFOV(camera.fov+(Math.PI/12-camera.fov)*0.1);
            player.setDirSpeed(Math.PI/360);
        }
        else{
            camera.setFOV(camera.fov+(Math.PI/4-camera.fov)*0.1);
            player.setDirSpeed(Math.PI/90);
        }
        camera.setZ(player.getZ(), 0);
        particles.update(level);
        if(Math.random()<0.1)particles.addExplosion(96,96,32,2,10);
    }

    @Override
    public void Draw(Graphics2D g, ImageCollection batch) {
        camera.castRays(batch, player.getX(), player.getY(), player.getDir());
        if (sprites!=null){
            for(Sprite o:sprites){
                camera.drawImage(batch,level,player, o.sprite(), o.x(), o.y(), true);
            }
            particles.draw(batch, camera, player);
        }
    }

    
    public static void main(String[] args){
        BSoDHackathon.main(args);
    }
    
    public void giveNextLevel(Level l){
        this.level=l;
        camera.setLevel(l.getWalls());
        player.setLevel(l.getWalls());
    }
    
    public boolean isRebellionHappening(){
        return isRebellionHappening;
    }
    
}
