package byow.Core;
import byow.Core.BackGround.*;
import byow.Core.Graph.*;
import byow.Core.MovingObject.*;
import byow.TileEngine.*;

import java.util.*;

public class GenerateWorld {
    int width;
    int height;
    int floor;
    int numOfRooms;
    TETile[][] worldState;
    Random random;
    Room room;
    Hallway hallway;
    Staircase staircase;
    Avatar avatar;
    Ghost ghost;

    /* Constructor */
    public GenerateWorld(int width, int height, TETile[][] worldState, int floor, Random random) {
        this.width = width;
        this.height = height;
        this.worldState = worldState;
        this.random = random;
        this.floor = floor;
        this.numOfRooms = floor * 1 + 6; // number of rooms increase by 1 for every floor

        fillWithBlank(worldState); // fill with blank

        /* Instantiate room object */
        room = new Room(width, height, worldState, numOfRooms, random);
    }

    /* Fill with nothing tile */
    public void fillWithBlank(TETile[][] tiles) {
        for (int x  = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* Generate rooms to the worldState */
    public void generateRoom() {
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
        avatar = new Avatar(worldState, random);
    }

    /* Generate a Ghost */
    public void generateGhost() {
        ghost = new Ghost(worldState, random);
    }

    /* Update the current state of the world */
    public void update(int x, int y) {
        avatar.update(x, y);
        ghost.update(avatar.getXPos(), avatar.getYPos());
    }

    /* Generate World with rooms and corridors */
    public TETile[][] generate() {
        generateRoom();
        generateStaircase();
        generateHallway();
        generateAvatar();
        generateGhost();
        return worldState();
    }

    /* Changes worldState to given state when the gamer starts a new game */
    public void newStart(TETile[][] tiles) {
        this.worldState = tiles;
        avatar.newStart();
        ghost.newStart();
    }

    /* Check whether to move a floor up*/
    public boolean isFloorUp() {
        return Position.samePosition(avatar.getPosition(), staircase.getPosition());
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
