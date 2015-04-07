package tools;

public class PermutationBiggestBasket extends Permutation {

	public PermutationBiggestBasket(long[] universe_array) {
		super(universe_array);
	}

	public void permute() {
		long app = -1;
		int random_index = -1;
		final int max_index = this.permuted_array.length - 1;
		for (int i = 1; i < this.permuted_array.length; i++) {
			random_index = r.nextInt(max_index) + 1;
			// swap...
			app = this.permuted_array[i];
			this.permuted_array[i] = this.permuted_array[random_index];
			this.permuted_array[random_index] = app;
		}
	}

}