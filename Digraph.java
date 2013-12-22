/**
 * This class contains the data structure of
 * a directed graph
 */
import java.util.*;
import java.io.*;

public class Digraph {
			/** The Vertex class **/
	private class Vertex implements Comparable<Vertex> {
		private int cityNumber; // The city's number
		private String cityCode; // The city's code
		private String cityName; // The city's name
		private int cityPopulation; // The city's population
		private int cityElevation; // The city's elevation
		private int minDistance; // The minimum distance 
		private List<Edge> adjacencies; // The list of neighbors 
		private Vertex previous; // The previous city
		
		/**
		 * The default constructors initializes the fields
		 * to default values
		 */
		public Vertex() {
			cityNumber = 0;
			cityCode = null;
			cityName = null;
			cityPopulation = 0;
			cityElevation = 0;
			minDistance = Integer.MAX_VALUE;
			adjacencies = new ArrayList<Edge>();
			previous = null;
		}
		
		/**
		 * The constructors initializes all the fields by
		 * passing the arguments
		 * @param number The city's number
		 * @param code The city's code
		 * @param name The city's name
		 * @param pop The city's population
		 * @param elev The city's elevation
		 */
		public Vertex(int number, String code, String name, int pop, int elev) {
			cityNumber = number;
			cityCode = code;
			cityName = name;
			cityPopulation = pop;
			cityElevation = elev;
			minDistance = Integer.MAX_VALUE;
			adjacencies = new ArrayList<Edge>();
			previous = null;
		} 
		
		/**
		 * The compareTo method compares the minimum distances of
		 * the two vertices 
		 * @param v The vertex input
		 */
		public int compareTo(Vertex v) {
			return Integer.compare(minDistance, v.minDistance);
		}
		
		/**
		 * The getNeighbor method returns the edge connect
		 * the two vertices
		 * @param v The vertex input
		 * @return the edge If there is one
		 * 		   null If the edge doesn't exist
		 */
		public Edge getNeighbor(Vertex v) {
			for (int i = 0; i < adjacencies.size(); i++) {
				if (adjacencies.get(i).end.cityCode.equals(v.cityCode))
					return adjacencies.get(i);
			}
			return null;
		}
		
		/**
		 * The toString method returns a string reference 
		 * to the vertex object
		 */
		public String toString() {
			String str = cityNumber + "\t" + cityCode + "\t" 
					+ cityName + "\t" + cityPopulation 
					+ "\t" + cityElevation ;
			return str;
		}
	}
		/** End of the Vertex class **/
	
			/** The Edge class **/
	private class Edge {
		private Vertex end; // The vertex of the destination
		private int distance; // The distance to the destination
		
		/**
		 * The default constructor initializes all the fields
		 * to null and 0
		 */
		public Edge() {
			end = null; 
			distance = 0;
		}
		
		/**
		 * The constructor initializes all the fields by passing
		 * the arguments
		 * @param e The verTex input
		 * @param dis The distance input
		 */
		public Edge(Vertex e, int dis) {
			end = e;
			distance = dis;
		}
		
		/**
		 * The toString method returns a string reference to
		 * the Edge object
		 */
		public String toString() {
			String str = end.cityNumber + " " + distance;
			return str;
		}
	}
		/** End of the Edge class **/
	
	private Vertex[] cities; // The array of vertices to hold different cities
	
	/**
	 * The constructor read the data from the input text file
	 * and initializes the data for the vertices and edges
	 * @param citiesFileName The text file of city's information
	 * @param roadsFileName The text file of path's information
	 */
	public Digraph(String citiesFileName, String roadsFileName) {
		String str, code, name;
		int number, pop, elev, startIndex = 0, endIndex = 0, dis = 0;
		
		// Array to hold 20 cities
		cities = new Vertex[20];		
		int i = 0; // The index of the array
		
		try {
			File citiesFile = new File(citiesFileName);
			Scanner inputCities = new Scanner(citiesFile);
			//Read from the city's file
			while (inputCities.hasNext()) {
				str = inputCities.next();
				number = Integer.parseInt(str);
				str = inputCities.next();
				code = str;
				str = inputCities.next();
				name = str;
				// Name has more than one words
				while (!inputCities.hasNextInt()) { 
					str = inputCities.next();
					name += " " + str;
				}
				str = inputCities.next();
				pop = Integer.parseInt(str);
				str = inputCities.next();
				elev = Integer.parseInt(str);
				// Create the Vertex object and put it in the array
				cities[i] = new Vertex (number, code, name, pop, elev);
				i++;
			}
			inputCities.close();
			
			File roadsFile = new File(roadsFileName);
			Scanner inputRoads = new Scanner(roadsFile);
			// Read from the path's file
			while (inputRoads.hasNext()) {
				startIndex = inputRoads.nextInt();
				endIndex = inputRoads.nextInt();
				dis = inputRoads.nextInt();
				// Create the Edge object and put it in the array
				Edge newEdge = new Edge(cities[endIndex - 1], dis);
				cities[startIndex - 1].adjacencies.add(newEdge);
			}
			inputRoads.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * The getCity method returns the vertex object which hold
	 * the city's information associated with the code input
	 * @param code The city's code
	 * @return the vertex object
	 */
	public Vertex getCity(String code) {
		for (int i = 0; i < cities.length; i++) {
			if ((cities[i]).cityCode.equals(code)) {
				return cities[i];
			}
		}
		return null;
	}
	
	/**
	 * The printCity method prints the information of the city
	 * associated with the code input
	 * @param code The city's code
	 */
	public void printCity(String code){
		// City's code doesn't exist
		if (getCity(code) == null)
			System.out.println("Error! Invalid city code");
		else // The city's code exists
			System.out.println(getCity(code));
	}
	
	/**
	 * The insertPath method inserts a new edge between two vertices
	 * @param codeStart The city's code of the starting point
	 * @param codeEnd The city's code of the destination
	 * @param dis The distance between two cities
	 */
	public void insertPath(String codeStart, String codeEnd, int dis) {
		// The codes don't exist
		if (getCity(codeStart) == null || getCity(codeEnd) == null) 
			 System.out.println("City codes don't exist");
		else {
			// The path doesn't exist
			if (getCity(codeStart).getNeighbor(getCity(codeEnd)) == null) {
				Edge newEdge = new Edge(getCity(codeEnd), dis);
				getCity(codeStart).adjacencies.add(newEdge);
				System.out.println("You have inserted a road from " +
					getCity(codeStart).cityName + " to " + 
					getCity(codeEnd).cityName + " with the distance of " + dis);
			}
			else // The path already exists
				System.out.println("The road from " +
						getCity(codeStart).cityName + " to " +
						getCity(codeEnd).cityName + " already exists");
		}
	}
	
	/**
	 * The removePath remove an edge between two vertices
	 * @param codeStart The city's code of the starting point
	 * @param codeEnd The city's code of the destination
	 */
	public void removePath(String codeStart, String codeEnd) {
		// The codes don't exist
		if (getCity(codeStart) == null || getCity(codeEnd) == null) 
			 System.out.println("City codes don't exist");
		else {
			// The path exists
			if (getCity(codeStart).getNeighbor(getCity(codeEnd)) != null) {
				Edge current = getCity(codeStart).getNeighbor(getCity(codeEnd));
				getCity(codeStart).adjacencies.remove(current);
				System.out.println("The road from " +
						getCity(codeStart).cityName + " to " +
						getCity(codeEnd).cityName + " has been deleted");
			}
			else // The path doesn't exist
				System.out.println("The road from " +
						getCity(codeStart).cityName + " to " +
						getCity(codeEnd).cityName + " doesn't exist");
		}
	}
	/**
	 * The computePath computes the path between two cities
	 * using Dijkstra's algorithm
	 * @param source The Vertex object of the destination
	 */
	public void computePath(Vertex source) {
		for (int i = 0; i < cities.length; i++) {
			cities[i].minDistance = Integer.MAX_VALUE;
			cities[i].previous = null;
		}
		
		source.minDistance = 0; // Set the source's minDinstance to 0
		// Create a priority queue(heap)
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source); // Add the source into the queue
		
		while (!vertexQueue.isEmpty()) { // Queue is not empty
			Vertex u = vertexQueue.poll(); // Get the head of the queue
			// Visiting each edge in u
			for (Edge e : u.adjacencies) {
				Vertex v = e.end; // Get the destination
				int d = e.distance; // Get the distance
				int disThroughU = u.minDistance + d;
				if (disThroughU < v.minDistance) {
				    vertexQueue.remove(v);
				    v.minDistance = disThroughU ;
				    v.previous = u;
				    vertexQueue.add(v);
				}
			}
		}
	}
	
	/**
	 * The getShortesPathTo method returns a list of path
	 * which are the shortest ones between two cities
	 * @param target The Vertex object of the destination
	 * @return path The collection of shortest paths 
	 */
	 public List<Vertex> getShortestPathTo(Vertex target) {
	        List<Vertex> path = new ArrayList<Vertex>();
	        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
	            path.add(vertex);
	        Collections.reverse(path);
	        return path;
	  }
	 
	 /**
	  * the printPath method prints the shortest distance and route
	  * between two cities
	  * @param codeStart The city's code of the starting point
	  * @param codeEnd The city's code of the destination
	  */
	 public void printPath(String codeStart, String codeEnd) {
		 if (getCity(codeStart) == null || getCity(codeEnd) == null) 
			 System.out.println("City codes don't exist");
		 else {	 
			 if (codeStart.equals(codeEnd)) 
				 System.out.println("You are already in the city.");
			 else {
				 computePath(getCity(codeStart));
				 List<Vertex> path = getShortestPathTo(getCity(codeEnd));
				 if ((path.size() - 1)== 0)
				     System.out.println("The city cannot be reached");
				 else {
				     System.out.print("The minimum distance between " + 
						      getCity(codeStart).cityName + " and " + 
						      getCity(codeEnd).cityName + " is " + 
						      path.get(path.size() - 1).minDistance +" through the route: ");
				     for (int i = 0; i < path.size(); i++) {
					 if (i != path.size() - 1)
					     System.out.print(path.get(i).cityName + ", ");
					 else
					     System.out.println(path.get(i).cityName + ".");
					 
				     }
				 }	
			 }
		 }
	 }
}
