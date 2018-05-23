package com.akami.context;

public class ServletTraceContextManager {
    private static ThreadLocal<ServletTraceContext> contextTL = new ThreadLocal<ServletTraceContext>();

    public static ServletTraceContext start() {
        if(contextTL.get() == null) {
            contextTL.set(new ServletTraceContext());
        }
        return contextTL.get();
    }

    public static ServletTraceContext getContext() {
        return contextTL.get();
    }

    public static ServletTraceContext end() {
        ServletTraceContext context = contextTL.get();
        contextTL.set(null);
        return context;
    }
}