package types;

public class TypeClassVarDecList
{
	public TypeClassVarDec head;
	public TypeClassVarDecList tail;
	
	public TypeClassVarDecList(TypeClassVarDec head, TypeClassVarDecList tail)
	{
		this.head = head;
		this.tail = tail;
	}
	public int size() {
		if (this.tail == null) {
			return 1;
		}
		return 1 + this.tail.size();
	}




	public Type findElement(String name) {
	// return the TypeClassVarDec with the given name, or null if not found


	if (this.head.name.equals(name))
	{
		if (this.head != null)
		{
			return this.head.type;
		}
		else
		{
			return null;
		}
	}

	if (this.tail != null)
	{
		return this.tail.findElement(name);
	}
	return null;
	}
}
