package com.akami.devStar.event.Set;


import com.akami.com.ThreadManager;
import com.akami.thread.ThreadUtil;
import javassist.*;

import javax.jms.JMSException;
import javax.servlet.jsp.JspWriter;
import java.util.concurrent.Executor;


public class RequsetResponseSet {
    ClassPool pool = null;
    ClassPool child = null;


    public byte[] transformClass( Class classToTransform, byte[] b) {
        CtClass cl = null;
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
                cl = child.makeClass(new java.io.ByteArrayInputStream(b));
                //CtMethod[] methods = cl.getMethods();

                System.out.println("CI : "+cl.getName());
                //ThreadUtil.getThreadDetail(Thread.currentThread().getId());
                if (cl.isInterface() == false) {
                    CtBehavior[] methods = cl.getDeclaredBehaviors();
                    for (int i = 0; i < methods.length; i++) {
                        System.out.println("RequsetResponseSet methods[" + i + "]: " + methods[i].getLongName());
                        if (methods[i].isEmpty() == false) {
                            System.out.println(methods[i].getLongName());
                            doTransform( methods[i]);


                            /*System.out.println("72 line====>>>> ");
                            //methods[i].insertBefore("{ com.akami.context.CommonDeco.beforeHttpService($1,$2); }");
                            methods[i].insertBefore("System.out.println(\"HI : \"+ $1 );");

                            System.out.println("74 line ");
                            // methods[i].insertAfter("com.akami.context.CommonDeco.afterHttpService($1,$2);");
                            System.out.println("76 line ");*/


                        }
                    }
                }
                b = cl.toBytecode();
            }
        } catch (Exception e) {
            System.out.println("e50: " + e);
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }
    public byte[] securityDetact( Class classToTransform, byte[] b) {
        CtClass cl = null;
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
                cl = child.makeClass(new java.io.ByteArrayInputStream(b));
                //CtMethod[] methods = cl.getMethods();

                System.out.println("CI : "+cl.getName());
                if (cl.isInterface() == false) {
                    CtBehavior[] methods = cl.getDeclaredBehaviors();
                    for (int i = 0; i < methods.length; i++) {
                        if(!methods[i].isEmpty()) {
                            System.out.println(":::>>> " + methods[i].getLongName());
                            if (methods[i].getLongName().contains("org.apache.jasper.runtime.HttpJspBase.service")) {
                                methods[i].insertBefore("System.out.println(\"=start=============\");" +
                                        "System.out.println($1);" );


                                methods[i].insertAfter("System.out.println(\"=after=============\");" +
                                        "System.out.println($1);");
                            }
                        }



                    }
                }
                b = cl.toBytecode();
            }
        } catch (Exception e) {
            System.out.println("e50: " + e);
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }




    public byte[] transformClass4Http(Class classToTransform, byte[] b) {
        CtClass cl = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            CtClass HttpServlet = null;
            CtClass threadChild = null;

            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(pool);
            try {
                child.insertClassPath("./classes");
                child.appendSystemPath();
                child.childFirstLookup = true;
                HttpServlet = child.getCtClass("javax.servlet.http.HttpServlet");

                int methodIndex = 0;

                if (child != null) {
                    cl = child.makeClass(new java.io.ByteArrayInputStream(b));
                    //CtMethod[] methods = cl.getMethods();
                    if (cl.subtypeOf(HttpServlet)) {
                        //System.out.println(">>>>>1111111111111111 : "+cl.getName());
                        if (cl.isInterface() == false) {

                            CtBehavior[] methods = cl.getDeclaredBehaviors();
                            StringBuilder sbs = new StringBuilder();
                            for (int i = 0; i < methods.length; i++) {
                                if (methods[i].isEmpty() == false) {

                                    methods[i].addLocalVariable("__elapsedTime", CtClass.longType);
                                    methods[i].addLocalVariable("__tid", CtClass.longType);

                                    sbs = new StringBuilder();
                                    sbs.append( "__elapsedTime = System.currentTimeMillis();       "        )
                                            .append("   __tid  = Thread.currentThread().getId();      "     )
                                            .append(" if(Thread.currentThread().getName().length() < 10 ){ "    )
                                            .append("      Thread.currentThread().setName(System.nanoTime()+\"_\"+Thread.currentThread().getId());"           )
                                            .append(" } ");

                                    methods[i].insertBefore(sbs.toString());


                                    //TODO jsp 변환 시점에 개인정보가 있으면 별도 처리 한다.
                                    if(methods[i].getLongName().contains("_jspService(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)")){

                                        String methodLongName = methods[i].getLongName();
                                        sbs = new StringBuilder();
                                        sbs.append( "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" )
                                                .append("String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));"                  )
                                                .append("org.json.JSONObject callTraceJson = new org.json.JSONObject();"                                     )
                                                .append("String _cv_agent_id = java.lang.System.getProperty(\"clearview.id\");"                              )
                                                //.append("String methodLongName =\"").append(methodLongName).append("\" ;"                                  )
                                                .append("callTraceJson.put(\"cv_agent_id\", _cv_agent_id );"                                                 )
                                                .append("callTraceJson.put(\"thread_id\",  __tid);"                                                          )
                                                .append("callTraceJson.put(\"elapsedTimeStr\", __elapsedTimeStr);"                                           )
                                                .append("callTraceJson.put(\"methodLongName\", \""+methodLongName +"\");"                                    )
                                                .append("com.akami.jmsclient.Producer callTraceSender = com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL[com.akami.com.TOPIC.CV_CALL_TREE.getIndex()];")
                                                .append("System.out.println(\"  ● callTraceJson.toString() :\"+callTraceJson.toString());")
                                                .append("callTraceSender.produceMessage(callTraceJson.toString());");

                                        methods[i].insertAfter(sbs.toString());

                                        /*methods[i].insertAfter(
                                                "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" +
                                                        " String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));" +
                                                       // " System.out.println( \"1 JspWriter out \" + out.write());" +
                                                       // " System.out.println( \"2 JspWriter out \" + out.toString());" +
                                                        " System.out.println( \"[ \t \"+ __tid +\"\t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + methods[i].getLongName() + "\t\");"
                                                // " System.out.println( \"[ \t \"+ com.akami.com.ThreadManager.getThreadDetail(__tid) +\" \t\"+__elapsedTimeStr +\" \t\"+(System.currentTimeMillis() - __elapsedTime) + \" ms \t]\t " + methods[i].getLongName() + "\t\");"
                                        );

                                       */
                                    }else{
                                        String methodLongName = methods[i].getLongName();
                                        sbs = new StringBuilder();
                                        sbs.append( "java.text.SimpleDateFormat __timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");" )
                                                .append("String __elapsedTimeStr = __timeFormat.format(new java.util.Date(__elapsedTime));"                  )
                                                .append("org.json.JSONObject callTraceJson = new org.json.JSONObject();"                                     )
                                                .append("String _cv_agent_id = java.lang.System.getProperty(\"clearview.id\");"                              )
                                                //.append("String methodLongName =\"").append(methodLongName).append("\" ;"                                  )
                                                .append("callTraceJson.put(\"cv_agent_id\", _cv_agent_id );"                                                 )
                                                .append("callTraceJson.put(\"thread_nm\",  Thread.currentThread().getName());"                                                          )
                                                .append("callTraceJson.put(\"elapsedTimeStr\", __elapsedTimeStr);"                                           )
                                                .append("callTraceJson.put(\"methodLongName\", \""+methodLongName +"\");"                                    )
                                                .append("com.akami.jmsclient.Producer callTraceSender = com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL[com.akami.com.TOPIC.CV_CALL_TREE.getIndex()];")
                                                .append("System.out.println(\"  ● callTraceJson.toString() :\"+callTraceJson.toString());")
                                                .append("callTraceSender.produceMessage(callTraceJson.toString());");

                                        methods[i].insertAfter(sbs.toString());


                                    }


                                    //doTransform(methods[i]);
                                }
                            }
                        }
                    }
                    //System.out.println("CI : "+cl.getName());

                    b = cl.toBytecode();
                }


            } catch (NotFoundException e) {
                // e.printStackTrace();
            }


        } catch (Exception e) {
            System.out.println("여기 ??: " + e);
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }



   /* public byte[] transformClass2(Class classToTransform, byte[] b) {
        CtClass currentClass = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            parent = ClassPool.getDefault();
            parent.insertClassPath(new LoaderClassPath(classLoader));
            child = new ClassPool(parent);
            child.insertClassPath("./classes");
            //child.appendSystemPath();
            child.childFirstLookup = true;
            if (parent != null) {
                currentClass = parent.makeClass(new java.io.ByteArrayInputStream(b));

                *//*statement = child.get("com.Hello");
                if (statement != null) {
                    System.out.println("***hello : " + child.get("com.Hello"));
                    System.out.println("***hello.getName() : " + statement.getName());
                    CtMethod m = statement.getDeclaredMethod("say");
                    m.insertAfter("System.out.println(\"안녕 강백석 \"");

                }
*//*

                if (currentClass.isInterface() == false) {
                    System.out.println("***currentClass : " + currentClass.getName());
                    CtBehavior[] methods = currentClass.getDeclaredBehaviors();

                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].isEmpty() == false) {
                            doTransform(methods[i]);
                        }


                    }


                }
                b = currentClass.toBytecode();
            }

            //byteCode = currentClass.toBytecode();
            //return byteCode;


        } catch (Exception e) {
            //System.out.println("e111: " + e);
        } finally {
            if (currentClass != null) {
                currentClass.detach();
            }
        }
        return b;
    }*/

    private void doTransform( CtBehavior method) throws NotFoundException, CannotCompileException, JMSException {
        System.out.println("****************RequsetResponseSet : 58:" + method.getLongName());
        //org.apache.catalina.core.StandardEngineValve.invoke(org.apache.catalina.connector.Request,org.apache.catalina.connector.Response)
        try {

            method.addLocalVariable("_tid", CtClass.longType);
            method.insertBefore(""
                            //1. _cv_req_id : 호출된 한 서비스와 호출된 값을 서로 맵핑 하기 위한 식별값 nano 값과 세션 ID를 사용한다.
                            //+ " try{"
                            //+ " System.out.println(\"1~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\");"
                            + "String _cv_req_id = System.nanoTime()+$1.getSession().getId();"
                            //+ " System.out.println(\"2~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\");"
                            + " $1.setAttribute(\"_cv_req_id\", _cv_req_id);"
                            //+ " System.out.println(\"3~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\");"
                            //2. _cv_cookie_id : clearview에서 식별한 사용자 구분 값 쿠키를 생성한다.
                            + "javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) $2;"
                            //+ "response.sendRedirect(\"/\");"
                            //3. _cv_agent_id : 수많은 agent 중 식별 값 (-Dclearview.id=a01)
                            + "String _cv_agent_id = java.lang.System.getProperty(\"clearview.id\");"
                            //4. sessionId : 브라우저 세션 ID
                            + "String sessionId = $1.getSession().getId();"
                            //5. clientIP : 사용자 IP 정보
                            + "String clientIP = $1.getRemoteAddr();"
                            //6. 접근 URI
                            + "String uri = $1.getRequestURI();"
//						+ "javax.servlet.http.HttpServletRequest httpRequest = (javax.servlet.http.HttpServletRequest) $1;"
                            //7. cententType
                            + "String contentType = $1.getContentType();"
                            //8. get / post 여부
                            + "String method = $1.getMethod();"

                            + "long time = System.currentTimeMillis(); "
                            + "java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");"
                            //9. _cv_req_s_time	: 서비스 호출 시간
                            + "String _cv_req_s_time = timeFormat.format(new java.util.Date(time));"
                            //현재시간을 request에 담아놓는다.
                            + " $1.setAttribute(\"_cv_req_s_time\", System.currentTimeMillis()+\"\");"
                            //clear view 에서 유일하게 식별할 수 있는 key 값도 셋팅한다.
                            //10. userAgent
                            + "String userAgent = $1.getHeader(\"User-Agent\");"
                            //+ " System.out.println(\"◼︎Request Object_______________________\");"
                            //11. userBodyParam : user parameter 정보
                            + " java.util.Enumeration params = $1.getParameterNames();"
                            + " org.json.JSONObject jo = new org.json.JSONObject();"
                            //+ " String userBodyParam ;"
                            + "while(params.hasMoreElements()){"
                            + "		String names = (String)params.nextElement();"
                            //+ " System.out.println(\"     ‣ \"+ names + \" : \" + $1.getParameter(names));"
                            + "  jo.put(names, $1.getParameter(names)); "
                            //+ "     userBodyParam.append(names+$1.getParameter(names));"
                            + " }"
                            //12. userHeaderParam
                            + " java.util.Enumeration hparams = $1.getHeaderNames();"
                            + " org.json.JSONObject joh = new org.json.JSONObject();"
                            //+ " String userBodyParam ;"
                            + "while(hparams.hasMoreElements()){"
                            + "		String names = (String)hparams.nextElement();"
                            //+ " System.out.println(\"     ‣ \"+ names + \" : \" + $1.getHeader(names));"
                            + "  joh.put(names, $1.getHeader(names)); "
                            //+ "     userBodyParam.append(names+$1.getParameter(names));"
                            + " }"
                            + " joh.put(\"_cv_req_id\",_cv_req_id);"
                            + " joh.put(\"sessionId\",sessionId);"
                            + " String userBodyParam  = jo.toString();"
                            + " String userHeaderParam  = joh.toString();"
                            + " org.json.JSONObject cJson = new org.json.JSONObject();"
                            + " cJson.put(\"cv_req_id\", _cv_req_id);"
                            + " cJson.put(\"cv_agent_id\", _cv_agent_id);"
                            + " cJson.put(\"sessionId\", sessionId);"
                            + " cJson.put(\"clientIP\", clientIP);"
                            + " cJson.put(\"uri\", uri);"
                            + " cJson.put(\"contentType\", contentType);"
                            + " cJson.put(\"method\", method);"
                            + " cJson.put(\"cv_req_start_time\", _cv_req_s_time);"
                            + " cJson.put(\"userAgent\", userAgent);"
                            + " cJson.put(\"userBodyParam\", userBodyParam);"
                            + " cJson.put(\"userHeaderParam\", userHeaderParam);"
                            // + " _tid =  Thread.currentThread().setName(System.nanoTime()+\"_\"+Thread.currentThread().getId()); "
                            + " _tid =  Thread.currentThread().getId(); "
                            + " cJson.put(\"txid\", _tid);"


                            //+ " org.json.JSONObject reqJson = new org.json.JSONObject();"
                            ///+ " reqJson.put(\"_cv_req_id\", _cv_req_id);"
                            //+ " reqJson.put(\"sessionId\", sessionId);"
                            //+ " reqJson.put(\"parameters\", userBodyParam);"


                            //+ " System.out.println(\"  ● _cv_req_id :\"+ _cv_req_id);"
                            //+ " System.out.println(\"  ● _cv_cookie_id :\"+ _cv_cookie_id);"
                            //+ " System.out.println(\"  ● _cv_agent_id :\"+ _cv_agent_id);"
                            //+ " System.out.println(\"  ● sessionId :\"+ sessionId);"
                            //+ " System.out.println(\"  ● clientIP :\"+ clientIP);"
                            //+ " System.out.println(\"  ● uri :\"+ uri);"
                            //+ " System.out.println(\"  ● contentType :\"+ contentType);"
                            //+ " System.out.println(\"  ● method :\"+ method);"
                            //+ " System.out.println(\"  ● _cv_req_s_time :\"+ _cv_req_s_time);"
                            //+ " System.out.println(\"  ● userAgent :\"+ userAgent);"
                            //+ " System.out.println(\" =====================================\");"
                            //+ " com.akami.jmsclient.Producer producer = com.akami.jmsclient.Producer.getInstance(/"/",/"cv_reqH/");"
                            //+ " producer.produceMessage(userHeaderParam);"
                            //+ " producer.produceMessage(userBodyParam);"
                            //+ " producer.produceMessage(cJson.toString());"
                            //+ " System.out.println(\"  pCVREqH :\"+ pCVREqH.);"
                            //+ "}catch(java.lang.Exception e){"
                            //+ "  System.out.println(\"clear view error : \" + e);"
                            //+ "}"
                            //+ " System.out.println(\"  ● com.akami.com.TOPIC.CV_REQ_H.getTopicName() :\"+ com.akami.com.TOPIC.CV_REQ_H.getTopicName());"
                            ///	+ " System.out.println(\"  ● com.akami.com.TOPIC.CV_REQ_B.getTopicName() :\"+ com.akami.com.TOPIC.CV_REQ_B.getTopicName());"
                            //+ " System.out.println(\"  ● com.akami.com.TOPIC.CV_VAL.getTopicName() :\"+ com.akami.com.TOPIC.CV_VAL.getTopicName());"
                            //+ " System.out.println(\"  ● userHeaderParam :\"+ userHeaderParam);"
                            //+ " System.out.println(\"  ● userBodyParam :\"+ userBodyParam);"
                            //+ " System.out.println(\"  ● cJson.toString() :\"+ cJson.toString());"
                            //+ " com.akami.jmsclient.Producer cvReqH = com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL[com.akami.com.TOPIC.CV_REQ_H.getIndex()];"
                            //+ " com.akami.jmsclient.Producer cvReqB = com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL[com.akami.com.TOPIC.CV_REQ_B.getIndex()];"
                            + " com.akami.jmsclient.Producer cvVAL = com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL[com.akami.com.TOPIC.CV_REQ.getIndex()];"

                            //+ " cvReqH.produceMessage(userHeaderParam);"
                            //+ " cvReqB.produceMessage(userBodyParam);"
                            + " System.out.println(\"  ● cJson.toString() :\"+cJson.toString());"
                            + " cvVAL.produceMessage(cJson.toString());"
            );


            //System.out.println("clearview.id::"+java.lang.System.getProperty("clearview.id"));


            method.insertAfter(""
                    //cookie 값 부터 가져오기
                    + "String _cv_cookie = \"_cv_cookie\";"
                    + "org.apache.catalina.connector.Request request = (org.apache.catalina.connector.Request) $1;"
                    + "javax.servlet.http.Cookie[] cookies = request.getCookies();"
                    + "for (int i = 0; i < cookies.length; i++) {"
                    + "	if(\"_cv_cookie\".equals(cookies[i].getName())){"
                    + "		_cv_cookie = cookies[i].getValue();"
                    + "		}"
                    + "}"
                    //만약 쿠키가 없다면 쿠키를 발급한다._cv_req_id
                    + "if (_cv_cookie.equals(\"_cv_cookie\")){"
                    + "  javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(\"_cv_cookie\", (String)$1.getAttribute(\"_cv_req_id\"));"
                    + " cookie.setMaxAge(60 * 60 * 24 * 365);"
                    + " cookie.setPath(\"/\");"
                    + " $2.addCookie(cookie);"
                    + " }"
                    + "javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) $2;"
                    + "String _cv_req_id = (String)$1.getAttribute(\"_cv_req_id\");"
                    + "String sessionId = $1.getSession().getId();"
                    + "String clientIP = $1.getRemoteAddr();"
                    + "String uri = $1.getRequestURI();"
                    + "javax.servlet.http.HttpServletRequest httpRequest = (javax.servlet.http.HttpServletRequest) $1;"
                    + "String contentType = $1.getContentType();"
                    + "String method = $1.getMethod();"
                    + "long _startTime = java.lang.Long.parseLong((String)$1.getAttribute(\"_cv_req_s_time\")); "
                    + "long _endTime = System.currentTimeMillis(); "
                    + "java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss.SSS\");"
                    + "String currentTime = timeFormat.format(new java.util.Date(_endTime));"
                    + "String userAgent = $1.getHeader(\"User-Agent\");"
                    + " org.json.JSONObject responseJson = new org.json.JSONObject();"
                    + " responseJson.put(\"cv_cookie_id\",_cv_cookie);"
                    + " responseJson.put(\"cv_req_id\",_cv_req_id);"
                    + " responseJson.put(\"cv_res_end_time\",currentTime);"
                    + " responseJson.put(\"cv_elaspe_time\",(_endTime - _startTime));"
                    + " responseJson.put(\"status\",$2.getStatus());"
                    + " responseJson.put(\"userAgent\",userAgent);"
                    + " responseJson.put(\"sessionId\",sessionId);"
                    + " responseJson.put(\"_tid\", _tid);"

                    //+ " System.out.println( \"[\"+currentTime+\"]◼︎Response Object_______________________\");"
                    //+ " String elapsedTime = $1.getAttribute(\"_cv_req_s_time\");"
                    //+ " elapsedTime = System.currentTimeMillis() - elapsedTime;"
                    //+ " System.out.println(\"  ● Client IP : \"+clientIP);"
                    //+ " System.out.println(\"  ● Elaspe Time :\"+ (_endTime - _startTime)+\" ms\");"
                    //+ " System.out.println(\"  ● Content-Type :\"+ contentType);"
                    //+ " System.out.println(\"  ● Method : \"+ method);"
                    //+ " System.out.println(\"  ● URI :\"+ uri);"
                    //+ " System.out.println(\"  ● status :\"+ $2.getStatus());"
                    //+ " System.out.println(\"  ● userAgent :\"+userAgent);"
                    //+ " System.out.println(\"  ● _cv_ :\"+_cv_);"
                    //+ " System.out.println(\"responseJson :\"+responseJson.toString());"
                    //+ " com.akami.jmsclient.Producer producer = com.akami.jmsclient.Producer.getInstance();"
                    //+ " producer.produceMessage(responseJson.toString());"
                    + " System.out.println(\"  ● responseJson.toString() :\"+responseJson.toString());"
                    + " com.akami.jmsclient.Producer cvResVal = com.akami.jmsclient.ProducerManager.PRODUCER_MANAGER_POOL[com.akami.com.TOPIC.CV_RES_VAL.getIndex()];"
                    + " cvResVal.produceMessage(responseJson.toString());"
            );
        } catch (Exception e) {
            System.err.println("~~~~~~");
            e.printStackTrace();
        }
        System.out.println("4");
        //}else{
        //	System.out.println("12313123213213123123123123123:"+method.getName());
        //}

    }

    private void probeStatement(CtClass currentClass) throws NotFoundException, CannotCompileException {

        CtMethod executeQuery = null;

        try {
            System.out.println("========================262 line");
            currentClass.getName();
            CtMethod[] ctMethods = currentClass.getDeclaredMethods();
            for (int i = 0; i < ctMethods.length; i++) {
                System.out.println(ctMethods[0].getLongName());
            }

            executeQuery = currentClass.getDeclaredMethod("executeQuery");
            executeQuery.insertBefore("try {System.out.println(\"probeStatement\"+$1);} catch (Exception e){System.out.println(\"no sql\");}");
            System.out.println("========================271 line");
        } catch (NotFoundException e) {
            return;
        }

    }

    //apache.jar를 포함해야하는지..고민...
//	public void setCookie(HttpServletRequest rep, String agentID) {
//	    HttpCookie cookie = new HttpCookie("_cv_"+agentID, "id");
//	    cookie.setMaxAge(60*60*24*365);
//	    cookie.setPath("/");
//	    rep.addCookie(cookie);
//	}
}
