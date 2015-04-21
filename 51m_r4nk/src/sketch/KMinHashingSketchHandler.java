package sketch;

import java.util.LinkedHashMap;

public class KMinHashingSketchHandler {
	
	/* 
	 * this method does side effect on the input array:
	 * the first element is swapped with the element that represents the biggest set	 * 
	 */
	public double getIntersectionCardinality(KMinHashingSketch[] sketches) {
		int index_of_sketch_for_superset = 0;
		long max_set_size = sketches[index_of_sketch_for_superset].original_set_size;
		int i = 0;
		for (KMinHashingSketch sk : sketches) {
			if (sk.original_set_size > max_set_size) {
				max_set_size = sk.original_set_size; 
				index_of_sketch_for_superset = i;
			}
			i++;
		} 
		// swap 
		KMinHashingSketch temp = sketches[index_of_sketch_for_superset];
		sketches[index_of_sketch_for_superset] = sketches[0];
		sketches[0] = temp;
		
		return this.getMultipleJaccardSimilarity(sketches) * max_set_size;
	}
	
	
	public double getMultipleJaccardSimilarity(MinHashingSketch[] sketches) {
		// MUST TO BE CHANGED!!!
		int num_common_elements = 0;
		int i = 0;
		Long element = null;
		LinkedHashMap<Long, Integer> element_num_sets = new LinkedHashMap<Long, Integer>(
				sketches.length * sketches[0].max_length);
		for (MinHashingSketch sk : sketches) {
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
		return (double) num_common_elements / (double) sketches[0].max_length;
	}

	public MinHashingSketch mergeSketches(MinHashingSketch[] sketches) {
		// Even though, a matrix have to be scanned by rows :|
		int k = 0;
		int i = 0;
		for (; i < sketches.length; i++)
			if (sketches[i].sketch.length > k)
				k = sketches[i].sketch.length;
		long[] merged_sketch = new long[k];
		long min_in_round;
		short round = 0;
		short[] indexes = new short[sketches.length];
		for (i = 0; i < indexes.length; i++)
			indexes[i] = 0;
		for (round = 0; round < k; round++) {
			// System.out.println("round: " + round);
			min_in_round = Long.MAX_VALUE;
			for (i = 0; i < indexes.length; i++) {
				// System.out.println(" sketches[i][indexes[i]]: " +
				// sketches[i][indexes[i]]);
				if (indexes[i] >= sketches[i].sketch.length)
					continue;
				if (sketches[i].sketch[indexes[i]] < min_in_round)
					min_in_round = sketches[i].sketch[indexes[i]];
			}
			merged_sketch[round] = min_in_round;
			for (i = 0; i < indexes.length; i++) {
				if (indexes[i] >= sketches[i].sketch.length)
					continue;
				if (sketches[i].sketch[indexes[i]] == min_in_round)
					indexes[i]++;
			}
		}
		return new KMinHashingSketch(merged_sketch);
	}
}
