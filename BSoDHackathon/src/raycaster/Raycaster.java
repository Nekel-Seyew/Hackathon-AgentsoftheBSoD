/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raycaster;

import Game.GameBase;
import Hardware_Accelerated.AccelGame;

/**
 *
 * @author pcowal15
 */
public class Raycaster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AccelGame(new Main(), "3D Maze Game").run();
    }
}
