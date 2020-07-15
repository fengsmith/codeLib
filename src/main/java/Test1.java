import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

/**
 * @Author:shifengqiang
 * @Date:2020/5/14 3:01 下午
 */
public class Test1 {


    public static void main(String[] args) {
        Object o = new Object();
        BeeperControl beeperControl = new BeeperControl();
        beeperControl.beepForAnHour();
    }

    static class BeeperControl {
        private final ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(199);

        private final ScheduledExecutorService scheduler2 =
                Executors.newScheduledThreadPool(1);

        Long l1 = System.currentTimeMillis();
        Random random = new Random(10);

        public void beepForAnHour() {
            final Runnable beeper = new Runnable() {
                public void run() {
                    System.out.println("beep" + " start " + (System.currentTimeMillis() - l1) / 1000);
                    try {
                        System.out.println(Thread.currentThread().getId());
                        int r = random.nextInt(4) + 1;
                        Thread.sleep(r * 1000L);

                        System.out.println("over " + (System.currentTimeMillis() - l1) / 1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            final ScheduledFuture<?> beeperHandle =
                    scheduler.scheduleWithFixedDelay(beeper, 1, 6, SECONDS);
//            scheduler2.schedule(() -> {
//                System.out.println("will cancle");
//                beeperHandle.cancel(true);
//            }, 10, SECONDS);
        }
    }

}
