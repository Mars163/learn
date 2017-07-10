package com.example.sort;


public class Sort {

    /**
     * 插入排序法：每次将一个数插入一个已排列好的数列中。
     * <br>数列从2个数开始慢慢增加到所有要排的数的长度，插入时从一个方向依次比较要插入的数与当前数的大小。
     * <p>时间复杂度：n²
     * @param arr
     */
    public static void insertion_sort(int[] arr){
        for(int i = 1; i < arr.length; i ++){
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key){
                arr[j + 1] = arr[j];
                j --;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * 二分插入排序法：插入的时候利用二分查找法寻找插入位置（这里使用递归方式实现）
     * <p>时间复杂度：nlog₂n
     * @param arr
     * @param begin
     * @param end
     */
    public static void insertion_sort_recursion(int[] arr, int begin, int end){
        if(begin >= end){
            return;
        }
        insertion_sort_recursion(arr, begin, end - 1);
        int key = arr[end];
        int p = binarySearch(arr, begin, end - 1, key);
        for(int i = end - 1; i >= p; i --){
            arr[i + 1] = arr[i];
        }
        arr[p] = key;
    }

    /**
     * 二分查找：在一个已排序好的数列中查找某个数，每次比较数列中值与数的大小，
     * 确定接下来要查找的子数列（左或右），再与子数列的中值比较，直到数列剩下1到2个数。
     * <p>时间复杂度：log₂n
     * @param arr
     * @param begin
     * @param end
     * @param key
     * @return 如果刚好找到相等的值，则返回相等的值的位置；
     * 如果没找到相等的值，则返回比它大并且最接近的值的位置；
     * 如果它大于数列中的所有数，则返回数列末尾位置加1
     */
    public static int binarySearch(int[] arr, int begin, int end, int key){
        if(end - begin <= 1){
            if(key <= arr[begin]){
                return begin;
            } else if(key > arr[end]){
                return end + 1;
            } else {
                return end;
            }
        }
        int mid = (begin + end) / 2;
        if(key == arr[mid]){
            return mid;
        } else if(key < arr[mid]){
            return binarySearch(arr, begin, mid - 1, key);
        } else {
            return binarySearch(arr, mid + 1, end, key);
        }
    }

    /**
     * 希尔排序：确定一个增量（初值为n/2），把所有数按增量分组（下标相差增量的数为一组），
     * 然后把每组进行插入排序。再缩小增量至1/2，再按相同方法对每组排序。直到最后增量为1，
     * 对所有数再进行一次插入排序（这时大部分数已经有了次序，会缩小插入排序的时间）。
     * <p>时间复杂度：nlog₂n
     * @param arr
     */
    public static void shell_sort(int[] arr){
        for(int gap = arr.length / 2; gap > 0; gap /= 2){
            for(int i = 0; i < gap; i ++){

                for(int index = i; index + gap < arr.length; index += gap){
                    int key = arr[index + gap];
                    int j = index;
                    while (j >= 0 && arr[j] > key){
                        arr[j + gap] = arr[j];
                        j -= gap;
                    }
                    arr[j + gap] = key;
                }

            }
        }
    }

    /**
     * 快速排序：每次将一个基准数（要排数列第一个数），移到某一位置，
     * 使在它前面的数都比它小，它后面的数都比它大。然后再递归排序它前面的数列和它后面的数列。
     * <p>时间复杂度：nlog₂n
     * @param arr
     * @param begin
     * @param end
     */
    public static void quick_sort(int[] arr, int begin, int end){
        if(begin >= end){
            return;
        }
        int key = begin;
        for(int i = begin; i <= end - 1; i ++){
            //移动key到中间位置
            if(arr[key] > arr[i + 1]){
                int t = arr[i + 1];
                for(int j = i;j >= key; j --){
                    arr[j + 1] = arr[j];
                }
                arr[key] = t;
                key++;
            }
        }
        quick_sort(arr, begin, key - 1);
        quick_sort(arr, key + 1, end);
    }

    /**
     * 归并排序：将数列分成两个子数列，将这两个子数列排好序后，合并这两个数列成一个新的数列完成排序。
     * <br>这两个子数列的排序方法也是递归拆成两个更小的数列，直到每个数列只有一个值，
     * 然后再依次合并两个子序列成中间数列，直到合并为完整数列。
     * <br>  合并方法是依次从两个数列头部取数比较大小，将小的放入要合并的数列中，直到所有数都放入。
     * <p>时间复杂度nlog₂n
     * @param arr
     * @param begin
     * @param end
     */
    public static void merge_sort(int[] arr, int begin, int end){
        if(begin >= end){
            return;
        }
        int mid = (begin + end) / 2;
        //递归排序两个子数列
        merge_sort(arr, begin, mid);
        merge_sort(arr, mid + 1, end);
        //合并两个子数列
        int[] tmpLeft = new int[mid - begin + 1];
        for(int i = 0; i < tmpLeft.length; i ++){
            tmpLeft[i] = arr[begin + i];
        }
        int[] tmpRight = new int[end - mid];
        for(int j = 0; j < tmpRight.length; j ++){
            tmpRight[j] = arr[mid + 1 + j];
        }
        int t = begin;
        int m = 0;
        int n = 0;
        while(m < tmpLeft.length || n < tmpRight.length){
            int tmp;
            if(m == tmpLeft.length){
                tmp = tmpRight[n];
                n ++;
            } else if(n == tmpRight.length){
                tmp = tmpLeft[m];
                m ++;
            } else {
                if(tmpLeft[m] <= tmpRight[n]){
                    tmp = tmpLeft[m];
                    m ++;
                } else {
                    tmp = tmpRight[n];
                    n ++;
                }
            }
            arr[t++] = tmp;
        }

    }

    /**
     * 基数排序：按照数的每一位（个十百千万），依次将数组中的数排序，直到最大数的最高位。
     * <br>排序的过程当中可以依据当前数位上数的不同，按顺序放入相应数字（0-9）对应的桶中，
     * 再从桶（0-9）中依次取出刚才存放的数到数组中完成一轮排序。
     * <p>时间复杂度：kn (k为最大数的位数)
     * <br>空间复杂度：10n
     * @param arr
     */
    public static void radix_sort(int[] arr){
        int max = 0;
        for(int i : arr){
            //寻找数组中最大的数
            if(i > max){
                max = i;
            }
        }
        int digits = 0;
        while (max > 0){
            //确定最大的数是几位数
            max /= 10;
            digits ++;
        }
        int[][] box = new int[10][arr.length];
        int[] indexs = new int[10];
        int n = 1;
        for(int i = 0; i < digits; i ++){ //进行最大数位次循环
            for(int j = 0; j < arr.length; j ++){
                //按照当前数位上的数，依次放置到相应数位的桶（临时数组）里
                int digit = (arr[j] / n) % 10;
                box[digit][indexs[digit]] = arr[j];
                indexs[digit] ++;
            }

            int t = 0;
            for(int k = 0; k < 10; k ++){
                //将桶（临时数组）里的所有数依次取出来放回原始数组
                if(indexs[k] > 0){
                    for(int l = 0; l < indexs[k]; l ++){
                        arr[t ++] = box[k][l];
                    }
                    indexs[k] = 0; //记得将桶的位置标识清零
                }
            }
            n *= 10;
        }

    }

    /**
     * 冒泡排序：依次比较相邻的两个数，如果大小相反则交换，
     * 每一轮比较可确定这一轮数列中的最小值，并移到边缘。
     * <p>时间复杂度：n²
     * @param arr
     */
    public static void bubble_sort(int[] arr){
        int t = 0;
        for(int i = 0; i < arr.length; i ++){
            for(int j = arr.length - 1; j > i; j --){
                if(arr[j - 1] > arr[j]){
                    t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                }
            }
        }
    }

    /**
     * 选择排序：每一次从待排序的数据元素中选出最小（或最大）的一个元素，
     * 存放在序列的起始位置，直到全部待排序的数据元素排完。
     * <p>时间复杂度：n²
     * @param arr
     */
    public static void selection_sort(int[] arr){
        int t = 0;
        for(int i = 0; i < arr.length - 1; i ++){
            int index = i;
            for(int j = i + 1; j < arr.length; j ++){ //确定最小值的index
                if(arr[index] > arr[j]){
                    index = j;
                }
            }
            if(i != index){ //如果i当前不是最小值，则替换为最小值
                t = arr[index];
                arr[index] = arr[i];
                arr[i] = t;
            }
        }
    }
}
