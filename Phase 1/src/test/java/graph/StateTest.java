package graph;

import graph.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    private Graph graph;
    private Node nodeA;
    private Node nodeB;
    private RouteContext routeContext;

    @BeforeEach
    void setUp() {
        nodeA = new Node(1);
        nodeB = new Node(2);
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(nodeA);
        nodes.add(nodeB);
        graph = new Graph(nodes);
        Edge.createEdge(nodeA, nodeB, false, 1);
        routeContext = new RouteContext();
    }

    @Test
    void testUnidirectionalState() {
        routeContext.setState(new UnidirectionalState());
        routeContext.changeDirection(graph, true);
        assertTrue(nodeA.getEdges().get(0).isDirected());
    }

    @Test
    void testBidirectionalState() {
        routeContext.setState(new BidirectionalState());
        routeContext.changeDirection(graph, false);
        assertFalse(nodeA.getEdges().get(0).isDirected());
    }
}