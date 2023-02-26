package byow.Core.FixedObject;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Fish extends FixedObject {
    private static final TETile TILETYPE = Tileset.FISH;

    /** Constructor */
    public Fish(TETile[][] tiles, Random random) {
        super(tiles, random);
        create(TILETYPE);
    }


    @Override
    public String toString() {
        return "Fish";
    }
}
