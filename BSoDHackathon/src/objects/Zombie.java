/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Advance.AMath;
import Utilities.Animation;
import Utilities.Vector2;
import raycaster.Camera;
import level.Level;

/**
 *
 * @author Kyle Sweeney
 */
public class Zombie extends Sprite{

    Animation sprite;
    int strength;
    double lastSystemTime=System.currentTimeMillis();
    
    public Zombie(String folder, Vector2 pos){
        super("Resources/Dungeons/train.png",pos);
        sprite=new Animation(8,0,new Vector2(),200);
        strength=1;
        if(folder.contains("Guy1")){
            make(1);
        }else if(folder.contains("Guy2")){
            make(2);
        }else if(folder.contains("Guy3")){
            make(3);
        }else if(folder.contains("Guy4")){
            make(4);
        }
        lastSystemTime=System.currentTimeMillis();
        this.health*=strength;
    }
    @Override
    public void Update(Player p) {
        if(this.health <= 0){
             Level.inst.setForRemove.add(this);
        }
        if (this.getPos().distance(p.pos) < 300) {
            this.moveTowardsPoint(p.pos);
            this.Update();
        }
        double time=System.currentTimeMillis();
        if (AMath.distance(pos, p.pos) < 100) {
            if (time - lastSystemTime > 750) {
                p.giveDamage(strength);
                lastSystemTime = time;
            }
        }
        if(AMath.distance(pos, p.pos) < 100 && p.isAttacking()){
            this.health-=10;
        }
        this.Update();
    }
    
    public Animation getSprite(){
        return sprite;
    }
    
    public void make(int j){
        strength=j;
        for(int i=1; i<9; i++){
            sprite.addCell("Resources/Sprites/Guy"+j+"Sprites/guy"+j+"fw"+i+".png");
        }
    }
    
    
    
}
