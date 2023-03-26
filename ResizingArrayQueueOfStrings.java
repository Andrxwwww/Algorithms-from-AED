public class ResizingArrayQueueOfStrings {

    private String[] array;
    private int top;
    private int tail;
    private int q;

    public ResizingArrayQueueOfStrings() {
        this.array = new String[10]; // queue com dimensao 10
        this.top = 0; // 1 elemento do queue
        this.tail = 0; // ultimo elemento do queue
        this.q = 0; // n de elementos no queue
    }

    //verifica se a queue está vazia
    public boolean isEmpty(){
        return q == 0;
    }

    //verifica o nmr de elementos na queue
    public int size(){
        return q;
    }


    public void resize(int capacity) {
        String[] BiggerArray = new String[capacity];
        for (int i = 0; i < BiggerArray.length; i++) {
            BiggerArray[i] = array[i + top];
        }
        array = BiggerArray;
        tail = q;
        top = 0;
    }

    public void enqueue(String item) {
        if (tail == array.length) {
            resize(2 * array.length);
        }
        array[tail++] = item;
        q++;
    }

    public String dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        String item = array[top];
        array[top++] = null;
        q--;
        return item;
    }

    public static void main(String[] args) {
        ResizingArrayQueueOfStrings test = new ResizingArrayQueueOfStrings();

        //enqueue de strings
        test.enqueue("Ola");
        test.enqueue("isto");
        test.enqueue("e");
        test.enqueue("um");
        test.enqueue("teste");

        while(!test.isEmpty()){
            System.out.print(test.dequeue());
        }
    }
}