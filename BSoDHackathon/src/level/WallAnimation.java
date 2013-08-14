/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import raycaster.Main;

/**
 *
 * @author kdsweenx
 */
public class WallAnimation {
    ArrayList<Image2D> sprite;
    int index;
    int framesPerSecond;
    int lastCycleNum;
    int count;
    
    public WallAnimation(String folder, int fps){
        sprite=new ArrayList<Image2D>();
        File f= new File(folder);
        for(int i=1; i<f.list().length; i++){
//            System.out.println(folder+"/"+i+".png");
            sprite.add(new Image2D(folder+"/"+i+".png"));
        }
//        for(String s: f.list()){
//            System.out.println(folder+s);
//            sprite.add(new Image2D(folder+"/"+s));
//        }
        framesPerSecond=fps;
        lastCycleNum=0;
        index=0;
        count=0;
    }
    
    public void Draw(ImageCollection batch, Vector2 pos, float scaleX, float scaleY, float angle, float percent, Color c, Rect drawnArea, int depth){
        int cyc=Main.inst.cycleNum;
        if(cyc > lastCycleNum || cyc == 0){
            count++;
            if(count >= 60/framesPerSecond){
                index++;
                index%=sprite.size();
            }
            lastCycleNum=cyc;
        }
        batch.Draw(sprite.get(index), pos, angle, c, percent, scaleX, scaleY, drawnArea, depth);
    }
    
    
}
