import java.nio.file.Files; //manipulación de archivos
import java.nio.file.Paths; //manejar y transformar rutas de archivos
import java.util.Arrays; //toString() y copyOfRange() con arreglos
import java.util.concurrent.ForkJoinPool; //ejecutor tareas fork-join
import java.util.concurrent.RecursiveAction; //fork-join, tarea sin devolver resultado

//extiende RecursiveAction para realizar quicksort usando fork-join
public class ForkJoinQuicksort extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;
    private final int THRESHOLD = 100; //is

    //arreglo entero
    public ForkJoinQuicksort(int[] array) {
        this(array, 0, array.length - 1);
    }

    //segmentar el arreglo
    public ForkJoinQuicksort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        //si el segmento es suf. pequeño, usar is
        if (left < right) {
            if (right - left < THRESHOLD) {
                insertionSort(array, left, right);
            } else {
                int pivotIndex = partition(array, left, right); //partir alrededor de un index
                //ordenar las dos mitades en paralelo (recursivo)
                invokeAll(new ForkJoinQuicksort(array, left, pivotIndex - 1),
                          new ForkJoinQuicksort(array, pivotIndex + 1, right));
            }
        }
    }

    //partir el segmento del array, devolver el /pivote
    private int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            //si el elemento es más pequeño que el pivote, mover al lado izquierdo
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, right);
        return i + 1;
    }

    //para intercambiar dos elementos en el arreglo
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //is
    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            //todo elemento mayor a key, mover 1 posición al frente
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {7, 12, 19, 3, 18, 4, 2, 6, 15, 8};
        ForkJoinQuicksort task = new ForkJoinQuicksort(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        System.out.println(Arrays.toString(array));
    }
}
