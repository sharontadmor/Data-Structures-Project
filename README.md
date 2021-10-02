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

| | succPrefixXor | prefixXor |
|---|---|---|
| | first 100 calls | all calls | first 100 calls | all calls |
|1|641|3279|273|284|


### Discussion

## Measurements II
### Introduction

### Results

### Discussion
