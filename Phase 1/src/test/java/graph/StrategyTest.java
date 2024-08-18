package graph;

import graph.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {
    private Graph graph;
    private Node nodeA;
    private Node nodeB;
    private DistanceStrategy bfsStrategy;
    private DistanceStrategy dijkstraStrategy;

    @BeforeEach
    void setUp() {
        nodeA = new Node(1);
        nodeB = new Node(2 );
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(nodeA);
        nodes.add(nodeB);
        graph = new Graph(nodes);
        Edge.createEdge(nodeA, nodeB, false, 1);
        bfsStrategy = new BfsStrategy(graph);
        dijkstraStrategy = new DijkstraStrategy(graph);
    }

    @Test
    void testBfsStrategy() {
        bfsStrategy.calculateDistance(nodeA, null);
        assertTrue(nodeB.isVisited());
        assertEquals(1, nodeB.getDistance());
    }

    @Test
    void testDijkstraStrategy() {
        dijkstraStrategy.calculateDistance(nodeA, null);
        assertTrue(nodeB.isVisited());
        assertEquals(1, nodeB.getDistance());
    }
}