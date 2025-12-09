package ast;

import symboltable.SymbolTable;
import types.*;

public class AstcFieldList extends AstNode
{
	public AstcField cField;
	public AstcFieldList cFieldList;

	public AstcFieldList(AstcField cField, AstcFieldList cFieldList)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		if (cField == null) System.out.print("====================== cFieldList -> cField\n");
        else System.out.print("====================== cFieldList -> cField cFieldList\n");
		
        this.cField = cField;
		this.cFieldList = cFieldList;
	}

	public void printMe()
	{
		System.out.format("AST CFIELD LIST\n");

		if (cField != null) cField.printMe();
		if (cFieldList != null) cFieldList.printMe();

		AstGraphviz.getInstance().logNode(serialNumber, "CFIELD LIST\n");
		
		if (cField != null) AstGraphviz.getInstance().logEdge(serialNumber,cField.serialNumber);
		if (cFieldList != null) AstGraphviz.getInstance().logEdge(serialNumber,cFieldList.serialNumber);
	}
	
	public Type semantMe(){
		if (cField != null) cField.semantMe();
		if (cFieldList != null) cFieldList.semantMe();
		return null;
	}
}
