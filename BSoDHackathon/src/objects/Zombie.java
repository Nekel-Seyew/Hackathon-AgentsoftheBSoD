/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Advance.AMath;
import Utilities.Animation;
import Utilities.Vector2;
import raycaster.Camera;

/**
 *
 * @author pcowal15
 */
public class Zombie extends Sprite{

    Animation sprite;
    int strength;
    
    public Zombie(String folder, Vector2 pos){
        super("Resources/Dungeons/train.png",pos);
        sprite=new Animation(8,0,new Vector2(),200);
    }
    @Override
    public void Update(Player p) {
        if(AMath.distance(pos, p.pos) < 400){
            this.moveTowardsPoint(p.pos);
        }
        if(AMath.distance(pos, p.pos) < 40){
            
        }
    }
    
    public Animation getSprite(){
        return sprite;
    }
    
}
