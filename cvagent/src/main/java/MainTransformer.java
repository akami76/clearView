

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import com.akami.devStar.event.Set.RequsetResponseSet;

import com.akami.transformer.biz.MakeCallTrace;
import javassist.*;


public class MainTransformer implements ClassFileTransformer {
    static ClassPool parent = null;
    static ClassPool child = null;
    private volatile long txid = 0;

    public MainTransformer() {
        System.out.println("ClearView Loading...... v0.00000001 ");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        parent = ClassPool.getDefault();
        parent.insertClassPath(new LoaderClassPath(classLoader));
        child = new ClassPool(parent);
        try {
            child.insertClassPath("./classes");
            child.appendSystemPath();
            child.childFirstLookup = true;


        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("ClearView Loading...... done. ");
    }


    public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain,
                            byte[] bytes) throws IllegalClassFormatException {
        byte[] b = bytes;



        //0. javax.servlet.http.HttpServlet 을 상속받은 모든 클래스를 관제대상으로 한다.
        //TODO : 본 처리에서 모든 container의 최상의 클래스인 위의 클래스를 통해서 In/OUT을 모니터링 하도록 개선해야 Flexible 하게 이식가능하다.
        RequsetResponseSet reqResSet = new RequsetResponseSet();
        b = reqResSet.transformClass4Http(redefiningClass, bytes);


        //1. For HttpRequest, HttpRespose
        //    HttpRequest 와 HttpRespose 를 수집하는 클래스
        if (className.contains("StandardEngineValve")) {
            System.out.println("StandardEngineValve: "+className);
            //StandardEngineValve 는 invoke 메소드 수행한다
            //invoke(Request request, Response response)


            //RequsetResponseSet reqResSet = new RequsetResponseSet();
            b = reqResSet.transformClass(redefiningClass, bytes);
        }

        // jsp 내의  개인정보를 찾아낸다.
        if(className.contains("HttpJspBase")){
            System.out.println("HttpJspBase===================================");
            b = reqResSet.securityDetact(redefiningClass, bytes);
        }

        /*if(className.startsWith("org.apache.jsp")){
            System.out.println("=========================>>>className : "+className);
            MakeCallTrace makeCallTrace = new MakeCallTrace();
            return makeCallTrace.detectSecurityInfo(txid, redefiningClass, bytes);
        }*/


        if (className.contains("com/mysql/jdbc/StatementImpl") || className.contains("com/mysql/jdbc/ConnectionImpl")) {

            //System.out.println("============================================================================================="+className);
            //2. Query 및 수행 속도를 뽑아 낸다.
            //if (className.contains("Connection")) {
            MakeCallTrace makeCallTrace = new MakeCallTrace();
            return makeCallTrace.transformClass(redefiningClass, bytes);
        }/*else {
            //System.out.println("StandardEngineValve: "+className);
            RequsetResponseSet reqResSet = new RequsetResponseSet();
            b = reqResSet.transformClass4Http(redefiningClass, bytes);
        }*//*else{
            MakeCallTrace makeCallTrace = new MakeCallTrace();
            return makeCallTrace.transformClass2(redefiningClass, bytes);
        }*/
        //}
        //}
        return b;
    }
    public byte[] transform2(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain,
                             byte[] bytes) throws IllegalClassFormatException {
        byte[] b = bytes;

        MakeCallTrace makeCallTrace = new MakeCallTrace();
        return makeCallTrace.transformClass2(redefiningClass, bytes);
        //}
        //return b;
    }

}
