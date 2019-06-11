package parallelprogramming;

import java.util.Random;
import java.util.concurrent.*;

public class parallelProgrammingWithForkJoin {
	public static final int ARRAY_SIZE = 10_000_000;

    public static void main(String[] args) {
        // Create a pool of threads
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = createArray(ARRAY_SIZE);
        long startTime;
        long endTime;

        MergeSort2 mergeSort = new MergeSort2(array, 0, array.length - 1);
        startTime = System.currentTimeMillis();

        pool.invoke(mergeSort); // Start execution and wait for result/return

        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " millis");
    }

    private static int[] createArray(final int size) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000);
        }
        return array;
    }
}

class MergeSort2 extends RecursiveAction {
    private int array[];
    private int left;
    private int right;

    public MergeSort2(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;
            RecursiveAction leftSort = new MergeSort2(array, left, mid);
            RecursiveAction rightSort = new MergeSort2(array, mid + 1, right);
            invokeAll(leftSort, rightSort);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int temp [] = new int[right - left + 1];
        int x = left;
        int y = mid + 1;
        int z = 0;
        while (x <= mid && y <= right) {
            if (array[x] <= array[y]) {
                temp[z] = array[x];
                z++;
                x++;
            } else {
                temp[z] = array[y];
                z++;
                y++;
            }
        }
        while (y <= right) {
            temp[z++] = array[y++];
        }
        while (x <= mid) {
            temp[z++] = array[x++];
        }

        for (z = 0; z < temp.length; z++) {
            array[left + z] = temp[z];
        }
    }
}
