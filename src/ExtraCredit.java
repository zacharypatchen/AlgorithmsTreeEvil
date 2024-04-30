import java.util.*;

public class ExtraCredit {
    /*
    Time Complexity: First for loop iterates w times, where w is the amount of arrays in trust. Second for loop iterates
    n times, where n is the int argument in the method's signature. So, O(w+n).

    Space Complexity: O(n+1) where n is the int argument in the method's signature. The space complexity is determined
    by the size of the count array, which has a size of n+1 to accommodate people labeled from 1 to n.
     */
    public int findJudge(int n, int[][] trust) {
        // count array to keep track of how much
        int[] count = new int[n+1]; // people are labled 1 through n
        for (int[] t : trust){
            count[t[0]]--;
            count[t[1]]++;
        }
        for (int i =1; i<=n; i++){
            if(count[i] == n-1) return i;
        }
        return -1;
    }

        /*
         * This class implements a solution to determine whether there is a valid path
         * from a given source node to a destination node in an undirected graph.
         * The graph is represented as an adjacency list.
         *
         * The algorithm used is Depth-First Search (DFS) implemented iteratively.
         * DFS is chosen for its simplicity and effectiveness in exploring graphs,
         * especially when the graph is sparse or has a deeper structure.
         *
         * Algorithm Steps:
         * 1. Check base cases: If the source node is the same as the destination,
         *    return true. If there are no edges in the graph, return false.
         *
         * 2. Create the graph: Initialize an adjacency list representation of the graph
         *    using the given number of vertices and edge information.
         *
         * 3. Initialize a stack for DFS traversal and a boolean array to mark visited nodes.
         *
         * 4. Start DFS from the source node:
         *    - Push the source node onto the stack and mark it as visited.
         *
         * 5. While the stack is not empty, pop a node from the stack:
         *    - If the popped node is the destination, return true (a path is found).
         *    - Otherwise, explore its neighbors:
         *      - For each unvisited neighbor, push it onto the stack and mark it as visited.
         *
         * 6. If the destination is not found after DFS traversal, return false (no valid path).
         *
         * Time Complexity Analysis:
         * - Constructing the graph: O(V + E), where V is the number of vertices and E is the number of edges.
         * - DFS traversal: O(V + E), where N is the number of vertices and E is the number of edges.
         *   Each vertex and edge is visited at most once during DFS traversal.
         * - Overall time complexity: O(V + E).
         *
         * Space Complexity Analysis:
         * - Graph representation: O(V + E), where V is the number of vertices and E is the number of edges.
         * - Stack and visited array: O(V), where V is the number of vertices.
         *   Additional space is used for DFS traversal.
         * - Overall space complexity: O(V + E).
         */
        /**
         * Determines whether there is a valid path from a given source node to a destination node.
         *
         * @param n             The number of vertices in the graph.
         * @param edges         The edge information of the graph.
         * @param source        The source node.
         * @param destination   The destination node.
         * @return              True if there is a valid path from source to destination, otherwise false.
         */
        public boolean validPath(int n, int[][] edges, int source, int destination) {
            // Check base cases
            if (source == destination) {
                return true;
            }
            if (edges.length == 0) {
                return false;
            }

            // Proceed to graph creation
            ArrayList<Integer>[] graph = createGraph(n, edges);

            // Initialize DFS stack
            Stack<Integer> stack = new Stack<>();
            boolean[] visited = new boolean[n];

            // Start DFS from source node
            stack.push(source);
            visited[source] = true;

            while (!stack.isEmpty()) {
                int currentNode = stack.pop();
                if (currentNode == destination) {
                    return true; // Found destination
                }
                // Explore neighbors
                for (int neighbor : graph[currentNode]) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                        visited[neighbor] = true;
                    }
                }
            }

            return false; // Destination not reachable from source
        }
        /**
         * Creates an adjacency list representation of the graph.
         *
         * @param n      The number of vertices in the graph.
         * @param edges  The edge information of the graph.
         * @return       An array of ArrayLists representing the graph.
         */
        private ArrayList<Integer>[] createGraph(int n, int[][] edges) {
            ArrayList<Integer>[] graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            // Add edges to the graph
            for (int[] edge : edges) {
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]); // Assuming undirected graph
            }
            return graph;
        }

            /*
             * Time Complexity: O(V + E), where V is the number of courses (vertices) and E is the number of prerequisites
             * (edges) in the graph.
             * The function constructs the adjacency matrix in O(E) time and then performs a DFS traversal on the graph,
             * visiting each vertex and each edge once. Therefore, the overall time complexity is O(V + E).
             *
             * Space Complexity: O(V^2), where V is the number of courses (vertices) in the graph.
             * The space complexity is determined by the size of the adjacency matrix, which has a size of VxV
             * to represent all possible prerequisites between courses.
             * Additionally, the space used by the visited and explored arrays also contributes to the space complexity,
             * each of which has size V. Therefore, the overall space complexity is O(V^2).
             */

            /*
             * The algorithm uses Depth-First Search (DFS) traversal.
             * DFS starts at the initial node (in this case, represented by the method `canFinish`) and explores as far as
             * possible along each branch before backtracking. In this context, DFS is used to traverse the graph of courses
             * and their prerequisites, checking for cycles. DFS visits each vertex (course) and each edge (prerequisite) once,
             * giving it a time complexity of O(V + E), where V is the number of courses (vertices) and E is the number of
             * prerequisites (edges) in the graph. DFS uses recursion or a stack to keep track of visited nodes and explore
             * neighboring nodes. In this implementation, it's done recursively in the `isCyclic` method. Additionally, DFS uses
             * space to store visited nodes. In this case, the space complexity is O(V), where V is the number of vertices
             * (courses) in the graph. This space is determined by the size of the `visited` and `explored` arrays.
             */


            public boolean canFinish(int numCourses, int[][] prerequisites) {
                if(prerequisites == null || prerequisites.length == 0) return true;

                boolean[][] adj = new boolean[numCourses][numCourses];
                for(int i = 0; i < prerequisites.length; i++){
                    adj[prerequisites[i][0]][prerequisites[i][1]] = true;
                }

                boolean[] visited = new boolean[numCourses];
                boolean[] explored = new boolean[numCourses];
                for(int i = 0; i < numCourses; i++){
                    if(!visited[i] && isCyclic(adj, i, visited, explored)){
                        return false;
                    }
                }

                return true;
        }
        /*
         * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
         * The function performs a DFS traversal of the graph, visiting each vertex and each edge once.
         *
         * Space Complexity: O(V), where V is the number of vertices in the graph.
         * The space complexity is determined by the size of the visited and explored arrays, which can hold at most
         * V vertices in the worst case.
         */

        // Checks if there is a cycle starting from node 'i' in the graph represented by the adjacency matrix 'adj'
            boolean isCyclic(boolean[][] adj, int i, boolean[] visited, boolean[] explored){
                visited[i] = true; // Mark the current node 'i' as visited.
                // Iterate over all adjacent nodes of 'i'
                for(int j = 0; j < adj.length; j++){
                    // Check if there is an edge from 'i' to 'j'
                    if(adj[i][j]){
                        // If 'j' is not visited, recursively check if there is a cycle starting from 'j'
                        if(!visited[j]){
                            if(isCyclic(adj, j, visited, explored)) return true;
                        }
                        // If 'j' is visited but not yet explored, it indicates a cycle
                        else if(!explored[j]) return true;
                    }
                }
                explored[i] = true; // Mark the current node 'i' as explored (all its adjacent nodes have been visited)
                return false; // No cycle found starting from 'i'
            }

    class Road {
        int source;
        int destination;
        int distance;

        Road(int source, int destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }
    }

    /*
     * Time Complexity: O(V + E), where V is the number of cities (vertices) and E is the number of roads (edges) in the graph.
     *   - Constructing the adjacency list: O(E), where E is the number of roads.
     *   - BFS traversal: O(V + E), where V is the number of vertices and E is the number of edges.
     *     Each road (edge) and each city (vertex) is visited at most once during BFS traversal.
     *   Overall time complexity: O(V + E).
     *
     * Space Complexity: O(V + E), where V is the number of cities (vertices) and E is the number of roads (edges) in the graph.
     *   - Space used by the adjacency list: O(E), where E is the number of roads.
     *   - Space used by the visited array: O(V), where V is the number of cities.
     *   - Space used by the queue: O(V + E), where N is the number of vertices and E is the number of edges.
     *   Overall space complexity: O(V + E).
     */

    /*
     * The algorithm uses Breadth-First Search (BFS) traversal.
     * BFS starts at the initial node (in this case, represented by the method `minScore`) and explores all the
     * neighboring nodes at the current depth level before moving on to the nodes at the next depth level.
     * In this context, BFS is used to traverse the graph of cities and roads, finding the minimum distance between them.
     * BFS visits each vertex (city) and each edge (road) once, giving it a time complexity of O(V + E), where V is the
     * number of cities (vertices) and E is the number of roads (edges) in the graph. BFS uses a queue to keep track of
     * nodes to visit next. In this implementation, it's done using a `Queue` of lists of `Road` objects.
     * Additionally, BFS uses space to store visited nodes. In this case, the space complexity is O(V), where V is the
     * number of vertices (cities) in the graph. This space is determined by the size of the `visited` array.
     * Overall space complexity is O(V + E) considering the space used by the adjacency list and the queue.
     */


    // Method to find the minimum score given the number of cities and roads
    public int minScore(int numCities, int[][] roads) {
        List<Road>[] adjacencyList = new ArrayList[numCities + 1];

        // Create adjacency list from the road information
        for (int[] road : roads) {
            Road forwardRoad = new Road(road[0], road[1], road[2]);
            Road backwardRoad = new Road(road[1], road[0], road[2]);

            if (adjacencyList[road[0]] == null) {
                adjacencyList[road[0]] = new ArrayList<>();
            }
            if (adjacencyList[road[1]] == null) {
                adjacencyList[road[1]] = new ArrayList<>();
            }

            adjacencyList[road[0]].add(forwardRoad);
            adjacencyList[road[1]].add(backwardRoad);
        }

        int[] visited = new int[numCities + 1];
        return findMinDistance(numCities, adjacencyList, visited);
    }

    // Method to find the minimum distance
    private int findMinDistance(int numCities, List<Road>[] adjacencyList, int[] visited) {
        int minDistance = Integer.MAX_VALUE;
        Queue<List<Road>> queue = new LinkedList<>();
        queue.offer(adjacencyList[1]);

        while (!queue.isEmpty()) {
            List<Road> currentList = queue.poll();
            if (currentList == null || currentList.size() == 0) {
                continue;
            }
            Road currentRoad = currentList.get(0);
            if (visited[currentRoad.source] == 1) {
                continue;
            }
            visited[currentRoad.source] = 1;
            for (Road adjacentRoad : currentList) {
                int distance = adjacentRoad.distance;
                minDistance = Math.min(minDistance, distance);
                queue.offer(adjacencyList[adjacentRoad.destination]);
            }
        }
        return minDistance;
    }


    public static void main (String[] args){
        ExtraCredit ec = new ExtraCredit();
        int[][] array = {{1,2,9},{2,3,6},{2,4,5},{1,4,7}};
        ec.minScore(4, array);
    }
}
