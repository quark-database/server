package ru.anafro.quark.server.utils.containers;

import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BTree<K extends Comparable<K>, V> implements Iterable<V> {
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private static final int CHILDREN_LIMIT = 4;

    private Node root;       // root of the B-tree
    private int height;      // height of the B-tree
    private int size;           // number of key-value pairs in the B-tree

    // helper B-tree node data type
    public static final class Node {
        public int childrenCount;                             // number of children
        public Entry[] children = new Entry[CHILDREN_LIMIT];   // the array of children

        private Node(int childrenCount) {
            this.childrenCount = childrenCount;
        }

        public Entry[] getChildren() {
            return children;
        }

        public int getChildrenCount() {
            return childrenCount;
        }

        public void traverse(Consumer consumer) {
            for (int i = 0; i < childrenCount; i++) {
                var child = children[i];
                // If this is not leaf, then before printing key[i],
                // traverse the subtree rooted with child C[i].
                if(child != null) {
                    if(child.getNext() != null) {
                        child.getNext().traverse(consumer);
                    }

                    if(child.getValue() != null) {
                        consumer.accept(child.getValue());
                    }
                }
            }
        }
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    public static class Entry {
        public Comparable key;
        public Object value;
        public Node next;     // helper field to iterate over array entries
        public Entry(Comparable key, Object value, Node next) {
            this.key  = key;
            this.value = value;
            this.next = next;
        }

        public Comparable getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }
    }

    /**
     * Initializes an empty B-tree.
     */
    public BTree() {
        root = new Node(0);
    }

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Returns the height of this B-tree (for debugging).
     *
     * @return the height of this B-tree
     */
    public int height() {
        return height;
    }


    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        return search(root, key, height);
    }


    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param  key the key
     * @param  value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V value) {
        if(key == null) {
            throw new IllegalArgumentException("argument key to put() is null");
        }

        Node u = insert(root, key, value, height);
        size += 1;

        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }
    public void remove(K key) {
        var entry = entrySearch(root, key, height);

        if(entry != null) {
            entry.value = null;
        }
    }


    private Entry entrySearch(Node x, K key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.childrenCount; j++) {
                if (eq(key, children[j].key)) return children[j];
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.childrenCount; j++) {
                if (j + 1 == x.childrenCount || less(key, children[j+1].key))
                    return entrySearch(children[j].next, key, ht-1);
            }
        }
        return null;
    }

    private V search(Node x, K key, int ht) {
        var entry = entrySearch(x, key, ht);
        return entry == null ? null : (V) entry.value;
    }


    private Node insert(Node target, K key, V val, int height) {
        int index;
        Entry entry = new Entry(key, val, null);

        if (height == 0) {  // external node
            for (index = 0; index < target.childrenCount; index++) {
                if (less(key, target.children[index].key)) {
                    break;
                }
            }
        } else {  // internal node
            for (index = 0; index < target.childrenCount; index++) {
                if (index + 1 == target.childrenCount || less(key, target.children[index + 1].key)) {
                    Node inserted = insert(target.children[index++].next, key, val, height - 1);

                    if (inserted == null) {
                        return null;
                    }

                    entry.key = inserted.children[0].key;
                    entry.value = null;
                    entry.next = inserted;

                    break;
                }
            }
        }

        for (int i = target.childrenCount; i > index; i--) {
            target.children[i] = target.children[i - 1];
        }

        target.children[index] = entry;
        target.childrenCount++;

        if (target.childrenCount < CHILDREN_LIMIT) {
            return null;
        } else {
            return split(target);
        }
    }

    // split node in half
    private Node split(Node target) {
        Node node = new Node(CHILDREN_LIMIT / 2);
        target.childrenCount = CHILDREN_LIMIT / 2;

        System.arraycopy(target.children, 2, node.children, 0, CHILDREN_LIMIT / 2);

        return node;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.
     */
    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int height, String indent) {
        var buffer = new TextBuffer();
        Entry[] children = h.children;

        if(height == 0) {
            for(int j = 0; j < h.childrenCount; j++) {
                buffer.append(indent, children[j].key, " ", children[j].value, "\n");
            }
        } else {
            for (int i = 0; i < h.childrenCount; i++) {
                if(i > 0) {
                    buffer.append(indent + "(" + children[i].key + ")\n");
                }

                buffer.append(toString(children[i].next, height-1, indent + "     "));
            }
        }

        return buffer.toString();
    }

    public void traverse(Consumer<V> consumer) {
        if(root != null) {
            root.traverse(consumer);
        }
    }

    public <A> A accumulate(A accumulator, BiConsumer<V, A> accumulationAction) {
        traverse(value -> accumulationAction.accept(value, accumulator));
        return accumulator;
    }

    public ArrayList<V> toList() {
        return accumulate(Lists.empty(), (value, list) -> list.add(value));
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public V next() {
                return null;
            }
        };
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

    public Node getRoot() {
        return root;
    }
}
