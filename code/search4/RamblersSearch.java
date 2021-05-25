/**
 * MapSearch.java
 * Phil Green 2013 version
 * Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
 *
 * search for map traversal
 */

import java.util.*;

public class RamblersSearch extends Search4 {

    private TerrainMap map; // map we're searching
    private Coords goal; // goal city

    public TerrainMap getMap() {
        return map;
    }

    public Coords getGoal() {
        return goal;
    }

    public RamblersSearch(TerrainMap m, Coords g) {
        map = m;
        goal = g;
    }
}
