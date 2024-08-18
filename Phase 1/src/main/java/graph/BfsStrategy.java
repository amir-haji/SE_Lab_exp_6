package graph;

public class BfsStrategy implements DistanceStrategy {
    private Graph graph;

    public BfsStrategy(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void calculateDistance(Node start, Node hate) {
        graph.bfs(start, hate);
    }
}