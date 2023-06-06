import java.util.*;

public class ST<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node{
        private final Key key;
        private Value val;
        private Node left, right;
        private int size;
        public Node(Key key, Value val, int size){
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public ST(){ // initialize empty symbol table
    }

    public boolean isEmpty(){

        return size() == 0;
    }

    public int size(){

        return size(root);
    }

    private int size(Node x){
        if(x == null)
            return 0;
        else
            return x.size;
    }

    public boolean contains(Key key){
        if(key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Key key){

        return get(root, key);
    }

    private Value get(Node x, Key key){
        if(key == null || x == null)
            throw new IllegalArgumentException("calls get() with a null key");
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            return get(x.left, key);
        else if(cmp > 0)
            return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left  = put(x.left,  key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }
    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table is empty");
        return min(root).key;
    }
    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table is empty");
        return max(root).key;
    }
    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) return null;
        Node x = select(root, k);
        return x.key;
    }
    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if      (t > k) return select(x.left,  k);
        else if (t < k) return select(x.right, k-t-1);
        else            return x;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else              return size(x.left);
    }

    int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys() {
        if (isEmpty()) {
            return new Queue<Key>();
        }
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null || hi == null) {
            throw new IllegalArgumentException("Keys cannot be null");
        }
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }

        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);

        if (cmpLo < 0) {
            keys(x.left, queue, lo, hi);
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            queue.enqueue(x.key);
        }
        if (cmpHi > 0) {
            keys(x.right, queue, lo, hi);
        }
    }
/*
    public static void main(String[] args) {
        ST<String, String> st = new ST<>();

        // Insert some values into the symbol table
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu", "128.112.128.15");
        st.put("www.yale.edu", "130.132.143.21");
        st.put("www.harvard.edu", "128.103.060.55");
        st.put("www.simpsons.com", "209.052.165.60");


        // Delete the "www.princeton.edu" key from the symbol table

        //st.deleteMax();
        st.delete("www.princeton.edu");

        // Print the values in the symbol table after deletion

        System.out.println(st.get("www.simpsons.com"));

        System.out.println("----------------------------");
        // Print the min and max keys in the symbol table
        System.out.println("Min: " + st.min());
        System.out.println("Max: " + st.max());

        System.out.println("----------------------------");
        // Print the floor and ceiling of the "www.harvard.edu" key
        System.out.println("Floor of harvard: " + st.floor("www.harvard.edu"));
        System.out.println("Ceiling of harvard: " + st.ceiling("www.harvard.edu"));
        System.out.println("rank of harvard: " + st.rank("www.harvard.edu"));

        // Print the number of keys in the symbol table
        System.out.println("Number of keys: " + st.size());
    }

 */
}
