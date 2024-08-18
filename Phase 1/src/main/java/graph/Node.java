package graph;

import java.util.ArrayList;
import java.util.stream.Collectors;

import lombok.Getter;
import org.javatuples.Pair;

import lombok.Data;
import lombok.Setter;

@Data
public class Node implements Comparable<Node> {
    private final ArrayList<Edge> edges;
    @Setter
    private boolean visited;
    @Getter
    @Setter
    private int distance;
    @Setter
    private int id;
    @Setter
    private boolean hated;  // New attribute

    public Node(int id) {
        edges = new ArrayList<>();
        this.id = id;
        this.hated = false;  // Initialize to false by default
    }

    public ArrayList<Pair<Node, Integer>> getAvailableWeightedNeighbors() {
        return getEdges()
                .stream()
                .filter(edge -> !edge.isDirected() || edge.getNodes().getValue0() == this)
                .map(edge -> new Pair<Node, Integer>(
                        (edge.getNodes().getValue0().equals(this)) ? edge.getNodes().getValue1()
                                : edge.getNodes().getValue0(),
                        edge.getWeight()))
                .filter(pair -> !pair.getValue0().isVisited() && !pair.getValue0().isHated())  // Skip hated nodes
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Node> getAvailableNeighbors() {
        return getAvailableWeightedNeighbors()
                .stream()
                .map(pair -> pair.getValue0())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int compareTo(Node o) {
        return o.hashCode() - hashCode();
    }
}