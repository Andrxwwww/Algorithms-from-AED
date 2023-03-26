import java.util.Iterator;

public class List<Item> implements Iterable<Item> {

    private Node first;
    private int size;

    public List() {
        first = null;
        size = 0;
    }

    public void add(Item item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;

        if (first == null) {
            first = newNode;
        } else {
            Node current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    public boolean removeFirst(Item item) {
        Node current = first;
        Node prev = null;

        while (current != null) {
            if (current.item.equals(item)) {
                if (prev == null) {
                    first = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    public boolean removeLast(Item item) {
        Node current = first;
        Node prev = null;
        Node last = null;
        boolean found = false;

        while (current != null) {
            if (current.item.equals(item)) {
                last = current;
                found = true;
            }
            prev = current;
            current = current.next;
        }

        if (found) {
            if (last == first) {
                first = first.next;
            } else if (last != null) {
                prev.next = last.next;
            }
            size--;
            return true;
        }

        return false;
    }

    public boolean removeAll(Item item) {
        boolean removed = false;

        while (removeFirst(item)) {
            removed = true;
        }

        return removed;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Item item) {
        Node current = first;

        while (current != null) {
            if (current.item.equals(item)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public int size() {
        return size;
    }



    private class Node {
        Item item;
        Node next;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    //Classe para modficarmos o Iterator
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }




    public static void main(String[] args) {

        List<String> pauta = new List<>();

        pauta.add("Joao");
        pauta.add("Catarina");
        pauta.add("Duarte");
        pauta.add("Andre");
        pauta.add("William");
        pauta.add("Duarte");

        System.out.println("A pauta tem o nome Andre -> " + pauta.contains("Andre") );
        System.out.println( "A pauta tem " + pauta.size() + " alunos" );

        System.out.println("PAUTA:");
        for (String nome : pauta) {
            System.out.println(nome);
        }

        pauta.removeLast("Duarte");

        System.out.println("A pauta tem o nome Andre -> " + pauta.contains("Andre") );
        System.out.println( "A pauta tem " + pauta.size() + " alunos" );

        System.out.println("PAUTA:");
        for (String nome : pauta) {
            System.out.println(nome);
        }
    }
}
/*
A eficiência espacial da solução é proporcional ao número de elementos na lista, ou seja, O(n)( n representa o tamanho da lista.

A eficiência temporal vai depender do nº de elementos na lista e da posição de cada elemento:

     add(Item item) -> O(1), pois é adicionado ao final da lista
    removeFirst(Item item) -> O(n), pois é preciso percorrer a lista até encontrar o primeiro item a ser removido.
    removeLast(Item item) -> O(n), pois é preciso percorrer a lista inteira para encontrar o último item a ser removido.
    removeAll(Item item) -> O(n^2), pois é executada a operação removeFirst em um loop até que todos os itens a serem removidos sejam removidos da lista.
    isEmpty() -> O(1), pois é apenas verificado se o tamanho da lista
contains(Item item) -> O(n), pois é preciso percorrer a lista para encontrar o item
    size() -> O(1), pois a contagem de elementos é mantida como uma variável da classe

    Iterator<Item> iterator() -> O(1), pois apenas é criado um novo objeto ListIterator.

    List() não tem impacto significativo na eficiência espacial, pois não aloca nenhum espaço adicional na memória apesar de mesmo assim inicializa variaveis por isso seria O(1).
 */