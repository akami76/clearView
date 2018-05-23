import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadMXBeanTest{
        @Test
        public void test() {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long[] allThreadIds = threadMXBean.getAllThreadIds();
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(allThreadIds);
            for (ThreadInfo threadInfo : threadInfos) {
                System.out.println("=======================================");
                System.out.println(threadInfo.getThreadName());
                System.out.println(threadInfo.getBlockedCount());
                System.out.println(threadInfo.getBlockedTime());
                System.out.println(threadInfo.getWaitedCount());
                System.out.println(threadInfo.getWaitedTime());
                System.out.println(threadInfo.getThreadId());
            }
        }

}

