package SortingAlgorithms;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SortingAlgorithms {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display menu for sorting algorithms
            System.out.println("Choose a sorting algorithm:");
            System.out.println("1. Selection Sort");
            System.out.println("2. Insertion Sort");
            System.out.println("3. Bubble Sort");
            System.out.println("4. Quick Sort");
            System.out.println("5. Radix Sort");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();

            if (choice == 6) break; // Exit if user chooses option 6

            // Create arrays of different sizes
            int[][] arrays = {
                    generateRandomArray(20),
                    generateRandomArray(100),
                    generateRandomArray(500),
                    generateRandomArray(1000)
            };

            // Sort and measure time for each array using the chosen algorithm
            for (int[] arr : arrays) {
                int[] copy = Arrays.copyOf(arr, arr.length); // Create a copy of the array
                long startTime = System.nanoTime(); // Start measuring time
                switch (choice) {
                    case 1: selectionSort(copy); break;
                    case 2: insertionSort(copy); break;
                    case 3: bubbleSort(copy); break;
                    case 4: quickSort(copy, 0, copy.length - 1); break;
                    case 5: radixSort(copy); break;
                    default: break;
                }
                long endTime = System.nanoTime(); // End measuring time
                // Display the time taken to sort the array
                System.out.println("Time for array of size " + arr.length + ": " + (endTime - startTime) + " nanoseconds");
            }
        }
    }

    // Method to generate an array filled with random integers
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);
        }
        return array;
    }

    // Selection Sort algorithm
    private static void selectionSort(int[] arr) {
        // Selection Sort:
        // Works by repeatedly finding the minimum element from the unsorted part of the array and putting it at the beginning.
        // Time Complexity: O(n^2)
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // Insertion Sort algorithm
    private static void insertionSort(int[] arr) {
        // Insertion Sort:
        // Builds the final sorted array one item at a time, by repeatedly taking the next element and inserting it into the correct position.
        // Time Complexity: O(n^2)
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Bubble Sort algorithm
    private static void bubbleSort(int[] arr) {
        // Bubble Sort:
        // Repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order.
        // Time Complexity: O(n^2)
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Quick Sort algorithm
    private static void quickSort(int[] arr, int low, int high) {
        // Quick Sort:
        // Picks an element as pivot and partitions the given array around the picked pivot.
        // Time Complexity: O(n log n) on average, O(n^2) worst-case
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    // Partition function used in Quick Sort
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // Counting Sort algorithm (used in Radix Sort)
    private static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    // Radix Sort algorithm
    private static void radixSort(int[] arr) {
        // Radix Sort:
        // It sorts the elements by first grouping the individual digits of the same place value.
        // Time Complexity: O(n * k) where n is the number of elements and k is the maximum number of digits
        int maxVal = Arrays.stream(arr).max().getAsInt();
        for (int exp = 1; maxVal / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }
}