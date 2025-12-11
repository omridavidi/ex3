package ast;

import symboltable.SymbolTable;
import types.*;


public class AstStmtVarDec extends AstStmt
{
	public AstVarDec varDec;

	public AstStmtVarDec(AstVarDec varDec)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== stmt -> varDec\n");
		// int x := 5;
		
		this.varDec = varDec;
	}

	public void printMe()
	{
		System.out.print("AST STMT VAR-DEC\n");

		if (varDec != null) varDec.printMe();

		AstGraphviz.getInstance().logNode(serialNumber, "STMT VAR-DEC\n");

		if (varDec != null) AstGraphviz.getInstance().logEdge(serialNumber,varDec.serialNumber);
	}

	public Type semantMe()
	{
		if (varDec != null) varDec.semantMe();
		return null;
	}
}
