package concurrent;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @Author:shifengqiang
 * @Date:2020/7/8 9:09 上午
 */
public class ObjectLearn {
    public static void main(String[] args) {
        Object lock = new Object();
        Queue<String> queue = new ArrayDeque<>();

        Thread producer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (queue.isEmpty()) {
                        System.out.println("producer add task");
                        queue.add("task");
                    }
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (!queue.isEmpty()) {
                        String task = queue.poll();
                        System.out.println("consumer consume task");

                    }
                    if (queue.isEmpty()) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        consumer.start();
        producer.start();

    }
}
