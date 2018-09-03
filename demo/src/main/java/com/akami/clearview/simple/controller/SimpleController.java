package com.akami.clearview.simple.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.JDBC4ServerPreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.akami.clearview.simple.service.SimpleService;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.`
 */
@Controller
public class SimpleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);
	
	@Inject
	private SimpleService service;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );

		return "home";
	}
	
	@RequestMapping(value = "threadAdmin", method = RequestMethod.GET)
	public ModelAndView threadAdmin(HttpServletRequest request) {


		ModelAndView view = new ModelAndView();
		view.setViewName("/threadAdmin");
		view.addObject("name", 	request.getParameter("name"));
		view.addObject("state", 	request.getParameter("state"));
		view.addObject("tid", 		request.getParameter("tid"));

		return view;
	}

	@RequestMapping(value = "helloMysql", method = RequestMethod.GET)
	public void  helloMysql(Model model) throws Exception{
		//com.Hello h = new Hello();
		logger.info("helloMysql...................");
		logger.info("helloMysql..................."+service.getList());
		logger.info("say()...................");
        //h.say();
		model.addAttribute("list",service.getList());
		//javax.servlet.http.HttpServlet
	}

	@RequestMapping(value = "helloMysql2", method = RequestMethod.GET)
	public void  helloMysql2(Model model) throws Exception{
		//com.Hello h = new Hello();
		logger.info("helloMysql...................");
		logger.info("helloMysql..................."+service.getList());
		logger.info("say()...................");
		Thread.sleep(1000);
		//h.say();
		model.addAttribute("list",service.getList());
		//javax.servlet.http.HttpServlet
	}
	
	@RequestMapping(value = "echoQue", method = RequestMethod.GET)
	public void  echoQue(Model model) throws Exception{
		logger.info("echoQue..................."+service.getList());
		model.addAttribute("list",service.getList());
		
	}
	
}
