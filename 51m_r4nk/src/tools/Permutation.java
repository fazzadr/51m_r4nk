package tools;

import java.util.Random;

public class Permutation {

	public static void main(String[] args) {
		// only for test ;)
		int n = 10;
		int rep = 100000;
		long[] universe_array = new long[n];
		for(int i=1; i<=n; i++){
			universe_array[i-1]=i;
		}
		
		int[][] pos = new int[n+1][n+1];
		for(int r=0; r<rep;r++){
			Permutation p = new Permutation(universe_array);
			p.permute();
			for(int i=0; i<n+1; i++){
				
				pos[i][(int)p.permuted_array[i]] +=1;
			}
		}
		for(int i=0; i<n+1;i++){
			for(int j=0; j<n+1; j++){
				System.out.print(pos[i][j]+" ");
			}
			System.out.println();
		}

	}

	protected static Random r = new Random();
	public long[] permuted_array;

	public Permutation(long[] universe_array) {
		this.permuted_array = new long[universe_array.length + 1];
		System.arraycopy(universe_array, 0, this.permuted_array, 1,
				universe_array.length);
		this.permute();
		
	}

	/**
	 * Randomly permutes the @permuted_array using as source of randomness @r .
	 * All permutations occur with approximately equal likelihood.
	 * 
	 * This implementation traverses the list backwards, from the last element
	 * up to the second, repeatedly swapping a randomly selected element into
	 * the "current position". Elements are randomly selected from the portion
	 * of the array that runs from the first element to the current position,
	 * inclusive.
	 */
	public void permute() {
		long app = -1;
		int random_index = -1;
		for (int i = this.permuted_array.length - 1; i >= 2; i--) {
			random_index = r.nextInt(i) + 1;
			// swap...
			app = this.permuted_array[i];
			this.permuted_array[i] = this.permuted_array[random_index];
			this.permuted_array[random_index] = app;
		}
	}

}