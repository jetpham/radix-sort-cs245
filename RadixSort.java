import java.util.*;

public class RadixSort {

    static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(100); // Random numbers between 0 and 999999
        }
        return array;
    }

    // https://www.geeksforgeeks.org/radix-sort/
    // A utility function to get maximum value in arr[]
    static int getMax(int arr[], int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    static void countSort(int arr[], int n, int exp) {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current
        // digit
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    // The main function to that sorts arr[] of
    // size n using Radix Sort
    static void radixsort(int arr[], int n) {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }

    // A utility function to check if the array is sorted
    static boolean isSorted(int arr[]) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    // Main driver method
    public static void main(String[] args) {
        // Array sizes to test
        int[] sizes = { 100, 1000, 10000, 100000, 1000000 };
        System.out.println(Arrays.toString(sizes).replaceAll("[\\[\\]]", ""));
        // Loop through each size and test the radix sort
        long[] times = new long[sizes.length];
        for (int i = 0; i < times.length; i++) {
            int[] testArray = generateRandomArray(sizes[i]);
            long startTime = System.nanoTime();
            radixsort(testArray, testArray.length);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            times[i] = duration;
        }
        System.out.println(Arrays.toString(times).replaceAll("[\\[\\]]", ""));
    }
}