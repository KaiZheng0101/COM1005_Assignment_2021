/**
 *	State in a map search
 *	Version for A*
 *	changes indicated by //A*
 * Phil Green	2013 version
 * Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
 */

import java.util.*;

public class RamblersState extends SearchState4{

 private Coords c;

 public RamblersState(Coords c , int lc, double rc) {
  this.c = c;
  this.localCost = lc;
  this.estRemCost = (int) rc;
 }

 @Override
 boolean goalPredicate(Search4 searcher) {
  RamblersSearch rs = (RamblersSearch) searcher;
  return c.getx() == rs.getGoal().getx() && c.gety() == rs.getGoal().gety();
 }

 @Override
 ArrayList<SearchState4> getSuccessors(Search4 searcher) {
  ArrayList<SearchState4> succs = new ArrayList();
  RamblersSearch rs = (RamblersSearch) searcher;
  TerrainMap tm = rs.getMap();
  int rn = tm.getDepth();
  int cn = tm.getWidth();
  int x = c.getx();
  int y = c.gety();
  if(x-1>=0) {
   Coords next = new Coords(y, x-1);
   succs.add(new RamblersState(next, tm.localCost(c,next), tm.estBetween(next, rs.getGoal(), "Manhattan")));
  }
  if(x+1<cn) {
   Coords next = new Coords(y, x+1);
   succs.add(new RamblersState(next, tm.localCost(c,next), tm.estBetween(next, rs.getGoal(), "Manhattan")));
  }
  if(y-1>=0) {
   Coords next = new Coords(y-1,x);
   succs.add(new RamblersState(next, tm.localCost(c,next), tm.estBetween(next, rs.getGoal(), "Manhattan")));
  }
  if(y+1<rn) {
   Coords next = new Coords(y+1, x);
   succs.add(new RamblersState(next, tm.localCost(c,next), tm.estBetween(next, rs.getGoal(), "Manhattan")));
  }
  return succs;
 }

 @Override
 boolean sameState(SearchState4 n2) {
  RamblersState rs = (RamblersState) n2;
  return c.getx() == rs.getC().getx() && c.gety() == rs.getC().gety();
 }

 public Coords getC() {
  return c;
 }

 @Override
 public String toString() {
  return "row: "+c.gety()+" col: "+c.getx();
 }
}