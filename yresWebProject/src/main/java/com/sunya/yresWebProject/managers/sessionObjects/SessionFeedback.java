package com.sunya.yresWebProject.managers.sessionObjects;

import java.util.ArrayList;
import java.util.Random;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataFeedback;

public class SessionFeedback
{
	private ArrayList<DataObject> dataObjects = new ArrayList<>();
	
	
	public DataFeedback consumeCode(String code)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);
		
		DataObject dataObject = null;
		
		synchronized (sm.getKeyHolder().getKeyFeedback())
		{
			for (var i : dataObjects)
			{
				if (code.equals(i.getCode()))
				{
					dataObject = i;
					break;
				}
			}
			
			if (dataObject!=null)
			{
				System.out.println("before removal: "+dataObjects);
				dataObjects.remove(dataObject);
				System.out.println("after removal: "+dataObjects);
				
				return dataObject.getDataFeedback();
			}
			else
				return null;
		}
	}
	
	
	public String generateCode(DataFeedback dataFeedback)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);
		
		Random random = new Random();
		
		DataObject dataObject = new DataObject();
		
		String code;
		
		synchronized (sm.getKeyHolder().getKeyFeedback())
		{
			boolean loopAgain;
			do
			{
				code = "";
				
				code += random.nextInt(1, 10);
				for (var i=0; i<9; i++)
				{
					code += random.nextInt(0, 10);
				}
				
				loopAgain = false;
				for (var i : dataObjects)
				{
					if (code.equals(i.getCode()))
						loopAgain = true;
				}
			}
			while (loopAgain);
			
			dataObject.setCode(code);
			dataObject.setDataFeedback(dataFeedback);
			
			dataObjects.add(dataObject);
		}
		
		return code;
	}
	
	
	class DataObject
	{
		private String code = null;
		private DataFeedback dataFeedback = null;
		
		
		public String getCode()
		{
			return code;
		}
		public void setCode(String code)
		{
			this.code = code;
		}
		public DataFeedback getDataFeedback()
		{
			return dataFeedback;
		}
		public void setDataFeedback(DataFeedback dataFeedback)
		{
			this.dataFeedback = dataFeedback;
		}
	}
}
