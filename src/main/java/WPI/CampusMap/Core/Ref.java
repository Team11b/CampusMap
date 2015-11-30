package WPI.CampusMap.Core;

import java.util.Hashtable;

/**
 * 
 * @author Benny
 */
public class Ref
{
	private static Hashtable<Object, Integer> counterTable;
	
	public static int getRefsTo(Object value)
	{
		return counterTable.get(value);
	}
	
	private Object value;
	
	public Ref(Object value)
	{
		synchronized (value)
		{
			Integer count = counterTable.get(value);
			if(count == null)
			{
				count = 1;
			}
			else
			{
				count++;
			}
			
			counterTable.put(value, count);
		}
		
		this.value = value;
	}
	
	public void release()
	{
		if(value == null)
			return;
		
		synchronized (value)
		{
			Integer count = counterTable.get(value);
			if(count == null)
			{
				count = 0;
			}
			else
			{
				count--;
			}
			
			counterTable.put(value, count);
		}
		
		value = null;
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		release();
	}
}
