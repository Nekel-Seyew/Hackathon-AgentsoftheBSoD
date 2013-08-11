/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuAndStory;

import Hardware_Accelerated.AccelGame;
import Utilities.FontStyle;
import Utilities.FontType;
import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.KeyBoard;
import Utilities.Vector2;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import objects.Player;

/**
 *
 * @author RomulusAaron
 */
public class Menu {
    Image2D cover;
    public boolean exitMenu;
    boolean journal;
    
    boolean start=true;
    
    ArrayList<StoryJournal> st;
    Iterator<StoryJournal> next;
    StoryJournal current;
    
    public Menu(){
        cover= new Image2D("Resources/Sprites/MenuCover.png");
        exitMenu=true;
        StoryJournal.makeStories();
        journal=false;
    }
    
    public void Update(KeyBoard k, Player p){
        if(start){
            AccelGame.gui.addKeyListener(new mover());
            AccelGame.frame.addKeyListener(new mover());
            start=false;
        }
        if(k.isKeyDown('p')){
            exitMenu=true;
            journal=false;
        }
        if(k.isKeyDown('j')){
            st=p.getAllEntries();
            next=st.iterator();
            journal=true;
            current=st.get(0);
        }
    }
    
      public void Draw(ImageCollection batch) {
          batch.Draw(cover, new Vector2(400, 300), 1000000000);
        if (journal) {
            for(int i=0; i<current.sayings.size(); i++){
                batch.DrawString(new Vector2(200, 200+(i*15)), current.sayings.get(i), Color.white, 1000000000, FontType.MONOSPACED, FontStyle.PLAIN, 12);
            }
        } else {
            batch.DrawString(new Vector2(200, 200), "P to Exit", Color.white, 1000000000, FontType.MONOSPACED, FontStyle.PLAIN, 12);
            batch.DrawString(new Vector2(200, 215), "J for Story Journal", Color.white, 1000000000, FontType.MONOSPACED, FontStyle.PLAIN, 12);
        }
    }
    
    public void start(){
        exitMenu=false;
    }
    
    public class mover implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar()=='n' && next.hasNext()){
                StoryJournal sj= next.next();
                if(sj.equals(current)){
                    current=next.next();
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        
    }
}
