package WPI.CampusMap.Core;

import java.util.HashSet;
import java.util.Hashtable;

/**
 * 
 * @author Benny
 */
public class Ref
{
	private static Hashtable<Object, Integer> counterTable = new Hashtable<>();
	private static Hashtable<Object, HashSet<Ref>> refTable = new Hashtable<>();
	
	public static int getRefsTo(Object value)
	{
		Integer count = counterTable.get(value);
		if(count == null)
			return 0;
		
		return count;
	}
	
	public static Ref getRefTo(Object value)
	{
		HashSet<Ref> set = refTable.get(value);
		
		if(set != null && set.iterator().hasNext())
			return set.iterator().next();
		
		return null;
	}
	
	protected Object value;
	
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
			
			HashSet<Ref> refSet = refTable.get(value);
			if(refSet == null)
			{
				refSet = new HashSet<>();
				refTable.put(value, refSet);
			}
			
			refSet.add(this);
		}
		
		this.value = value;
	}
	
	/**
	 * Release this reference to an object.
	 */
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
	
	/**
	 * Release this reference and all other references to an object.
	 */
	public void releaseAll()
	{
		if(value == null)
			return;
		
		synchronized (value)
		{			
			counterTable.put(value, 0);
			
			HashSet<Ref> refSet = refTable.get(value);
			for(Ref ref : refSet)
			{
				ref.value = null;
			}
			
			refSet.clear();
		}
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		release();
	}
}
