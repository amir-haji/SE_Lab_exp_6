package graph;

public class DijkstraStrategy implements DistanceStrategy {
    private Graph graph;

    public DijkstraStrategy(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void calculateDistance(Node start, Node hate) {
        graph.dijkstra(start, hate);
    }
}