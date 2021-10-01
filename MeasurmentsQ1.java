import java.util.ArrayList;
import java.util.Collections;

public class MeasurmentsQ1 {

	/**
	 * inserts n nodes with unique keys to a given AVL tree.
	 */
	public static void insertUniqueRandomNumbers(int n, AVLTree tree) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			Integer integer = Integer.valueOf(i);
			list.add(integer);
		}
		Collections.shuffle(list);
		for (int i = 0; i < n; i++) {
			tree.insert(list.get(i), Math.random() < 0.5);
		}

	}

	/**
	 * for a given key k in a given tree, call method prefixXor(k) or
	 * succPrefixXor(k) and return the time it takes.
	 */
	public static long measure(AVLTree tree, int k) {
		long before = System.nanoTime();
		tree.prefixXor(k);
//		tree.succPrefixXor(k);
		long after = System.nanoTime();
		return after - before;
	}

	/**
	 * calculates the average time it takes to call method prefixXor(k). returns
	 * list with two items: (1) minimalAv - average over the 100 minimal keys, (2)
	 * totalAv - average over i keys.
	 */
	public static ArrayList<Long> q1(int i) {
		long minimalAv = 0;
		long totalAv = 0;
		ArrayList<Long> list = new ArrayList<Long>();
		// (1) insert n = i * 500 random unique integers into AVL tree:
		AVLTree tree = new AVLTree();
		System.out.println(">>> i: " + i / 500);
		insertUniqueRandomNumbers(i, tree);
		// (2) iterate the keys in sorted order, measure each key:
		for (int k = 0; k < 100; k++) {
			long res = measure(tree, k);
			minimalAv += res;
		}
		list.add(minimalAv / 100);
		totalAv += minimalAv;
		for (int k = 100; k < i; k++) {
			long res = measure(tree, k);
			totalAv += res;
		}
		list.add(totalAv / i);
		return list;
	}

	public static void main(String[] args) {
		for (int i = 6; i > 0; i--) {
			System.out.println(q1(i * 500));
		}

	}

}
