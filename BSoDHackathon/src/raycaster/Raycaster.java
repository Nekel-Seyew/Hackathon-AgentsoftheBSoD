/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raycaster;

import Game.GameBase;

/**
 *
 * @author pcowal15
 */
public class Raycaster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","True");
        new GameBase(new ThreeDMazeGame(), "3D Maze Game").Run();
    }
}
