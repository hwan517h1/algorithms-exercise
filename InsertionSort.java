public class InsertionSort {

	public static void main(String[] args) {
		int[] input = {5, 4, 3, 2, 10, 0, 9, 8, 7, -21, -111, 6, 31, 11, 23, 100, 103, 201, 28, 43, 37, 1, -1};

		sort(input, 1, input.length);

		for (int i : input) {
			System.out.print(i + " ");
		}
	}

	private static void sort(int[] array, int p, int r) {
		for(int i = p + 1; i < r + 1; i++) {
			int sorted = i - 1;
			int tmp = array[i - 1];

			while(sorted >= p && tmp < array[sorted - 1]) {
				array[sorted] = array[sorted - 1];
				sorted--;
			}

			array[sorted] = tmp;
		}
	}
}
