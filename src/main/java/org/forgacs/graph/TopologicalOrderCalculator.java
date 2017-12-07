package org.forgacs.graph;

import lombok.experimental.UtilityClass;
import org.forgacs.graph.domain.Graph;
import org.forgacs.graph.domain.NodeVisitor;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class TopologicalOrderCalculator {

    public static List<String> topologicalOrder(Graph graph) {
        LinkedList<String> order = new LinkedList<>();
        new DepthFirstSearchTraversalStrategy()
                .traverse(graph, (node, visitType) -> {
                     if (visitType == NodeVisitor.VisitType.FINISH) {
                         order.addFirst(node.getValue());
                     }
                     else if (visitType == NodeVisitor.VisitType.LOOP_DETECTED) {
                         throw new LoopDetectedException("Loop detected at node: " + node.getValue());
                     }
                });
        return order;
    }

    public class LoopDetectedException extends RuntimeException {
        public LoopDetectedException(String message) {
            super(message);
        }
    }

}
