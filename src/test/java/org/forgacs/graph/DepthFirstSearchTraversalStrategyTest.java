package org.forgacs.graph;

import org.forgacs.graph.domain.Graph;
import org.forgacs.graph.domain.Node;
import org.forgacs.graph.domain.NodeVisitor;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Some simple test to test the DFS strategy independently
 */
public class DepthFirstSearchTraversalStrategyTest {

    DepthFirstSearchTraversalStrategy depthFirstSearchTraversalStrategy = new DepthFirstSearchTraversalStrategy();

    @Test
    public void shouldCalculateDSTonEmptyGraph() {
        Graph graph = new Graph(Collections.emptySet());
        depthFirstSearchTraversalStrategy.traverse(graph, (node, visitType) -> {});
        //no exceptions to be thrown
    }

    @Test
    public void shouldCalculateValidDFS() {
        List<String> dfsOrder = new LinkedList<>();
        Graph graph = new DependencyGraphBuilder()
                .addTransition("z=>y")
                .addTransition("y=>x")
                .build();
        depthFirstSearchTraversalStrategy.traverse(graph, (node, visitType) -> {
            if (visitType == NodeVisitor.VisitType.START) {
                dfsOrder.add(node.getValue());
            }
        });
        assertThat(dfsOrder).containsExactly("x","y","z");
    }

    @Test
    public void shouldCalculateValidDFSOnComplexGraph() {
        List<String> dfsOrder = new LinkedList<>();
        List<String> loopNodes = new LinkedList<>();
        Graph graph = new DependencyGraphBuilder()
                .addTransition("y=>x")
                .addTransition("k=>x")
                .addTransition("l=>y")
                .addTransition("m=>y")
                .addTransition("k=>l")
                .addTransition("y=>k")
                .build();
        depthFirstSearchTraversalStrategy.traverse(graph, (node, visitType) -> {
            if (visitType == NodeVisitor.VisitType.START) {
                dfsOrder.add(node.getValue());
            }
            if (visitType == NodeVisitor.VisitType.LOOP_DETECTED) {
                loopNodes.add(node.getValue());
            }
        });
        assertThat(dfsOrder).containsExactly("x", "y", "l", "k", "m");
        assertThat(loopNodes).containsExactly("k");
    }


}