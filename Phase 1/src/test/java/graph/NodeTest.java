package graph;

import graph.Edge;
import graph.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    private Node node;
    private Node neighbor;
    private Edge edge;

    @BeforeEach
    void setUp() {
        node = new Node(1 );
        neighbor = new Node(2 );
        Edge.createEdge(node, neighbor, false, 1);
    }

    @Test
    void testNodeInitialization() {
        assertNotNull(node.getEdges());
        assertEquals(1, node.getEdges().size());
    }

    @Test
    void testGetAvailableNeighbors() {
        ArrayList<Node> neighbors = node.getAvailableNeighbors();
        assertEquals(1, neighbors.size());
        assertEquals(neighbor, neighbors.get(0));
    }

    @Test
    void testHatedNodeIsExcluded() {
        neighbor.setHated(true);
        ArrayList<Node> neighbors = node.getAvailableNeighbors();
        assertEquals(0, neighbors.size());
    }
}