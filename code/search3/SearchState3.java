
/**
*	State in a state-space search
*	abstract class
*   must implement goalPredicate, getSuccessors, sameState, toString
*   variable cost version - has localCost variable
*   Phil Green 2013 version
*   Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
*/

import java.util.*;

public abstract class SearchState3 {

  // change from search2 - need to give local cost
  protected int localCost;

  /**
   * accessor for local cost
   */

  public int getLocalCost() {
    return localCost;
  }

  // must implement goalPredicate, getSuccessors, sameState, toString

  // goalPredicate takes a SearchNode & returns a boolean if it's a goal

  abstract boolean goalPredicate(Search3 searcher);

  abstract ArrayList<SearchState3> getSuccessors(Search3 searcher);

  abstract boolean sameState(SearchState3 n2);
}
