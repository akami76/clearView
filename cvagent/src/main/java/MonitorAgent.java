

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;

import com.akami.jmsclient.ProducerManager;
import com.akami.thread.ThreadUtil;

public class MonitorAgent {
	//Timer timer = new Timer("clearView Agent Timer", true);

    //TODO Thread Queue를 개발해야함
    public  static  SynchronousQueue<String> queue = new SynchronousQueue<String>();


    public static void premain(String args, Instrumentation inst) throws Exception {
        System.out.println("1. broker start =============================");

       // MyMonitorThread myMonitorThread = new MyMonitorThread()




        ProducerManager producerManager = new ProducerManager();
        inst.addTransformer(new MainTransformer());
        //inst.addTransformer(new SetCallTree());
        //inst.addTransformer(new Profiler());
        //Profiler profiler = new Profiler(inst);
        //inst.addTransformer(profiler);
        //jdbc 모니터링
        // inst.addTransformer(new JdbcQueryTransformer());
       // monitorAgent = new MonitorAgent();
       // monitorAgent.start();


    }

    public static void agentmain(String args, Instrumentation inst) throws Exception {
        System.out.println("agentmain invoked.!!!!!!@@@@@@@@@@@@@@@@@@@@@@");
        premain(args, inst);

    }

    public void start() {
        //JVM 정보 수집
       /// MBeanTimer mBeanTimer = new MBeanTimer();
        //invoke 되어 1초경과하면 1초마다 JVM 정보 수집을 한다.
        //timer.scheduleAtFixedRate(mBeanTimer, 10000, 10000);


    }



}
