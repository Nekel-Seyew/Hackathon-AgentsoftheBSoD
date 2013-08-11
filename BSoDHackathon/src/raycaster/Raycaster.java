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
 * @author pcowal15
 */
public class Raycaster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AccelGame(new Main(), "Finding Brunhilde").run();
    }
}
