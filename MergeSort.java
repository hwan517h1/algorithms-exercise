public class MergeSort {

	public static void main(String args[]){
		int[] input = {5, 4, 3, 2, 10, 0, 9, 8, 7, -21, -111, 6, 31, 11, 23, 100, 103, 201, 28, 43, 37, 1, -1};

		sort(input, 1, input.length);

		for (int i: input) {
			System.out.print(i + " ");
		}
	}

	private static void sort(int[] array, int p, int r) {
		int q = (p + r) / 2;

		if (p < r) {
			sort(array, p, q);
			sort(array, q + 1, r);
			merge(array, p, q, r);
		}
	}

	private static void merge(int[] array, int p, int q, int r) {
		int[] tmpArray = new int[r - p + 1];
		int i = p;
		int j = q + 1; // q라고 실수하지 말 것
		int k = 0;

		// while문의 조건에서 =가 없으면 배열의 원소가 1개인 경우를 처리할 수 없음
		while ((i <= q) && (j <= r)) {
			if (array[i - 1] <= array[j - 1]) {
				tmpArray[k] = array[i - 1];
				i++;
				k++;
			} else {
				tmpArray[k] = array[j - 1];
				j++;
				k++;
			}
		}

		// 위의 while문을 그대로 반복함
		while (i <= q) {
			tmpArray[k] = array[i - 1];
			i++;
			k++;
		}

		while (j <= r) {
			tmpArray[k] = array[j - 1];
			j++;
			k++;
		}

		k = 0;
		for (int tmp : tmpArray) {
			array[p - 1 + k] = tmp;
			k++;
		}
	}
}	
