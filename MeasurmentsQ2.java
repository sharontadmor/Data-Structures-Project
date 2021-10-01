import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MeasurmentsQ2 {

	public static long arithmeticSeries(int n, AVLTree tree) {
		long res = 0;
		for (int i = 0; i < n; i++) {
			res += measure(tree, i);
		}
		return res / n;
	}

	public static long arithmeticSeries(int n, BSTree tree) {
		long res = 0;
		for (int i = 0; i < n; i++) {
			res += measure(tree, i);
		}
		return res / n;
	}

	/**
	 * create balanced sequence.
	 */
	public static int[] eqSeq(int n) {
		int[] arr = new int[n];
		AVLTree avl = new AVLTree();
		for (int i = 1; i < n + 1; i++) {
			avl.insert(i, true);
		}
		arr[0] = avl.getRoot().getKey();
		AVLTree.AVLNode[] a = new AVLTree.AVLNode[1];
		a[0] = avl.getRoot();
		int j = 0;
		int depth = 1;
		while (j < n) {
			AVLTree.AVLNode[] b = new AVLTree.AVLNode[(int) Math.pow(2, depth)];
			int h = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] != null && a[i].left.isRealNode()) {
					arr[j + 1] = a[i].left.key;
					b[h] = a[i].left;
					j += 1;
					h += 1;
				}
				if (a[i] != null && a[i].right.isRealNode()) {
					arr[j + 1] = a[i].right.key;
					b[h] = a[i].right;
					j += 1;
					h += 1;
				}
				if (arr[n - 1] != 0) {
					return arr;
				}
			}
			depth += 1;
			a = b;
		}
		return arr;
	}

	public static long balancedSeries(int n, AVLTree tree) {
		long res = 0;
		int[] list = eqSeq(n);
		for (int i = 0; i < n; i++) {
			res += measure(tree, list[i]);
		}
		return res / n;
	}
	
	public static long balancedSeries(int n, BSTree tree) {
		long res = 0;
		int[] list = eqSeq(n);
		for (int i = 0; i < n; i++) {
			res += measure(tree, list[i]);
		}
		return res / n;
	}

	public static long randomSeries(int n, AVLTree tree) {
		long res = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			Integer integer = Integer.valueOf(i);
			list.add(integer);
		}
		Collections.shuffle(list);
		for (int i = 0; i < n; i++) {
			res += measure(tree, list.get(i));
		}
		return res / n;
	}

	public static long randomSeries(int n, BSTree tree) {
		long res = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			Integer integer = Integer.valueOf(i);
			list.add(integer);
		}
		Collections.shuffle(list);
		for (int i = 0; i < n; i++) {
			res += measure(tree, list.get(i));
		}
		return res / n;
	}

	public static long measure(AVLTree tree, int k) {
		long before = System.nanoTime();
		tree.insert(k, Math.random() < 0.5);
		long after = System.nanoTime();
		return after - before;
	}

	public static long measure(BSTree tree, int k) {
		long before = System.nanoTime();
		tree.insert(k, Math.random() < 0.5);
		long after = System.nanoTime();
		return after - before;
	}

	/**
	 * calculates the average time it takes to insert n = i * 1000 keys to: (1) AVL
	 * tree, (2) BS tree, when the keys are ordered in: (a) arithmetic series, (b)
	 * balanced series, (c) random series.
	 */
	public static void q2(int i) {
		System.out.println(">>> i: " + i / 1000);
		// (1) AVL tree
		// (a) arithmetic series:
		AVLTree AVLTree1 = new AVLTree();
		long average = arithmeticSeries(i, AVLTree1);
		System.out.println("AVLTree arithmetic series: " + average);
		// (b) balanced series:
		AVLTree AVLTree2 = new AVLTree();
		average = balancedSeries(i, AVLTree2);
		System.out.println("AVLTree balanced series: " + average);
		// (c) random series:
		AVLTree AVLTree3 = new AVLTree();
		average = randomSeries(i, AVLTree3);
		System.out.println("AVLTree random series: " + average);
		// (2) BS tree
		// (a) arithmetic series:
		BSTree BSTree1 = new BSTree();
		average = arithmeticSeries(i, BSTree1);
		System.out.println("BSTree arithmetic series: " + average);
		// (b) balanced series:
		BSTree BSTree2 = new BSTree();
		average = balancedSeries(i, BSTree2);
		System.out.println("BSTree balanced series: " + average);
		// (c) random series:
		BSTree BSTree3 = new BSTree();
		average = randomSeries(i, BSTree3);
		System.out.println("BSTree random series: " + average);
		return;
	}

	public static void main(String[] args) {
		for (int i = 6; i > 0; i--) {
			q2(i * 1000);
		}
	}

}
