package sketch;

import java.util.LinkedHashMap;

public class ContainmentSketchHandler {
	
	/* 
	 * this method does side effect on the input array:
	 * the first element is swapped with the element that represents the smaller set	 * 
	 */
	public double getIntersectionCardinality(ContainmentSketch[] sketches) {
		int index_of_sketch_for_smaller_original_set = 0;
		long min_set_size = sketches[index_of_sketch_for_smaller_original_set].original_set_size;
		int i = 0;
		for (ContainmentSketch sk : sketches) {
			if (sk.original_set_size < min_set_size) {
				min_set_size = sk.original_set_size; 
				index_of_sketch_for_smaller_original_set = i;
			}
			i++;
		} 
		// swap 
		ContainmentSketch temp = sketches[index_of_sketch_for_smaller_original_set];
		sketches[index_of_sketch_for_smaller_original_set] = sketches[0];
		sketches[0] = temp;
		
		return this.getMultipleContainmentSimilarity(sketches) * min_set_size;
	}
	
	public double getMultipleContainmentSimilarity(
			ContainmentSketch[] sketches) {
		// MUST TO BE CHANGED!!!
		int num_common_elements = 0;
		int i = 0;
		Long element = null;
		int sum_of_lengths = 0;
		for (ContainmentSketch sk : sketches) {
			sum_of_lengths += sk.getLength();
		}
		LinkedHashMap<Long, Integer> element_num_sets = new LinkedHashMap<Long, Integer>(
				sum_of_lengths);
		for (ContainmentSketch sk : sketches) {
			for (i = 0; i < sk.sketch.length; i++) {
				element = sk.sketch[i];
				element_num_sets.put(element,
						(element_num_sets.get(element) == null ? 0
								: element_num_sets.get(element)) + 1);
				if (element_num_sets.get(element) == sketches.length) {
					num_common_elements++;
				}
			}
		}
		return (double) num_common_elements / (double) sketches[0].getLength();
	}
	
	public  ContainmentSketch mergeSketches(
			MinHashingSketch[] sketches) {
		// This is interesting for "OR" queries...
		return null;
	}
}
