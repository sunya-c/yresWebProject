package com.sunya.yresWebProject.managers.sessionObjects;

import java.util.ArrayList;
import java.util.Random;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.managers.SessionManager;

public class SessionRedirecting
{
	private ArrayList<String> codes = new ArrayList<>();
	
	
	public boolean consumeCode(String code)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);
		
		synchronized (sm.getKeyHolder().getKeyRedirecting())
		{
			return codes.remove(code);
		}
	}
	
	
	public String generateCode()
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);
		
		Random random = new Random();
		
		String code;
		
		synchronized (sm.getKeyHolder().getKeyRedirecting())
		{
			do
			{
				code = "";
				
				code += random.nextInt(1, 10);
				for (var i=0; i<9; i++)
				{
					code += random.nextInt(0, 10);
				}
			}
			while (codes.contains(code));
			
			codes.add(code);
		}
		
		return code;
	}
}
