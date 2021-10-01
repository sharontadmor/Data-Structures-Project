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

