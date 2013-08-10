/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import java.awt.Color;
import level.Level;
import raycaster.Camera;

/**
 *
 * @author pcowal15
 */
public class Particle {
    double x;
    double y;
    double z;
    double xspeed;
    double yspeed;
    double zspeed;
    double gravity;
    double resistance;
    double bounce;
    boolean sticky;
    int life;
    Color color;
    public Particle(Color Color,double X, double Y, double Z,double xSpeed, double ySpeed, double zSpeed, double Gravity, double Resistance,double Bounce, boolean Sticky, int Life){
        x=X;
        y=Y;
        z=Z;
        xspeed=xSpeed;
        yspeed=ySpeed;
        zspeed=zSpeed;
        gravity=Gravity;
        resistance=Resistance;
        bounce=Bounce;
        sticky=Sticky;
        life=Life;
        color=Color;
    }
    public void update(Level level){
        dX(level);
        dY(level);
        dZ();
        life--;
    }
    public void dX(Level level){
        if (level.wall(x+xspeed, y)){
            if(sticky){
                bounce=0;
                gravity=0;
                //x=64*Math.floor((x+xspeed)/64);
                
                if(xspeed>0)x-=2;
                if(xspeed<0)x+=2;
                sticky=false;
            }
            x-=xspeed;
            xspeed*=-bounce;
            yspeed*=bounce;
            zspeed*=bounce;
        }
        x+=xspeed;
    }
    public void dY(Level level){
        if(level.wall(x, y+yspeed)){
            if(sticky){
                bounce=0;
                gravity=0;
                //y=64*Math.floor((y+yspeed)/64);
                if(yspeed>0)y-=2;
                if(yspeed<0)y+=2;
                sticky=false;
            }
            y-=yspeed;
            xspeed*=bounce;
            yspeed*=-bounce;
            zspeed*=bounce;
        }
        y+=yspeed;
    }
    public void dZ(){
        if(z<0||z>64){
            if(sticky){
                bounce=0;
                gravity=0;
                sticky=false;
            }
            z-=zspeed;
            xspeed*=bounce;
            yspeed*=bounce;
            zspeed*=-bounce;
        }
        z+=zspeed;
        zspeed+=gravity;
    }
    public void draw(ImageCollection batch, Camera camera, Player player){
        camera.drawParticle(batch,player,x,y,z,color);
    }
}
