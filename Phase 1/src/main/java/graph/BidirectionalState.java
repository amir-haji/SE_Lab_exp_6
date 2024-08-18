package graph;

public class BidirectionalState implements RouteState {
    @Override
    public void changeDirection(Graph graph, boolean directed) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setDirected(false);
            }
        }
    }

    @Override
    public void changeTimeUnit(Graph graph, int unit) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setWeight(unit);
            }
        }
    }
}