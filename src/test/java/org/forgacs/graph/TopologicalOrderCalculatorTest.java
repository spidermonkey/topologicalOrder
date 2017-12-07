package org.forgacs.graph;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.forgacs.graph.TopologicalOrderCalculator.*;

public class TopologicalOrderCalculatorTest {

    @Test
    public void shouldCalculateCorrectOrderInCaseOfOneNode() {
        Graph graph = new DependencyGraphBuilder()
                .addTransition("x =>")
                .addTransition("y =>")
                .addTransition("z =>")
                .build();
        List<String> order = topologicalOrder(graph);
        assertThat(order).containsExactlyInAnyOrder("x", "y", "z");
    }

    @Test
    public void shouldWorkForSimpleInput() {
        Graph graph = new DependencyGraphBuilder()
                .addTransition("x =>")
                .addTransition("y => z")
                .addTransition("z =>")
                .build();
        List<String> order = topologicalOrder(graph);
        assertThat(order).containsExactly("z", "y", "x");
    }

    @Test
    public void shouldWorkForComplexInput() {
        Graph graph = new DependencyGraphBuilder()
                .addTransition("u =>")
                .addTransition("v => w")
                .addTransition("w => z")
                .addTransition("x => u")
                .addTransition("y => v")
                .addTransition("z =>")
                .build();
        List<String> order = topologicalOrder(graph);
        assertThat(order).containsExactly("z", "w", "v", "y", "u", "x");
    }

    @Test(expected = LoopDetectedException.class)
    public void shouldDetectCircle() {
        Graph graph = new DependencyGraphBuilder()
                .addTransition("u => x")
                .addTransition("x => y")
                .addTransition("y => u")
                .build();
        topologicalOrder(graph);
    }

    @Test
    public void shouldWorkWithMultiEdges() {
        Graph graph = new DependencyGraphBuilder()
                .addTransition("x => y")
                .addTransition("x => y")
                .addTransition("y =>")
                .build();
        List<String> order = topologicalOrder(graph);
        assertThat(order).containsExactly("y", "x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectSelfLoops() {
        Graph graph = new DependencyGraphBuilder()
                .addTransition("x => x")
                .build();
        topologicalOrder(graph);
    }

}