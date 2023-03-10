package byow.Core;

import byow.Core.ScreenRenderer.GameOverScreenRenderer;
import byow.Core.ScreenRenderer.LoadScreenRenderer;
import byow.Core.ScreenRenderer.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;

/** Game Class takes user Input and updates the screen by communicating with GenerateWorld Class.
 * In the Game Class, renderers are initiates and renderFrame is called according to user's input.
 * Renderers include;
 * 1) LoadRender - loading screen that is displayed until user input is received
 * 2) TERenderer - game play screen that includes tiles, ghosts, avatars and fish
 * 3) GameOverScreenRenderer - game over screen that is displayed when the avatar dies */
public class Game {
    int width;
    int height;
    int floor;
    boolean quit;
    int turns;
    int fish;
    int hp;
    TERenderer ter;
    Random random;
    TETile[][] initialWorld;
    GenerateWorld gw;
    LoadScreenRenderer ls;
    GameOverScreenRenderer go;

    /** Constructor */
    public Game(int width, int height, TERenderer ter, Random random) {
        this.width = width;
        this.height = height;
        this.ter = ter;
        this.random = random;
        this.quit = false;
        initialWorld = new TETile[width][height];
        this.floor = 1;
        this.turns = 0;
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
                setToDefault();
                generateWorld(); // generates a new dungeon
            }
            gameScreen();
        }
    }

    /** If the loadPhrase is not empty, calls the loadGame method. If the loadPhrase is an empty String --
     *  user starts the game for the first time or restarts the game after quitting -- waits for user input
     *  and updates the screen according to user input. For every user input, increases number of turn,
     *  and loadPhrase. */
    public void gameScreen() {
        char input;

        /* Moves according to the directional value user inputs;
         * Increases the total turns taken for every input */
        while (!quit) {
            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrame(initialWorld, floor, turns, gw.getAvatar().getHP(), fish);
            }
            input = StdDraw.nextKeyTyped();
            input = Character.toLowerCase(input);

            // If input is q, do not cache the character to the load Phrase.
            if (input == 'q') {
                quit = true;
                break;
            }
            turns += 1;
            updateScreen(input); // Updates the screen with given input
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
        } else if (input == 'e') {
            if (fish >= 1) {
                gw.eatFish();
                fish -= 1;
            } else {
                System.out.println("You don`t have any fish!");
            }
        }

        /* Checks whether to move a floor up. If the user moves a floor up, increase
        * floor variable by one and call generateWorld method and resets the loadPhrase. */
        if (gw.isFloorUp()) {
            floor += 1;
            this.hp = gw.getAvatar().getHP();
            generateWorld();
        }

        /* Checks whether avatar have obtained an item and calls related method;
        * 1) removes obtained item from the existing item on the screen
        * 2) add to the list of items that the avatar has acquired */
        if (gw.obtain()) {
            fish += 1;
            System.out.println("Fish Obtained!");
        }

        /* If the player`s HP turns 0, renders game over screen */
        if (gw.getAvatar().getHP() <= 0) {
            /* Initialize Game Over Screen */
            go = new GameOverScreenRenderer();
            go.initialize(this.width, this.height , floor, turns);

            char c = '0';
            while (c != 'l') {
                while (!StdDraw.hasNextKeyTyped()) {
                    go.renderFrame();
                }
                c = StdDraw.nextKeyTyped();
            }
            System.out.println("hi");
            setToDefault();
            startGame();
        }
    }
    public void setToDefault() {
        hp = 100;
        turns = 0;
        fish = 0;
    }
}
