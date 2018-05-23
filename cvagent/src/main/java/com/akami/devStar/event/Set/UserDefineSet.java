package com.akami.devStar.event.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class UserDefineSet {
	ClassPool pool = null;

	public byte[] transformClass(Class classToTransform, byte[] b) {
		
		 
		
		
		CtClass cl = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get ( "com/akami/board/contorller/BoardController");
			 CtMethod m = cc.getDeclaredMethod ( "listAll");
			 m.insertBefore ( "{System.out.println (\"Good night!\");}");
			 cc.writeFile ();
			 System.out.println("--------------------");
			 
			pool.insertClassPath(new LoaderClassPath(classLoader));
			if (pool != null) {
				cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
				if (cl.isInterface() == false) {
					
					CtBehavior[] methods = cl.getDeclaredBehaviors();
					for (int i = 0; i < methods.length; i++) {
						System.out.println("methods[" + i + "]: " + methods[i].getName());
						if (methods[i].isEmpty() == false) {
							
							doTransform(methods[i]);
							addTiming(cl,methods[i].getLongName());
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

	private void doTransform(CtBehavior method) throws NotFoundException, CannotCompileException {
		// if (method.getName().equals("invoke")) {

		try {
			method.addLocalVariable("_dev_startTime", CtClass.longType);
			method.insertBefore("_dev_startTime = System.currentTimeMillis();");
			// method.insertAfter("System.out.println(\"Execution Duration (ms
			// sec): \"+ (System.currentTimeMillis() - _dev_startTime) );");
		} catch (Exception e) {
			System.err.println("Aa e: " + e);
		}

	}
	
	private static void addTiming(
									CtClass clas, 
									String mname
									)
									throws NotFoundException, CannotCompileException {
	         
	        //  get the method information (throws exception if method with
	        //  given name is not declared directly by this class, returns
	        //  arbitrary choice if more than one with the given name)
	        CtMethod mold = clas.getDeclaredMethod(mname);
	         
	        //  rename old method to synthetic name, then duplicate the
	        //  method with original name for use as interceptor
	        String nname = mname+"$impl";
	        mold.setName(nname);
	        CtMethod mnew = CtNewMethod.copy(mold, mname, clas, null);
	         
	        //  start the body text generation by saving the start time
	        //  to a local variable, then call the timed method; the
	        //  actual code generated needs to depend on whether the
	        //  timed method returns a value
	        String type = mold.getReturnType().getName();
	        StringBuffer body = new StringBuffer();
	        body.append("{\nlong start = System.currentTimeMillis();\n");
	        if (!"void".equals(type)) {
	            body.append(type + " result = ");
	        }
	        body.append(nname + "($$);\n");
	         
	        //  finish body text generation with call to print the timing
	        //  information, and return saved value (if not void)
	        body.append("System.out.println(\"Call to method " + mname +
	            " took \" +\n (System.currentTimeMillis()-start) + " +
	            "\" ms.\");\n");
	        if (!"void".equals(type)) {
	            body.append("return result;\n");
	        }
	        body.append("}");
	         
	        //  replace the body of the interceptor method with generated
	        //  code block and add it to class
	        mnew.setBody(body.toString());
	        clas.addMethod(mnew);
	         
	        //  print the generated code block just to show what was done
	        System.out.println("Interceptor method body:");
	        System.out.println(body.toString());
	    }

}
