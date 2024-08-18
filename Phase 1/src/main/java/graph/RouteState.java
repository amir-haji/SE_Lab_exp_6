package graph;

public interface RouteState {
    void changeDirection(Graph graph, boolean directed);

    void changeTimeUnit(Graph graph, int unit);
}