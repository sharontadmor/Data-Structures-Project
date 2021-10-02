# AVL tree

A Data Structures project by Sharon Tadmor,

Created with Java 8.

## Classes
### Class: AVLNode
This inner class represents a node in an AVL tree. All operations take O(1) time complexity.

### Class: AVLTree
This class represents an AVL tree with integer keys and boolean values.
A constructor initiates an empty AVL tree (O(1) time complexity). The user can build an AVL tree by inserting or deleting keys.

The class has methods which keep the tree balanced upon insertion and deletion of keys. This allows for efficient time complexity of O(log ⁡n), n being the number of items in the tree, for the tree operations: search, insert, delete, successor, predecessor, and the method prefixXor(int k).

min() and max()’s low time complexity of O(1) is achieved by maintaining instance fields: min and max, upon insertion and deletion.

## Measurements I
### Introduction
The first set of measurements compares between the methods prefixXor(int k) and succPrefixXor(int k). The latter takes O(n) time complexity, and is an inefficient version of the former, which takes O(log⁡ n) time.

For *i = 1,…,5* the following process was repeated:

1. Insert *n = 500 * i* different integers at random.
2. Iterate the keys in sorted order and call prefixXor(int k) for each key.
3. Repeat step 2 with succPrefixXor(int k).

The measurements started from the biggest tree to the smallest. In addition, calibration was made by running an extra measurement first.

### Results
Table 1 – average time for function call in nano seconds:

![image](https://user-images.githubusercontent.com/73187826/135728159-9cd6524b-5fd6-4c76-89bf-f9c21b53e11b.png)

Graph 1 – average time in nano seconds dependent on the number of calls for prefixXor:

![image](https://user-images.githubusercontent.com/73187826/135727806-740fa166-e622-4923-957d-2bc41e167f38.png)

Graph 2 – average time in nano seconds dependent on the number of calls for succPrefixXor:

![image](https://user-images.githubusercontent.com/73187826/135727815-d8a0444a-35f3-43f7-801b-3b1dcbc7e890.png)

### Discussion
We measured the time for a single call (efficient and inefficient) for each node. The average was taken over all the nodes, i.e., the average operation time as the tree grow.

From the theoretical analysis of time complexity, we expect lower times for smaller inputs. In addition, we expect a logarithmic trend for the efficient method, and a linear trend for the inefficient one. For the first 100 nodes we might expect lower times and more similar trends between the two methods.

As expected, the larger the input (more nodes), the higher the average time. This is because more actions are performed and overall, they take more time.

In the efficient method, the most time-consuming operations are searching for the node with key k and climbing back up to the root. These operations depend on the height of the tree, which is O(log⁡ n) in an AVL tree. We want the average over all the keys because the closer a node is to the root, the faster the operation on it.

In the inefficient method, the most time-consuming operation is searching the successors for each key until k. This operation depends on the number of nodes in the tree.

As for the first 100 keys, In the efficient method the differences in times between the first 100 keys and all the keys are insignificant. This is because a logarithmic trend grows slowly, and the time complexity is more uniform for different size inputs. In the inefficient method, the differences are significant – the average time for all the keys is significantly higher than that of just the first 100 keys.

Furthermore, times are similar for the first 100 keys between the efficient and inefficient methods. Time is linear in the rank of a key, therefore the time is roughly constant for a constant rank of one hundred.

## Measurements II
### Introduction
The second set of measurements compares the performances of an AVL tree with a binary search tree (BST). Measurements were taken on class AVLTree as well as BSTree, which represents a binary search tree and was created by removing the rebalancing mechanism of an AVL tree.

For *i = 1,…,5* the following process was repeated:
1. Insert *n = 1000 * i* items to an AVL tree for 3 cases:

   a. 	Arithmetic progression – the numbers *1,2,3,…,1000 * i*.
   
   b. 	Balanced progression – series of numbers which produce a tree with minimal possible height ⌊log⁡ n⌋, without a balance mechanism.
   
   c.	Random integers.
2. Insert the same items to a binary search tree (not balanced).
3. For each tree record the average time per single insertion. Average is taken over n insertions.

The measurements started from the biggest tree to the smallest. In addition, calibration was made by running an extra measurement first.
### Results
Table 1 - average time for item insertion in nano seconds:

![image](https://user-images.githubusercontent.com/73187826/135728324-f677b3d2-63ff-419c-8d08-7b7ad12eacbf.png)

Graph 1 – average time in nano seconds dependent on the number of calls for AVL tree:

![image](https://user-images.githubusercontent.com/73187826/135728355-48b309c0-1a32-4223-8c76-6cf4b9e29942.png)

Graphs 2,3 – average time in nano seconds dependent on the number of calls for BST:

![image](https://user-images.githubusercontent.com/73187826/135728366-8a1db610-6f92-4a33-ae6c-38b91997be0b.png)
![image](https://user-images.githubusercontent.com/73187826/135728371-922a3a06-496b-42c9-98c5-f96e701c98a4.png)

### Discussion
We measured the time it takes to insert each node. The average is taken over all the nodes, i.e., the average time for insertion as the tree grow.

In general, the results are as expected. The larger the input (more nodes), the higher the average time. This is because more actions are performed and overall, they take more time. More specifically, in BST with balanced and random progressions there are two irregular results, stages 2 and 4, where the results are higher than expected. However, in both those progressions the general trend is as expected.

Also as expected, in both AVL tree and BST, the fastest insertion is achieved from a balanced progression. Such progression creates a tree with the lowest possible height of ⌊log⁡ n⌋ without needing a rebalance mechanism. Thus, insertion can run efficiently in O(log ⁡n) time complexity in both tree types.

In a binary search tree, random progression allows for low time complexity as well. This is since when a binary search tree is built randomly, the expectancy of the height is O(log⁡ n). The worst insertion time in a binary search tree is achieved from an arithmetic progression. This is to be expected because without rebalancing, a tree in the shape of a long line is created. In this case, time complexity is O(n).

As opposed to BST, in an AVL tree the worst insertion time is achieved from random progression. Nevertheless, the differences between the three progressions in an AVL tree are insignificant, except for one irregular value in stage 5 of random progression (1,206). The expected result is seen because a logarithmic trend grows slowly, and the time complexity is uniform for different inputs.

In comparison between the same progression in different type trees, the time for balanced and random is lower in a binary search tree. This is because the rebalance operations are spared. In an AVL tree, for each insertion there are rotations and maintenance of nodes’ fields, which add some time to the actual running time. However, the differences are not very significant, thus we can conclude that rotations don’t harm the running time. Furthermore, they are profitable in the worst case of an arithmetic progression.
