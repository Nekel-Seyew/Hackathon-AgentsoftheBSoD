/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import java.awt.Color;
import java.util.ArrayList;
import java.util.ListIterator;
import level.Level;
import raycaster.Camera;

/**
 *
 * @author Sonar
 */
public class ParticleManager {
    ArrayList<Particle>particles;
    Color color;
    double gravity;
    double bounce;
    double resistance;
    boolean sticky;
    int life;
    public ParticleManager(Color Color, double Gravity, double Bounce, double Resistance,boolean Sticky, int Life){
        particles=new ArrayList<Particle>();
        color=Color;
        gravity=Gravity;
        bounce=Bounce;
        resistance=Resistance;
        sticky=Sticky;
        life=Life;
    }
    public void update(Level level){
        ListIterator l=particles.listIterator();
        while(l.hasNext()){
            Particle p=(Particle)l.next();
            p.update(level);
            if(p.life<0)l.remove();
        }
    }
    public void addExplosion(double x, double y, double z, double speed, int count){
        double theta,phi,xspeed,yspeed,zspeed;
        for(int i=0; i<count; i++){
            theta=Math.random()*2*Math.PI;
            phi=Math.random()*Math.PI;
            xspeed=speed*Math.cos(theta)*Math.sin(phi);
            yspeed=speed*Math.sin(theta)*Math.sin(phi);
            zspeed=speed*Math.cos(phi);
            addParticle(x,y,z,xspeed,yspeed,zspeed);
        }
        
    }
    public void addParticle(double x, double y, double z, double xspeed, double yspeed, double zspeed){
        particles.add(new Particle(color,x,y,z,xspeed,yspeed,zspeed,gravity,resistance,bounce,sticky,life));
    }
    public void draw(ImageCollection batch, Camera camera, Player player){
        ListIterator l=particles.listIterator();
        while(l.hasNext()){
            Particle p=(Particle)l.next();
            p.draw(batch, camera, player);
        }
    }
}
