import java.util.*;

class Edge {
    String from;
    String to;
    double rate;

    public Edge(String from, String to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }
}

public class MaximizeConversion {
    Map<String, List<Edge>> graph;

    public MaximizeConversion() {
        this.graph = new HashMap<>();
    }

    public void addEdge(String from, String to, double rate) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.get(from).add(new Edge(from, to, rate));
    }

    public List<String> findMaxConversionPath(String from, String to) {
        Map<String, Double> minCost = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Double.compare(b.rate, a.rate));

        minCost.put(from, 1.0); // Starting with a conversion rate of 1.0

        pq.offer(new Edge(from, from, 1.0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (!graph.containsKey(current.to)) continue;
            for (Edge neighbor : graph.get(current.to)) {
                double newRate = current.rate * neighbor.rate;
                if (!minCost.containsKey(neighbor.to) || newRate > minCost.get(neighbor.to)) {
                    minCost.put(neighbor.to, newRate);
                    parent.put(neighbor.to, current.to);
                    pq.offer(new Edge(from, neighbor.to, newRate));
                }
            }
        }

        List<String> path = new ArrayList<>();
        String current = to;
        while (!current.equals(from)) {
            path.add(current);
            current = parent.get(current);
        }
        path.add(from);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        MaximizeConversion converter = new MaximizeConversion();
        converter.addEdge("USD", "CAN", 9);
        converter.addEdge("USD", "COP", 10);
        converter.addEdge("CAN", "COP",20);

        String from = "USD";
        String to = "COP";
        List<String> path = converter.findMaxConversionPath(from, to);

        System.out.println("Max conversion path from " + from + " to " + to + ": " + path);
    }
}
