package logic;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class implementing the Dijkstra's algorithm to find the shortest path in a graph
 * @author Robin Algier, Maxime Mathis--Fumel
 *
 */
public class Dijkstra {
	
	/**
	 * Initializes the weight array.
	 * @param n the number of vertexes in the graph
	 * @param i the source of the path
	 * @return the initialized array containing the weight of every edge in the path
	 */
	private int[] distanceMinimale(int n, int i) {
		int[] t = new int[n];
		Arrays.fill(t, Integer.MAX_VALUE); //Integer.MAX_VALUE is used to forbid going from one vertex to the other
		t[i] = 0;
		return t;
	}
	
	/**
	 * Gets the minimum of an array contained in a list.
	 * @param t the array to look into
	 * @param l the ArrayList the minimum should be contained in
	 * @return
	 */
	private int minimum(int[] t, ArrayList<Integer> l) {
		int min = l.get(0);
		for(int element : l) {
			if(t[element] < t[min])
				min = element;
		}
		return min;
	}
	
	/**
	 * Removes a value from a list.
	 * @param l the list to look into
	 * @param k the value to remove
	 */
	private void enleve(ArrayList<Integer> l, int k) {
		for(int indice = 0; indice < l.size(); indice++) {
			if(l.get(indice) == k) {
				l.remove(indice);
			}
		}
	}
	
	/**
	 * Runs the Dijkstra's algorithm.
	 * @param g the adjacency matrix of the graph
	 * @param i the source of the path
	 * @param j the destination of the path
	 * @return the shortest path between the source and the destination
	 */
	public ArrayList<Integer> dijkstra(int[][] g, int i, int j) {
		int n = g[0].length;
		int[] pere = new int[n]; //contains the parent of each node in the path
		pere[i] = i;
		ArrayList<Integer> entraitement = new ArrayList<>(); //contains the vertexes adjacent to the processed ones
		entraitement.add(i);
		ArrayList<Integer> traite = new ArrayList<>(); //contains the processed vertexes
		int[] t = distanceMinimale(n, i);
		
		boolean b = true; //false if we found the shortest path to the destination
		while(!entraitement.isEmpty() && b) { //not all vertexes have been processed
			int k = minimum(t, entraitement); //the vertex with the shortest path from the source yet
			if(k != j) {
				enleve(entraitement, k);
				traite.add(k);
				for(int q=0; q<n; q++) {
					if(g[k][q]!=100 && !traite.contains(q)) { //ignore edges with weight 100 (keep only the adjacent ones)
						if(!entraitement.contains(q))
							entraitement.add(q);
						if(t[k] + g[k][q] < t[q]) {
							t[q] = t[k] + g[k][q];
							pere[q] = k;
						}
					}
				}
			}
			else
				b = false;
		}
		ArrayList<Integer> chemin = new ArrayList<>();
		chemin.add(j);
		int x = j;
		while(x != i) { //fetch the path from source to destination
			x = pere[x]; //follow the path backwards
			chemin.add(0, x);
		}
		return chemin;
	}

}
