package org.forgacs.graph;


import org.forgacs.graph.domain.Graph;
import org.forgacs.graph.domain.Node;
import org.forgacs.graph.domain.NodeVisitor;

import java.util.*;

import static org.forgacs.graph.domain.NodeVisitor.VisitType.*;

public class DepthFirstSearchTraversalStrategy {

    private enum TraverseState {
        STARTED, FINISHED
    }

    public void traverse(Graph graph, NodeVisitor nodeVisitor) {
        Map<Node, TraverseState> visitedVertices = new HashMap<>();
        for (Node vertex : graph.vertices) {
                if (!visitedVertices.containsKey(vertex)) {
                    traverse(vertex, nodeVisitor, visitedVertices);
                }
        }
    }

    private void traverse(Node node, NodeVisitor nodeVisitor, Map<Node, TraverseState> visitedVertices) {
        nodeVisitor.visit(node, START);
        visitedVertices.put(node, TraverseState.STARTED);
        for (Node successor : node.getSuccessors()) {
            if (!visitedVertices.containsKey(successor)) {
                traverse(successor, nodeVisitor, visitedVertices);
            }
            else {
                if (visitedVertices.get(successor) == TraverseState.STARTED) {
                    nodeVisitor.visit(node, LOOP_DETECTED);
                }
            }
        }
        nodeVisitor.visit(node, FINISH);
        visitedVertices.put(node, TraverseState.FINISHED);
    }
}
