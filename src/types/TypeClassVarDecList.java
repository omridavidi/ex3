package types;
import ast.*;

public class TypeClassVarDecList
{
	public TypeClassVarDec head;
	public TypeClassVarDecList tail;
	
	public TypeClassVarDecList(TypeClassVarDec head, TypeClassVarDecList tail)
	{
		this.head = head;
		this.tail = tail;
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
	
	public static TypeClassVarDecList classVarDecListFromAst(AstcFieldList cFieldList) {
		if (cFieldList == null) {
			return null;
		}
		
		AstDec dec = cFieldList.head.dec;
		String fieldName = null;
		Type fieldType = null;
		
		// Extract name and type based on declaration type
		if (dec instanceof AstVarDec)
			{
			// if dec is a variable declaration
			AstVarDec vd = (AstVarDec) dec;
			fieldName = vd.name;
			fieldType = vd.type.semantMe();
			}
		else if (dec instanceof AstFuncDec)
			{
			// if dec is a function declaration

			AstFuncDec fd = (AstFuncDec) dec;
			fieldName = fd.name;

			// Build function type from signature
			Type returnType = fd.type.semantMe();		
			TypeList params = (fd.paramList != null ? fd.paramList.buildTypeList() : null);

			fieldType = new TypeFunction(returnType, fd.name, params);
		}
		
		else
		{
			throw new RuntimeException("Unknown declaration type in class field list");
		}

		
		// Create the current node
		TypeClassVarDec varDec = new TypeClassVarDec(fieldType, fieldName);
		
		// Recursively process the tail
		TypeClassVarDecList tail = classVarDecListFromAst(cFieldList.tail);
		
		return new TypeClassVarDecList(varDec, tail);
	}
}
