package sketch;

import java.util.Set;

import tools.PermutationBiggestBasket;

public class PermutedMinMaxHashSketch extends PermutedMinHashSketch {

	public PermutedMinMaxHashSketch(long[] sketch) {
		super(sketch);
	}
	
	
	public static long[] getMinMaxHashValue(Set<Long> set,
			PermutationBiggestBasket permutation) {
		long[] min_max_hash_elements = new long[2];
		Long min_hash_element = -1L;
		Long max_hash_element = Long.MAX_VALUE;
		Long min_position = Long.MAX_VALUE;
		Long max_position = Long.MIN_VALUE;
		Long position = Long.MAX_VALUE;
		//System.out.println(set.size()+" "+permutation.permuted_array.length);
		for (Long element : set) {
			//if(element.intValue()>permutation.permuted_array.length)
				//System.out.println(element.intValue()+" "+permutation.permuted_array.length);
			position = permutation.permuted_array[element.intValue()];
			if (position < min_position) {
				min_hash_element = element;
				min_position = position;
			}
			if (position > max_position) {
				max_hash_element = element;
				max_position = position;
			}
		}
		min_max_hash_elements[0] = min_hash_element;
		min_max_hash_elements[1] = max_hash_element;
		return min_max_hash_elements;
	}

}
