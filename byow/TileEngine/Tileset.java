package byow.TileEngine;

import java.awt.Color;
import java.util.*;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 */

public class Tileset {
    public static final TETile AVATAR = new TETile(',', Color.white, Color.black, "you",
            "/img/cat.jpg");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "Wall","/img/tile/wall.jpg");
    public static final TETile FLOOR = new TETile(' ', new Color(128, 192, 128), Color.black ,
            "floor", "/img/tile/floornew.jpg");
    public static final TETile NOTHING = new TETile(' ', new Color(255,255,255), new Color(0,0,0), "nothing");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");

    public static final TETile GHOST = new TETile(',', Color.green, Color.black, "ghost",
            "/img/ghost.jpg");
    public static final TETile FISH = new TETile(',', Color.green, Color.black, "fish",
            "/img/fish.png");
    public static final TETile STAIRS = new TETile(',', Color.green, Color.black, "stairs",
            "/img/tile/stairsnew.jpg");
    public static ArrayList<TETile> fixedObject = new ArrayList<>(List.of(STAIRS));
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


