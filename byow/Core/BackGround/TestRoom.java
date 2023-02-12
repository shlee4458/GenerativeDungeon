package byow.Core.BackGround;

import byow.Core.Position;
import byow.TileEngine.TETile;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestRoom {

    @Test
    public void TestStore() {
        TETile[][] initialWorld = new TETile[80][50];
        Random random = new Random(1000);
        Room room = new Room(80, 80, initialWorld, 3, random);
        room.drawAll();
        HashMap<Position, Position> rc = room.roomsCreated;
        for (Map.Entry<Position, Position> entry : rc.entrySet()) {
            System.out.println(entry.getKey().getX());
            System.out.println(entry.getKey().getY());
            System.out.println(entry.getValue().getX());
            System.out.println(entry.getValue().getY());
        }
    }
}
