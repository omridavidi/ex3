package types;

public class TypeClassVarDec
{
	public Type type;
	public String name;
	
	// both for data members and methods

	public TypeClassVarDec(Type type, String name)
	{
		this.type = type;
		this.name = name;
	}
}