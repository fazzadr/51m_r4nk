package sketch;


public class MoreHashFunctionsMinHashSketch extends MinHashingSketch {

	protected MoreHashFunctionsMinHashSketch(long[] sketch, int k) {
		this.max_length = k;
		this.sketch = sketch;
	}
	
	

}
