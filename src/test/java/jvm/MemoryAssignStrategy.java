package jvm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018年5月1日10:54:34
 */
@Slf4j
public class MemoryAssignStrategy {

    private static final int ONE_M = 1024 * 1024;

    /**
     * 对象优先在Eden分配
     */
    @Test
    public void eden() {
        /**
         * VM参数   -verbose:gc -Xms20M -Xmx20M -Xmn9M -XX:+PrintGCDetails -XX:SurvivorRatio=8
         * -Xms20M -Xmx20M -Xmn9M 表示堆内存设置成20M，不可扩展，其中新生代9M，老年代11M
         *  -XX:SurvivorRatio=8 表示新生代中一个Eden区和一个Survivor区的比例为8:1
         */
        byte[] allocation1;
        byte[] allocation2;
        byte[] allocation3;
        byte[] allocation4;
        log.info("here");
        allocation1 = new byte[2 * ONE_M];
        allocation2 = new byte[2 * ONE_M];
        allocation3 = new byte[2 * ONE_M];
        allocation4 = new byte[4 * ONE_M];

/**

 [GC (Allocation Failure) [PSYoungGen: 8192K->512K(8704K)] 8192K->1969K(19968K), 0.0144789 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 11:23:35.970 [main] INFO jvm.MemoryAssignStrategy - here
 1、 [GC(Allocation Failure) gc停顿的类型GC/FullGC 如果有full，说明发生了stop the world
 用来区分(distinguish)是 Minor GC 还是 Full GC 的标志(Flag). 这里的 GC 表明本次发生的是 Minor GC.
 2、(Allocation Failure) 引起垃圾回收的原因. 本次GC是因为年轻代中没有任何合适的区域能够存放需要分配的数据结构而触发的
 3、[PSYoungGen: gc发生的区域]
 4、8192K->512K(8704K) gc前该内存区域已使用容量->gc后该内存区域已使用容量（该内存区域总容量）
 5、8192K->1969K(19968K) gc前堆已使用容量->gc后堆已使用容量（堆总容量）
 [GC (Allocation Failure) [PSYoungGen: 7979K->496K(8704K)] 9437K->2884K(19968K), 0.0010925 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 Heap
 PSYoungGen      total 8704K, used 6851K [0x00000000ff700000, 0x0000000100000000, 0x0000000100000000)
 eden space 8192K, 77% used [0x00000000ff700000,0x00000000ffd34fc0,0x00000000fff00000)
 from space 512K, 96% used [0x00000000fff80000,0x00000000ffffc010,0x0000000100000000)
 to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 ParOldGen       total 11264K, used 6484K [0x00000000fec00000, 0x00000000ff700000, 0x00000000ff700000)
 object space 11264K, 57% used [0x00000000fec00000,0x00000000ff255248,0x00000000ff700000)
 Metaspace       used 6319K, capacity 6530K, committed 6784K, reserved 1056768K
 class space    used 733K, capacity 789K, committed 896K, reserved 1048576K
 */

    }

    /**
     * -Xms100m -Xmx100M -XX:+UseSerialGC
     */
    @Test
    public void systemGc() {
        try {
            fillHeap(1000);
            System.gc();
        } catch (InterruptedException e) {
            log.error("fillHeap e={}", ExceptionUtils.getStackTrace(e));
        }
    }

    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int i = RandomUtils.nextInt(5, 30);
                    log.info("random is ={}", i);
                    try {
                        Thread.sleep(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "BusyThread");
        thread.start();
    }

    public static void createLockthread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                        log.info("been notified");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Lockthread");
        thread.start();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();

        br.readLine();
        Object o = new Object();
        createLockthread(o);


//        try {
//            fillHeap(1000);
//            System.gc();
//        } catch (InterruptedException e) {
//            log.error("fillHeap e={}", ExceptionUtils.getStackTrace(e));
//        }
    }

    static class OOMObject {
        public byte[] placeHolder = new byte[64 * 1024];
    }

    private static void fillHeap(int number) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>(number);
        for (int i = 1; i < number; i++) {
            Thread.sleep(50);
            log.info("running times ={}", i);
            list.add(new OOMObject());
        }
        //  System.gc();
        //Thread.sleep(100);
        log.info("after gc ");
    }


}

