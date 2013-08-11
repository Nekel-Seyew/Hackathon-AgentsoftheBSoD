/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Image2D;
import Utilities.Vector2;

/**
 *
 * @author pcowal15
 */
public abstract class Sprite {
    public Vector2 pos;
    Vector2 velocity;
    double direction;
    double speed;
    double health;
    boolean alive;
    double radius;
    Image2D sprite;
    String spritePath;
    //KyleDefined
    boolean alerted;
    public Sprite(String Sprite,Vector2 position){
        spritePath=Sprite;
        sprite=new Image2D(Sprite);
        pos=position;
        velocity=new Vector2();
        direction=0;
        speed=1;
        health=100;
        alive=true;
    }
    public void Update(){
        pos.add(velocity);
    }
    public void hit(double damage){
        if (health>0){
            health-=damage;
            if (health<=0){
                alive=false;
            }
        }
    }
    public boolean alive(){
        return alive;
    }
    public Image2D sprite(){
        return sprite;
    }
    public String spritePath(){
        return spritePath;
    }
    public double x(){
        return pos.getX();
    }
    public double y(){
        return pos.getY();
    }
    public void setDirection(){
        direction=Math.atan2(velocity.getY(),velocity.getX());
    }
    public boolean checkCollision(Vector2 Position,double Radius){
        Vector2 displacement=pos.clone();
        displacement.subtract(Position);
        return (displacement.length()<Radius+radius);
    }
    public void moveTowardsPoint(Vector2 target){
        direction=Math.atan2(target.getY()+pos.getY(),target.getX()-pos.getX());
        velocity.setX(Math.cos(direction)*speed);
        velocity.setY(Math.sin(direction)*speed);
    }
    
    public void Alert(){
        this.alerted=true;
    }
}
