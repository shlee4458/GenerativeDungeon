package byow.Core;
import byow.Core.BackGround.*;
import byow.Core.FixedObject.Fish;
import byow.Core.FixedObject.*;
import byow.Core.Graph.*;
import byow.Core.MovingObject.Avatar;
import byow.Core.MovingObject.Ghost;
import byow.Core.BackGround.Staircase;
import byow.TileEngine.*;

import java.util.*;

public class GenerateWorld {
    int width;
    int height;
    int floor;
    int numOfRooms;
    int numOfGhosts;
    int numOfFish;
    int fishObtained;
    int hp;
    TETile[][] worldState;
    Random random;
    Room room;
    Hallway hallway;
    Staircase staircase;
    Avatar avatar;
    ArrayList<Ghost> ghosts;
    ArrayList<FixedObject> fixedObjects; // existing fixedObjects on the map
    ArrayList<FixedObject> fixedObjectsCache; // cache the fixed objects


    /* Constructor */
    public GenerateWorld(int width, int height, TETile[][] worldState, int floor, int fishObtained, int hp, Random random) {
        this.width = width;
        this.height = height;
        this.worldState = worldState;
        this.random = random;
        this.floor = floor;
        this.ghosts = new ArrayList<>();
        this.fixedObjects = new ArrayList<>();
        this.numOfRooms = 8 + floor * 1; // number of rooms increase by 2 for every floor
        this.numOfGhosts = Math.min(floor / 2 + 1, 4); // number of ghosts increases in every 2 floors
        this.numOfFish = Math.min(numOfRooms / 5, 4); // one fish in every 6 rooms
        this.fishObtained = fishObtained;
        this.hp = hp;

        fillWithBlank(worldState); // fill with blank
    }

    /* Fill with Nothing Tile */
    public void fillWithBlank(TETile[][] tiles) {
        for (int x  = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* Generate World with rooms and corridors */
    public TETile[][] generate() {
        generateRoom();
        generateStaircase();
        generateHallway();
        generateAvatar();
        generateGhost();
        generateFixedObjects();
        return worldState();
    }

    /* Generate rooms to the worldState */
    public void generateRoom() {
        /* Instantiate room object */
        room = new Room(width, height, worldState, numOfRooms, random);
        room.drawAll();
    }


    /* Generate a single staircase that leads to the next floor */
    public void generateStaircase() {
        staircase = new Staircase(width, height, worldState, random);
        staircase.draw();
    }

    /* Generate hallways between rooms */
    public void generateHallway() {

        /* Instantiate hallway object */
        ArrayList<Position> Vertex = room.getVertex();
        Graph g = new Graph(Vertex);
        KruskalMST kruskal = new KruskalMST(g);
        hallway = new Hallway(worldState, room, kruskal);

        hallway.drawAll();
    }

    /* Generate an Avatar */
    public void generateAvatar() {
        avatar = new Avatar(worldState, fishObtained, hp,random);
    }

    /* Generate a Ghost */
    public void generateGhost() {
        for (int num = 0; num < numOfGhosts; num++) {
            ghosts.add(new Ghost(worldState, random));
        }
    }

    /* Generate fish on a random floor tile */
    public void generateFixedObjects() {
        for (int num = 0; num < numOfFish; num++) {
            fixedObjects.add(new Fish(worldState, random)); // generate Fish
        }
        fixedObjectsCache = new ArrayList<>();
        fixedObjectsCache = (ArrayList<FixedObject>) fixedObjects.clone();
    }

    /* Update the current state of the world */
    public void update(int x, int y) {
        avatar.update(x, y);
        for (Ghost g : ghosts) {
            g.update(avatar.getXPos(), avatar.getYPos());
        }
    }

    /* Changes worldState to given state when the gamer starts a new game */
    public void newStart(TETile[][] tiles) {
        this.worldState = tiles;
        avatar.newStart();
        for (Ghost g : ghosts) {
            g.newStart();
        }
        for (FixedObject obj : fixedObjectsCache) {
            obj.newStart();
        }
    }

    /* Check if the avatar has obtained an item
    * If the avatar has obtained the item, remove the item from the ArrayList of existing item
    * and add it to the list of obtained items for the avatar */
    public boolean obtain() {

        // Use clone; fixedObjectsCache to iterate to prevent concurrent modification error
        for (FixedObject obj : fixedObjectsCache) {
            if (isObtain(avatar, obj)) {
                avatar.obtain(obj); // avatar obtains the item
                fixedObjects.remove(obj);
                return true;
            }
        }
        return false;
    }

    /* Check whether to move a floor up*/
    public boolean isFloorUp() {
        return Position.samePosition(avatar.getPosition(), staircase.getPosition());
    }

    /* Check if the item was obtained by the avatar */
    public boolean isObtain(Avatar avatar, FixedObject obj) {
        return Position.samePosition(avatar.getPosition(), obj.getPosition());
    }

    /* Get Random */
    public Random getRandom() {
        return random;
    }

    /* Returns World State of TETile */
    public TETile[][] worldState() {
        return worldState;
    }
}
