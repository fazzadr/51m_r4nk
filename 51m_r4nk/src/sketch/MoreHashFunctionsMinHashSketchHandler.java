package sketch;

public class MoreHashFunctionsMinHashSketchHandler {
	
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
		return new MoreHashFunctionsMinHashSketch(merged_sketch, k);
	}

}
