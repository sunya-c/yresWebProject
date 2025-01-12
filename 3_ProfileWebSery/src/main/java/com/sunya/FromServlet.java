package com.sunya;

import com.sunya.exceptions.ServletContextNotFoundException;

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
 */
public class FromServlet
{
	private String fullServletName = "";
	String fromServletAttribute;

	/**
	 * @param context ~ ServletContext, which stores names to compare with.
	 * 
	 * @param contextReference ~ the servlet name (in the context) which you want the client to visit before this page.
	 * 
	 * @param session ~ HttpSession. In the page/servlet you want the client to visit before this page, 
	 * set the value of the attribute name "fromServlet" to "this.getServletName()".
	*/
	// Constructor
	public FromServlet(ServletContext context, String contextReference, HttpSession session) throws ServletContextNotFoundException
	{
		String nameInContext = context.getInitParameter(contextReference);
		
		if ( nameInContext != null)
		{
			System.out.println("nameInContext is valid : " + nameInContext);
			this.fullServletName = nameInContext;
		}
		if (nameInContext == null)
		{
			this.fullServletName = null;
			throw new ServletContextNotFoundException("Check the spelling of \"" + contextReference + "\".");
		}
		
		fromServletAttribute = (String) session.getAttribute("fromServlet");
	}
	// end -- Constructor
	
	/**
	 * Check whether the client has visited the specified page/servlet before visiting this page.
	 * <br>
	 * @return true ~ if the client has visited the page you desired ("fromServlet" == page/servlet name desired).<br>
	 * <pre>
	 *    false ~ otherwise ("fromServlet" != page/servlet name desired).
	 * </pre>
	 */
	// return 
	public boolean isFromServlet()
	{
			if (fullServletName.equals(fromServletAttribute))
				return true;
			else
				return false;
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
