/**
 * @author: Kai Zheng
 * @date: 2021-05-20 14:04
 * @Version 1.8
 **/

/**
 * @(#)TerrainMap.java
 * Terrain Maps for the Rambler's problem
 * store and display as PGM images
 * Phil Green  2018/1/10
 * Heidi Christensen 2021
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TerrainMap {
    private int[][] tmap; // the map, array of pixels
    private int width; // max x dimension
    private int depth; // max y dimension
    private int height; // max height
    private String mno; // magic number
    private String irfan; // irfan comment line
    // accessors

    public int[][] getTmap() {
        return tmap;
    };

    public int getWidth() {
        return width;
    };

    public int getDepth() {
        return depth;
    };

    public int getHeight() {
        return height;
    };

    private static final int MAXVAL = 255; // We'll use a constant maximum greyscale value

    /**
     * constructor, given a PGM image Reads a PGM file. The maximum greyscale value
     * is rescaled to be between 0 and 255.
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public TerrainMap(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            mno = scanner.next(); // magic number
            if (!scanner.hasNextInt()) {
                irfan = scanner.nextLine();
            }
            ; // advance until next Int
            if (!scanner.hasNextInt()) {
                irfan = scanner.nextLine();
            }
            ;
            width = scanner.nextInt();
            depth = scanner.nextInt();
            height = scanner.nextInt();

            tmap = new int[depth][width];

            // read pixels
            for (int i = 0; i < depth; ++i) {
                for (int j = 0; j < width; ++j) {

                    int value = (int) scanner.nextInt();
                    // normalize to 255
                    value = (int) Math.round(((double) value) / height * MAXVAL);
                    tmap[i][j] = value;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        }
    }

    // write a terrain map to a file
    public void writeTMap(String fname) {

        try {
            FileWriter writer = new FileWriter(fname, true);
            writer.write(mno + "\n");
            writer.write(width + " ");
            writer.write(depth + "\n");
            writer.write(height + "\n");

            for (int i = 0; i < depth; ++i) {
                for (int j = 0; j < width; ++j) {
                    writer.write(tmap[i][j] + " ");
                }
                writer.write("\n"); // write new line
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // show a path on a terrain map
    // path is ArrayList of Coords
    // make every pixel on path 255

    public void showPath(ArrayList<Coords> path) {
        for (Coords c : path)
            tmap[c.gety()][c.getx()] = 255;
    }

    public int localCost(Coords c1, Coords c2) {

        int h1 = tmap[c1.gety()][c1.getx()];
        int h2 = tmap[c2.gety()][c2.getx()];
        if (h2 <= h1) {
            return 1;
        }
        return 1 + h2 -h1;
    }

    public double estBetween(Coords c1, Coords c2, String heuristics) {
        if (heuristics.equals("Manhattan")) {
            return Math.abs(c2.getx()-c1.getx()) +  Math.abs(c2.gety()-c1.gety());
        } else if(heuristics.equals("Euclidean")) {
            double ans = (c2.getx()-c1.getx()) * (c2.getx()-c1.getx());
            ans += (c2.gety()-c1.gety()) * (c2.gety()-c1.gety());
            return Math.sqrt(ans);
        } else if(heuristics.equals("height")) {
            return localCost(c1,c2)-1;
        }
        // else we use mix heuristics
        return 0.4*estBetween(c1,c2, "Manhattan") + 0.3*estBetween(c1,c2, "Euclidean") + 0.3*estBetween(c1,c2, "height");
    }
}
