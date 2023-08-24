import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


/**
 * @ClassName: SynthesisSort
 * @Description: 综合排序，测试各种排序算法的性能
 */
public class SynthesisSort {
    public static void main(String[] args) {
        testAllSort();
    }

    /**
     * @ClassName: copyArr
     * @Description: 复制生成的随机数组
     */
    public static int [] copyArr(int[] arr) {
        int[] t = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            t[i] = arr[i];
        }
        return t;
    }

    /**
     * @ClassName: init
     * @Description: 生成N个随机数,且生成个数至少20000个
     */
    public static int[] init() {
        Scanner sc = new Scanner(System.in);
        //len表示生成随机数个数（至少20000）
        System.out.println("请输入要生成的随机数个数: ");
        int len;
        while (true) {
            len = sc.nextInt();
            if (len < 20000) {
                System.out.println("生成的随机个数太少(大于20000)!");
            } else {
                break;
            }
        }
        //创建数组，用于存放随机数
        int[] arr = new int [len];
        //生成len个随机数
        for (int i = 0; i < len; i++) {
            Random random = new Random();
            int ret = random.nextInt(20000);
            arr[i] = ret;
        }
        return arr;
    }

    /**
     * @ClassName: selectSort
     * @Description: 选择法排序
     */
    public static HashMap<String, Long> selectSort(int[] arr) {
        long start = System.currentTimeMillis();
        HashMap<String, Long> hashMap = new HashMap<>();
        long cmp = 0, swap = 0;
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i+1; j < arr.length; j++) {
                if (arr[index] > arr[j])
                    index = j;
                cmp++;
            }
            int t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
            swap++;
        }
        hashMap.put("swap", swap);
        hashMap.put("cmp", cmp);
        long end = System.currentTimeMillis();
        System.out.println("选择排序算法运行时间为：" + (end-start) + "ms");
        return hashMap;
    }

    /**
     * @ClassName: shellSort
     * @Description: 希尔排序
     */
    public static HashMap<String, Long> shellSort(int[] arr) {
        long start = System.currentTimeMillis();
        HashMap<String, Long> hashMap = new HashMap<>();
        int n = arr.length;
        long swap = 0, cmp = 0;
        // 初始步长设置为数组长度的一半
        int gap = n / 2;
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                // 对子序列进行插入排序
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                    cmp++;
                }
                arr[j] = temp;
                swap++;
            }
            // 缩小步长
            gap /= 2;
        }
        hashMap.put("swap", swap);
        hashMap.put("cmp", cmp);
        long end = System.currentTimeMillis();
        System.out.println("希尔排序算法运行时间为：" + (end-start) + "ms");
        return hashMap;
    }

    /**
     * @ClassName: quickSort
     * @Description: 快速排序
     */
    public static void quickSort(int[] arr, int low, int high, HashMap<String, Long> cs) {
        int i, j, temp, t;
        if (low > high)
            return;
        i = low;
        j = high;
        temp = arr[(i+j)/2];
        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
                cs.put("cmp", cs.get("cmp")+1);
            }
            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
                cs.put("cmp", cs.get("cmp")+1);
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
                cs.put("swap", cs.get("swap")+1);
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j - 1, cs);
        //递归调用右半数组
        quickSort(arr, j + 1, high, cs);
    }

    /**
     * @ClassName: heapSort
     * @Description: 堆排序
     */
    public static HashMap<String, Long> heapSort(int[] arr) {
        long start = System.currentTimeMillis();
        HashMap<String, Long> hashMap = new HashMap<>();
        long swap = 0, cmp = 0;
        for(int i = arr.length - 1; i>=0; i--) {
            HashMap<String, Long> hashMap1 = heapAdjust(arr, i, arr.length);
            swap += hashMap1.get("swap");
            cmp += hashMap1.get("cmp");
        }
        for(int i = arr.length-1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            HashMap<String, Long> hashMap1 = heapAdjust(arr, 0, i);
            swap += hashMap1.get("swap");
            cmp += hashMap1.get("cmp");
        }
        hashMap.put("swap", swap);
        hashMap.put("cmp", cmp);
        long end = System.currentTimeMillis();
        System.out.println("堆排序算法运行时间为：" + (end-start) + "ms");
        return hashMap;
    }

    /**
     * @ClassName: heapAdjust
     * @Description: 堆的调整
     */
    public static HashMap<String, Long> heapAdjust(int[] arr, int parent, int length){
        HashMap<String, Long> hashMap = new HashMap<>();
        long swap = 0, cmp = 0;
        int temp = arr[parent];
        int child = parent * 2 + 1;
        while ( child < length) {
            if (child+1 < length && arr[child+1] > arr[child]) {
                child++;
            }
            cmp++;
            if (temp >= arr[child]) {
                break;
            }
            cmp++;
            arr[parent] = arr[child];
            parent = child;
            child = parent * 2 + 1;
            swap++;
        }
        arr[parent] = temp;
        hashMap.put("swap", swap);
        hashMap.put("cmp", cmp);
        return hashMap;
    }

    /**
     * @ClassName: bubbleSort
     * @Description: 冒泡排序
     */
    public static HashMap<String, Long> bubbleSort(int[] arr) {
        long start = System.currentTimeMillis();
        HashMap<String, Long> hashMap = new HashMap<>();
        int temp;
        long swap = 0, cmp = 0;
        for(int i = 0; i< arr.length-1; i++){
            for(int j = 0; j<arr.length-1-i; j++){
                cmp++;
                if (arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swap++;
                }
            }
        }
        hashMap.put("swap", swap);
        hashMap.put("cmp", cmp);
        long end = System.currentTimeMillis();
        System.out.println("冒泡排序算法运行时间为：" + (end-start) + "ms");
        return hashMap;
    }

    /**
     * @ClassName: testAllSort
     * @Description: 排序算法性能测试
     */
    public static void testAllSort() {
        //初始化随机数
        int[] arr = init();
        int[] temp = copyArr(arr);

        System.out.println("-----------------------------");
        //选择排序
        HashMap<String, Long> hashMap = selectSort(temp);

        //希尔排序
        temp = copyArr(arr);
        HashMap<String, Long> hashMap1 = shellSort(temp);

        //快速排序
        HashMap<String, Long> hashMap2 = new HashMap<>();//用来存放交换和比较的次数
        hashMap2.put("swap", 0L);
        hashMap2.put("cmp", 0L);
        temp = copyArr(arr);
        long start = System.currentTimeMillis();
        quickSort(temp, 0, arr.length - 1, hashMap2);
        long end = System.currentTimeMillis();
        System.out.println("快速排序算法运行时间为：" + (end-start) + "ms");

        //堆排序
        temp = copyArr(arr);
        HashMap<String, Long> hashMap3 = heapSort(temp);

        //冒泡排序
        temp = copyArr(arr);
        HashMap<String, Long> hashMap4 = bubbleSort(temp);

        System.out.println("-----------------------------");
        System.out.println("算法\t\t交换次数\t\t\t比较次数");
        System.out.println("选择排序\t\t" + hashMap.get("swap") +"\t\t\t" + hashMap.get("cmp"));
        System.out.println("希尔排序\t\t" + hashMap1.get("swap") +"\t\t\t" + hashMap1.get("cmp"));
        System.out.println("快速排序\t\t" + hashMap2.get("swap") +"\t\t\t" + hashMap2.get("cmp"));
        System.out.println("堆排序  \t\t" + hashMap3.get("swap") +"\t\t\t" + hashMap3.get("cmp"));
        System.out.println("冒泡排序\t\t" + hashMap4.get("swap") +"\t\t" + hashMap4.get("cmp"));
        System.out.println("-----------------------------");
        System.out.println("在随机情况下较快的为希尔排序和堆排序");
        System.out.println("-----------------------------");

    }
}
