package sketch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import tools.PermutationBiggestBasket;

public class PermutedMinHashSketch extends MoreHashFunctionsMinHashSketch {

	protected static Random r = new Random();

	public PermutedMinHashSketch(long[] sketch, long original_set_size) {
		super(sketch, sketch.length, original_set_size);
	}

	public static HashMap<Long, Long> createAPermutationOfUniverseSetOfElements(
			Set<Long> universe_set) {
		HashMap<Long, Long> permutation = new HashMap<Long, Long>();
		ArrayList<Long> universe_array = new ArrayList<Long>(universe_set);
		Collections.shuffle(universe_array, PermutedMinHashSketch.r);
		long i = 1;
		for (Long element : universe_array) {
			permutation.put(element, i++);
		}
		return permutation;
	}

	public static long getMinHashValue(Set<Long> set,
			PermutationBiggestBasket permutation) {
		Long min_hash_element = -1L;
		Long min_position = Long.MAX_VALUE;
		Long position = Long.MAX_VALUE;
		for (Long element : set) {
			position = permutation.permuted_array[element.intValue()];
			if (position < min_position) {
				min_hash_element = element;
				min_position = position;
			}
		}
		return min_hash_element;
	}

}
