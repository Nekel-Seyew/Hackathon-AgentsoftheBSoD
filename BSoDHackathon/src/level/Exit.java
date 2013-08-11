/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import Advance.AMath;
import Utilities.Vector2;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Logger;
import objects.Player;
import objects.Sprite;
import raycaster.Main;

/**
 *
 * @author RomulusAaron
 */
public class Exit {
    
    
    String next;
    Vector2 pos;
    Color c;
    String now;
    Main game;
    
    public Exit(String n, Vector2 pos, Color c, String now){
        next= new String(n);
        this.pos=pos;
        this.c=c;
        this.now=new String(now);
    }
    
    public String nextPlace(){
        return next;
    }
    
    public void update(Player p, Main game){
        this.game=game;
        if(AMath.distance(pos, p.pos) < 100){
            game.giveNextLevel(next(p,game.isRebellionHappening));
            p.exitLevel(this);
        }
    }
    
    public Level next(Player p, boolean rebel){
        try {
            game.setLocation(next);
            if(rebel){
                return new Level("Resources/Dungeons/"+next.concat("2.png"), p, new ArrayList<Sprite>());
            }else{
                return new Level("Resources/Dungeons/"+next.concat(".png"), p, new ArrayList<Sprite>());
            }
        } catch (IOException ex) {
            Logger.getLogger(Exit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return null;
        }
    }
}
