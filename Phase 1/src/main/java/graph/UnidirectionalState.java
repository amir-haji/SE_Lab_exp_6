package graph;

public class UnidirectionalState implements RouteState {
    @Override
    public void changeDirection(Graph graph, boolean directed) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setDirected(true);
            }
        }
    }

    @Override
    public void changeTimeUnit(Graph graph, int unit) {
        System.out.println("You cannot change time unit in unindirected mode.");
    }
}