package jvm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author frank
 * @version 1.0
 * @date 2018/5/12 0012 下午 6:09
 */
@Slf4j
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc HOOK = null;

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        log.info("FinalizeEscapeGc finalize executed");
        FinalizeEscapeGc.HOOK = this;
    }

    @Test
    public void test() {
        HOOK = new FinalizeEscapeGc();
        gc();
/**
 * 两次执行的代码都一样，结果却不同，因为finalize()方法只能被调用一次。
 */
        gc();
    }

    private void gc() {
        HOOK = null;
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (HOOK != null) {
            log.info("HOOK alive");
        } else {
            log.info("obj dead");
        }
    }
}
