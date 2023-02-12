package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class LoadScreenRenderer {
    int width;
    int height;

    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;

        // Set canvas size and the x, y scale accordingly
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
    }

    public void renderFrame() {

        // Set background
        StdDraw.clear(new Color(0, 0, 0));

        // Title
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.text(width * 0.5, height * 0.68, "Generative Dungeon");

        // Options
        Font fontSmall = new Font("Monaco", Font.BOLD, 22);
        StdDraw.setFont(fontSmall);
        StdDraw.setPenColor(100,100,100);
        StdDraw.text(width * 0.5, height * 0.34, "New Game (N)");
        StdDraw.text(width * 0.5, height * 0.29, "Load Game (L)");
        StdDraw.text(width * 0.5, height * 0.24, "Quit Game (Q)");
        StdDraw.show();
    }
}
