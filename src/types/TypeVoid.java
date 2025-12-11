package types;

public class TypeVoid extends Type
{
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/

	private static TypeVoid instance = null;

	protected TypeVoid() {}

	public static TypeVoid getInstance()
	{
		if (instance == null)
		{
			instance = new TypeVoid();
		}
		return instance;
	}
}
