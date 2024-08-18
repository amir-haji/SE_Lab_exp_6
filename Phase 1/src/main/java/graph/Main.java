package graph;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        ArrayList<Node> nodes = new ArrayList<>();
        System.out.println("Get the input graph");
        System.out.println("The number of nodes:");
        int n = scanner.nextInt();
        for(int i = 1; i <= n; i++) {
            nodes.add(new Node(i));
        }
        System.out.println("The number of edges:");
        int m = scanner.nextInt();
        for(int i = 0; i < m; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Edge.createEdge(nodes.get(x - 1), nodes.get(y - 1), false, 1);
        }


        Graph graph = new Graph(nodes);


        RouteContext routeContext = new RouteContext();
        DistanceStrategy bfsStrategy = new BfsStrategy(graph);
        DistanceStrategy dijkstraStrategy = new DijkstraStrategy(graph);


        String input;

        System.out.println("Enter 'exit' to quit the program.");
        while (true) {
            System.out.println("Enter your request:");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("unidirectionalize all routes")) {
                routeContext.setState(new UnidirectionalState());
                routeContext.changeDirection(graph, true);
                System.out.println("All routes are now unidirectional.");
            } else if (input.equalsIgnoreCase("make all routes bidirectional")) {
                routeContext.setState(new BidirectionalState());
                routeContext.changeDirection(graph, false);
                System.out.println("All routes are now bidirectional.");
            } else if (input.startsWith("change train time unit")) {

                System.out.println("New time unit(int):");
                int unit = scanner.nextInt();
                routeContext.changeUnit(graph, unit);
                System.out.println("Train time unit changed.");
            } else if (input.startsWith("distance by train")) {

                System.out.println("Enter the origin city and destination:");
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                Node cityA = nodes.get(a - 1);
                Node cityB = nodes.get(b - 1);
                bfsStrategy.calculateDistance(cityA, null);
                System.out.println("Time distance by train: " + cityB.getDistance());
            } else if (input.startsWith("distance by bus")) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                Node cityA = nodes.get(a - 1);
                Node cityB = nodes.get(b - 1);
                dijkstraStrategy.calculateDistance(cityA, null);
                System.out.println("Time distance by bus: " + cityB.getDistance());
            } else if (input.startsWith("fastest way to travel")) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                Node cityA = nodes.get(a - 1);
                Node cityB = nodes.get(b - 1);
                bfsStrategy.calculateDistance(cityA, null);
                int busDistance = cityB.getDistance();
                dijkstraStrategy.calculateDistance(cityA, null);
                int trainDistance = cityB.getDistance();

                if (busDistance <= trainDistance) {
                    System.out.println("Bus is quicker than the train");
                } else {
                    System.out.println("Train is quicker than the bus");
                }

            } else if (input.startsWith("possible to avoid city")) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int c = scanner.nextInt();
                Node cityA = nodes.get(a - 1);
                Node cityB = nodes.get(b - 1);
                Node hatedCity = nodes.get(c - 1);
                boolean canAvoid = graph.canTravelWithoutHatedCity(cityA, cityB, hatedCity);
                System.out.println("Possible to avoid hated city: " + canAvoid);
            } else {
                System.out.println("Invalid request. Try again.");
            }
        }

        scanner.close();
    }
}