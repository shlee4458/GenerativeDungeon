package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    private static Random random;

    /* Constructor */
    public Engine(int seed) {
        this.random =  new Random(seed);
    }

    /* Initiate game class and starts the game */
    public void interactWithKeyboard() {
        Game g = new Game(WIDTH, HEIGHT, ter, random);
        g.generateWorld();
        g.startGame();
    }
}
