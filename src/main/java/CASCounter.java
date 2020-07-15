import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author:shifengqiang
 * @Date:2020/5/17 11:46 上午
 */
public class CASCounter {
    private Unsafe unsafe;
    private volatile long counter = 0;
    private long offset;

    private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    public CASCounter() throws Exception {
        unsafe = getUnsafe();
        offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
    }

    public void increment() {
        long before = counter;
        while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
            before = counter;
        }
    }

    public long getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        try {
            CASCounter casCounter = new CASCounter();
            casCounter.increment();
            Long l = casCounter.getCounter();
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
