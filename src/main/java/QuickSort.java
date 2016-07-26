import java.util.List;

/**
 * author:      shfq
 * description:
 * create date: 2016/7/26.
 */
public class QuickSort {
    private static void quickSort(List<Integer> integers, int leftBoundary, int rightBoundary) {
        if ((rightBoundary - leftBoundary) == 0 || (rightBoundary - leftBoundary) == -1) {
            return;
        }
        int pivotIndex = partition(integers, leftBoundary, rightBoundary, (leftBoundary + rightBoundary) / 2);
        quickSort(integers, leftBoundary, pivotIndex-1);
        quickSort(integers, pivotIndex + 1, rightBoundary);
    }

    private static int partition(List<Integer> integers, int leftBoundary, int rightBoundary, int pivotIndex) {
        swap(integers, leftBoundary, pivotIndex);
        int pivotValue = integers.get(leftBoundary);
        int boundary = leftBoundary + 1;
        for (int i = 0; i < rightBoundary - leftBoundary; i++) {
            int current = i + 1 + leftBoundary;
            if (integers.get(current) < pivotValue) {
                swap(integers, current, boundary);
                boundary = boundary + 1;
            }
        }
        swap(integers, leftBoundary, boundary - 1);


        return boundary-1;
    }

    private static void swap(List<Integer> integers, int index1, int index2) {
        if (integers == null || integers.size() == 1) {
            return;
        }
        if (index1 < 0 || index2 < 0 || index1 >= integers.size() || index2 >= integers.size()) {
            return;
        }
        int temp = integers.get(index1);
        integers.set(index1, integers.get(index2));
        integers.set(index2, temp);
    }
}
