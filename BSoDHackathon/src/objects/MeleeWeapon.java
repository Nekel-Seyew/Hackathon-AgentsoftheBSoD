/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Vector2;

/**
 *
 * @author Sonar
 */
public class MeleeWeapon {
    Image2D sprite;
    double state;
    boolean attacking;
    public MeleeWeapon(String path){
        sprite=new Image2D(path);
        state=0;
        attacking=false;
    }
    public void update(){
        if(attacking){
            state+=0.2;
            if(state>=1){
                attacking=false;
            }
        }
        else{
            state=Math.max(0,state-0.1);
        }
    }
    public void attack(){
        if(state==0&&!attacking){
            attacking=true;
        }
    }
    public void Draw(ImageCollection batch){
        Vector2 pos=new Vector2(640-state*400,480+state*50);
        if(attacking){
            pos=new Vector2(640-state*400,230+state*300);
        }
        batch.Draw(sprite, pos,(float)(-0.2-state),(float)3,(float)2.1,10000);
    }
}
