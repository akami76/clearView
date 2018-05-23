import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by akamikang on 2017. 7. 18..
 */
public class JdbcQueryTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

        byte[] byteCode = bytes;

        ClassPool pool = ClassPool.getDefault();
        CtClass currentClass = null;
        CtClass statement = null;

        try {


            currentClass = pool.makeClass(new java.io.ByteArrayInputStream(bytes));
            statement = pool.get("java.sql.Statement");
            if (currentClass.subtypeOf(statement) && !currentClass.isInterface()) {




                //System.out.println("111111111111111111111111");
               // System.out.println("statement : "+ statement.getName());



               CtMethod executeQuery = statement.getDeclaredMethod("executeQuery");


                System.out.println("executeQuery.getLongName() : "+executeQuery.getLongName());




                CtBehavior[] methods = statement.getDeclaredBehaviors();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].isEmpty() == false) {
                        //changeMethod(methods[i]);
                    }
                }

               if(executeQuery != null) {
                  // executeQuery.addLocalVariable("elapsedTime", CtClass.longType);
                  /* executeQuery.insertBefore(
                           "elapsedTime = System.currentTimeMillis();" +
                                   "System.out.println( \" :\" + $1);" +
                                   "");
                   executeQuery.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                           + "System.out.println(\"SQL Executing in ms: \" + elapsedTime);}");*/

                   executeQuery.insertBefore("try {System.out.println(\"probeStatement\"+$1);} catch (Exception e){e.printStackTrace(); System.out.println(\"no sql\");}");


               }
               /* CtMethod m = currentClass.getDeclaredMethod("executeQuery");
                m.addLocalVariable("elapsedTime", CtClass.longType);
                m.insertBefore(
                        "elapsedTime = System.currentTimeMillis();" +
                                "System.out.println( \" :\" + $1);" +
                                "");
                m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                        + "System.out.println(\"SQL Executing in ms: \" + elapsedTime);}");*/
                //currentClass.writeFile();
                byteCode = statement.toBytecode();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.detach();
            }
        }

        return byteCode;
    }

    private void probeStatement(CtClass currentClass) throws NotFoundException, CannotCompileException {

        CtMethod executeQuery = null;

        try {
            System.out.println("========================47 line");
            executeQuery = currentClass.getDeclaredMethod("executeQuery");
            System.out.println("========================49 line");
        } catch (NotFoundException e) {
            e.printStackTrace();
            return;
        }
        executeQuery.insertBefore("try {System.out.println(\"probeStatement\"+$1);} catch (Exception e){e.printStackTrace(); System.out.println(\"no sql\");}");
    }

}
