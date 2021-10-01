/**
 * public class AVLTree
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * 
 * @author Sharon Tadmor
 */

public class AVLTree {
	final public AVLNode VIRTUAL = new AVLNode();
	public AVLNode root;
	public AVLNode min;
	public AVLNode max;
	public int size;
	final public int EMPTY = 0;
	final public int INVALID = -1;
	final public int ILLEGAL_BF = 2;

	/**
	 * This constructor creates an empty AVLTree.
	 * 
	 * time complexity: O(1)
	 */
	public AVLTree() {
		this.root = VIRTUAL;
		this.min = VIRTUAL;
		this.max = VIRTUAL;
		this.size = EMPTY;
	}

	/**
	 * public boolean empty()
	 * <p>
	 * returns true if and only if the tree is empty
	 * 
	 * time complexity: O(1)
	 */
	public boolean empty() {
		return size() == EMPTY ? true : false;
	}

	/**
	 * public boolean search(int k)
	 * <p>
	 * returns the info of an item with key k if it exists in the tree otherwise,
	 * returns null
	 * 
	 * time complexity: O(log n)
	 */
	public Boolean search(int k) {
		AVLNode node = searchNode(k);
		if (node != null && k == node.getKey()) {
			return node.getValue();
		}
		return null;
	}

	/**
	 * @param k
	 * @return the node with key k if k is in the tree. otherwise, return the node
	 *         that would be k's parent.
	 * 
	 *         time complexity: O(log n)
	 */
	public AVLNode searchNode(int k) {
		AVLNode prev = null;
		AVLNode curr = this.root;
		while (curr.isRealNode()) {
			if (k == curr.getKey()) {
				return curr;
			}
			prev = curr;
			if (k < curr.getKey()) {
				curr = curr.getLeft();
			} else {
				curr = curr.getRight();
			}
		}
		return prev;
	}

	/**
	 * links two nodes as parent and its left or right child.
	 * 
	 * @pre child.isRealNode() == true
	 * @param parent
	 * @param child
	 * 
	 *               time complexity: O(1)
	 */
	private void setEdge(AVLNode parent, AVLNode child) {
		child.setParent(parent);
		if (parent.isRealNode()) {
			if (child.getKey() < parent.getKey()) {
				parent.setLeft(child);
			} else {
				parent.setRight(child);
			}
		}

	}

	/**
	 * public int insert(int k, boolean i)
	 * <p>
	 * inserts an item with key k and info i to the AVL tree. the tree remains valid
	 * (keep its invariants). returns the number of nodes which require rebalancing
	 * operations (i.e. promotions or rotations). This always includes the
	 * newly-created node. returns -1 if an item with key k already exists in the
	 * tree.
	 * 
	 * time complexity: O(log n)
	 */
	public int insert(int k, boolean i) {
		int count = 1; // 1 stands for the node we inserted.
		AVLNode newNode = new AVLNode(k, i);
		if (size() == 0) { // tree is empty
			this.root = newNode;
			this.size++;
			setTreeMin(newNode);
			setTreeMax(newNode);
			return count;
		}
		AVLNode parent = searchNode(k);
		if (k == parent.getKey()) { // k already in tree.
			return INVALID;
		}
		setEdge(parent, newNode);
		if (k < getTreeMin().getKey()) { // new minimum in the tree.
			setTreeMin(newNode);
		} else if (k > getTreeMax().getKey()) { // new max in the tree.
			setTreeMax(newNode);
		}
		this.size++;
		count += rebalanceTree(parent);
		return count;
	}

	/**
	 * public int delete(int k)
	 * <p>
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * remains valid (keep its invariants). returns the number of nodes which
	 * required rebalancing operations (i.e. demotions or rotations). returns -1 if
	 * an item with key k was not found in the tree.
	 * 
	 * time complexity: O(log n)
	 */
	public int delete(int k) {
		if (size() == 0) { // tree is empty
			return INVALID;
		}
		AVLNode node = searchNode(k);
		if (k != node.getKey()) { // k not in tree.
			return INVALID;
		}
		if (node.getKey() == getTreeMin().getKey()) {
			deleteTreeMin(node);
		}
		if (node.getKey() == getTreeMax().getKey()) {
			deleteTreeMax(node);
		}
		AVLNode curr = deleteNode(node);
		this.size--;
		return rebalanceTree(curr);
	}

	/**
	 * sets new minimum to the tree upon minimum deletion.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(log n)
	 */
	private void deleteTreeMin(AVLNode node) {
		AVLNode succ = successor(node);
		if (succ == null) {
			setTreeMin(VIRTUAL);
		} else {
			setTreeMin(succ);
		}
	}

	/**
	 * sets new maximum to the tree upon maximum deletion.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(log n)
	 */
	private void deleteTreeMax(AVLNode node) {
		AVLNode pred = predecessor(node);
		if (pred == null) {
			setTreeMax(VIRTUAL);
		} else {
			setTreeMax(predecessor(node));
		}
	}

	/**
	 * deletes a node from the tree in one of three cases: (1) node is a leaf. (2)
	 * node has one child. (3) node has two children.
	 * 
	 * @param node
	 * @return curr AVL node from which to start rebalancing the tree.
	 * 
	 *         time complexity: O(log n)
	 */
	private AVLNode deleteNode(AVLNode node) {
		AVLNode curr;
		if (!node.getLeft().isRealNode() && !node.getRight().isRealNode()) { // node is a leaf.
			curr = node.getParent();
			deleteLeaf(node);
		} else if (node.getLeft().isRealNode() && node.getRight().isRealNode()) { // node has two children.
			AVLNode succ = successor(node);
			curr = succ.getParent() == node ? successor(node) : succ.getParent();
			deleteByReplacement(node);
		} else { // node has one child.
			curr = node.getLeft().isRealNode() ? node.getLeft() : node.getRight();
			deleteByBypass(node);
		}
		return curr;

	}

	/**
	 * deletes leaf node.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(1)
	 */
	private void deleteLeaf(AVLNode node) {
		if (node.getKey() == getRoot().getKey()) {
			setRoot(VIRTUAL);
			return;
		}
		if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(VIRTUAL);
		} else {
			node.getParent().setRight(VIRTUAL);
		}
		node.setParent(VIRTUAL);
	}

	/**
	 * deletes node with two children, by replacing it with its successor.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(log n)
	 */
	private void deleteByReplacement(AVLNode node) {
		AVLNode succ = successor(node);
		deleteNode(succ);
		if (node.getRight().isRealNode()) {
			setEdge(succ, node.getRight());
		}
		setEdge(succ, node.getLeft());
		if (node.getKey() == getRoot().getKey()) {
			setRoot(succ);
			succ.setParent(VIRTUAL);
		} else {
			setEdge(node.getParent(), succ);
		}
		node.setParent(VIRTUAL);
		node.setLeft(VIRTUAL);
		node.setRight(VIRTUAL);
		succ.maintainFields();
	}

	/**
	 * deletes node with one child, by bypassing it.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(1)
	 */
	private void deleteByBypass(AVLNode node) {
		if (node.getKey() == getRoot().getKey()) {
			if (node.getLeft().isRealNode()) {
				setRoot(node.getLeft());
				node.getLeft().setParent(VIRTUAL);
				node.setLeft(VIRTUAL);
			} else {
				setRoot(node.getRight());
				node.getRight().setParent(VIRTUAL);
				node.setRight(VIRTUAL);
			}
			return;
		}
		if (node.getLeft().isRealNode()) {
			setEdge(node.getParent(), node.getLeft());
			node.setLeft(VIRTUAL);
		} else {
			setEdge(node.getParent(), node.getRight());
			node.setRight(VIRTUAL);
		}
		node.setParent(VIRTUAL);
	}

	/**
	 * rebalances the tree into a legal AVL tree. maintains nodes' fields.
	 * 
	 * @param node
	 * @return number of nodes which require rebalancing operations (i.e. promotions
	 *         or rotations).
	 * 
	 *         time complexity: O(log n)
	 */
	private int rebalanceTree(AVLNode node) {
		int count = 0;
		while (node.isRealNode()) {
			int prevHeight = node.getHeight();
			node.maintainFields();
			boolean heightChanged = prevHeight != node.getHeight();
			boolean legalBF = Math.abs(node.getBalanceFactor()) < ILLEGAL_BF;
			if (heightChanged || !legalBF) {
				count++;
				if (!legalBF) {
					rotate(node);
				}
			}
			node = node.getParent();
		}
		return count;
	}

	/**
	 * rebalances a node whose balance factor is illegal, specifically BF = 2 or -2.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(1)
	 */
	private void rotate(AVLNode node) {
		if (node.getBalanceFactor() == ILLEGAL_BF) {
			if (node.getLeft().getBalanceFactor() == -1) {
				rotateLeft(node.getLeft());
			}
			rotateRight(node);
		} else if (node.getBalanceFactor() == (-1) * ILLEGAL_BF) {
			if (node.getRight().getBalanceFactor() == 1) {
				rotateRight(node.getRight());
			}
			rotateLeft(node);
		}
	}

	/**
	 * performs a right rotation on a node.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(1)
	 */
	private void rotateLeft(AVLNode node) {
		AVLNode x = node.getRight();
		if (!x.isRealNode()) {
			node = node.getLeft();
			x = node.getRight();
		}
		node.setRight(x.getLeft());
		x.setParent(node.getParent());
		if (x.getLeft().isRealNode()) {
			x.getLeft().setParent(node);
		}
		if (!node.getParent().isRealNode()) {
			setRoot(x);
		} else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(x);
		} else {
			node.getParent().setRight(x);
		}
		x.setLeft(node);
		node.setParent(x);
		node.maintainFields();
	}

	/**
	 * performs a left rotation on a node.
	 * 
	 * @param node
	 * 
	 *             time complexity: O(1)
	 */
	private void rotateRight(AVLNode node) {
		AVLNode x = node.getLeft();
		if (!x.isRealNode()) {
			node = node.getRight();
			x = node.getLeft();
		}
		node.setLeft(x.getRight());
		x.setParent(node.getParent());
		if (x.getRight().isRealNode()) {
			x.getRight().setParent(node);
		}
		if (!node.getParent().isRealNode()) {
			setRoot(x);
		} else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(x);
		} else {
			node.getParent().setRight(x);
		}
		x.setRight(node);
		node.setParent(x);
		node.maintainFields();

	}

	/**
	 * public Boolean min()
	 * <p>
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty
	 * 
	 * time complexity: O(1)
	 */
	public Boolean min() {
		return empty() ? null : getTreeMin().getValue();
	}

	/**
	 * @pre empty() == false
	 * @param node
	 * @return AVL node with the smallest key in the given node's subtree.
	 * 
	 *         time complexity: O(log n)
	 */
	public AVLNode findMinimumOfSubtree(AVLNode node) {
		while (node.getLeft().isRealNode()) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * sets the item with the smallest key in the tree.
	 * 
	 * @param min
	 * 
	 *            time complexity: O(1)
	 */
	private void setTreeMin(AVLNode min) {
		this.min = min;
	}

	/**
	 * @return item with the smallest key in the tree.
	 * 
	 *         time complexity: O(1)
	 */
	public AVLNode getTreeMin() {
		return this.min;
	}

	/**
	 * public Boolean max()
	 * <p>
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty
	 * 
	 * time complexity: O(1)
	 */
	public Boolean max() {
		return empty() ? null : getTreeMax().getValue();
	}

	/**
	 * @param node
	 * @return AVL node with the largest key in the given node's subtree.
	 * 
	 *         time complexity: O(log n)
	 */
	public AVLNode findMaximumOfSubtree(AVLNode node) {
		while (node.getRight().isRealNode()) {
			node = node.getRight();
		}
		return node;
	}

	/**
	 * sets the item with the largest key in the tree.
	 * 
	 * @param max
	 * 
	 *            time complexity: O(1)
	 */
	private void setTreeMax(AVLNode max) {
		this.max = max;
	}

	/**
	 * @return item with the largest key in the tree.
	 * 
	 *         time complexity: O(1)
	 */
	public AVLNode getTreeMax() {
		return this.max;
	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty.
	 * 
	 * time complexity: O(n)
	 */
	public int[] keysToArray() {
		int[] arr = new int[size()];
		if (this.empty()) {
			return arr;
		}
		getSortedKeysArray(getRoot(), arr, 0);
		return arr;
	}

	/**
	 * Traverses the tree in-order, to create a sorted array which contains all keys
	 * in the tree.
	 * 
	 * time complexity: O(n)
	 */
	private int getSortedKeysArray(AVLNode node, int[] arr, int idx) {
		if (node.getLeft().isRealNode()) {
			idx = getSortedKeysArray(node.getLeft(), arr, idx);
		}
		arr[idx++] = node.getKey();
		if (node.getRight().isRealNode()) {
			idx = getSortedKeysArray(node.getRight(), arr, idx);
		}
		return idx;
	}

	/**
	 * public boolean[] infoToArray()
	 * <p>
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 * 
	 * time complexity: O(n)
	 */
	public boolean[] infoToArray() {
		boolean[] arr = new boolean[size()];
		if (this.empty()) {
			return arr;
		}
		getSortedValuesArray(getRoot(), arr, 0);
		return arr;
	}

	/**
	 * Traverses the tree in-order, to create an array which contains the info of
	 * all the nodes in the tree, according to sorted order of keys.
	 * 
	 * time complexity: O(n)
	 */
	private int getSortedValuesArray(AVLNode node, boolean[] arr, int idx) {
		if (node.getLeft().isRealNode()) {
			idx = getSortedValuesArray(node.getLeft(), arr, idx);
		}
		arr[idx++] = node.getValue();
		if (node.getRight().isRealNode()) {
			idx = getSortedValuesArray(node.getRight(), arr, idx);
		}
		return idx;

	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree.
	 * 
	 * time complexity: O(1)
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or virtual node if the tree is empty.
	 * 
	 * time complexity: O(1)
	 */
	public AVLNode getRoot() {
		return this.root;
	}

	/**
	 * public void setRoot(AVLNode root)
	 * <p>
	 * sets the root of the tree.
	 * <p>
	 * 
	 * @param root
	 * 
	 *             time complexity: O(1)
	 */
	public void setRoot(AVLNode root) {
		this.root = root;
	}

	/**
	 * public AVLNode successor
	 *
	 * given a node 'node' in the tree, return the successor of 'node' in the tree
	 * (or null if successor doesn't exist)
	 *
	 * @param node - the node whose successor should be returned
	 * @return the successor of 'node' if exists, null otherwise
	 * 
	 *         time complexity: O(log n)
	 */
	public AVLNode successor(AVLNode node) {
		if (node.getRight().isRealNode()) {
			return findMinimumOfSubtree(node.getRight());
		}
		AVLNode succ = node.getParent();
		while (succ.isRealNode() && node == succ.getRight()) {
			node = succ;
			succ = node.getParent();
		}
		return succ.isRealNode() ? succ : null;
	}

	/**
	 * public AVLNode predecessor
	 *
	 * given a node 'node' in the tree, return the predecessor of 'node' in the tree
	 * (or null if predecessor doesn't exist)
	 *
	 * @param node - the node whose predecessor should be returned
	 * @return the predecessor of 'node' if exists, null otherwise
	 * 
	 *         time complexity: O(log n)
	 */
	public AVLNode predecessor(AVLNode node) {
		if (node.getLeft().isRealNode()) {
			return findMaximumOfSubtree(node.getLeft());
		}
		AVLNode pred = node.getParent();
		while (pred.isRealNode() && node == pred.getLeft()) {
			node = pred;
			pred = node.getParent();
		}
		return pred.isRealNode() ? pred : null;
	}

	/**
	 * public boolean prefixXor(int k)
	 *
	 * Given an argument k which is a key in the tree, calculate the xor of the
	 * values of nodes whose keys are smaller or equal to k.
	 *
	 * precondition: this.search(k) != null
	 *
	 * time complexity: O(log n)
	 */
	public boolean prefixXor(int k) {
		int totalCount = 0;
		AVLNode curr = searchNode(k);
		AVLNode prev = null;
		while (curr.isRealNode()) {
			if (k == curr.getKey() || prev == curr.getRight()) {
				if (curr.getValue()) {
					totalCount++;
				}
				totalCount += curr.getLeft().getTrueCount();
			}
			prev = curr;
			curr = prev.getParent();
		}
		if (totalCount % 2 == 0) {
			return false;
		}
		return true;
	}

	/**
	 * public boolean succPrefixXor(int k)
	 *
	 * This function is identical to prefixXor(int k) in terms of input/output.
	 * However, the implementation of succPrefixXor is the following: starting from
	 * the minimum-key node, iteratively call successor until you reach the node of
	 * key k. Return the xor of all visited nodes.
	 *
	 * precondition: this.search(k) != null
	 * 
	 * time complexity: O(n)
	 */
	public boolean succPrefixXor(int k) {
		int totalCount = 0;
		AVLNode curr = getTreeMin();
		while (curr != null && curr.getKey() <= k) {
			totalCount += curr.getValue() ? 1 : 0;
			curr = successor(curr);
		}
		if (totalCount % 2 == 0) {
			return false;
		}
		return true;
	}

	/**
	 * public class AVLNode
	 * <p>
	 * This class represents a node in the AVL tree.
	 * <p>
	 */
	public class AVLNode {
		public int key;
		public boolean value;
		public int height;
		public int balanceFactor;
		public int trueCount;
		public boolean isRealNode;
		public AVLNode parent;
		public AVLNode left;
		public AVLNode right;
		final public int LEAF_DATA = 0;

		/**
		 * this constructor initiates a real node with key and value. node is initiated
		 * with virtual parent and children.
		 * 
		 * @param key
		 * @param value
		 * @param parent
		 * 
		 *               time complexity: O(1)
		 */
		public AVLNode(int key, boolean value) {
			this.key = key;
			this.value = value;
			this.parent = VIRTUAL;
			this.left = VIRTUAL;
			this.right = VIRTUAL;
			this.height = LEAF_DATA;
			this.balanceFactor = LEAF_DATA;
			this.trueCount = value == true ? 1 : 0;
			this.isRealNode = true;

		}

		/**
		 * this constructor initiates a virtual node.
		 * 
		 * time complexity: O(1)
		 */
		public AVLNode() {
			this.key = INVALID;
			this.value = false;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.height = INVALID;
			this.balanceFactor = INVALID;
			this.trueCount = LEAF_DATA;
			this.isRealNode = false;
		}

		/**
		 * @return node's key.
		 * 
		 *         time complexity: O(1)
		 */
		public int getKey() {
			return this.key;
		}

		/**
		 * @return node's value (info). for virtual node return null.
		 * 
		 *         time complexity: O(1)
		 */
		public Boolean getValue() {
			return this.isRealNode() ? this.value : null;
		}

		/**
		 * sets left child.
		 * 
		 * @param node
		 * 
		 *             time complexity: O(1)
		 */
		public void setLeft(AVLNode node) {
			this.left = node;
		}

		/**
		 * @return left child. if called for virtual node, return value is ignored.
		 * 
		 *         time complexity: O(1)
		 */
		public AVLNode getLeft() {
			return this.left;
		}

		/**
		 * sets right child.
		 * 
		 * @param node
		 * 
		 *             time complexity: O(1)
		 */
		public void setRight(AVLNode node) {
			this.right = node;
		}

		/**
		 * @return right child. if called for virtual node, return value is ignored.
		 * 
		 *         time complexity: O(1)
		 */
		public AVLNode getRight() {
			return this.right;
		}

		/**
		 * sets parent.
		 * 
		 * @param node
		 * 
		 *             time complexity: O(1)
		 */
		public void setParent(AVLNode node) {
			this.parent = node;
		}

		/**
		 * @return parent if there is one, virtual otherwise
		 * 
		 *         time complexity: O(1).
		 */
		public AVLNode getParent() {
			return this.parent;
		}

		/**
		 * @return true if this is a non-virtual AVL node, false otherwise.
		 * 
		 *         time complexity: O(1)
		 */
		public boolean isRealNode() {
			return this.isRealNode;
		}

		/**
		 * sets the height of the node.
		 * 
		 * @param height
		 * 
		 *               time complexity: O(1)
		 */
		public void setHeight() {
			this.height = 1 + Math.max(getLeft().getHeight(), getRight().getHeight());
		}

		/**
		 * @return the height of the node
		 * 
		 *         time complexity: O(1)
		 */
		public int getHeight() {
			return this.height;
		}

		/**
		 * sets the balance factor (BF) of the node.
		 * 
		 * @param balanceFactor
		 * 
		 *                      time complexity: O(1)
		 */
		public void setBalanceFactor() {
			this.balanceFactor = getLeft().getHeight() - getRight().getHeight();
		}

		/**
		 * @return the balance factor (BF) of the node
		 * 
		 *         time complexity: O(1)
		 */
		public int getBalanceFactor() {
			return this.balanceFactor;
		}

		/**
		 * sets the number of nodes with value true in this node's subtree.
		 * 
		 * @param trueCount
		 * 
		 *                  time complexity: O(1)
		 */
		public void setTrueCount() {
			this.trueCount = getLeft().getTrueCount() + getRight().getTrueCount() + (getValue() == true ? 1 : 0);
		}

		/**
		 * @return the number of nodes with value true in this node's subtree.
		 * 
		 *         time complexity: O(1)
		 */
		public int getTrueCount() {
			return this.trueCount;
		}

		/**
		 * maintains this node's fields.
		 * 
		 * time complexity: O(1)
		 */
		public void maintainFields() {
			setHeight();
			setBalanceFactor();
			setTrueCount();

		}

	}

}
