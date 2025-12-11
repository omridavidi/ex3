package types;

public class TypeClass extends Type
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TypeClass father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TypeClassVarDecList dataMembers;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TypeClass(TypeClass father, String name, TypeClassVarDecList dataMembers)
	{
		this.name = name;
		this.father = father;
		this.dataMembers = dataMembers;
	}

	public String toString()
	{
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean isClass(){ return true;}
	
	public boolean isSubTypeOf(TypeClass other) {

		TypeClass current = this;
		while (current != null) {
			if (current.name.equals(other.name)) {
				return true;
			}
			current = current.father;
		}
		return false;
	}

	public TypeClassVarDec findElement(String name)
	{
		return this.dataMembers.findElement(name);
		
	}

	@Override
	public boolean isAssignableFrom(Type other) {
		// other == TypeNil.getInstance() handles the nil case (typeNil is singleton)
		if (other == TypeNil.getInstance()) {
			return true;
		}

		if(!(other instanceof TypeClass)) {
			return false;
		}

		return (this.isSubTypeOf((TypeClass)other));
	}

	
}
