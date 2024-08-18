package graph;

import graph.Edge;
import graph.Graph;
import graph.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private Graph graph;
    private Node nodeA;
    private Node nodeB;
    private Node hatedNode;

    @BeforeEach
    void setUp() {
        nodeA = new Node(1 );
        nodeB = new Node(2 );
        hatedNode = new Node(3 );
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(hatedNode);
        graph = new Graph(nodes);
        Edge.createEdge(nodeA, nodeB, false, 1);
    }

    @Test
    void testBFS() {
        graph.bfs(nodeA, null);
        assertTrue(nodeB.isVisited());
        assertEquals(1, nodeB.getDistance());
    }

    @Test
    void testDijkstra() {
        graph.dijkstra(nodeA, null);
        assertTrue(nodeB.isVisited());
        assertEquals(1, nodeB.getDistance());
    }

    @Test
    void testCanTravelWithoutHatedCity() {
        hatedNode.setHated(true);
        assertTrue(graph.canTravelWithoutHatedCity(nodeA, nodeB, hatedNode));
    }

    @Test
    void testCannotTravelToHatedCity() {
        hatedNode.setHated(true);
        Edge.createEdge(nodeB, hatedNode, false, 1);
        assertTrue(graph.canTravelWithoutHatedCity(nodeA, nodeB, hatedNode));
    }
}