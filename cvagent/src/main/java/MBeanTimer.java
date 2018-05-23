import java.lang.management.ManagementFactory;
import java.util.TimerTask;

import javax.management.*;
import javax.management.openmbean.CompositeData;

import com.akami.com.TOPIC;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.json.JSONObject;

import static com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL;


public class MBeanTimer extends TimerTask {
    public void run() {

        try {
            getThreadCont();
            getHeapMemoryUsage();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SigarException {
       // Sigar sigar = new Sigar(); //1. sigar객체 생성
       // CpuPerc cpu = sigar.getCpuPerc(); //2. 전체 cpu에 대한 사용량
       // System.out.println(cpu);
        MBeanTimer m =new MBeanTimer();
        try {
            m.getThreadCont();
            m.getHeapMemoryUsage();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void getThreadCont() throws MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        //jmx["java.lang:type=Threading", "ThreadCount"]
        ObjectName mom = new ObjectName("java.lang:type=Threading");

        MBeanServer connection = ManagementFactory.getPlatformMBeanServer();


        Object attrValue = connection.getAttribute(mom, "ThreadCount");


        int threadCnt = (Integer)attrValue;
        //System.out.println("threadCnt : "+ threadCnt);

        JSONObject threadJson = new JSONObject();
        threadJson.put("threadCnt", threadCnt);

        PRODUCER_MANAGER_POOL[TOPIC.CV_THREAD_CNT.getIndex()].produceMessage(threadJson.toString());

    }


    public void getHeapMemoryUsage() throws MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        try {


            ObjectName mom = new ObjectName("java.lang:type=Memory");

            MBeanServer connection = ManagementFactory.getPlatformMBeanServer();


            Object attrValue = connection.getAttribute(mom, "HeapMemoryUsage");

            long max = Long.parseLong(((CompositeData)attrValue).get("max").toString());
            long used = Long.parseLong(((CompositeData)attrValue).get("used").toString());
            long heapUsedPercent = Math.round((used*1.0 / max*1.0) * 100.0);


            JSONObject memJson = new JSONObject();
            memJson.put("mem", heapUsedPercent);

            PRODUCER_MANAGER_POOL[TOPIC.CV_MEM.getIndex()].produceMessage(memJson.toString());


        } catch (Exception e) {
            System.out.println(String.format("ERROR : %s", e.toString()));
            System.err.println();
        }

    }


 
}


