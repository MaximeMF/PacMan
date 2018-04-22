package logic;

import java.util.ArrayList;
import java.util.Arrays;

public class Dijkstra {
	
	private int[] distanceMinimale(int n, int i) {
		int[] t = new int[n];
		Arrays.fill(t, Integer.MAX_VALUE);
		t[i] = 0;
		return t;
	}
	
	private int minimum(int[] t, ArrayList<Integer> l) {
		int min = l.get(0);
		for(int element : l) {
			if(t[element] < t[min])
				min = element;
		}
		return min;
	}
	
	private void enleve(ArrayList<Integer> l, int k) {
		for(int indice = 0; indice < l.size(); indice++) {
			if(l.get(indice) == k) {
				l.remove(indice);
			}
		}
	}
	
	public ArrayList<Integer> dijkstra(int[][] g, int i, int j) {
		int n = g[0].length;
		int[] pere = new int[n];
		pere[i] = i;
		ArrayList<Integer> entraitement = new ArrayList<>();
		entraitement.add(i);
		ArrayList<Integer> traite = new ArrayList<>();
		int[] t = distanceMinimale(n, i);
		
		boolean b = true;
		while(!entraitement.isEmpty() && b) {
			int k = minimum(t, entraitement);
			if(k != j) {
				enleve(entraitement, k);
				traite.add(k);
				for(int q=0; q<n; q++) {
					if(g[k][q]!=100 && !traite.contains(q)) {
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
		while(x != i) {
			x = pere[x];
			chemin.add(0, x);
		}
		return chemin;
	}

}
