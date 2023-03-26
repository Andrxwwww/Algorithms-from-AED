import java.util.Iterator;
import java.util.NoSuchElementException;

public class ItemQueue<Item> implements Iterable<Item> {

    private Node<Item> first; // inicio da fila
    private Node<Item> last; // final da fila
    private int q; // n de elementos da fila

    public static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public ItemQueue() { // criar uma fila vazia
        first = null; // 1 elemento do queue
        last = null; // ultimo elemento do queue
        q = 0; // n de elementos no queue
    }

    //verifica se a queue está vazia
    public boolean isEmpty(){
        return first == null;
    }

    //verifica o nmr de elementos na queue
    public int size(){
        return q;
    }

    public void enqueue(Item item) {
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        q++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = first.item;
        first = first.next;
        q--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }
    // retorna um iterador que percorre os itens da fila do menos ao mais recentemente adicionado
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // classe para implementar um iterador para a fila
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        ItemQueue<Integer> q = new ItemQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.println("Size: " + q.size());
        for (int num : q) {
            System.out.println(num);
        }
        System.out.println("Dequeue: " + q.dequeue());
        System.out.println("Dequeue: " + q.dequeue());
        System.out.println("Size: " + q.size());
        q.enqueue(7);
        System.out.println("Size: " + q.size());
        for (int num : q) {
            System.out.println(num);
        }
    }
}
// A eficiência temporal de cada operação da fila é constante e a eficiência espacial da solução é linear no número de elementos na fila