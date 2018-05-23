package com.akami.com;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;

public class ThreadManager {

    public static HashMap<Long, Thread> ACTIVE_THREAD = new HashMap<Long, Thread>();

    public static HashMap<String, String> getThreadDetail(long thread_id) {

        HashMap<String, String> m = new HashMap();
        if (thread_id == 0)
            return m;
        ThreadMXBean tmb = ManagementFactory.getThreadMXBean();

        ThreadInfo f = tmb.getThreadInfo(thread_id, 500);
        if (f == null)
            return m;

        m.put("Thread Id", f.getThreadId()+"");
        m.put("Thread Cpu Time", (tmb.getThreadCpuTime(thread_id) / 1000000)+"");
        m.put("Thread User Time", (tmb.getThreadUserTime(thread_id) / 1000000)+"");

        m.put("Blocked Count", (f.getBlockedCount()+""));
        m.put("Blocked Time", (f.getBlockedTime()+""));
        m.put("Waited Count", (f.getWaitedCount()+""));
        m.put("Waited Time", (f.getWaitedTime()+""));
        m.put("Lock Owner Id", (f.getLockOwnerId()+""));
        m.put("Lock Name", (f.getLockName()+""));
        m.put("Lock Owner Name", f.getLockOwnerName());
        m.put("Thread Name", f.getThreadName());
        m.put("Stack Trace", getStackTrace(f.getStackTrace(),0));
        m.put("State", f.getThreadState().toString()+"");

        return m;
    }

    public static void add(Thread thread){
        ACTIVE_THREAD.put(thread.getId(), thread);
    }



    public static String getStackTrace(StackTraceElement[] se, int skip) {
        if (se == null || se.length <= skip)
            return "";
        String CRLF = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer();
        for (int i = skip; i < se.length; i++) {
            if (sb.length() > 0) {
                sb.append(CRLF);
            }
            sb.append(se[i]);
        }
        return sb.toString();
    }
}
