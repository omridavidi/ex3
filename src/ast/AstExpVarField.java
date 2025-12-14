package ast;

import symboltable.SymbolTable;
import types.*;


public class AstExpVarField extends AstExpVar
{
	public AstExpVar var;
	public String fieldName;
	
	public AstExpVarField(AstExpVar var, String fieldName, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== var -> var DOT ID( %s )\n",fieldName);
		// x.a
		
		this.var = var;
		this.fieldName = fieldName;
	}

	public void printMe()
	{
		System.out.format("FIELD\nNAME\n(___.%s)\n",fieldName);

		if (var != null) var.printMe();

		AstGraphviz.getInstance().logNode(
                serialNumber,
			String.format("FIELD\nVAR\n___.%s",fieldName));

		if (var  != null) AstGraphviz.getInstance().logEdge(serialNumber,var.serialNumber);
	}

	public Type semantMe()
	{
		Type t = null;
		TypeClass tc = null;
		
		if (var != null) t = var.semantMe();
	
		if (!(t instanceof TypeClass)) throw new RuntimeException("semantic error: cannot access field of non-class type");
		tc = (TypeClass) t;
		
		for (TypeClassVarDecList node = tc.dataMembers; node != null; node=node.tail)
		{
			if (node.head.name == fieldName) return node.head.type;
		}
		
		throw new RuntimeException("semantic error: field '" + fieldName + "' not found in class");
	}
}
