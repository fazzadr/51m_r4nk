package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author ikki
 *
 */
public class EvaluateNumberCommonElementsFast extends EvaluateNumberCommonElements {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long t0 = -1;
		long t1 = -1;
		
		Random r = new Random();
		double prob_to_add = 0.6;
		int max_element = 10000;
		int num_sets = 12;
		
		
		ArrayList<ArrayList<Long>> sets_lists = new ArrayList<ArrayList<Long>>();
		for (int set_index = 0; set_index < num_sets; set_index++) {
			sets_lists.add(new ArrayList<Long>());
		}
		
		
		for (int e = 1; e <= max_element; e++) {
			for (int set_index = 0; set_index < num_sets; set_index++) {
				if (r.nextDouble() <= prob_to_add) {
					sets_lists.get(set_index).add((long)e);
				}
			}
		}
		
		long[][] sets = new long[num_sets][];
		for (int set_index = 0; set_index < num_sets; set_index++) {
			long[] temp_array = new long[sets_lists.get(set_index).size()];
			for (int i = 0; i < sets_lists.get(set_index).size(); i++) {
				temp_array[i] = sets_lists.get(set_index).get(i);
			}
			sets[set_index] = temp_array;
		}
		
		
		
		for (long[] c_set : sets) {
			System.out.println(Arrays.toString(c_set));
			System.out.println("length= " + c_set.length);
		}
		System.out.println();
		System.out.println();
		
		EvaluateNumberCommonElements ence = null;
		
		ence = new EvaluateNumberCommonElements();
		
		
		int num_samples = 10;
		int nce_1 = 0;
		t0 = System.currentTimeMillis();
		for (int t = 0; t < num_samples; t++) {
			nce_1 = ence.getNumCommonElements(sets);
		}
		t1 = System.currentTimeMillis();
		System.out.println(" " + ence.getClass());
		System.out.println("  nce_1= " + nce_1);
		System.out.println("  required_time= " + (double)(t1-t0)/(double)num_samples + "msec");
		
		ence = new EvaluateNumberCommonElementsFast();
		int nce_2 = 0;
		t0 = System.currentTimeMillis();
		for (int t = 0; t < num_samples; t++) {
			nce_2 = ence.getNumCommonElements(sets);
		}
		t1 = System.currentTimeMillis();
		System.out.println(" " + ence.getClass());
		System.out.println("  nce_2= " + nce_2);
		System.out.println("  required_time= " + (double)(t1-t0)/(double)num_samples + "msec");
		
	}
	
	
	
	/**
	 * 
	 */
	public int getNumCommonElements(long[][] sets) {
		int num_common_elements = 0;
		int[] i_ = new int[sets.length];
		Arrays.fill(i_, 0);
		int indx = 0;
		boolean hit = false;
		int k = 1;
		int k_minus_1 = 0;
		
		
		while (i_[k_minus_1] < sets[k_minus_1].length) {
			
			while (i_[k] < sets[k].length 
					&& 
					sets[k][i_[k]] < sets[k_minus_1][i_[k_minus_1]]) {
				// To align the pointers.
				i_[k]++;
			}
			if (i_[k] >= sets[k].length) {
				// Mandatory check, otherwise in the next 'for' loop
				// we could have an "out of bound exception" ;) 
				return num_common_elements;
			}
			
			hit = true;
			// It checks if all pointers point to the same value.
			for (indx = 1; indx < i_.length; indx++) {
				if (sets[indx][i_[indx]] != sets[indx - 1][i_[indx - 1]]) {
					hit = false;
					break;
				}
			}
			
			if (hit) {
				num_common_elements++;
				// It shifts all pointers one position to the right.
				for (indx = 0; indx < i_.length; indx++) {
					i_[indx]++;
					if (i_[indx] >= sets[indx].length) {
						// At least one list has terminated: we can return.
						return num_common_elements;
					}
				}
			} else {
				// k <- k+1 mod n
				k_minus_1 = k;
				k++;
				if (k == i_.length) {
					k = 0;
				}
			}
			
		}
		
		return num_common_elements;
	}

}