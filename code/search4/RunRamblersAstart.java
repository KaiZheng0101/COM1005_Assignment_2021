import java.util.Scanner;

/**
 * @author: Kai Zheng
 * @date: 2021-05-25 12:30
 * @Version 1.8
 **/
public class RunRamblersAstart {
    public static void main(String[] arg) {

        TerrainMap map1 = new TerrainMap("tmc.pgm");
        System.out.println("Input a goal coordinate");
        Scanner sc = new Scanner(System.in);
        System.out.println(String.format("the y-axis no greater than %d", map1.getDepth()-1));
        System.out.println(String.format("the x-axis no greater than %d", map1.getWidth()-1));
        System.out.println("Input the y-axis for start: ");
        int ys = sc.nextInt();
        System.out.println("Input the x-axis for start: ");
        int xs = sc.nextInt();
        System.out.println("Input the y-axis for end: ");
        int ye = sc.nextInt();
        System.out.println("Input the x-axis for end: ");
        int xe = sc.nextInt();

        RamblersSearch searcher = new RamblersSearch(map1, new Coords(ye, xe));
        SearchState4 initState = (SearchState4) new RamblersState(new Coords(ys, xs), 0, 0);


        String res_bb = searcher.runSearch(initState, "AStar");
        System.out.println(res_bb);

    }

}
