package org.forgacs.graph;

import org.forgacs.graph.domain.Graph;
import org.forgacs.graph.domain.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DependencyGraphBuilder {

    private Map<String, Node> vertices = new HashMap<>();

    public DependencyGraphBuilder addTransition(String transition) {
        String[] split = transition.split("=>");
        String nodeValue = split[0].trim();
        String dependingOnValue = split.length > 1 ? split[1].trim() : null;
        if (nodeValue.equals(dependingOnValue)) {
            throw new IllegalArgumentException("No self references are enabled");
        }
        Node node = vertices.get(nodeValue);
        Node dependingOn = vertices.get(dependingOnValue);
        if (node == null) {
            node = createAndRegisterNewNode(nodeValue);
        }
        if (dependingOnValue != null && dependingOn == null) {
            dependingOn = createAndRegisterNewNode(dependingOnValue);
        }
        if (dependingOnValue != null) {
            dependingOn.getSuccessors().add(node);
        }
        return this;
    }

    private Node createAndRegisterNewNode(String dependingOnValue) {
        Node dependingOn = new Node(dependingOnValue);
        vertices.put(dependingOn.getValue(), dependingOn);
        return dependingOn;
    }

    public Graph build() {
        return new Graph(new HashSet<>(vertices.values()));
    }

}
