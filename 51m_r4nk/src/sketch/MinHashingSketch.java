package sketch;


import java.util.Arrays;

public abstract class MinHashingSketch {

	public int max_length;
	public long[] sketch;

	
	

	
	public String toString() {
		return this.max_length + " - " + Arrays.toString(this.sketch);
	}
}
