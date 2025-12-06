package ast;

import symboltable.SymbolTable;
import types.*;

public class AstTypeID extends AstType
{
	public String ID;
	
	public AstTypeID(String ID)
	{
		super(ID);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== ID: (%s)\n",ID);

		this.ID = ID;
	}

	public void printMe()
	{

		System.out.format("AST TYPE ID( %s )\n",ID);

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format(" ID : %s",ID));
	}
}
