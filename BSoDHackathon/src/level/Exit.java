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
    
    public Exit(String n, Vector2 pos, Color c){
        next= new String(n);
        this.pos=pos;
        this.c=c;
    }
    
    public void update(Player p, Main game){
        if(AMath.distance(pos, p.pos) < 100){
            game.giveNextLevel(next(p,game.isRebellionHappening));
        }
    }
    
    public Level next(Player p, boolean rebel){
        try {
            if(rebel){
                return null; //TODO: custom code for each post-rebellion levels.
            }else{
                return new Level(next.concat(".png"), p, new ArrayList<Sprite>());
            }
        } catch (IOException ex) {
            Logger.getLogger(Exit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return null;
        }
    }
}
