package types;

public class TypeNil extends Type
{
	 // singleton class - like given typeInt code

	private static TypeNil instance = null;	private TypeNil()
	{
		this.name = "nil";
	}
	public static TypeNil getInstance()
	{
		if (instance == null)
		{
			instance = new TypeNil();
		}
		return instance;
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean isCompatibleWith(Type other) {
		// nil can only be assigned to class types
		return other instanceof TypeClass;
	}

	
}
