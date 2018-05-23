package com.akami.devStar.event.Set;


import javassist.*;

import javax.jms.JMSException;


public class HelloSet {

    ClassPool parent = null;
    ClassPool child = null;
    CtClass statement = null;

    public byte[] transformClass(Class classToTransform, byte[] b) {
        CtClass currentClass = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            parent = ClassPool.getDefault();
            parent.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(parent);
            child.insertClassPath("./classes");
            child.appendSystemPath();
            child.childFirstLookup = true;


            //if (parent != null) {
            currentClass = parent.makeClass(new java.io.ByteArrayInputStream(b));

            statement = child.get("java.sql.Connection");
           // java.sql.PreparedStatement
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

            }
            // }

        } catch (Exception e) {
            System.err.println("e111: " + e);
        } finally {
            if (currentClass != null) {
                currentClass.detach();
            }
        }
        return b;
    }

    private void doTransform(CtBehavior method) throws NotFoundException, CannotCompileException, JMSException {

        try {

            method.insertBefore("System.out.println(\"akami fighting\");"

            );

            method.insertAfter(""

            );
        } catch (Exception e) {
            System.err.println("~~~~~~");
            e.printStackTrace();
        }
        //}else{
        //	System.out.println("12313123213213123123123123123:"+method.getName());
        //}

    }


}
