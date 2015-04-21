package sketch;

public class MoreHashFunctionsMinHashSketchHandler {
	
	/* 
	 * this method does side effect on the input array:
	 * the first element is swapped with the element that represents the biggest set	 * 
	 */
	public double getIntersectionCardinality(MoreHashFunctionsMinHashSketch[] sketches) {
		int index_of_sketch_for_superset = 0;
		long max_set_size = sketches[index_of_sketch_for_superset].original_set_size;
		int i = 0;
		for (MoreHashFunctionsMinHashSketch sk : sketches) {
			if (sk.original_set_size > max_set_size) {
				max_set_size = sk.original_set_size; 
				index_of_sketch_for_superset = i;
			}
			i++;
		} 
		// swap 
		MoreHashFunctionsMinHashSketch temp = sketches[index_of_sketch_for_superset];
		sketches[index_of_sketch_for_superset] = sketches[0];
		sketches[0] = temp;
		
		return this.getMultipleJaccardSimilarity(sketches) * max_set_size;
	}
	
	
	public double getMultipleJaccardSimilarity(MinHashingSketch[] sketches) {
		int common_values = 0;
		boolean all_equal = true;
		for (int c = 0; c < sketches[0].max_length; c++) {
			all_equal = true;
			for (int r = 1; r < sketches.length; r++) {
				if (sketches[r].sketch[c] != sketches[r - 1].sketch[c]) {
					all_equal = false;
					break;
				}
			}
			if (all_equal) {
				common_values++;
			}
		}
		return (double)common_values/(double)sketches[0].max_length;
	}

	public MinHashingSketch mergeSketches(MinHashingSketch[] sketches) {
		// Even though, a matrix have to be scanned by rows :|
		int k = sketches[0].max_length;
		int d = sketches.length;
		long[] merged_sketch = new long[k]; 
		int i;
		int j;
		long min;
		for (j = 0; j < k; j++) {
			min = sketches[0].sketch[j];
			for (i = 1; i < d; i++)
				if (sketches[i].sketch[j] < min)
					min = sketches[i].sketch[j];
			merged_sketch[j] = min;
		}
		
		/*
		 * 
		 * TODO
		 * -1 perchè essendo merge non so dimensione. va bene????
		 */
		return new MoreHashFunctionsMinHashSketch(merged_sketch, k, -1);
	}

}
