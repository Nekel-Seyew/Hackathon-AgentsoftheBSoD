/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import Advance.AMath;
import Utilities.Vector2;
import java.awt.Color;
import java.util.logging.Logger;
import objects.Player;
import raycaster.Main;

/**
 *
 * @author RomulusAaron
 */
public class Exit {
    
    
    String next;
    Vector2 pos;
    Color color;
    String now;
    Main game;
    double angle;
    Vector2 playerExitPosition;
    boolean justUsed;
    
    public Exit(String n, Vector2 pos, Color c, String now){
        next= new String(n);
        this.pos=pos;
        this.color=c;
        this.now=new String(now);
        justUsed=false;
    }
    
    public String nextPlace(){
        return next;
    }
    
    public void setAngle(double pa){
        this.angle=pa;
    }
    
    public void update(Player p, Main game){
        this.game=game;
        if(AMath.distance(pos, p.pos) < 100 && game.keyboard.isKeyDown('e') && !justUsed){
            game.giveNextLevel(next(p,game.isRebellionHappening));
            p.exitLevel(this);
        }
        if(justUsed && game.keyboard.isKeyUp('e')){
            justUsed=false;
        }
    }
    
    public Level next(Player p, boolean rebel){
        try {
            Level l=LevelMaster.levels.get(next);
            if(!l.hasVisited()){
                l.setVisited(true);
                l.make();
            }
            for(Exit e: l.exits){
                if(e.next.equals(game.getLocation())){
                    l.givePlayer(p, e.playerExitPosition, e.angle);//my guess, not good enough player location.
                    e.justUsed=true;
                }
            }
            game.setLocation(next);
            return l;
//            if(rebel){
//                return new Level("Resources/Dungeons/"+next.concat("2.png"), p, new ArrayList<Sprite>());
//            }else{
//                return new Level("Resources/Dungeons/"+next.concat(".png"), p, new ArrayList<Sprite>());
//            }
        } catch (Exception ex) {
            Logger.getLogger(Exit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return null;
        }
    }
}
