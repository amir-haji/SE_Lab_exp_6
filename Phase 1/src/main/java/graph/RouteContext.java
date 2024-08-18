package graph;

public class RouteContext {
    private RouteState state;

    public void setState(RouteState state) {
        this.state = state;
    }

    public void changeDirection(Graph graph, boolean directed) {
        state.changeDirection(graph, directed);
    }

    public void changeUnit(Graph graph, int unit) {
        state.changeTimeUnit(graph, unit);
    }
}