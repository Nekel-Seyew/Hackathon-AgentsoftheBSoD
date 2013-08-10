/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Logger;
import objects.Player;
import objects.Sprite;

/**
 *
 * @author RomulusAaron
 */
public class Exit {
    String next;
    
    public Exit(String n){
        next= new String(n);
    }
    
    public Level next(Player p){
        try {
            return new Level(next.concat(".png"), p, new ArrayList<Sprite>());
        } catch (IOException ex) {
            Logger.getLogger(Exit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return null;
        }
    }
}
