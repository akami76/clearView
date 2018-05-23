package com.akami.transformer.biz;

import com.LogTargetClass;
import com.akami.com.Contants;
import javassist.*;

import javax.jms.JMSException;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Matcher;


public class MakeCallTrace {
    ClassPool parent = null;
    ClassPool child = null;
    CtClass statement = null;
    LogTargetClass logTargetClass = null;


    public MakeCallTrace() {
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
    }

   /* public byte[] transformClass(Class classToTransform, byte[] b) {
        CtClass currentClass = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(pool);
            try {
                child.insertClassPath("./classes");
                child.appendSystemPath();
                child.childFirstLookup = true;


            } catch (NotFoundException e) {
                e.printStackTrace();
            }

            if (child != null) {
                currentClass = child.makeClass(new java.io.ByteArrayInputStream(b));

                statement = child.get("java.sql.Connection");
                if (currentClass.subtypeOf(statement) && !currentClass.isInterface()) {
                    System.out.println("currentClass : " + currentClass.getName());
                    //CtMethod methods[] = currentClass.getMethods();
                    CtMethod executeQuery = null;
                    if (currentClass.isInterface() == false) {
                        CtBehavior[] methods = currentClass.getDeclaredBehaviors();
                        for (int i = 0; i < methods.length; i++) {
                            if (methods[i].isEmpty() == false) {
                                System.out.println(" methods[" + i + "]: " + methods[i].getLongName());
                                //doTransform(methods[i]);


                            }
                        }
                    }
                    b = currentClass.toBytecode();
                }


            }
        } catch (Exception e) {
            System.out.println("e111: " + e);
        } finally {
            if (currentClass != null) {
                currentClass.detach();
            }
        }
        return b;
    }*/

    public byte[] transformClass(Class classToTransform, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        byte[] byteCode = classfileBuffer;


        CtClass currentClass = null;
        CtClass preparedStatement = null;
        CtClass statement = null;

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(pool);
            try {
                child.insertClassPath("./classes");
                child.appendSystemPath();
                child.childFirstLookup = true;


            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            currentClass = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));
            //System.out.println("==000> " + currentClass.getName());
            //if (currentClass.isAnnotation()) {
              //  System.out.println("==0001> " + currentClass.getName());
                //return byteCode;
            //} else if (currentClass.isFrozen()) {
              //  System.out.println("==0003> " + currentClass.getName());
                //return byteCode;
            //} else if(!logTargetClass.isTarget(currentClass.getName())){
             //   return byteCode;
            //}

            //java.sql.Statement
            statement = child.get("java.sql.Statement");
            //java.sql.preparedStatement
            preparedStatement = child.get("java.sql.Connection");
            CtClass etype = ClassPool.getDefault().get("java.lang.Exception");
            CtMethod[] methods = null;
            if (currentClass.subtypeOf(statement)  ) {
                //System.out.println("==11111> " + currentClass.getName());
                 methods = currentClass.getMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].getLongName().contains("executeQuery(java.lang.String)")) {

                        //System.out.println("====================> " + methods[i].getLongName());
                        methods[i].addLocalVariable("__elapsedTime", CtClass.longType);
                        methods[i].addLocalVariable("__tid", CtClass.longType);
                        methods[i].insertBefore(
                                "__elapsedTime = System.currentTimeMillis();"
                               // + " __tid =  Thread.currentThread().setName(System.nanoTime()+\"_\"+Thread.currentThread().getId()); "
                                + " __tid =  Thread.currentThread().getId()); "
                                // +"System.out.println( \" SQL :\" + $1);"
                        );
                        methods[i].insertAfter(
                                "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" +
                                        " String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));" +
                                        " System.out.println( \"[SQL \"+ __tid +\" \t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + methods[i].getLongName() + "\t\"+$1);"
                        );
                    }
                }
            }else if(currentClass.subtypeOf(preparedStatement) ) {
                methods = currentClass.getMethods();

                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].getLongName().contains("prepareStatement(java.lang.String)")) {

                        //System.out.println("====================> " + methods[i].getLongName());
                        methods[i].addLocalVariable("__elapsedTime", CtClass.longType);
                        methods[i].addLocalVariable("__tid", CtClass.longType);
                        methods[i].insertBefore(
                                "__elapsedTime = System.currentTimeMillis();"
                                       // + " __tid =  Thread.currentThread().setName(System.nanoTime()+\"_\"+Thread.currentThread().getId()); "
                                        + " __tid =  Thread.currentThread().getId(); "
                                // +"System.out.println( \" SQL :\" + $1);"
                        );
                        methods[i].insertAfter(
                                "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" +
                                        " String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));" +
                                        " System.out.println( \"[SQL \"+ __tid +\" \t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + methods[i].getLongName() + "\t\"+$1);"
                        );
                    }
                }

            }
            return currentClass.toBytecode();
        } catch (Exception e) {
            ///e.printStackTrace();
        } finally {
            currentClass.detach();
        }
        return byteCode;
    }


    /*public byte[] detectSecurityInfo(long txid, Class classToTransform, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        byte[] byteCode = classfileBuffer;


        CtClass currentClass = null;
        CtClass jspServlet = null;

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(pool);
            try {
                child.insertClassPath("./classes");
                child.appendSystemPath();
                child.childFirstLookup = true;


            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            currentClass = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));


            System.out.println("currentClass : "+currentClass.getName());


            return currentClass.toBytecode();
        } catch (Exception e) {
            ///e.printStackTrace();
        } finally {
            currentClass.detach();
        }
        return byteCode;
    }*/

    public byte[] transformClass2(Class classToTransform, byte[] b) {
        CtClass cl = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(pool);
            try {
                child.insertClassPath("./classes");
                child.appendSystemPath();
               // child.childFirstLookup = true;


            } catch (NotFoundException e) {
                e.printStackTrace();
            }

            if (child != null) {
                cl = child.makeClass(new java.io.ByteArrayInputStream(b));
                if (cl.isInterface() == false) {
                    CtBehavior[] methods = cl.getDeclaredBehaviors();
                    for (int i = 0; i < methods.length; i++) {
                        System.out.println("RequsetResponseSet methods[" + i + "]: " + methods[i].getName());
                        if (methods[i].isEmpty() == false) {
                            //CtMethod[] methods = cl.getDeclaredMethods();
                            methods[i].insertBefore("System.out.println( \" In \" );" );
                            methods[i].insertAfter("System.out.println( \" Out \" );" );
                        }
                    }


                }
                b = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("e111: " + e);
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }


    /*public byte[] transformClass(Class classToTransform, byte[] b) {
        CtClass currentClass = null;
        CtClass etype =null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            parent = ClassPool.getDefault();
            parent.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(parent);
            child.insertClassPath("./classes");
            child.appendSystemPath();
            child.childFirstLookup = true;

            etype = ClassPool.getDefault().get("java.lang.Exception");
            currentClass = parent.makeClass(new java.io.ByteArrayInputStream(b));

            statement = child.get("java.sql.Connection");
            if (currentClass.subtypeOf(statement) && !currentClass.isInterface()) {
                System.out.println("currentClass : " + currentClass.getName());
                CtMethod methods[] = currentClass.getMethods();

                for (int i = 0; i < methods.length; i++) {
                    if ("prepareStatement".equals(methods[i].getName())) {

                        CtMethod executeQuery = null;

                        executeQuery = currentClass.getDeclaredMethod("prepareStatement");


                        //executeQuery.insertBefore("try {System.out.println(\"\"+$1);} catch (Exception e){System.out.println(\"no sql\");}");

                        //if( methods[i].getLongName().equals("com.mysql.jdbc.ConnectionImpl.prepareStatement(java.lang.String)")) {
                        //System.out.println(" >>>>>>: " + methods[i].getName());
                        //System.out.println(" >>>>>>: " + methods[i].getGenericSignature());
                        //System.out.println(" >>>>>>: " + methods[i].getMethodInfo());
                        //System.out.println(" >>>>>>: " + methods[i].getMethodInfo2());
                        //System.out.println(" >>>>>>: " + methods[i].getMethodInfo());
                        //doTransform(methods[i]);


                        if (executeQuery != null) {

                            executeQuery.addLocalVariable("elapsedTime", CtClass.longType);
                            executeQuery.insertBefore(
                                    "elapsedTime = System.currentTimeMillis();" +
                                            "System.out.println( \" :\" + $1);" +
                                            "");
                            executeQuery.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                                    + "System.out.println(\"SQL Executed in ms: \" + elapsedTime);}"
                            );

                        }
                        // }

                    }
                }

                // }
                b = currentClass.toBytecode();

            }else {
                CtMethod[] methods = currentClass.getDeclaredMethods();
                System.out.println("666666666666666 callTrace :" + currentClass.getName());

                *//*if (!Modifier.isNative(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())) {
                    String insertString = "System.out.println(\"started method " + method.getName() + "\");";
                    method.insertBefore(insertString);
                }*//*

                for (int k = 0; k < methods.length; k++) {
                    if (!methods[k].isEmpty()) {
                        if (!Modifier.isNative(methods[k].getModifiers())) {
                            System.out.println("777777 callTrace :" + currentClass.getName());
                            continue;
                        } else if (!Modifier.isAbstract(methods[k].getModifiers())) {
                            System.out.println("888888 callTrace :" + currentClass.getName());
                            continue;
                        } else if (!Modifier.isEnum(methods[k].getModifiers())) {
                            System.out.println("99999 callTrace :" + currentClass.getName());
                            continue;
                        } else if (!Modifier.isStatic(methods[k].getModifiers())) {
                            System.out.println("111111 callTrace :" + currentClass.getName());
                            continue;
                        } else if (!Modifier.isFinal(methods[k].getModifiers())) {
                            System.out.println("222222 callTrace :" + currentClass.getName());
                            continue;
                        } else if (!Modifier.isStrict(methods[k].getModifiers())) {
                            System.out.println("33333 callTrace :" + currentClass.getName());
                            continue;
                        } else if (!Modifier.isFinal(methods[k].getModifiers())) {
                            System.out.println("444444 callTrace :" + currentClass.getName());
                            continue;
                        }

                        if (methods[k].getName().equals("executeQuery")) {
                            continue;
                        }
                        if (methods[k].getName().indexOf("$") > 0) {
                            continue;
                        }
                        if(!Contants.HEAP_CLASS_LIST_SET.contains(methods[k].getLongName())) {
                            methods[k].addLocalVariable("__elapsedTime", CtClass.longType);
                            methods[k].insertBefore(
                                    "__elapsedTime = System.currentTimeMillis();"
                            );
                            methods[k].insertAfter(
                                    "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" +
                                            " String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));" +
                                            " System.out.println( \"[\t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + currentClass.getName() + "." + methods[k].getName() + "\");"
                            );
                            methods[k].addCatch("{ System.out.println(\"catch " + methods[k].getName() + "\");  }", etype);


                            Contants.HEAP_CLASS_LIST_SET.add(methods[k].getLongName());
                        }else{
                            System.out.println("이미 추가됨 : ==================>"+methods[k].getLongName());
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("e111: " + e);
        } finally {
            if (currentClass != null) {
                currentClass.detach();
            }
        }
        return b;
    }*/


    /**/

}



/*else {
                    CtMethod[] methods = currentClass.getDeclaredMethods();
                    System.out.println("666666666666666 callTrace :" + currentClass.getName());

                    for (int k = 0; k < methods.length; k++) {
                        if (!methods[k].isEmpty()) {
                            if ((methods[k].getModifiers() & Modifier.ABSTRACT) > 0) {
                                System.out.println("777777 callTrace :" + currentClass.getName());
                                continue;
                            } else if ((methods[k].getModifiers() & Modifier.NATIVE) > 0) {
                                System.out.println("888888 callTrace :" + currentClass.getName());
                                continue;
                            }

                            if (methods[k].getName().equals("executeQuery")) {
                                continue;
                            }
                            if (methods[k].getName().indexOf("$") > 0) {
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
                            methods[k].addCatch("{ System.out.println(\"catch " + methods[k].getName() + "\");  }", etype);
                        }
                    }
                }*/