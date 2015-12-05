package WPI.CampusMap.Backend.Core.Ref;

public class TypedRef<T> extends Ref
{

	public TypedRef(T value) {
		super(value);
	}
	
	@SuppressWarnings("unchecked")
	public T getValue()
	{
		return (T)value;
	}

}
