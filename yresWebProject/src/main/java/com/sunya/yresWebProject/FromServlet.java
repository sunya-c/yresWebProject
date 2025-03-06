package com.sunya.yresWebProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.exceptions.ServletContextNotFoundException;
import com.sunya.yresWebProject.managers.SessionManager;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

/**
 * This class will help you verify whether the client 
 * has visited a specified page/servlet before entering the current page.
 * <br><br>
 * An example situation when this class will come in handy is when 
 * you want to do some operations in ExampleServlet.java first before 
 * showing the results in ExamplePage.jsp. In this case you don't want 
 * the client to visit ExamplePage.jsp right away without visiting 
 * ExampleServlet.java first.
 * <br><br>
 * To use this class, in the class you want clients to visit before visiting a page, 
 * set the value of the attribute name "fromServlet" to {@code this.getClass().getName()}. 
 * In the page you don't want clients to visit right away, create an object of {@code FromServlet} class, 
 * then call {@code isFromServlet(className)}. Replace "className" with the name of the class you 
 * want clients to visit first.
 * 
 */
@Component
public class FromServlet
{
	private String fullServletName = "";
	private String fromServletAttribute;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SessionManager sm;
	
	
	
	
	/**
	 * Check whether the client has visited the specified page/servlet before visiting this page.
	 * 
	 * @param expectedClass ~ the class name that you want the client to visit before visiting this page.<br>
	 * 
	 * @return <strong>true</strong> ~ if the client has visited the page you desired ("fromServlet" == page/servlet name desired).<br>
	 *         <strong>false</strong> ~ otherwise ("fromServlet" != page/servlet name desired).
	 * @throws ServletContextNotFoundException 
	 */
	public boolean isFromServlet(String expectedClass) throws ServletContextNotFoundException
	{
		setupFromServlet(expectedClass);
		
		if (fullServletName.equals(fromServletAttribute))
			return true;
		else
			return false;
	}
	
	private void setupFromServlet(String expectedClass) throws ServletContextNotFoundException
	{
		String nameInContext = context.getInitParameter(expectedClass);
		
		if ( nameInContext != null)
		{
			this.fullServletName = nameInContext;
		}
		if (nameInContext == null)
		{
			this.fullServletName = null;
			throw new ServletContextNotFoundException("Check the spelling of \"" + expectedClass + "\".");
		}
		
		fromServletAttribute = (String)session.getAttribute(sm.FROM_SERVLET);
		System.out.println(fromServletAttribute);
		System.out.println(fullServletName);
	}
	
	
	
	
	// getter & setter
	public String getFullServletName()
	{
		return fullServletName;
	}

	public String getFromServletAttribute()
	{
		return fromServletAttribute;
	}
	// end -- getter & setter
}
