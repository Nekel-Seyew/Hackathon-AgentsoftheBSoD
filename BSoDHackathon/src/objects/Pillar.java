/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Vector2;

/**
 *
 * @author pcowal15
 */
public class Pillar extends Sprite{
    
    public Pillar(Vector2 pos){
        super("src/Resources/pillar.png",pos);
        health=-1;
        radius=5;
    }
    
}
