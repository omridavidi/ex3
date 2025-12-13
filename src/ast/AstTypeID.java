package ast;

import symboltable.SymbolTable;
import types.*;

public class AstTypeID extends AstType
{
	public String ID;
	
	public AstTypeID(String ID)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== ID: (%s)\n",ID);
		// myVar
		
		this.ID = ID;
	}

	public void printMe()
	{
		System.out.format("AST TYPE ID( %s )\n",ID);

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format(" ID : %s",ID));
	}

	public Type semantMe(){
		Type type = SymbolTable.getInstance().find(ID);

		if (type == null || type == TypeVoid.getInstance()) throw new RuntimeException("semantic error");
		return type;
	}
}
