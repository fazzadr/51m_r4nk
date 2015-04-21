package sketch;

import java.util.Arrays;
import java.util.Set;

import tools.PermutationBiggestBasket;

public class ContainmentSketch {

	protected static final int default_value_for_m = 2;
	protected int 		m;
	protected long[] 	sketch;
	protected long 		original_set_size;
	 
	
	/*public KMinHashingSketchSetContainment(Set<Long> set, HashMap<Long, Long> permutation, int m) {
		this.m = m;
		long[] list_of_elements = new long[set.size()];
		int i = 0;
		long c_permutation_value = -1;
		for (Long element : set) {
			c_permutation_value = permutation.get(element);
			if (c_permutation_value % this.m == 0) {
				list_of_elements[i++] = c_permutation_value; 
			}
		}
		this.sketch = new long[i];
		System.arraycopy(list_of_elements, 0, this.sketch, 0, i);
		Arrays.sort(this.sketch);
	}*/
	public ContainmentSketch(Set<Long> set, PermutationBiggestBasket permutation, int m, long original_set_size) {
		this.m = m;
		this.original_set_size = original_set_size;
		long[] list_of_elements = new long[set.size()];
		int i = 0;
		long c_permutation_value = -1;
		for (Long element : set) {
			c_permutation_value = permutation.permuted_array[element.intValue()];
			//c_permutation_value = permutation.get(element);
			if (c_permutation_value % this.m == 0) {
				list_of_elements[i++] = c_permutation_value; 
			}
		}
		this.sketch = new long[i];
		System.arraycopy(list_of_elements, 0, this.sketch, 0, i);
		Arrays.sort(this.sketch);
	}
	
	
	public ContainmentSketch(long[] sketch, int m) {
		this.m = m;
		this.sketch = sketch;
	}

	public int getLength() {
		return this.sketch.length;
	}
}
