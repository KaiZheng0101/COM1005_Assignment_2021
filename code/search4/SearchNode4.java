/**
 *	Node in a state-space search
 *   variable costs case
 *   Must implement goalP, getSuccessors, sameState, node_toString
 *   node has local cost & global cost now
 *   This version for A*
 *   Mods indicated by //A*
 *   Phil Green 2013 version
 * Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
 *  
*/

import java.util.*;

public class SearchNode4 {

  private SearchState4 state;
  // change from search2
  private int Cost;
  private int globalCost;
  private int localCost;
  private int estRemCost; //A*
  private int estTotalCost; //A*

  private SearchNode4 parent; // the parent node

  /**
   * constructor
   *
   * @param s  a SearchState
   * @param lc local cost of getting to this node from its predecessor
   */

  public SearchNode4(SearchState4 s, int lc) {
    state = (SearchState4) s;
    localCost = lc; // change from search2

  }

  /**
   * constructor
   *
   * @param s  a SearchState
   * @param lc local cost of getting to this node from its predecessor
   */

  public SearchNode4(SearchState4 s, int lc, int erc) {
    state = (SearchState4) s;
    localCost = lc; // change from search2
    estRemCost=erc;
  }

  /**
   * accessor for state
   */

  public SearchState4 get_State() {
    return state;
  }

  /**
   * accessor for parent
   */

  public SearchNode4 getParent() {
    return parent;
  }

  /**
   * mutator for parent
   */
  public void setParent(SearchNode4 n) {
    parent = n;
  }

  /**
   * mutator for localcost
   *
   */

  public void setLocalCost(int lc) {
    localCost = lc;
  }

  /**
   * mutator for localcost
   *
   */

  public int getLocalCost() {
    return localCost;
  }

  /**
   * mutator for localcost
   *
   */
  public void setGlobalCost(int lc) {
    globalCost = lc;
  }

  /**
   * acccessor for globalcost
   *
   */
  public int getGlobalCost() {
    return globalCost;
  }

  // must implement goalPredicate, getSuccessors, sameState, node_toString


  /**
   * mutator for estremcost
   * for A*
   */
  public void setestRemCost(int erc) {
    estRemCost=erc;
  }

  /**
   * accessor for estremcost
   * for A*
   */
  public int getestRemCost() {
    return estRemCost;
  }

  /**
   * mutator for esttotalcost
   * for A*
   */
  public void setestTotalCost(int erc) {
    estTotalCost=erc;
  }

  /**
   * accessor for esttotalcost
   * for A*
   */
  public int getestTotalCost() {
    return estTotalCost;
  }

  /**
   * goalPredicate takes a SearchNode & returns true if it's a goal
   *
   * @param searcher the current search
   */

  public boolean goalPredicate(Search4 searcher) {
    return state.goalPredicate(searcher);
  }

  /**
   * getSuccessors for this node
   *
   * @param searcher the current search
   * @return ArrayList of successor nodes
   */

  public ArrayList<SearchNode4> getSuccessors(Search4 searcher) {
    ArrayList<SearchState4> slis = state.getSuccessors(searcher);
    ArrayList<SearchNode4> nlis = new ArrayList<SearchNode4>();

    for (SearchState4 suc_state : slis) {
      SearchNode4 n = new SearchNode4(suc_state, suc_state.getLocalCost());
      nlis.add(n);
    }
    return nlis;
  }

  /**
   * sameState - does another node have same state as this one?
   *
   * @param n2 the other node
   */

  public boolean sameState(SearchNode4 n2) {
    return state.sameState(n2.get_State());
  }

  public String toString() {
    String parent_state;
    if (parent == null)
      parent_state = "null";
    else
      parent_state = parent.get_State().toString();
    return "node with state (" + state.toString() + ") parent state (" + parent_state + ") local cost (" + localCost
            + ") global cost (" + globalCost + ")";
  }
}
