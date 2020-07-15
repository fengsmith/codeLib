package concurrent;


/**
 * @Author:shifengqiang
 * @Date:2020/4/14 3:59 下午
 */
public class VolatileLearn {
    static int i = 0, j = 0;
    static void one() { i++; j++; }
    static void two() {
        if (j > i) {
            System.out.println("i=" + i + " j=" + j);
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                one();
            }
        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                two();
            }
        });

        thread1.start();
        thread2.start();

    }
}
