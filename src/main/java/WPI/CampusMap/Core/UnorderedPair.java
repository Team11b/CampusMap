package WPI.CampusMap.Core;

public final class UnorderedPair<T1, T2>
{
	private T1 a;
	private T2 b;
	
	public UnorderedPair(T1 a, T2 b)
	{
		this.a = a;
		this.b = b;
	}
	
	@Override
	public int hashCode()
	{
		return (a.hashCode() + b.hashCode());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof UnorderedPair<?, ?>)
		{
			UnorderedPair<?, ?> pair = (UnorderedPair<?, ?>)obj;
			
			return (a.equals(pair.a) && b.equals(pair.b)) || (a.equals(pair.b) && b.equals(pair.a));
		}
		
		return super.equals(obj);
	}
	
	public T1 getA()
	{
		return a;
	}
	
	public T2 getB()
	{
		return b;
	}
}
