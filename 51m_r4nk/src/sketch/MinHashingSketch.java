package sketch;


import java.util.Arrays;

public abstract class MinHashingSketch {

	public int max_length;
	public long[] sketch;
	protected long 		original_set_size;
	
	

	
	public String toString() {
		return this.max_length + " - " + Arrays.toString(this.sketch);
	}
}
