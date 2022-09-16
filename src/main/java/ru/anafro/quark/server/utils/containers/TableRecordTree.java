package ru.anafro.quark.server.utils.containers;

/**
 *  The {@code BTree} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>size</em>, and <em>is-empty</em> methods.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a B-tree. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  The <em>get</em>, <em>put</em>, and <em>contains</em> operations
 *  each make log<sub><em>m</em></sub>(<em>n</em>) probes in the worst case,
 *  where <em>n</em> is the number of key-value pairs
 *  and <em>m</em> is the branching factor.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/62btree">Section 6.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick, Kevin Wayne
 * @since  Quark 1.1
 */
public class TableRecordTree {
//    // max children per B-tree node = M-1
//    // (must be even and greater than 2)
//    private static final int MAX_CHILDREN = 4;
//    private Node root;       // root of the B-tree
//    private int height;      // height of the B-tree
//    private int size;           // number of key-value pairs in the B-tree
//
//    // helper B-tree node data type
//    private static final class Node implements Iterable<TableRecord> {
//        private int childrenCount;                             // number of children
//        private final TableRecord[] children = new TableRecord[MAX_CHILDREN];   // the array of children
//
//        // create a node with k children
//        private Node(int childrenCount) {
//            this.childrenCount = childrenCount;
//        }
//
//        public static Node createTreeRoot() {
//            return new Node(0);
//        }
//
//        @Override
//        public Iterator<TableRecord> iterator() {
//            return Stream.of(children).iterator();
//        }
//
//        public TableRecord childAt(int index) {
//            return children[index];
//        }
//
//        public TableRecord[] getChildren() {
//            return children;
//        }
//
//        public int getChildrenCount() {
//            return childrenCount;
//        }
//    }
//
//    /**
//     * Initializes an empty B-tree.
//     */
//    public TableRecordTree() {
//        root = Node.createTreeRoot();
//    }
//
//    /**
//     * Returns true if this symbol table is empty.
//     * @return {@code true} if this symbol table is empty; {@code false} otherwise
//     */
//    public boolean isEmpty() {
//        return size() == 0;
//    }
//
//    /**
//     * Returns the number of key-value pairs in this symbol table.
//     * @return the number of key-value pairs in this symbol table
//     */
//    public int size() {
//        return size;
//    }
//
//    /**
//     * Returns the height of this B-tree (for debugging).
//     *
//     * @return the height of this B-tree
//     */
//    protected int height() {
//        return height;
//    }
//
//
//    /**
//     * Returns the value associated with the given key.
//     *
//     * @param  key the key
//     * @return the value associated with the given key if the key is in the symbol table
//     *         and {@code null} if the key is not in the symbol table
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public ArrayList<TableRecord> select(TableRecordSelector selector, int skip, int limit) {
//        Objects.requireNonNull(selector);
//
//        return search(root, selector, height, skip, limit, Lists.empty());
//    }
//
//    private ArrayList<TableRecord> search(Node currentNode, TableRecordSelector selector, int currentHeight, int skip, int limit, ArrayList<TableRecord> recordsAlreadySelected) {
//        TableRecord[] children = currentNode.getChildren();
//
//        // external node
//        if(currentHeight == 0) {
//            for(int index = 0; index < currentNode.childrenCount; index++) {
//                if(selector.shouldBeSelected(currentNode.childAt(index))) {
//                    if(skip != 0) {
//                        skip--;
//                    } else if(limit != 0) {
//                        recordsAlreadySelected.add(currentNode.childAt(index));
//                        limit--;
//                    } else {
//                        return recordsAlreadySelected;
//                    }
//                }
//                if (eq(key, children[index].key)) return (Value) children[index].val;
//            }
//        }
//
////        else {
////            for (int index = 0; index < currentNode.childrenCount; index++) {
////                if (index == currentNode.childrenCount - 1 || less(key, children[index+1].key))
////                    return search(children[index].next, key, currentHeight-1);
////            }
////        }
//    }
//
//
//    /**
//     * Inserts the key-value pair into the symbol table, overwriting the old value
//     * with the new value if the key is already in the symbol table.
//     * If the value is {@code null}, this effectively deletes the key from the symbol table.
//     *
//     * @param  key the key
//     * @param  val the value
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public void put(Key key, Value val) {
//        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
//        Node u = insert(root, key, val, height);
//        size++;
//        if (u == null) return;
//
//        // need to split root
//        Node t = new Node(2);
//        t.children[0] = new Entry(root.children[0].key, null, root);
//        t.children[1] = new Entry(u.children[0].key, null, u);
//        root = t;
//        height++;
//    }
//
//    private Node insert(Node h, Key key, Value val, int ht) {
//        int j;
//        Entry t = new Entry(key, val, null);
//
//        // external node
//        if (ht == 0) {
//            for (j = 0; j < h.childrenCount; j++) {
//                if (less(key, h.children[j].key)) break;
//            }
//        }
//
//        // internal node
//        else {
//            for (j = 0; j < h.childrenCount; j++) {
//                if ((j+1 == h.childrenCount) || less(key, h.children[j+1].key)) {
//                    Node u = insert(h.children[j++].next, key, val, ht-1);
//                    if (u == null) return null;
//                    t.key = u.children[0].key;
//                    t.val = null;
//                    t.next = u;
//                    break;
//                }
//            }
//        }
//
//        for (int i = h.childrenCount; i > j; i--)
//            h.children[i] = h.children[i-1];
//        h.children[j] = t;
//        h.childrenCount++;
//        if (h.childrenCount < MAX_CHILDREN) return null;
//        else         return split(h);
//    }
//
//    // split node in half
//    private Node split(Node h) {
//        Node t = new Node(MAX_CHILDREN /2);
//        h.childrenCount = MAX_CHILDREN /2;
//        for (int j = 0; j < MAX_CHILDREN /2; j++)
//            t.children[j] = h.children[MAX_CHILDREN /2+j];
//        return t;
//    }
//
//    /**
//     * Returns a string representation of this B-tree (for debugging).
//     *
//     * @return a string representation of this B-tree.
//     */
//    public String toString() {
//        return toString(root, height, "") + "\n";
//    }
//
//    private String toString(Node h, int ht, String indent) {
//        StringBuilder s = new StringBuilder();
//        Entry[] children = h.children;
//
//        if (ht == 0) {
//            for (int j = 0; j < h.childrenCount; j++) {
//                s.append(indent + children[j].key + " " + children[j].val + "\n");
//            }
//        }
//        else {
//            for (int j = 0; j < h.childrenCount; j++) {
//                if (j > 0) s.append(indent + "(" + children[j].key + ")\n");
//                s.append(toString(children[j].next, ht-1, indent + "     "));
//            }
//        }
//        return s.toString();
//    }
//
//
//    // comparison functions - make Comparable instead of Key to avoid casts
//    private boolean less(Comparable k1, Comparable k2) {
//        return k1.compareTo(k2) < 0;
//    }
//
//    private boolean eq(Comparable k1, Comparable k2) {
//        return k1.compareTo(k2) == 0;
//    }
}