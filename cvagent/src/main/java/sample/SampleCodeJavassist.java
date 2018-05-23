package sample;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class SampleCodeJavassist {
	public static void main(String[] args) throws Exception {
        /* Create object of ClassPool */
        ClassPool objClassPool = ClassPool.getDefault();
        /**
         * Create an object of CtClass.
         * Get the instance of class you want to inject the code.
         * Be careful while accessing the class. You have to specify whole the package name.
         */
        CtClass objCtClass = objClassPool.get("CommonUtil");
        /**
         * Create an object of CtMethod.
         * Get the method where you want to inject the code.
         */
        CtMethod objCtMethod = objCtClass.getDeclaredMethod("printToConsole");
        /* Inject the code at the begging of the method */
        objCtMethod.insertBefore("{ System.out.println(\"Hello World! at the beggining of method [Runtime from Test class]\"); }");
        /* Inject the code at the end of the method */
        objCtMethod.insertAfter("{ System.out.println(\"Hello World! at the end of method [Runtime from Test class]\"); }");
        /* Create an object of Class */
        Class objClass = objCtClass.toClass();
        /* Create an instance of CommonUtil class using objClass */
        CommonUtil objCommonUtil = (CommonUtil)objClass.newInstance();
        /* Execute method printToConsole */
        objCommonUtil.printToConsole();
    }
}

