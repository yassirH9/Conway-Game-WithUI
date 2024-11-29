package com.hassir.gameoflifegui.gameoflife;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameOfLife {
    private final int rows;
    private final int cols;
    @Getter //to avoid the getter and setter in grid
    private int[][] grid;
    private int[][] nextGrid;
    private int generationCount = 0;
    private final int numThreads;
    @Getter
    private final ExecutorService executorService;
    @Getter
    private double generationTime = 0.00;//used to keep track of the generation time

    private int stableGenerationCounter = 0;
    private int lastAliveCells = 0;

    public GameOfLife(int rows, int cols, int nThreads) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
        this.nextGrid = new int[rows][cols];
        this.numThreads = nThreads;
        //using a thread pool to parallelize the computation of the next generation by chunks
        this.executorService = Executors.newFixedThreadPool(nThreads);
    }

    // Initialize the grid with random values
    public void initializeGrid(float chance) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Math.random() > chance ? 1 : 0; // 20% chance of being alive
            }
        }
    }

    // Compute the next generation
    public void computeNextGeneration() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(numThreads);

        int chunkSize = rows / numThreads;
        //start timer of execution
        long startTime = System.nanoTime();

        for (int t = 0; t < numThreads; t++) {
            int startRow = t * chunkSize;
            int endRow = (t == numThreads - 1) ? rows : startRow + chunkSize;

            executorService.submit(() -> {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < cols; j++) {
                        nextGrid[i][j] = computeNextState(i, j);
                    }
                }
                latch.countDown();
            });
        }

        latch.await();

        // Swap the grids
        int[][] temp = grid;
        grid = nextGrid;
        nextGrid = temp;

        generationTime = (System.nanoTime() - startTime) / 1_000_000.00;

        //if the generation inference but the number of alive cells is the same, the game is stable
        if (generationCount > 0 && getAliveCells() == lastAliveCells) {
            stableGenerationCounter++;
        }

        lastAliveCells = getAliveCells();
        generationCount++;

    }

    private int computeNextState(int x, int y) {
        int liveNeighbors = countLiveNeighbors(x, y);
        if (grid[x][y] == 1) {
            return (liveNeighbors == 2 || liveNeighbors == 3) ? 1 : 0;
        } else {
            return (liveNeighbors == 3) ? 1 : 0;
        }
    }

    private int countLiveNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                //continue skip the current iteration of the loop
                //in this case is used to avoid counting the cell itself
                if (i == 0 && j == 0) continue;
                int nx = (x + i + rows) % rows; // Wrap around edges
                int ny = (y + j + cols) % cols;
                count += grid[nx][ny];
            }
        }
        return count;
    }

    public int getAliveCells() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getCurrentGeneration() {
        return generationCount;
    }

    public boolean checkForStablePattern(int generation) {
        //if after 150 generations the number of cells doesn't change the simulation aspect a stable pattern
        return stableGenerationCounter > generation;
    }
}
