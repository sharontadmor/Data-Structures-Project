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

For i = 1,…,5 the following process was repeated:

1. Insert n = 500 * i different integers at random.
2. Iterate the keys in sorted order and call prefixXor(int k) for each key.
3. Repeat step 2 with succPrefixXor(int k).

The measurements started from the biggest tree to the smallest. In addition, calibration was made by running an extra measurement first.

### Results
Table 1 – average time for function call in nano seconds:

![image](https://user-images.githubusercontent.com/73187826/135727779-b33375d7-2780-42fe-bb98-9f16839045c0.png)

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

### Results

### Discussion
