package parallelprogramming;

import java.util.Random;


public class paralellProgrammingWithOneThread {
	 public static final int ARRAY_SIZE = 10_000_000;

    public static void main(String[] args) {
        long startTime;
        long endTime;
        int[] array = createArray(ARRAY_SIZE);
        MergeSort mergeSort = new MergeSort(array);

        startTime = System.currentTimeMillis(); // Get the current time before sorting
        mergeSort.sort();
        endTime = System.currentTimeMillis();   // Get the current time after sorting
        
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

class MergeSort {
    private int array[];

    public MergeSort(int[] array) {
        this.array = array;
    }

    public void sort() {
        sort(0, array.length - 1);
    }

    
    private void sort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(left, mid);
            sort(mid + 1, right);
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
