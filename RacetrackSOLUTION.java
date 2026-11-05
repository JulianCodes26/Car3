import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RacetrackSOLUTION {

    /** Racetrack from text file **/
    private char[][] track;
    /** Weights of the track position **/
    private int[][] weights;
    /** Constant Value for finish line weight **/
    final private int FINISH_WEIGHT = 0;
    /** Constant Value for wall weight **/
    final private int WALL_WEIGHT = 9999;
    /** Constant Value for pre initialized track weight **/
    final private int INIT_TRACK_WEIGHT = -1;

    /** Constructor for loading desired track layout from a txt file and positions weights (txt.1,2,3 **/
    public RacetrackSOLUTION(String filename) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(filename);
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            lines.add(fileScanner.nextLine());
        }
        fileScanner.close();

        int rows = lines.size();
        int cols = lines.get(0).length();

        track = new char[rows][cols];
        weights = new int[rows][cols];

        /** Initialize current weights for program **/
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                track[i][j] = line.charAt(j);
                if (track[i][j] == 'X') {
                    weights[i][j] = WALL_WEIGHT;
                } else if (track[i][j] == 'F') {
                    weights[i][j] = FINISH_WEIGHT;
                } else {
                    weights[i][j] = INIT_TRACK_WEIGHT;
                }
            }
        }

        /** Calculates the distance of the field weight **/
        calculateWeights();
    }

    /** Calculates the distance of the weights from the finish (f) **/
    private void calculateWeights() {
        Queue<int[]> queue = new LinkedList<>();

        /** Find all finish lines to use as BFS starting points **/
        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                if (track[r][c] == 'F') {
                    queue.add(new int[]{r, c});
                }
            }
        }

        /** 8-way directional vectors (including diagonals) **/
        int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int currentWeight = weights[r][c];

            for (int i = 0; i < 8; i++) {
                int newRow = r + dRow[i];
                int newCol = c + dCol[i];

                /** Check bounds **/
                if (newRow >= 0 && newRow < height() && newCol >= 0 && newCol < width()) {
                    // Only update valid open track spaces that haven't been visited yet
                    if (track[newRow][newCol] != 'X' && weights[newRow][newCol] == INIT_TRACK_WEIGHT) {
                        weights[newRow][newCol] = currentWeight + 1;
                        queue.add(new int[]{newRow, newCol});
                    }
                }
            }
        }
    }

    /** provide the weights grids **/
    public void displayWeights() {
        System.out.println("====================TRACK WEIGHTS====================");
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (weights[i][j] == WALL_WEIGHT) {
                    System.out.print(" X ");
                } else {
                    System.out.printf("%2d ", weights[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("=====================================================");
    }

    /** Show current racetrack format of layout **/
    public void displayTrack() {
        System.out.println("====================RACETRACK LAYOUT=================");
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                System.out.print(track[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("=====================================================");
    }


    /** @return - track position **/
    public char getTrack(int row, int col){
        return track[row][col];
    }
    /** Update position on track **/
    public void setTrack(int row, int col, char value){
        track[row][col] = value;
    }
    /** @return - weight value **/
    public int getWeight(int row, int col){
        return weights[row][col];
    }
    /** Update position on weight track **/
    public void setWeight(int row, int col, int value){
        weights[row][col] = value;
    }
    /** @return - number of rows **/
    public int height() {
        return track.length;
    }
    /** @return - number of columns **/
    public int width() { return track[0].length; }

    /** Display game banner (Optional) **/
    public void displayBanner(){
        String art = 	"   ______     _     __   ____                           \n" +
                "  / ____/____(_)___/ /  / __ \\____ _________  __________\n" +
                " / / __/ ___/ / __  /  / /_/ / __ `/ ___/ _ \\/ ___/ ___/\n" +
                "/ /_/ / /  / / /_/ /  / _, _/ /_/ / /__/  __/ /  (__  ) \n" +
                "\\____/_/  /_/\\__,_/  /_/ |_|\\__,_/\\___/\\___/_/  /____/  \n" +
                "                                                        ";

        System.out.println(art + "\n");
    }



    /**
     * This method creates a track and weights using static values (For Programs 2 and 3 only)
     */
    public void useDefaultTrack(){
        weights = new int[][]{
                {WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT},
                {WALL_WEIGHT, 11, 11, 11, 11, 11, 11, WALL_WEIGHT, FINISH_WEIGHT, FINISH_WEIGHT, WALL_WEIGHT},
                {WALL_WEIGHT, 10, 10, 10, 10, 10, 11, WALL_WEIGHT, 1, 1, WALL_WEIGHT},
                {WALL_WEIGHT, 10, 9, 9, 9, 10, WALL_WEIGHT, WALL_WEIGHT, 2, 2, WALL_WEIGHT},
                {WALL_WEIGHT, 10, 9, 8, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, 3, 3, WALL_WEIGHT},
                {WALL_WEIGHT, 10, 9, 8, 7, 6, 5, 4, 4, 4, WALL_WEIGHT},
                {WALL_WEIGHT, 10, 9, 8, 7, 6, 5, 5, 5, 5, WALL_WEIGHT},
                {WALL_WEIGHT, 10, 9, 8, 7, 6, 6, 6, 6, 6, WALL_WEIGHT},
                {WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT, WALL_WEIGHT}
        };
        track = new char[][]{
                {'X','X','X','X','X','X','X','X','X','X','X'},
                {'X','T','T','T','T','T','T','X','F','F','X'},
                {'X','T','T','T','T','T','T','X','T','T','X'},
                {'X','T','T','T','T','T','X','X','T','T','X'},
                {'X','T','T','T','X','X','X','X','T','T','X'},
                {'X','T','T','T','T','T','T','T','T','T','X'},
                {'X','T','T','T','T','T','T','T','T','T','X'},
                {'X','T','T','T','T','T','T','T','T','T','X'},
                {'X','X','X','X','X','X','X','X','X','X','X'},
        };
    }
}
