package model;

import javax.swing.ImageIcon;

public class Brick {
    private int bricksXPos[] = { 50, 350, 450, 550, 50, 300, 350, 450, 550, 150, 150, 450, 550, 250, 50, 100, 150, 550, 250,
            350, 450, 550, 50, 250, 350, 550, 50, 150, 250, 300, 350, 550, 50, 150, 250, 350, 450, 550, 50, 250, 350,
            550 };

    private int bricksYPos[] = { 50, 50, 50, 50, 100, 100, 100, 100, 100, 150, 200, 200, 200, 250, 300, 300, 300, 300, 350, 350,
            350, 350, 400, 400, 400, 400, 450, 450, 450, 450, 450, 450, 500, 500, 500, 500, 500, 500, 550, 550, 550,
            550 };

    private int solidBricksXPos[] = { 150, 350, 150, 500, 450, 300, 600, 400, 350, 200, 0, 200, 500 };

    private int solidBricksYPos[] = { 0, 0, 50, 100, 150, 200, 200, 250, 300, 350, 400, 400, 450 };

    private int brickON[] = new int[42];
    private boolean indestructible[] = new boolean[42];

    public Brick() {
        for (int i = 0; i < brickON.length; i++) {
            brickON[i] = 1;
        }
        // Example: Make the first 5 bricks indestructible
        for (int i = 0; i < 5; i++) {
            indestructible[i] = true;
        }
    }

    public int[] getBricksXPos() {
        return bricksXPos;
    }

    public int[] getBricksYPos() {
        return bricksYPos;
    }

    public int[] getSolidBricksXPos() {
        return solidBricksXPos;
    }

    public int[] getSolidBricksYPos() {
        return solidBricksYPos;
    }

    public int[] getBrickON() {
        return brickON;
    }

    public boolean[] getIndestructible() {
        return indestructible;
    }
}
