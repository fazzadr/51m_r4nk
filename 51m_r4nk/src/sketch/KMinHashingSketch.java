package sketch;

import java.util.Arrays;
import java.util.Set;

import tools.PermutationBiggestBasket;

public class KMinHashingSketch extends MinHashingSketch {

	protected KMinHashingSketch() {
		
	}
	
	public KMinHashingSketch(Set<Long> set, PermutationBiggestBasket permutation,
			int sketch_length) {
		this.max_length = sketch_length;
		this.sketch = new long[(int) Math.min(sketch_length, set.size())];
		int i = 0;
		long[] list_of_elements = new long[set.size()];
		for (Long element : set) {
			list_of_elements[i++] = permutation.permuted_array[element.intValue()];
		}
		Arrays.sort(list_of_elements);
		System.arraycopy(list_of_elements, 0, this.sketch, 0, this.sketch.length);
	}

	public KMinHashingSketch(long[] sketch) {
		this.sketch = sketch;
		this.max_length = sketch.length;
	}
	

}
