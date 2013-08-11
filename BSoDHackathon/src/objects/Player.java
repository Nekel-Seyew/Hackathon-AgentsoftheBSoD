/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import Utilities.Vector2;
import level.Level;


/**
 *
 * @author pcowal15
 */
public class Player {
    public Vector2 pos;//the position of the player
    double direction;
    double speed;
    double hspeed;
    double vspeed;
    double dirspeed;
    double cellSize;
    double z;
    double zdirection;
    double radius=3;
    double bob;
    boolean moving;
    boolean isAttacking;
    
    MeleeWeapon mw;
    
    public Player(double x, double y, double angle){
        pos=new Vector2(x,y);
        direction=angle;
        speed=4;
        hspeed=0;
        vspeed=0;
        dirspeed=Math.PI/90;
        cellSize=64;
        z=32;
        bob=0;
        moving=false;
        mw=new MeleeWeapon("Resources/Sprites/sword.png");
        isAttacking=false;
    }
    public double speed(){
        return Math.sqrt(hspeed*hspeed+vspeed*vspeed);
    }
    public void turnLeft(){
        direction+=dirspeed;
        if (direction>=Math.PI*2){
            direction-=Math.PI*2;
        }
        
    }
    public void turnRight(){
        direction-=dirspeed;
        if (direction<=0){
            direction+=Math.PI*2;
        }
        
    }
    public void update(Level level){
        double s=Math.sqrt(hspeed*hspeed+vspeed*vspeed);
        z=32+s*Math.sin(bob)/2;
        if (s>speed){
            s/=speed;
            hspeed/=s;
            vspeed/=s;
        }
        if (!moving){
            if (hspeed>0) hspeed=Math.max(0,hspeed-1);
            else hspeed=Math.min(0,hspeed+1);
            if (vspeed>0) vspeed=Math.max(0,vspeed-1);
            else vspeed=Math.min(0,vspeed+1);
            
        }
        dX(vspeed*Math.cos(direction),level);
        dY(-vspeed*Math.sin(direction),level);
        dX(hspeed*Math.cos(direction-Math.PI/2),level);
        dY(-hspeed*Math.sin(direction-Math.PI/2),level);
        bob+=0.25;
        moving=false;
        mw.update();
    }
    public void setDirSpeed(double dirSpeed){
        dirspeed=dirSpeed;
    }
    public void moveForwards(Level level){
        vspeed=Math.min(speed,vspeed+1);
        moving=true;
    }
    public void moveBackwards(Level level){
        vspeed=Math.max(-speed,vspeed-1);
        moving=true;
    }
    public void moveLeft(Level level){
        hspeed=Math.max(-speed,hspeed-1);
        moving=true;
    }
    public void moveRight(Level level){
        hspeed=Math.min(speed,hspeed+1);
        moving=true;
    }
    public void dX(double dx,Level level){
        if (!level.rectCell(pos.getX()+dx, pos.getY(),radius)){
            pos.dX(dx);
        }
        else{
            if (dx>0.01) dX(dx*0.9,level);
        }
    }
    public void dY(double dy,Level level){
        if (!level.rectCell(pos.getX(),pos.getY()+dy,radius)){
            pos.dY(dy);
        }
        else{
            if (dy>0.01) dY(dy*0.9,level);
        }
    }
    public double getX(){
        return pos.getX();
    }
    public double getY(){
        return pos.getY();
    }
    public double getZ(){
        return z;
    }
    public double getDir(){
        return direction;
    }
    public void setPos(double x,double y){
        pos=new Vector2(x,y);
    }
    
    public void swingSword(){
        mw.attack();
        isAttacking=true;
    }
    
    public void Draw(ImageCollection batch){
        mw.Draw(batch);
    }
    
    public boolean isAttacking(){
        return isAttacking();
    }
    
}
