package com.hassir.gameoflifegui;

import com.hassir.gameoflifegui.gameoflife.GameOfLife;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;

public class GameOfLifeGUI extends Application {
    private static final int CELL_SIZE = 10; // Size of each cell (pixels)
    private static final int ROWS = 50;
    private static final int COLS = 50;
    /**
     * <b>THREAD_NUMBER</b> used to set the number of threads that will be used to compute the next generation in parallel
     * <p><b>default value</b>: 4
     * </p>
     * <p><b>NOTE</b>: the number of chunks will be decided splicing number of rows and the number os threads
     * </p>
     */
    private static final int THREAD_NUMBER = 4;
    /**
     * <b>ALIVE_PROBABILITY</b> used to set in the first generation if a cell is alive or not
     * <p><b>0.5f means that 50% chance</b> of a cell of being alive or not in first generation
     * </p>
     * <p><b>default value: 0.8f means 20% chance</b> of a cell of being alive or not in first generation
     * </p>
     * **/
    private static final float ALIVE_PROBABILITY = 0.5f;
    /**
     * <b>SET_STABLE_AFTER</b> used to set how many generations should the program wait until the game
     * is setted as stable
     * <p><b>default value</b>: 150 generations
     * </p>
     * **/
    private static final int SET_STABLE_AFTER = 150;
    /**
     * <b>TIME_BETWEEN_GRID_UPDATES</b> used to establish the time (in milliseconds) between each update of the grid and compute
     * the next generation
     * <p><b>default value</b>: 25 (in milliseconds)
     * </p>
     * **/
    private static final int TIME_BETWEEN_GRID_UPDATES = 25;

    private GameOfLife game;
    private Rectangle[][] rectangles;
    private Label aliveLabel;
    private Label generationLabel;
    private Label generationTimeLabel;


    @Override
    public void start(Stage primaryStage) {
        Timeline timeline;
        game = new GameOfLife(ROWS, COLS, THREAD_NUMBER);
        game.initializeGrid(ALIVE_PROBABILITY);

        // Create the main layout
        BorderPane mainPane = new BorderPane();

        // Create the grid
        GridPane gridPane = createGrid();

        // Create and style the label
        aliveLabel = new Label("Alive Cells: 0");
        generationLabel = new Label("Generation: 0");
        generationTimeLabel = new Label("Generation Time: 0ms");
        //create a box to stick together the labels
        VBox labelBox = new VBox(10); // 10px spacing between labels
        labelBox.getChildren().addAll(generationLabel, aliveLabel, generationTimeLabel);
        labelBox.setAlignment(Pos.TOP_CENTER);

        aliveLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px");
        generationLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px");
        generationTimeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: green");

        // Add the grid and label to the layout
        mainPane.setRight(labelBox); // Place the label at the top
        BorderPane.setAlignment(labelBox, Pos.TOP_CENTER); //palce the labelbox in the top center
        mainPane.setCenter(gridPane);    // Place the grid in the center

        // Create the scene and set it to the stage
        //50 and 90 used to fix the desplacament of the labels respect the grid in the window
        Scene scene = new Scene(mainPane, COLS * CELL_SIZE + 345, ROWS * CELL_SIZE + 50);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.show();

        // Animation loop
        timeline = new Timeline(new KeyFrame(Duration.millis(TIME_BETWEEN_GRID_UPDATES), e -> updateGrid()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play(); // Start animation
    }

    @lombok.SneakyThrows // To catch InterruptedException in the updateGrid method
    private void updateGrid() {
        if(game.checkForStablePattern(SET_STABLE_AFTER)) {
            generationLabel.setText("Stable generation on " + (game.getCurrentGeneration()-SET_STABLE_AFTER)+" generations");
            generationLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: red");
            return; // Exit the method if the pattern is stable for 150 generations
        }

        game.computeNextGeneration();

        int[][] grid = game.getGrid();
        aliveLabel.setText("Alive Cells: " + game.getAliveCells());
        generationLabel.setText("Generation: " + game.getCurrentGeneration());
        DecimalFormat ds = new DecimalFormat("#0.00");
        if(game.getGenerationTime() <= 0.30){
            generationTimeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: rgba(0,128,0,0.2)");
        }else if (game.getGenerationTime() >=0.31 && game.getGenerationTime() <= 0.60){
            generationTimeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: rgba(255,255,0,0.2)");
        }else {
            generationTimeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: rgba(255,0,0,0.2)");
        }
        generationTimeLabel.setText("Generation Time: " + ds.format(game.getGenerationTime()) + "ms");

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                rectangles[i][j].setFill(grid[i][j] == 1 ? Color.BLACK : Color.WHITE);
            }
        }
    }

    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        rectangles = new Rectangle[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.LIGHTGRAY);
                gridPane.add(rect, j, i);
                rectangles[i][j] = rect;
            }
        }

        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}