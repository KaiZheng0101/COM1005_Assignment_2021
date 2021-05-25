/**
 * TestTerrainMap.java
 *
 * Phil Green 2013 version
 * Heidi Christensen 2021 version
 *
 * Example of how you load a terrain map
 */

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RunRamblersBB {

    /**
     * constructor, given a PGM image Reads a PGM file. The maximum greyscale value
     * is rescaled to be between 0 and 255.
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     */

    public static void main(String[] arg) throws NullPointerException{

        public class RunRamblersBB {

            public static void main(String[] arg) {

                TerrainMap map1 = new TerrainMap("tmc.pgm");
                System.out.println("Input a goal coordinate");
                Scanner sc = new Scanner(System.in);
                System.out.println(String.format("the y-axis no greater than %d", map1.getDepth()));
                System.out.println(String.format("the x-axis no greater than %d", map1.getWidth()));
                System.out.println("Input the y-axis for start: ");
                int ys = sc.nextInt();
                System.out.println("Input the x-axis for start: ");
                int xs = sc.nextInt();
                System.out.println("Input the y-axis for end: ");
                int ye = sc.nextInt();
                System.out.println("Input the x-axis for end: ");
                int xe = sc.nextInt();

                RamblersSearch searcher = new RamblersSearch(map1, new Coords(ye, xe));
                // the estimate cost is no use for BB
                SearchState4 initState = (SearchState4) new RamblersState(new Coords(ys, xs), 0, 0);


                String res_astar = searcher.runSearch(initState, "branchAndBound");
                System.out.println(res_astar);

            }

    }
}