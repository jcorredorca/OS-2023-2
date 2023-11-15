import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

//Clase que extiende RecursiveAction para realizar mergesort usando fork-join
public class ForkJoinMergesort extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;
    private final int THRESHOLD = 100; //is

    //arreglo completo
    public ForkJoinMergesort(int[] array) {
        this(array, 0, array.length - 1);
    }

    //segmento(s) del arreglo
    public ForkJoinMergesort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left < THRESHOLD) {
            insertionSort(array, left, right); // is para arreglos pequeños
        } else {
            //divide el arreglo en 2 y crea tarea para cada mitad
            int middle = left + (right - left) / 2;
            ForkJoinMergesort leftTask = new ForkJoinMergesort(array, left, middle);
            ForkJoinMergesort rightTask = new ForkJoinMergesort(array, middle + 1, right);

            invokeAll(leftTask, rightTask); // ordenar las mitades en paralelo

            merge(left, middle, right); // juntar los segmentos
        }
    }

    // combinar dos subarreglos ordenados
    private void merge(int left, int middle, int right) {
        int[] leftArray = Arrays.copyOfRange(array, left, middle + 1);
        int[] rightArray = Arrays.copyOfRange(array, middle + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        // Copia los elementos restantes (si los hay)
        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }

        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }
    }

    //is
    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            //elementos mayores a key, se mueven 1 adelante
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] array = {12, 7, -5, 3, 15, 0, 9, 11, 6};
        ForkJoinMergesort task = new ForkJoinMergesort(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        System.out.println(Arrays.toString(array));
    }
}
