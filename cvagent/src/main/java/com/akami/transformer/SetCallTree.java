package com.akami.transformer;

import com.LogTargetClass;
import com.akami.devStar.event.Set.HelloSet;
import com.akami.devStar.event.Set.RequsetResponseSet;
import com.akami.transformer.biz.MakeCallTrace;
import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SetCallTree implements ClassFileTransformer {
    static ClassPool parent = null;
    static ClassPool child = null;

    public SetCallTree() {


        System.out.println("SetCallTree Loading...... v0.00000001 ");
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
        System.out.println("SetCallTree Loading...... done. ");
    }





    public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain,
                            byte[] bytes) throws IllegalClassFormatException {
        CtClass currentClass = null;
        //LogTargetClass logTargetClass = new LogTargetClass();
        byte[] b =  bytes;
        try {
            currentClass = child.makeClass(new java.io.ByteArrayInputStream(b));

           // System.out.println(currentClass.getName() + "logTargetClass.isTarget(currentClass.getName() : "+ logTargetClass.isTarget(currentClass.getName()));

            if (currentClass.isInterface()) {
                return b;
           // } else if (!logTargetClass.isTarget(currentClass.getName())) {
             //   return b;
            }
            // System.out.println("==========: " + className );
            //2. Query 및 수행 속도를 뽑아 낸다.
            if (className.contains("Connection")) {
                //System.out.println("69 line SetCallTree : " + className );
                //StandardEngineValve 는 invoke 메소드 수행한다
                //invoke(Request request, Response response)
                MakeCallTrace makeCallTrace = new MakeCallTrace();
//TODO 본 메소드 사용한다면 파라미터 수정해야함
                return makeCallTrace.transformClass(redefiningClass,b);
                //} else {
                //   return bytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        return b;


    //}



       /* CtClass currentClass = null;
        CtClass statement = null;

        try {
            currentClass = child.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));

            System.out.println("4 callTrace :" + currentClass.getName() +" " + className );


            if (currentClass.isInterface()) {
                return byteCode;
            } else if (!logTargetClass.isTarget(currentClass.getName())) {
                return byteCode;
            }

            System.out.println("5 callTrace :" + currentClass.getName()  );


            statement = child.get("java.sql.Statement");
            System.out.println("statement.getName() : "+statement.getName());
            CtClass etype = ClassPool.getDefault().get("java.lang.Exception");

            if (currentClass.subtypeOf(statement) && !currentClass.isInterface()) {

                CtMethod m = currentClass.getDeclaredMethod("executeQuery");
                m.addLocalVariable("__elapsedTime", CtClass.longType);
                m.insertBefore(
                        "__elapsedTime = System.currentTimeMillis();" +
                                "System.out.println( \" SQL :\" + $1);");
                m.insertAfter(
                        "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" +
                                " String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));" +
                                " System.out.println( \"[\t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + currentClass.getName() + "." + m.getName() + "\");"
                );


            } else {
                CtMethod[] methods = currentClass.getDeclaredMethods();

                for (int k = 0; k < methods.length; k++) {
                    if (!methods[k].isEmpty()) {
                        if ((methods[k].getModifiers() & Modifier.ABSTRACT) > 0) {
                            continue;
                        } else if ((methods[k].getModifiers() & Modifier.NATIVE) > 0) {
                            continue;
                        }

                        if (methods[k].getName().equals("executeQuery")) {
                            continue;
                        }
                        methods[k].addLocalVariable("__elapsedTime", CtClass.longType);
                        methods[k].insertBefore(
                                "__elapsedTime = System.currentTimeMillis();"
                        );
                        methods[k].insertAfter(
                                "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" +
                                        " String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));" +
                                        " System.out.println( \"[\t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + currentClass.getName() + "." + methods[k].getName() + "\");"
                        );
                        methods[k].addCatch("{ System.out.println(\"catch " + methods[k].getName() + "\");  $e.printStackTrace();}", etype);
                    }
                }
            }
            return currentClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            currentClass.detach();
        }*/
        //return byteCode;

    }

    private byte[] transformClass(Class<?> classToTransform, byte[] b, String className) {
        System.out.println("--------------------------------className : " + className);
        //ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            //cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
            cl = child.get(className);

            b = cl.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }

}
