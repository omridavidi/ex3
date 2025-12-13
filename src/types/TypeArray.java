package types;

public class TypeArray extends Type
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public Type arrayDataType;


	public TypeArray(String name, Type arrayDataType)
	{
		this.name = name;
		this.arrayDataType = arrayDataType;
	}

	public String toString()
	{
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean isArray(){ return true;}

	@Override
	public boolean isCompatibleWith(Type other) {
		// Arrays can accept nil
		if (other == TypeNil.getInstance()) {
			return true;
		}

		// Arrays are only compatible if they are the exact same type object
		return this == other;
	}
}
