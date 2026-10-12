/*
 * CPTR 2265 - Lab 7, Part B (optional starter)
 * ============================================
 *
 * A simple data structure whose sorting algorithm is chosen with an if/else
 * chain on a string. Adding a new algorithm means editing this class, and the
 * client must know the magic strings.
 *
 * Your task: apply the STRATEGY pattern so that
 *   - each sorting algorithm lives in its own class implementing a common
 *     interface (e.g. interface SortStrategy { void sort(int[] values); }),
 *   - NumberCollection receives a strategy (constructor and/or a setter),
 *   - the client can switch algorithms at runtime WITHOUT changing the code
 *     inside NumberCollection.
 *
 * You may use this file, or write your own simple data structure instead.
 * Run with Java 21:  java NumberCollection.java
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Strategy Interface
interface SortStrategy {
    void sort(int[] values);
}

// Concrete Strategy Implementations
class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] values) {
        int n = values.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (values[j] > values[j + 1]) {
                    int tmp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = tmp;
                }
            }
        }
    }
}

class MergeSortStrategy implements SortStrategy {
    @Override 
    public void sort(int[] values) {
        int[] sorted = mergeSort(values);
        System.arraycopy(sorted, 0, values, 0, sorted.length);
    }
        // Implementation for merge sort
        private int[] mergeSort(int[] data) {
            if (data.length <= 1) {
                return data;
            }
            int mid = data.length / 2;
            int[] left = mergeSort(Arrays.copyOfRange(data, 0, mid));
            int[] right = mergeSort(Arrays.copyOfRange(data, mid, data.length));
            int[] merged = new int[data.length];
            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                merged[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
            }
            while (i < left.length) merged[k++] = left[i++];
            while (j < right.length) merged[k++] = right[j++];
            return merged;
    }

}

// Context class
public class NumberCollection {
    private final List<Integer> values = new ArrayList<>();
    private SortStrategy sortStrategy;

    public NumberCollection(int... initial) {
        for (int v : initial) {
            values.add(v);
        }
    }

    public void add(int value) {
        values.add(value);
    }

    public List<Integer> values() {
        return new ArrayList<>(values);
    }

    public void setSortStrategy(SortStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Sort strategy cannot be null");
        }
        this.sortStrategy = strategy;
    }

    public void sort() {
        if (sortStrategy == null) {
            throw new IllegalStateException("No sorting strategy selected");
        }
        int[] data = values.stream().mapToInt(Integer::intValue).toArray();
        sortStrategy.sort(data);
        values.clear();
        for (int v : data) {
            values.add(v);
        }
    }

    // ------------------------------------------------------------ client ---
    public static void main(String[] args) {
        NumberCollection nums = new NumberCollection(42, 7, 19, 3, 88, 25, 61);
        // Select bubble sort strategy
        nums.setSortStrategy(new BubbleSortStrategy());
        nums.sort();
        System.out.println("Bubble sort: " + nums.values());
        // Select merge sort strategy
        nums.setSortStrategy(new MergeSortStrategy());
        nums.sort();
        System.out.println("Merge sort:  " + nums.values());
    }
}
