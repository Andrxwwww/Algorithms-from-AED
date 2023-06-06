import java.util.Arrays;
public class QuicksortUpdated {

    private static final int INSERTION_SORT_THRESHOLD = 10;

    public static void sort(Comparable[] array) {
        shuffle(array);
        sort(array, 0, array.length - 1);
    }

    private static void shuffle(Comparable[] array) {
        for (int i = 0; i < array.length; i++) {
            int j = (int) (Math.random() * (i + 1));
            exchange(array, i, j);
        }
    }

    private static void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        if (hi - lo + 1 < INSERTION_SORT_THRESHOLD) {
            insertionSort(array, lo, hi);
            return;
        }
        int m = medianOf3(array, lo, (lo + hi) / 2, hi);
        exchange(array, lo, m);
        int j = partition(array, lo, hi);
        sort(array, lo, j - 1);
        sort(array, j + 1, hi);
    }

    private static void insertionSort(Comparable[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            Comparable key = array[i];
            int j = i - 1;
            while (j >= lo && less(key, array[j])) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static int medianOf3(Comparable[] array, int i, int j, int k) {
        return less(array[i], array[j]) ?
                (less(array[j], array[k]) ? j : less(array[i], array[k]) ? k : i) :
                (less(array[k], array[j]) ? j : less(array[k], array[i]) ? k : i);
    }

    private static int partition(Comparable[] array, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (less(array[++i], array[lo])) {
                if (i == hi) {
                    break;
                }
            }
            while (less(array[lo], array[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exchange(array, i, j);
        }
        exchange(array, lo, j);
        return j;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exchange(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static void main(String[] args) {
        Comparable[] array = { 5, 2, 9, 3, 6, 8, 1, 7, 4 };
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
