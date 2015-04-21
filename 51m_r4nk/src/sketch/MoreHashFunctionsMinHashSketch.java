package sketch;


public class MoreHashFunctionsMinHashSketch extends MinHashingSketch {

	protected MoreHashFunctionsMinHashSketch(long[] sketch, int k, long original_set_size) {
		this.max_length = k;
		this.sketch = sketch;
		this.original_set_size = original_set_size;
	}
	
	

}
