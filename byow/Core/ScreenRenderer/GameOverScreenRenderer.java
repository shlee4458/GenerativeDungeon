package byow.Core.ScreenRenderer;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class GameOverScreenRenderer extends LoadScreenRenderer {
    int floor;
    int turns;
    private static final int TILE_SIZE = 16;

    @Override
    public void initialize(int w, int h) {
        super.initialize(w * TILE_SIZE, h * TILE_SIZE);
    }

    public void initialize(int w, int h, int floor, int turns) {
        initialize(w, h);
        this.floor= floor;
        this.turns = turns;
    }

    @Override
    public void renderFrame() {

        // Set background
        StdDraw.clear(new Color(0, 0, 0));

        // Game Over
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.setPenColor(255, 0, 0);
        StdDraw.text(width * 0.5, height * 0.68, "Game Over!");

        // Score
        Font subFont = new Font("Monaco", Font.ITALIC, 20);
        StdDraw.setFont(subFont);
        StdDraw.setPenColor(255, 255, 0);
        StdDraw.text(width * 0.5, height * 0.54, "You played total " + turns + " turns.");
        StdDraw.text(width * 0.5, height * 0.50, "You reached the floor " + floor + ".");

        // Option
        Font fontSmall = new Font("Monaco", Font.BOLD, 22);
        StdDraw.setFont(fontSmall);
        StdDraw.setPenColor(100,100,100);
        StdDraw.text(width * 0.5, height * 0.34, "To return to the loading screen, press (L)");

        StdDraw.show();
    }
}
