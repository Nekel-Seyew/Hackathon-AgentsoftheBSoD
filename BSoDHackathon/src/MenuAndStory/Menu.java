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

/**
 *
 * @author RomulusAaron
 */
public class Menu {
    Image2D cover;
    public boolean exitMenu;
    
    public Menu(){
        cover= new Image2D("Resources/Sprites/MenuCover.png");
        exitMenu=true;
        StoryJournal.makeStories();
    }
    
    public void Update(KeyBoard k){
        if(k.isKeyDown('p')){
            exitMenu=true;
        }
    }
    
    
    public void Draw(ImageCollection batch){
        batch.Draw(cover, new Vector2(400,300),  1000000000);
        batch.DrawString(new Vector2(200, 200), "P to Exit", Color.white, 1000000000, FontType.MONOSPACED, FontStyle.PLAIN, 12);
        batch.DrawString(new Vector2(200, 215), "J for Story Journal", Color.white, 1000000000, FontType.MONOSPACED, FontStyle.PLAIN, 12);
    }
    
    public void start(){
        exitMenu=false;
    }
}
