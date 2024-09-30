package org.game;

import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesweeperGame {
    private static final Logger LOGGER = Logger.getLogger(MinesweeperGame.class.getName());
    private int gridSize;
    private char[][] grid;
    private boolean[][] mines;
    private int totalMines;

    public MinesweeperGame(int gridSize, int totaMines) {
        this.gridSize = gridSize;
        this.totalMines = totaMines;
        this.grid = new char[gridSize][gridSize];
        this.mines = new boolean[gridSize][gridSize];
        initializeGrid();
    }

    public int getGridSize() {
        return gridSize;
    }

    public char getGridCell(int row, int col) {
        return grid[row][col];
    }

    public void initializeGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '-'; // hidden cells
                mines[i][j] = false;
            }
        }
    }

    public void placeMines() {
        Random random = new Random();
        int placedMines = 0;
        while (placedMines < totalMines) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            if (!mines[row][col]) {
                mines[row][col] = true;
                placedMines++;
            }
        }
    }

    public int getMineCount() {
        int count = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void forceMinePlacement(int row, int col) {
        mines[row][col] = true;
    }

    public int revealCell(int row, int col) {
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) {
            throw new ArrayIndexOutOfBoundsException("Cell out of Bounds!");
        }
        if (mines[row][col]) {
            LOGGER.log(Level.SEVERE, "Mine hit at ({0}, {1})!", new Object[]{
                    row, col});
            throw new RuntimeException("Boom you hit a mine!");
        } else {
            int adjacentMines = countAdjacentMines(row, col);
            grid[row][col] = (char) (adjacentMines + '0'); // Mark the cell with adjacent mine count
            return adjacentMines;
        }

    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < gridSize && j >= 0 && j < gridSize && mines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void displayGrid() {
        System.out.println(" ");
        System.out.print(" ");
        for (int i = 0; i < gridSize; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        char rowName = 'A';
        for (int i = 0; i < gridSize; i++) {
            System.out.print(rowName + " ");
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + " ");
            }
            rowName++;
            System.out.println();
        }
    }


    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Starting Minesweeper Game...");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the grid (eg. 4 x 4 grid)");
            int gridSize = scanner.nextInt();
            System.out.println("Enter the number of mines");
            int totalMines = scanner.nextInt();
            MinesweeperGame game = new MinesweeperGame(gridSize, totalMines);
            game.placeMines();
            boolean gameOver = false;
            while(!gameOver) {
                game.displayGrid();
                System.out.println("Select a square to reveal (eg, A1):");
                String move = scanner.next();
                char rowChar = move.charAt(0);
                int row = rowChar - 'A';
                int col = Character.getNumericValue(move.charAt(1)) - 1;
                try {
                    game.revealCell(row, col);
                    if(game.getGridCell(row, col)=='B') {
                        gameOver = true;
                        System.out.println("Game Over! you hit a mine!");
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    gameOver = true;
                }
            }

        }
    }
}