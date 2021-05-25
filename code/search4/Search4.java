/*
 *	Search.java - abstract class specialising to MapSearch etc
 *	This version supports A*
 *	See //A* for changes
 *	Phil Green 2013 version
 *  Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
*/

import java.util.*;

public abstract class Search4 {

    protected SearchNode4 initNode; // initial node
    protected SearchNode4 currentNode; // current node
    protected SearchNode4 old_node; // node found on open with same state as new one
    protected ArrayList<SearchNode4> open; // open - list of SearchNodes
    protected ArrayList<SearchNode4> closed; // closed
    protected ArrayList<SearchNode4> successorNodes; // used in expand & vetSuccessors

    // run a search
    public String runSearch(SearchState4 initState, String strat) {

        initNode = new SearchNode4(initState, 0); // create initial node
        initNode.setGlobalCost(0); // change from search2

        // change from search1 - print strategy
        System.out.println("Starting " + strat + " Search");

        open = new ArrayList<SearchNode4>(); // initial open, closed
        open.add(initNode);
        closed = new ArrayList<SearchNode4>();

        int numIteration = 1;

        while (!open.isEmpty()) {

            // print contents of open
            System.out.println("-------------------------");
            System.out.println("iteration no " + numIteration);
            System.out.println("open is");
            for (SearchNode4 nn : open) {
                String nodestr = nn.toString();
                System.out.println(nodestr);
            }

            selectNode(strat); // change from search1 -selectNode selects next node given strategy,
            // makes it currentNode & removes it from open
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
    private void expand() {

        // get all successor nodes
        successorNodes = currentNode.getSuccessors(this); // pass search instance

        // change from search2
        // set global costs and parents for successors

        for (SearchNode4 snode : successorNodes) {
            snode.setGlobalCost(currentNode.getGlobalCost() + snode.getLocalCost());
            snode.setParent(currentNode);
            snode.setestTotalCost(snode.getGlobalCost()+snode.getestRemCost()); //A*
        }

        vetSuccessors(); // filter out unwanted - DP check

        // add surviving nodes to open
        for (SearchNode4 snode : successorNodes)
            open.add(snode);
    }

    // vet the successors - reject any whose states are on open or closed
    // change from search2 to do DP check

    private void vetSuccessors() {
        ArrayList<SearchNode4> vslis = new ArrayList<SearchNode4>();

        for (SearchNode4 snode : successorNodes) {
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

    // onClosed - is the state for a node the same as one on closed?
    private boolean onClosed(SearchNode4 newNode) {
        boolean ans = false;
        for (SearchNode4 closedNode : closed) {
            if (newNode.sameState(closedNode))
                ans = true;
        }
        return ans;
    }

    // onOpen - is the state for a node the same as one on open?
    // if node found, remember it in old_node
    private boolean onOpen(SearchNode4 newNode) {
        boolean ans = false;
        Iterator ic = open.iterator();
        while ((ic.hasNext()) && !ans) { // there can only be one node on open with same state
            SearchNode4 openNode = (SearchNode4) ic.next();
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
        currentNode = (SearchNode4) open.get(osize - 1); // last node added to open
        open.remove(osize - 1); // remove it
    }

    private void breadthFirst() {
        currentNode = (SearchNode4) open.get(0); // first node on open
        open.remove(0);
    }

    // change from search2
    private void branchAndBound() {
        Iterator i = open.iterator();
        SearchNode4 minCostNode = (SearchNode4) i.next();
        for (; i.hasNext();) {
            SearchNode4 n = (SearchNode4) i.next();
            if (n.getGlobalCost() < minCostNode.getGlobalCost()) {
                minCostNode = n;
            }
        }

        currentNode = minCostNode;
        open.remove(minCostNode);
    }

    private void AStar() {
        Iterator i = open.iterator();
        SearchNode4 minCostNode = (SearchNode4) i.next();
        for (; i.hasNext();) {
            SearchNode4 n = (SearchNode4) i.next();
            if (n.getestTotalCost() < minCostNode.getestTotalCost()) {
                minCostNode = n;
            }
        }
        currentNode=minCostNode;
        open.remove(minCostNode);
    }


    // change from search1
    // report success - reconstruct path, convert to string & return
    private String reportSuccess() {

        SearchNode4 n = currentNode;
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
