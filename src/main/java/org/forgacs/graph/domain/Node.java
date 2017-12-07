package org.forgacs.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Node {
    private final String value;
    private final List<Node> successors = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
