package tools;

import java.util.LinkedHashMap;


public class EvaluateNumberCommonElements {
	
	
	public int getNumCommonElements(long[][] sets) {
		int num_common_elements = 0;
		int i = 0;
		int sum_of_lengths = 0;
		for (long[] set : sets) {
			sum_of_lengths += set.length;
		}
		LinkedHashMap<Long, Integer> element_num_sets = new LinkedHashMap<Long, Integer>(
				sum_of_lengths);
		for (long[] set : sets) {
			for (i = 0; i < set.length; i++) {
				
				element_num_sets.put(set[i],
						(element_num_sets.get(set[i]) == null ? 0
								: element_num_sets.get(set[i])) + 1);
				
				if (element_num_sets.get(set[i]) == sets.length) {
					num_common_elements++;
				}
			}
		}
		return num_common_elements;
	} 
}