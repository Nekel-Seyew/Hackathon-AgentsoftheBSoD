/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Advance.AMath;
import Utilities.Vector2;

/**
 *
 * @author RomulusAaron
 */
public class NPC extends Sprite{
    
    public NPC(String s, Vector2 p){
        super(s,p);
    }
    
    public void update(Player p){
        if(p.isAttacking && AMath.distance(pos, p.pos)<100){
            this.health-=1;
        }
        if(this.health<=0){
            this.alive=false;
        }
    }
}
