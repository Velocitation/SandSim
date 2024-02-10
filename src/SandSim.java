import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SandSim extends JPanel implements ActionListener {
    int[][] grid;
    float[][] velocityGrid;
    int cols, rows;
    final int w = 5; // Size of each square
    float hueValue = 200;
    final float gravity = 0.1F;
    final Timer timer;

    public SandSim(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        cols = width / w;
        rows = height / w;
        grid = new int[cols][rows];
        velocityGrid = new float[cols][rows];

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addSand(e.getX(), e.getY());
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                addSand(e.getX(), e.getY());
            }
        });

        timer = new Timer(50, this);
    }

    public void startSimulation() {
        timer.start();
    }

    private void addSand(int mouseX, int mouseY) {
        int mouseCol = mouseX / w;
        int mouseRow = mouseY / w;
        int matrix = 5;
        int extent = matrix / 2;
        for (int i = -extent; i <= extent; i++) {
            for (int j = -extent; j <= extent; j++) {
                if (Math.random() < 0.75) {
                    int col = mouseCol + i;
                    int row = mouseRow + j;
                    if (withinCols(col) && withinRows(row)) {
                        grid[col][row] = (int)hueValue;
                        velocityGrid[col][row] = 1;
                    }
                }
            }
        }
    }

    private boolean withinCols(int i) {
        return i >= 0 && i < cols;
    }

    private boolean withinRows(int j) {
        return j >= 0 && j < rows;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (grid[i][j] > 0) {
                    g.setColor(Color.getHSBColor(grid[i][j] / 360F, 1F, 1F));
                    int x = i * w;
                    int y = j * w;
                    g.fillRect(x, y, w, w);
                }
            }
        }
    }

    private void update() {
        int[][] nextGrid = new int[cols][rows];
        float[][] nextVelocityGrid = new float[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                int state = grid[i][j];
                if (state > 0) {
                    float velocity = velocityGrid[i][j];
                    boolean moved = false;

                    if (j < rows - 1 && grid[i][j + 1] == 0) { // Check directly below
                        nextGrid[i][j + 1] = state;
                        nextVelocityGrid[i][j + 1] = velocity + gravity;
                        moved = true;
                    } else { // Check diagonally down-left and down-right
                        int[] directions = {1, -1};
                        for (int dir : directions) {
                            int nextCol = i + dir;
                            if (withinCols(nextCol) && j < rows - 1 && grid[nextCol][j + 1] == 0) {
                                nextGrid[nextCol][j + 1] = state;
                                nextVelocityGrid[nextCol][j + 1] = velocity + gravity;
                                moved = true;
                                break;
                            }
                        }
                    }

                    if (!moved) { // If sand can't move, it stays in the current position
                        nextGrid[i][j] = state;
                        nextVelocityGrid[i][j] = 0; // Reset velocity as it's now static
                    }
                }
            }
        }

        grid = nextGrid;
        velocityGrid = nextVelocityGrid;

        hueValue += 0.5F;
        if (hueValue > 360) hueValue = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
}