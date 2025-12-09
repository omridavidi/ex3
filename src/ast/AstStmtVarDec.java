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
		return var.semantMe();
	}
}
	AstGraphviz.getInstance().logNode(
                serialNumber,
			String.format("STMT\nDEC\nVAR"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AstGraphviz.getInstance().logEdge(serialNumber,var.serialNumber);
	}

	public Type semantMe()
	{
		if (varDec != null) return varDec.semantMe();
		return null;
	}
}
