/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raycaster;

import Game.GameBase;
import Hardware_Accelerated.AccelGame;
import java.awt.Color;

/**
 *
 * @author pcowal15 and kyle sweeney
 */
public class Raycaster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        if(System.getProperty("os.name").contains("Win")){
//            System.setProperty("sun.java2d.opengl", "false");
//            System.setProperty("sun.java2d.d3d", "True");
//        }
        
        Main main = new Main();
        
        main.giveResolutionAndQuality(Integer.parseInt(args[0].substring(0,args[0].indexOf("x"))),
                Integer.parseInt(args[0].substring(args[0].indexOf("x")+1)), 
                args[1]);
        new AccelGame(main, "Finding Brunhilde").run();
    }
}
