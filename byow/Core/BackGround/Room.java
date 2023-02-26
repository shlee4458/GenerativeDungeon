package byow.Core.BackGround;

import byow.Core.Position;
import byow.TileEngine.*;

import java.util.*;

public class Room implements BackGround {

    /* Key Position: bottom left of the rectangle, or the start position
    * Value Position: top right of the rectangle, or the end position */
    HashMap<Position, Position> roomsCreated;
    int width;
    int height;
    Random random;
    TETile[][] tiles;
    int numberOfRooms;
    final private int MAXSIZE = 8; // maximum size of the room
    final private int DISTANCE = 4; // minimum distance between rooms

    /** Constructor */
    public Room(int width, int height, TETile[][] tiles, int numberOfRooms, Random random) {
        roomsCreated = new HashMap<>();
        this.width = width;
        this.height = height;
        this.random = random;
        this.tiles = tiles;
        this.numberOfRooms = numberOfRooms;
    }

    /** Draw Rooms by iterating draw method roomNumber of times */
    @Override
    public void drawAll() {
        for (int n = 0; n < this.numberOfRooms; n++) {
            drawSingle();
        }
    }

    /** DrawRoom with given start position and the end position */
    @Override
    public void draw(Position start, Position end) {
        for (int x = start.getX() - 1; x < end.getX() + 2; x++) {
            for (int y = start.getY() - 1; y < end.getY() + 2; y++) {
                if (x == start.getX() - 1 || x == end.getX() + 1 ||
                        y == start.getY() - 1 || y == end.getY() + 1) {
                    tiles[x][y] = Tileset.WALL; // Draw Wall
                } else {
                    tiles[x][y] = Tileset.FLOOR; // Draw Floor
                }
            }
        }
    }

    /** Draw a single room and add the created room to the roomsCreated hashTable object */
    public void drawSingle() {
        Position startPos = getRandomStart();
        Position endPos = getRandomEnd(startPos);
        draw(startPos, endPos);
        roomsCreated.put(startPos, endPos);
    }

    /** Check if the position of the starting position of the room is valid
     * @Param Position pos: Position to check the validity
     * @Return boolean: returns true if the given position is valid */
    public boolean isStartValid(Position pos) {
        for (Map.Entry<Position, Position> entry : roomsCreated.entrySet()) {
            Position start = entry.getKey();
            Position end = entry.getValue();
            if (isValidHelper(pos, start, end)) {
                return false;
            }
        }
        return true;
    }

    /** Check if the position of the end position of the room is valid
     * @Param Position startPos: */
    public boolean isEndValid(Position startPos, Position endPos) {
        for (int x = startPos.getX(); x < endPos.getX() + 1; x++) {
            for (int y = startPos.getY(); y < endPos.getY() + 1; y++) {
                Position tempPos = new Position(x, y);
                if (!isStartValid(tempPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns true, if the x and y coordinate of the newly generated room and the previously
     * generated room is larger than the constant variable DISTANCE, otherwise false. */
    public boolean isValidHelper(Position pos, Position start, Position end) {
        if (pos.getX() >= start.getX() - DISTANCE && pos.getX() <= end.getX() + DISTANCE &&
                pos.getY() >= start.getY() - DISTANCE && pos.getY() <= end.getY() + DISTANCE) {
            return true;
        }
        return false;
    }

    /** Get random start position by first initiating the Position object with x, y coordinate of 6, 6
     * and then iterating until the Position is a valid start. */
    public Position getRandomStart() {
        Position startPos = new Position(6,6); // first room is created at 6,6 coordinate
        while (!isStartValid(startPos)) {
            int startX = random.nextInt(3, width - 8);
            int startY = random.nextInt(3, height - 8);
            startPos.changePos(startX, startY);
        }
        return startPos;
    }

    /** Get random end position by first randomly getting room size within MAXSIZE given the start
     * Position and then iterating until the end Position is valid.
     * @Param Position startPos: bottom left x, y coordinate of the room
     * @Return Position: randomly-generated top right x, y coordinate of the room */
    public Position getRandomEnd(Position startPos) {
        int endX = random.nextInt(startPos.getX(), startPos.getX() + MAXSIZE);
        int endY = random.nextInt(startPos.getY(), startPos.getY() + MAXSIZE);
        Position endPos = new Position(endX, endY);
        while (!isEndValid(startPos, endPos)) {
            endX = random.nextInt(startPos.getX(), startPos.getX() + MAXSIZE);
            endY = random.nextInt(startPos.getY(), startPos.getY() + MAXSIZE);
            endPos.changePos(endX, endY);
        }
        return endPos;
    }

    /** Return an ArrayList of Positions that contains the middle position of each room
     * Middle Position is mean value of the start position and the end position of the room.
     * For example, middle position of the room with (5, 10) and (8, 16) is (6, 13). */
    public ArrayList<Position> getVertex() {
        ArrayList<Position> Vertex = new ArrayList<>();
        for (Map.Entry<Position, Position> entry : roomsCreated.entrySet()) {
            Vertex.add(Position.midPos(entry.getKey(), entry.getValue()));
        }
        return Vertex;
    }
}
