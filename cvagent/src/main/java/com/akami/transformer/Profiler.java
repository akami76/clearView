package com.akami.transformer;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Profiler implements ClassFileTransformer {
    protected static Profiler profilerInstance = null;
    protected Instrumentation instrumentation = null;
    protected ClassPool classPool;

    public static void premain(String agentArgs, Instrumentation inst) {
        Profiler profiler = new Profiler(inst);
    }

    public static Profiler getInstance() {
        return profilerInstance;
    }

    public Profiler(Instrumentation inst) {
        profilerInstance = this;
        instrumentation = inst;
        classPool = ClassPool.getDefault();
        instrumentation.addTransformer(this);
    }

    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        classPool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));
        CtClass cc = null;
        try {
            cc = classPool.get(className);
        } catch (NotFoundException nfe) {
            System.err.println("NotFoundException: " + nfe.getMessage() + "; transforming class " + className + "; returning uninstrumented class");
        }
        CtMethod[] methods = cc.getMethods();
        for (int k = 0; k < methods.length; k++) {
            try {
                methods[k].insertBefore("com.akami.transformer.Profiler.getInstance().notifyMethodEntry(\"" + className + "\", \"" + methods[k].getName() + "\");");
                methods[k].insertAfter("com.akami.transformer.Profiler.getInstance().notifyMethodExit(\"" + className + "\", \"" + methods[k].getName() + "\");");
            } catch (CannotCompileException cce) {
                System.err.println("CannotCompileException: " + cce.getMessage() + "; instrumenting method " + methods[k].getLongName() + "; method will not be instrumented");
            }
        }
        // return the new bytecode array:
        byte[] newClassfileBuffer = null;
        try {
            newClassfileBuffer = cc.toBytecode();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage() + "; transforming class " + className + "; returning uninstrumented class");
            return null;
        } catch (CannotCompileException cce) {
            System.err.println("CannotCompileException: " + cce.getMessage() + "; transforming class " + className + "; returning uninstrumented class");
            return null;
        }
        return newClassfileBuffer;
    }

    public void notifyMethodEntry(String className, String methodName) {
        System.out.println("Thread '" + Thread.currentThread().getName() + "' entering method '" + methodName + "' of class '" + className + "'");
    }

    public void notifyMethodExit(String className, String methodName) {
        System.out.println("Thread '" + Thread.currentThread().getName() + "' exiting method '" + methodName + "' of class '" + className + "'");
    }
}