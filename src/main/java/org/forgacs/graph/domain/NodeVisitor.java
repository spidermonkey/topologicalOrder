package org.forgacs.graph.domain;

@FunctionalInterface
public interface NodeVisitor {

    enum VisitType {
        START, FINISH, LOOP_DETECTED,
    }

    void visit(Node node, VisitType visitType);

}
