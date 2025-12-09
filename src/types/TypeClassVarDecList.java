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

	public TypeClassVarDec get(String name) {
		if (this.head.name.equals(name)) {
			return this.head;
		}
		if (this.tail != null) {
			return this.tail.get(name);
		}
		return null;
	}
}
