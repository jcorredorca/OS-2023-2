//Quicksort

    public static void main(String[] args) {
        int[] array = {7, 12, 19, 3, 18, 4, 2, 6, 15, 8};
        ForkJoinQuicksort task = new ForkJoinQuicksort(array);
        ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(task);
        System.out.println(Arrays.toString(array));
    }

//Mergesort

    public static void main(String[] args) {
        int[] array = {12, 7, -5, 3, 15, 0, 9, 11, 6};
        ForkJoinMergesort task = new ForkJoinMergesort(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        System.out.println(Arrays.toString(array));
    }