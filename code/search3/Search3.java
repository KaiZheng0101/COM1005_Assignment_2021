/*
*	Search.java - abstract class specialising to MapSearch etc
* Phil Green 2013 version
* Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
*/

import java.util.*;

public abstract class Search3 {

  protected SearchNode3 initNode; // initial node 初始节点
  protected SearchNode3 currentNode; // current node  当前节点
  protected SearchNode3 old_node; // node found on open with same state as new one （在打开时发现与新节点状态相同的节点）
  protected ArrayList<SearchNode3> open; // open - list of SearchNodes
  protected ArrayList<SearchNode3> closed; // closed
  protected ArrayList<SearchNode3> successorNodes; // used in expand & vetSuccessors

  // run a search
  public String runSearch(SearchState3 initState, String strat) {

    initNode = new SearchNode3(initState, 0, 0); // create initial node
    initNode.setGlobalCost(0); // change from search2

    // change from search1 - print strategy
    System.out.println("Starting " + strat + " Search");

    open = new ArrayList<SearchNode3>(); // initial open, closed
    open.add(initNode);
    closed = new ArrayList<SearchNode3>();

    //数值迭代
    int numIteration = 1;

    while (!open.isEmpty()) {

      // print contents of open
      System.out.println("-------------------------");
      System.out.println("iteration no " + numIteration);
      System.out.println("open is");
      for (SearchNode3 nn : open) {
        String nodestr = nn.toString();
        System.out.println(nodestr);
      }

      selectNode(strat); // change from search1 -selectNode selects next node given strategy,（selectNode选择给定策略的下一个节点）
      // makes it currentNode & removes it from open
      // （将其设置为currentNode并将其从打开状态中删除）
      System.out.println("Current node: " + currentNode.toString());

      if (currentNode.goalPredicate(this))
        return reportSuccess(); // success
      // change from search1 - call reportSuccess

      expand(); // go again
      closed.add(currentNode); // put current node on closed
      numIteration = numIteration + 1;
    }

    return "Search Fails"; // out of the while loop - failure

  }

  // expand current node
  // 扩展当前节点
  private void expand() {

    // get all successor nodes
    // 获取所有后继节点
    successorNodes = currentNode.getSuccessors(this); // pass search instance 通过搜索实例

    // change from search2
    // set global costs and parents for successors

    for (SearchNode3 snode : successorNodes) {
      snode.setGlobalCost(currentNode.getGlobalCost() + snode.getLocalCost());
      snode.setParent(currentNode);
    }

    vetSuccessors(); // filter out unwanted - DP check

    // add surviving nodes to open
    // 添加尚存的节点以打开
    for (SearchNode3 snode : successorNodes)
      open.add(snode);
  }

  // vet the successors - reject any whose states are on open or closed
  // 拒绝任何处于打开或关闭状态的节点
  // change from search2 to do DP check

  private void vetSuccessors() {
    ArrayList<SearchNode3> vslis = new ArrayList<SearchNode3>();

    for (SearchNode3 snode : successorNodes) {
      if (!(onClosed(snode))) { // if on closed, ignore
        if (!(onOpen(snode))) {
          vslis.add(snode); // if not on open, add it
        } else { // DPcheck - node found on open is in old_node
          if (snode.getGlobalCost() <= old_node.getGlobalCost()) { // compare global costs
            old_node.setParent(snode.getParent()); // better route, modify node
            old_node.setGlobalCost(snode.getGlobalCost());
            old_node.setLocalCost(snode.getLocalCost());
          }
        }
      }
    }

    successorNodes = vslis;
  }

  // onClosed - is the state for a node the same as one on closed? 节点的状态与关闭时的状态相同吗？
  private boolean onClosed(SearchNode3 newNode) {
    boolean ans = false;
    for (SearchNode3 closedNode : closed) {
      if (newNode.sameState(closedNode))
        ans = true;
    }
    return ans;
  }

  // onOpen - is the state for a node the same as one on open?
  // if node found, remember it in old_node
  private boolean onOpen(SearchNode3 newNode) {
    boolean ans = false;
    //设置迭代器
    Iterator ic = open.iterator();
    while ((ic.hasNext()) && !ans) { // there can only be one node on open with same state 打开时只能有一个节点处于相同状态
      SearchNode3 openNode = (SearchNode3) ic.next();
      if (newNode.sameState(openNode)) {
        ans = true;
        old_node = openNode;
      }
    }
    return ans;
  }

  // select the next node
  private void selectNode(String strat) {
    if (strat == "depthFirst")
      depthFirst();
    else if (strat == "breadthFirst")
      breadthFirst();
    else
      branchAndBound();
  }

  private void depthFirst() {
    int osize = open.size();
    currentNode = (SearchNode3) open.get(osize - 1); // last node added to open
    open.remove(osize - 1); // remove it
  }

  private void breadthFirst() {
    currentNode = (SearchNode3) open.get(0); // first node on open
    open.remove(0);
  }

  // change from search2
  private void branchAndBound() {
    Iterator i = open.iterator();
    SearchNode3 minCostNode = (SearchNode3) i.next();
    for (; i.hasNext();) {
      SearchNode3 n = (SearchNode3) i.next();
      if (n.getGlobalCost() < minCostNode.getGlobalCost()) {
        minCostNode = n;
      }
    }

    currentNode = minCostNode;
    open.remove(minCostNode);
  }

  // change from search1
  // report success - reconstruct path, convert to string & return
  private String reportSuccess() {

    SearchNode3 n = currentNode;
    StringBuffer buf = new StringBuffer(n.toString());
    int plen = 1;

    while (n.getParent() != null) {
      buf.insert(0, "\n");
      n = n.getParent();
      buf.insert(0, n.toString());
      plen = plen + 1;
    }

    System.out.println("=========================== \n");
    System.out.println("Search Succeeds");

    System.out.println("Efficiency " + ((float) plen / (closed.size() + 1)));
    System.out.println("Solution Path\n");
    return buf.toString();
  }
}
