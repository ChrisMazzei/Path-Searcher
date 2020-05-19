import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ProjectLauncher {

	public static void main(String[] args) {
		/**
		 * Create undirected weighted Graph.
		 * The Graph's vertices are cities.
		 * The Graph's edges are distances from the vertices its connected to.
		 */
		Graph<Integer> graph = new Graph<>();
		
		/**
		 * Initialize the cities.
		 * Without the program taking user inputed cities it is simpler to just label cities as digits (ints) for initialization purposes.
		 */
		for(int i=1; i<=20; i++)
			graph.addVertex(i);
		
		/**
		 * Adding edges to connect the vertices.
		 * addEdge(x, y, z) --> x and y are vertices, z is the weight / edge / distance from x to y or y to x.
		 */
		graph.addEdge(1, 2, 71);
		graph.addEdge(2, 3, 75);
		graph.addEdge(3, 4, 118);
		graph.addEdge(3, 10, 140);
		graph.addEdge(4, 5, 111);
		graph.addEdge(5, 6, 70);
		graph.addEdge(6, 7, 75);
		graph.addEdge(7, 8, 120);
		graph.addEdge(8, 9, 146);
		graph.addEdge(9, 10, 80);
		graph.addEdge(10, 1, 151);
		graph.addEdge(10, 11, 99);
		graph.addEdge(11, 13, 211);
		graph.addEdge(9, 12, 87);
		graph.addEdge(8, 12, 138);
		graph.addEdge(12, 13, 101);
		graph.addEdge(13, 14, 90);
		graph.addEdge(13, 15, 85);
		graph.addEdge(15, 16, 98);
		graph.addEdge(16, 17, 86);
		graph.addEdge(15, 18, 142);
		graph.addEdge(18, 19, 92);
		graph.addEdge(19, 20, 87);
		
		// Outputting test results
		System.out.println(graph.getNeighbors(2));
		System.out.println(Astar(3, 7, heuristic(0,0), graph));
	}
	
	/**
	 * Work backwards through the cameFrom map to reconstruct the path from x to y.
	 * @param cameFrom HashMap that tracks which vertex led to which vertex.
	 * @param current The current vertex.
	 * @param graph The graph.
	 * @param goal The goal vertex.
	 * @return The string containing the path from x to y.
	 */
	private static String reconstructPath(HashMap<Integer, Integer> cameFrom, Integer current, Graph<Integer> graph, Integer goal) {
		StringBuilder path = new StringBuilder();
		
		while(cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			if(current != -1)
				path.insert(0, current + " -> ");
		}
		path.append(goal);
		return path.toString();
	}
	
	/**
	 * Implementing the A* algorithm to efficiently find the shortest path from the start vertex to goal vertex.
	 * @param start The starting vertex.
	 * @param goal The goal vertex.
	 * @param h The heuristic function value (in this case will always be 0 for experimental purposes,
	 *          typically the 'Manhattan distance' would be used).
	 * @param graph The graph.
	 * @return If the path exists return the value of the reconstructPath function, otherwise return null / path not found.
	 */
	private static String Astar(Integer start, Integer goal, int h, Graph<Integer> graph) {
		PriorityQueue<Node> queue = new PriorityQueue<Node>(comparator);
		queue.add(new Node(0, start));
		HashMap<Integer, Integer> cameFrom = new HashMap<>();
		HashMap<Integer, Integer> cost = new HashMap<>();
		cameFrom.put(start, -1);
		cost.put(start, 0);
		
		while(!queue.isEmpty()) {
			Integer current = queue.poll().getNode();
			
			if(current == goal)
				return reconstructPath(cameFrom, current, graph, goal);
			
			Iterable<Integer> it = graph.getNeighbors(current);
			Iterator<Integer> iterator = it.iterator();
			while(iterator.hasNext()) {
				Integer neighbor = iterator.next();
				int newCost = cost.get(current) + graph.getEdge(current, neighbor);
				
				if(!cost.containsKey(neighbor) || newCost < cost.get(neighbor)) {
					cost.put(neighbor, newCost);
					int priority = newCost + h;
					queue.add(new Node(priority, neighbor));
					cameFrom.put(neighbor, current);
				}
			}
		}
		return "path not found...?";
	}
	
	/**
	 * Experimental value of 0 is used, can replace with Manhattan distance / straight line distance from vertex a to vertex b.
	 * @param a Vertex x
	 * @param b Vertex y
	 * @return Straight line distance from vertex x to vertex y
	 */
	private static int heuristic(int a, int b) {
		return 0;
	}
	
	/**
	 * Comparator function used for the PriorityQueue in the Astar function.
	 * Sorts the PriorityQueue based on the value / priority of the first parameter.
	 * EXAMPLE --> queue.add(new Node(0, 5)); stores city 5 in the queue with a priority of 0.
	 * Lower priority is prioritized.
	 * Priority >= 0.
	 * Priority is distance to get to that vertex.
	 */
	public static Comparator<Node> comparator = new Comparator<Node>() {
		@Override
		public int compare(Node n1, Node n2) {
			return (int) (n1.getVal() - n2.getVal());
		}
	};
}
