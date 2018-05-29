/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2017 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.muarine.algorithms;

/**
 * 转载于http://www.cnblogs.com/eniac12/p/5329396.html#s32
 * 排序算法，属于在内存实现的内部排序方法
 * 稳定的主要有：冒泡、插入、快速、选择、归并。 不稳定的主要有：希尔、堆、快速
 *
 *
 * 衡量排序算法稳定与否的标准：当数组中两个元素值相等时，算法是否改变元素的下标索引
 *
 * @author maoyun0903@163.com
 * @project hello
 * @package com.muarine.algorithms
 * @date 3/13/17
 */
public class SortAlgorithms {

    /**
     * 冒泡
     */
    public int[] bubbleSort(int[] originArray){

        for (int i=0;i<originArray.length;i++){
            for (int j=i+1;j<originArray.length;j++){
                if (originArray[i]>originArray[j]){
                    int temp = originArray[j];
                    originArray[j] = originArray[i];
                    originArray[i] = temp;
                }
            }
            System.out.println("冒泡排序第"+i+"次:"+print(originArray));
        }
        return originArray;
    }

    /**
     * 插入排序
     * 将需排序集合分为未排序和已排序两部分,刚开始
     *
     * @return
     */
    public int[] insertSort(int[] originArray){
        for (int i = 1; i < originArray.length;i++){
            int temp = originArray[i];
            int j = i-1;
            while (j>=0 && temp < originArray[j]){
                originArray[j+1] = originArray[j];
                j--;
            }
            originArray[j+1] = temp;
            System.out.println("插入排序第"+i+"次:"+print(originArray));
        }
        return originArray;
    }

    /**
     * 二分插入排序
     * 排序主要是比较和交换操作，相比于插入排序，二分插入比较次数少，交换操作多
     */
    public int[] binaryInsertSort(int[] originArray){

        for (int i = 1; i < originArray.length; i++){
            // 右手抓到一张扑克牌
            int get = originArray[i];
            // 拿在左手上的牌总是排序好的，所以可以用二分法
            int left = 0;
            // 手牌左右边界进行初始化
            int right = i - 1;
            // 采用二分法定位新牌的位置
            while (left <= right){
                int mid = (left + right) / 2;
                if (originArray[mid] > get) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // 将欲插入新牌位置右边的牌整体向右移动一个单位
            System.arraycopy(originArray, left, originArray, left + 1, i - left);
            // 将抓到的牌插入手牌
            originArray[left] = get;
        }
        return originArray;
    }

    /**
     * 希尔插入排序，递减增量排序，将数组分成几个区块，交换数据可移动较大步长
     * @return
     */
    public int[] shellInsertSort(int[] originArray){
        int h = 0;
        // 生成初始增量
        while (h <= originArray.length){
            h = 3 * h + 1;
        }
        while (h >= 1){
            for (int i = h; i < originArray.length; i++)
            {
                int j = i - h;
                int get = originArray[i];
                while (j >= 0 && originArray[j] > get)
                {
                    originArray[j + h] = originArray[j];
                    j = j - h;
                }
                originArray[j+h] = get;
            }
            // 递减增量
            h = (h - 1) / 3;
        }
        return originArray;
    }

    /**
     * 归并排序
     *
     * @return
     */
    public int[] mergeSort(int[] originArray, int left, int mid, int right){
        int len = right - left + 1;
        int[] temp = new int[len];       // 辅助空间O(n)
        int index = 0;
        int i = left;                   // 前一数组的起始元素
        int j = mid + 1;                // 后一数组的起始元素
        while (i <= mid && j <= right){
            temp[index++] = originArray[i] <= originArray[j] ? originArray[i++] : originArray[j++];  // 带等号保证归并排序的稳定性
        }
        while (i <= mid){
            temp[index++] = originArray[i++];
        }
        while (j <= right){
            temp[index++] = originArray[j++];
        }
        for (int k = 0; k < len; k++){
            originArray[left++] = temp[k];
        }
        return originArray;
    }

    /**
     * 递归实现的归并排序(自顶向下)
     *
     * @param originArray   需排序的数组
     * @param left  排序游标开始位置
     * @param right 游标结束位置
     */
    public int[] mergeSortRecursion(int[] originArray, int left, int right){
        // 当待排序的序列长度为1时，递归开始回溯，进行merge操作
        if (left == right){
            return null;
        }
        int mid = (left + right) / 2;
        mergeSortRecursion(originArray, left, mid);
        mergeSortRecursion(originArray, mid + 1, right);
        mergeSort(originArray, left, mid, right);

        return originArray;
    }

    /**
     * 非递归(迭代)实现的归并排序(自底向上)
     * @param originArray 需排序的数组
     */
    public int[] mergeSortIteration(int[] originArray){
        // 子数组索引,前一个为A[left...mid]，后一个子数组为A[mid+1...right]
        int len = originArray.length;
        int left, mid, right;
        // 子数组的大小i初始为1，每轮翻倍
        for (int i = 1; i < len; i *= 2){
            left = 0;
            // 后一个子数组存在(需要归并)
            while (left + i < len){
                mid = left + i - 1;
                // 后一个子数组大小可能不够
                right = mid + i < len ? mid + i : len - 1;
                mergeSort(originArray, left, mid, right);
                // 前一个子数组索引向后移动
                left = right + 1;
            }
        }
        return originArray;
    }

    /**
     * 选择排序
     *
     * @return
     */
    public int[] selectSort(int[] originArray){
        // i为已排序序列的末尾
        for (int i = 0; i < originArray.length - 1; i++){
            int min = i;
            // 未排序序列
            for (int j = i + 1; j < originArray.length; j++){
                // 找出未排序序列中的最小值
                if (originArray[j] < originArray[min]){
                    min = j;
                }
            }
            if (min != i){
                // 放到已排序序列的末尾，该操作很有可能把稳定性打乱，所以选择排序是不稳定的排序算法
                swap(originArray, min, i);
            }
        }
        return originArray;
    }

    /**
     * 交换位置
     * @param originArray
     * @param i
     * @param j
     */
    private void swap(int originArray[], int i, int j)
    {
        int temp = originArray[i];
        originArray[i] = originArray[j];
        originArray[j] = temp;
    }

    /**
     * 从A[i]向下进行堆调整
     * @param originArray
     * @param i
     * @param size
     */
    public void heapify(int originArray[], int i, int size){
        // 左孩子索引
        int leftChild = 2 * i + 1;
        // 右孩子索引
        int rightChild = 2 * i + 2;
        // 选出当前结点与其左右孩子三者之中的最大值
        int max = i;
        if (leftChild < size && originArray[leftChild] > originArray[max]) {
            max = leftChild;
        }
        if (rightChild < size && originArray[rightChild] > originArray[max]) {
            max = rightChild;
        }
        if (max != i){
            // 把当前结点和它的最大(直接)子节点进行交换
            swap(originArray, i, max);
            // 递归调用，继续从当前结点向下进行堆调整
            heapify(originArray, max, size);
        }
    }

    /**
     * 建堆，时间复杂度O(n)
     * @param originArray
     * @param n
     * @return
     */
    private int buildHeap(int originArray[], int n){
        int heapSize = n;
        // 从每一个非叶结点开始向下进行堆调整
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            heapify(originArray, i, heapSize);
        }
        return heapSize;
    }

    /**
     * 堆排序
     * @param originArray
     */
    public int[] heapSort(int[] originArray)
    {
        // 建立一个最大堆
        int heapSize = buildHeap(originArray, originArray.length);
        // 堆（无序区）元素个数大于1，未完成排序
        while (heapSize > 1){
            // 将堆顶元素与堆的最后一个元素互换，并从堆中去掉最后一个元素
            // 此处交换操作很有可能把后面元素的稳定性打乱，所以堆排序是不稳定的排序算法
            swap(originArray, 0, --heapSize);
            // 从新的堆顶元素开始向下进行堆调整，时间复杂度O(logn)
            heapify(originArray, 0, heapSize);
        }
        return originArray;
    }

    /**
     * 划分函数
     * @param originArray
     * @param left
     * @param right
     * @return
     */
    public int partition(int[] originArray, int left, int right){
        // 这里每次都选择最后一个元素作为基准
        int pivot = originArray[right];
        // tail为小于基准的子数组最后一个元素的索引
        int tail = left - 1;
        // 遍历基准以外的其他元素
        for (int i = left; i < right; i++){
            // 把小于等于基准的元素放到前一个子数组末尾
            if (originArray[i] <= pivot){
                swap(originArray, ++tail, i);
            }
        }
        // 最后把基准放到前一个子数组的后边，剩下的子数组既是大于基准的子数组
        swap(originArray, tail + 1, right);
        // 该操作很有可能把后面元素的稳定性打乱，所以快速排序是不稳定的排序算法
        // 返回基准的索引
        return tail + 1;
    }

    /**
     * 快速排序,不稳定
     * @param originArray
     * @param left
     * @param right
     */
    public int[] quickSort(int[] originArray, int left, int right)
    {
        if (left >= right) {
            return null;
        }
        // 基准的索引
        int pivotIndex = partition(originArray, left, right);
        quickSort(originArray, left, pivotIndex - 1);
        quickSort(originArray, pivotIndex + 1, right);
        return originArray;
    }

    /**
     * 打印数组
     * @date 5/29/18
     * @param arrays    数组
     */
    public static String print(int[] arrays){
        final StringBuilder builder = new StringBuilder();
        for (int element : arrays){
            builder.append(element).append(",");
        }
        return builder.toString();
    }

    public static void main(String[] args) {

        int[] arrays = new int[]{15,2,3,15,6,90};

        int[] operateArray = arrays.clone();
        // 冒泡
        int[] bubbleSort = new SortAlgorithms().bubbleSort(operateArray);
        System.out.println("冒泡排序结果:" + print(bubbleSort));
        System.out.println();

        // 插入
        operateArray = arrays.clone();
        int[] insertSort = new SortAlgorithms().insertSort(operateArray);
        System.out.println("插入排序结果:"+print(insertSort));
        System.out.println();

        // 二分插入
        operateArray = arrays.clone();
        int[] binaryInsertSort = new SortAlgorithms().binaryInsertSort(operateArray);
        System.out.println("二分插入排序结果:"+print(binaryInsertSort));
        System.out.println();

        // 希尔插入
        operateArray = arrays.clone();
        int[] shellInsertSort = new SortAlgorithms().shellInsertSort(operateArray);
        System.out.println("希尔插入排序结果:"+print(shellInsertSort));
        System.out.println();

        // 归并递归排序
        operateArray = arrays.clone();
        int[] mergeRecursionSort = new SortAlgorithms().mergeSortRecursion(operateArray, 0, operateArray.length-1);
        System.out.println("归并递归排序结果:"+print(mergeRecursionSort));
        System.out.println();

        // 归并迭代排序
        operateArray = arrays.clone();
        int[] mergeIteratorSort = new SortAlgorithms().mergeSortIteration(operateArray);
        System.out.println("归并迭代排序结果:"+print(mergeIteratorSort));
        System.out.println();

        // 堆排序
        operateArray = arrays.clone();
        int[] heapSort = new SortAlgorithms().heapSort(operateArray);
        System.out.println("堆排序结果:"+print(heapSort));
        System.out.println();


        // 快速
        operateArray = arrays.clone();
        int[] quickSort = new SortAlgorithms().quickSort(operateArray, 0, operateArray.length-1);
        System.out.println("快速排序结果:" + print(quickSort));
        System.out.println();

        // 选择
        operateArray = arrays.clone();
        int[] selectSort = new SortAlgorithms().selectSort(operateArray);
        System.out.println("选择排序结果:" + print(selectSort));
        System.out.println();

    }
}
