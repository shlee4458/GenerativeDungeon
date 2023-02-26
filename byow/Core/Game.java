package byow.Core;

import byow.Core.ScreenRenderer.LoadScreenRenderer;
import byow.Core.ScreenRenderer.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;

public class Game {
    String loadPhrase;  // cache user input parsed when user requests to load the game
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
    TETile[][] worldGenerated; // caches the current state of world, used when loading the game
    GenerateWorld gw;
    LoadScreenRenderer ls;

    /** Constructor */
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

        /* Initialize Load Screen */
        ls = new LoadScreenRenderer();
        ls.initialize(width, height);
    }

    /** Initiates GenerateWorld Class and calls generate method in the class instance. */
    public void generateWorld() {
        gw = new GenerateWorld(width, height, initialWorld, floor, fish, hp, this.random);
        initialWorld = gw.generate();
        worldGenerated = TETile.copyOf(initialWorld); // cache the initial world created
    }

    /** Starts Game or New Game after quitting the game. This part of the code is only executed
    * when the game initially starts or when the user quits the game and starts the new game */
    public void startGame() {
        while (true) {
            quit = false; // resets quit to false when the game restarts

            /* Initialize the default screen size */
            ter.initialize(width, height);

            /* Render loading screen until receives input */
            while (!StdDraw.hasNextKeyTyped()) {
                ls.renderFrame();
            }
            ter.initialize(width, height); // initializes the game screen

            /* Takes user input and executes accordingly, If the user input is n,
            resets state variables and loadPhrase, and Generates a new dungeon. */
            char input = StdDraw.nextKeyTyped();
            char inputLower = Character.toLowerCase(input);
            if (inputLower == 'n') { // Resets state variables and loadPhrase
                floor = 1;
                numOfSteps = 0;
                fish = 0;
                loadPhrase = "";
                generateWorld(); // generates a new dungeon
            }
            gameScreen(loadPhrase);
        }
    }

    /** If the loadPhrase is not empty, calls the loadGame method. If the loadPhrase is an empty String --
     *  user starts the game for the first time or restarts the game after quitting -- waits for user input
     *  and updates the screen according to user input. For every user input, increases number of steps,
     *  and loadPhrase. */
    public void gameScreen(String s) {
        char input;

        if (s.length() > 0) {
            loadGame(s);
        }

        /* Moves according to the directional value user inputs;
         * Increases the total step taken for every input */
        while (!quit) {
            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrame(initialWorld, floor, numOfSteps, hp, fish);
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

    /** Takes a cached String as an input and parses the character to update the screen */
    public void loadGame(String s) {
        /* Create an Arraylist of characters from the cached string */
        ArrayList<Character> ch = new ArrayList<>();
        for (char c : s.toCharArray()) {
            ch.add(c);
        }

        /* Iterate over the cached characters and move accordingly */
        for (char c : ch) {
            updateScreen(c);
        }
    }

    /** Update screen based on user input.
     * 1) 'w': moves the avatar up by 1
     * 2) 's': moves the avatar down by 1
     * 3) 'a': moves the avatar right by 1
     * 4) 'd': moves the avatar left by 1 */
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

        /* Checks whether to move a floor up. If the user moves a floor up, increase
        * floor variable by one and call generateWorld method and resets the loadPhrase. */
        if (gw.isFloorUp()) {
            floor += 1;
            generateWorld();
            loadPhrase = "";
        }

        /* Checks whether avatar have obtained an item and calls related method;
        * 1) removes obtained item from the existing item on the screen
        * 2) add to the list of items that the avatar has acquired */
        if (gw.obtain()) {
            fish += 1;
            System.out.println("obtained");
        }
    }
}
