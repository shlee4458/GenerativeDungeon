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
    int floor;
    boolean quit;
    int numOfSteps;
    int fish;
    int hp;
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
        this.floor = 1;
        this.numOfSteps = 0;
        this.fish = 0;
        this.hp = 100;

        /* Load Screen initialize */
        ls = new LoadScreenRenderer();
        ls.initialize(width, height);
    }

    /* Generate World */
    public void generateWorld() {
        gw = new GenerateWorld(width, height, initialWorld, floor, fish, hp, this.random);
        initialWorld = gw.generate();
        worldGenerated = TETile.copyOf(initialWorld); // cache the initial world created
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

            char input = StdDraw.nextKeyTyped();
            char inputLower = Character.toLowerCase(input);

            ter.initialize(width, height); // Resets the screen setting
            if (inputLower == 'n') { // Resets loadPhrase and world tile to the initial state
                numOfSteps = 0;
                floor = 1;
                loadPhrase = "";
            }
            gw.newStart(TETile.copyOf(getWorldGenerated()));
            gameScreen(loadPhrase);
        }
    }

    /* Play game screen */
    public void gameScreen(String s) {
        char input;
        if (s.length() > 0) {
            loadGame(s);
        }

        // Moves according to the directional value user inputs;
        // Increases the total step taken for every input
        while (!quit) {
            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrame(initialWorld, floor, numOfSteps, this.hp, this.fish);
            }
            input = StdDraw.nextKeyTyped();
            input = Character.toLowerCase(input);

            // If input is q, do not cache the character to the load Phrase.
            if (input == 'q') {
                quit = true;
                break;
            }
            numOfSteps += 1;
            loadPhrase += input; // Updates cache
            updateScreen(input); // Updates the screen with given input
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
        }

        // check whether to move one floor up
        if (gw.isFloorUp()) {
            floor += 1;
            generateWorld(); // regenerate the world with a floor increased by one
        }

        // Checks whether avatar have obtained an item and calls related method;
        // 1) removes obtained item from the existing item on the screen
        // 2) add to the list of items that the avatar has acquired
        if (gw.obtain()) {
            fish += 1;
            System.out.println("obtained");
        }
    }

    /* Load game */
    public void loadGame(String s) {
        // Create an Arraylist of characters from the cached string
        ArrayList<Character> ch = new ArrayList<>();
        for (char c : s.toCharArray()) {
            ch.add(c);
        }

        // Iterate over the cached characters and move accordingly
        for (char c : ch) {
            updateScreen(c);
        }
    }

    // Get world generated
    public TETile[][] getWorldGenerated() {
        return worldGenerated;
    }
}
