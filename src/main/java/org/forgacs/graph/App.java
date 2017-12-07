package org.forgacs.graph;

import org.forgacs.graph.domain.Graph;

import java.util.List;

import static org.forgacs.graph.TopologicalOrderCalculator.topologicalOrder;

public class App {

    public static void main(String[] args) {
        //some example dependency tree
        Graph graph = new DependencyGraphBuilder()
                .addTransition("u =>")
                .addTransition("v => w")
                .addTransition("w => z")
                .addTransition("x => u")
                .addTransition("y => v")
                .addTransition("z =>")
                .build();
        List<String> dependencyOrder = topologicalOrder(graph);
        System.out.println(dependencyOrder);
    }
}
