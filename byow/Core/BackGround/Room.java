package byow.Core.BackGround;

import java.util.*;
import java.util.Random;

import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room implements BackGround {

    HashMap<Position, Position> roomsCreated; // First Position; Bottom left of the rectangle,
                                              // Second Position; Top Right of the rectangle
    int width;
    int height;
    Random random;
    TETile[][] tiles;
    int numberOfRooms;
    final private int MAXSIZE = 8;
    final private int DISTANCE = 4;

    /* Constructor */
    public Room(int width, int height, TETile[][] tiles, int numberOfRooms, Random random) {
        roomsCreated = new HashMap<>();
        this.width = width;
        this.height = height;
        this.random = random;
        this.tiles = tiles;
        this.numberOfRooms = numberOfRooms;
    }

    /* Draw Rooms by iterating draw method roomNumber of times */
    @Override
    public void drawAll() {
        for (int n = 0; n < this.numberOfRooms; n++) {
            drawSingle();
        }
    }

    /* DrawRoom with given start position and the end position */
    @Override
    public void draw(Position start, Position end) {
        /* Draw floor and wall */
        for (int x = start.getX() - 1; x < end.getX() + 2; x++) {
            for (int y = start.getY() - 1; y < end.getY() + 2; y++) {
                if (x == start.getX() - 1 || x == end.getX() + 1 ||
                        y == start.getY() - 1 || y == end.getY() + 1) {
                    tiles[x][y] = Tileset.WALL;
                } else {
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    /* Draw a single room and update roomsCreated hashTable */
    public void drawSingle() {
        Position startPos = getRandomStart();
        Position endPos = getRandomEnd(startPos);
        draw(startPos, endPos);
        roomsCreated.put(startPos, endPos);
    }

    /* Check if the position of the starting position of the room is valid */
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

    /* Check if the position of the end position of the room is valid */
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

    /* Returns false if pos is included in the square created by start and end pos */
    public boolean isValidHelper(Position pos, Position start, Position end) {
        if (pos.getX() >= start.getX() - DISTANCE && pos.getX() <= end.getX() + DISTANCE &&
                pos.getY() >= start.getY() - DISTANCE && pos.getY() <= end.getY() + DISTANCE) {
            return true;
        }
        return false;
    }

    /* Get random start position
    * Change position until the start position is valid
    * */
    public Position getRandomStart() {
        Position startPos = new Position(6,6);
        while (!isStartValid(startPos)) {
            int startX = random.nextInt(3, width - 8);
            int startY = random.nextInt(3, height - 8);
            startPos.changePos(startX, startY);
        }
        return startPos;
    }

    /* Get random end position */
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

    /* Return an ArrayList that contains the center position of each room */
    public ArrayList<Position> getVertex() {
        ArrayList<Position> Vertex = new ArrayList<>();
        for (Map.Entry<Position, Position> entry : roomsCreated.entrySet()) {
            Vertex.add(Position.midPos(entry.getKey(), entry.getValue()));
        }
        return Vertex;
    }
}
