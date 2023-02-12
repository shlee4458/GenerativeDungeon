package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;

public class Game {

    /* Declare instance variables */
    String loadPhrase;  // cache for loading
    int width;
    int height;
    boolean quit;
    TERenderer ter;
    Random random;
    TETile[][] initialWorld;
    TETile[][] worldGenerated = null;
    GenerateWorld gw;
    LoadScreenRenderer ls;


    /* Constructor */
    public Game(int width, int height, TERenderer ter, Random random) {
        this.width = width;
        this.height = height;
        this.ter = ter;
        this.random = random;
        this.quit = false;
        initialWorld = new TETile[width][height];

        /* Generate world */
        gw = new GenerateWorld(width, height, initialWorld, this.random);
        if (worldGenerated == null) {
            initialWorld = gw.generate();
            worldGenerated = TETile.copyOf(initialWorld); // cache the initial world created
        }

        /* Load Screen initialize */
        ls = new LoadScreenRenderer();
        ls.initialize(width, height);
    }

    /* starts game */
    public void startGame() {
        while (true) {
            quit = false;

            // Initialize the default screen size
            ter.initialize(width, height);

            // Render loading screen until receives input
            while (!StdDraw.hasNextKeyTyped()) {
                ls.renderFrame();
            }

            char gameOption = StdDraw.nextKeyTyped();
            char inputLower = Character.toLowerCase(gameOption);

            ter.initialize(width, height); // Resets the screen setting
            if (inputLower == 'n') { // Resets loadPhrase and world tile to the initial state
                loadPhrase = "";
                gw.newStart(TETile.copyOf(getWorldGenerated()));
            }
            gameScreen(loadPhrase);
        }
    }

    /* Play game screen */
    public void gameScreen(String s) {
        char input;
        if (s.length() > 0) {
            loadGame(s);
        }

        while (!quit) {
            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrame(initialWorld);
            }
            input = StdDraw.nextKeyTyped();
            input = Character.toLowerCase(input);
            loadPhrase += input; // Updates cache
            updateScreen(input);
        }
    }

    /* Update screen based on user input */
    public void updateScreen(char input) {
        if (input == 'w') {
            gw.update(0, 1);
        } else if (input == 's') {
            gw.update(0, -1);
        } else if (input == 'a') {
            gw.update(-1, 0);
        } else if (input == 'd') {
            gw.update(1, 0);
        } else if (input == 'q') {
            quit = true;
        }
    }

    /* Load game */
    public void loadGame(String s) {
        // Create an Arraylist of characters from the cached string
        ArrayList<Character> ch = new ArrayList<>();
        for (char c : s.toCharArray()) {
            ch.add(c);
        }

        // Delete q from the end of the list
        ch.remove(ch.size() - 1);
        ch.remove(ch.size() - 1);

        for (char c : ch) {
            updateScreen(c);
        }
    }

    public TETile[][] getWorldGenerated() {
        return worldGenerated;
    }

}
