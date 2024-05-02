import java.util.*;

// Represents an exchange rate between two currencies
class ExchangeRate {
    String sourceCurrency;
    String targetCurrency;
    double rate;

    // Constructor to initialize the exchange rate
    public ExchangeRate(String sourceCurrency, String targetCurrency, double rate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }
}

public class MaximizeConversion {
    // Graph representation using adjacency list
    Map<String, List<ExchangeRate>> exchangeRateGraph;

    // Constructor to initialize the graph
    public MaximizeConversion() {
        this.exchangeRateGraph = new HashMap<>();
    }

    // Method to add an exchange rate to the graph
    // Complexity: O(1)
    public void addExchangeRate(String sourceCurrency, String targetCurrency, double rate) {
        exchangeRateGraph.putIfAbsent(sourceCurrency, new ArrayList<>());
        exchangeRateGraph.get(sourceCurrency).add(new ExchangeRate(sourceCurrency, targetCurrency, rate));
    }

    // Method to find the path with the highest conversion rate
    // Complexity: O(E log V), where E is the number of exchange rates and V is the number of currencies
    public List<String> findMaxConversionPath(String sourceCurrency, String targetCurrency) {
        // Data structures for Dijkstra's algorithm
        Map<String, Double> maxRate = new HashMap<>(); // Stores maximum conversion rate
        Map<String, String> parent = new HashMap<>(); // Stores parent currency for path reconstruction
        PriorityQueue<ExchangeRate> pq = new PriorityQueue<>((a, b) -> Double.compare(b.rate, a.rate)); // Priority queue for exchange rates

        // Initialize starting conversion rate
        maxRate.put(sourceCurrency, 1.0); // Starting with a conversion rate of 1.0

        // Add initial exchange rate to priority queue
        pq.offer(new ExchangeRate(sourceCurrency, sourceCurrency, 1.0));

        // Dijkstra's algorithm to find maximum conversion rate
        while (!pq.isEmpty()) {
            ExchangeRate currentExchangeRate = pq.poll(); // Extract the exchange rate with the highest conversion rate
            if (!exchangeRateGraph.containsKey(currentExchangeRate.targetCurrency)) continue; // Skip if no outgoing exchange rates from current currency
            for (ExchangeRate neighborExchangeRate : exchangeRateGraph.get(currentExchangeRate.targetCurrency)) { // Iterate through exchange rates of current currency
                double newRate = currentExchangeRate.rate * neighborExchangeRate.rate; // Calculate new conversion rate
                if (!maxRate.containsKey(neighborExchangeRate.targetCurrency) || newRate > maxRate.get(neighborExchangeRate.targetCurrency)) {
                    // Update maximum conversion rate and parent currency if new rate is greater
                    maxRate.put(neighborExchangeRate.targetCurrency, newRate);
                    parent.put(neighborExchangeRate.targetCurrency, currentExchangeRate.targetCurrency);
                    pq.offer(new ExchangeRate(sourceCurrency, neighborExchangeRate.targetCurrency, newRate)); // Add neighbor currency to priority queue
                }
            }
        }

        // Reconstruct path from 'targetCurrency' to 'sourceCurrency'
        List<String> path = new ArrayList<>();
        String currentCurrency = targetCurrency;
        while (!currentCurrency.equals(sourceCurrency)) {
            path.add(currentCurrency);
            currentCurrency = parent.get(currentCurrency);
        }
        path.add(sourceCurrency);
        Collections.reverse(path); // Reverse path to get 'sourceCurrency' to 'targetCurrency'
        return path;
    }

    // Main method to demonstrate the usage
    public static void main(String[] args) {
        // Create a currency converter instance
        MaximizeConversion converter = new MaximizeConversion();
        // Add exchange rates to the graph
        converter.addExchangeRate("USD", "CAN", 9);
        converter.addExchangeRate("USD", "COP", 10);
        converter.addExchangeRate("CAN", "COP", 20);

        // Define source and target currencies
        String sourceCurrency = "USD";
        String targetCurrency = "COP";

        // Find the path with the highest conversion rate
        List<String> path = converter.findMaxConversionPath(sourceCurrency, targetCurrency);

        // Print the result
        System.out.println("Max conversion path from " + sourceCurrency + " to " + targetCurrency + ": " + path);
    }
}
