package byow.Core;

import byow.Core.ScreenRenderer.TERenderer;

import java.util.Random;

/** Engine class takes an integer command line argument as an input from the user,
 *  and initiates the random class from java.util module. Constant variable WIDTH,
 *  and HEIGHT is declared in this class, which will be passed into game class that
 *  acts as a controller for a whole game program.
 * */


public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    private static Random random;

    /** Constructor */
    public Engine(int seed) {
        this.random =  new Random(seed);
    }

    /** Initiate game class, generates the dungeon, and starts the game */
    public void interactWithKeyboard() {
        Game g = new Game(WIDTH, HEIGHT, ter, random);
        g.generateWorld();
        g.startGame();
    }
}
