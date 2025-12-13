package ast;

import symboltable.SymbolTable;
import types.*;

public class AstStmtCall extends AstStmt
{
	public AstCallExp call;

	public AstStmtCall(AstCallExp call)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== stmt -> callExp;\n");
		// foo(x, y + 1);
		
		this.call = call;
	}

	public void printMe()
	{
		System.out.print("AST STMT CALL\n");

		if (call != null) call.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
				"STMT CALL\n");

		if (call != null) AstGraphviz.getInstance().logEdge(serialNumber, call.serialNumber);
	}

	public Type semantMe(){
		if (call == null) throw new RuntimeException("semantic error: call statement has no call expression");

        Type t = call.semantMe();
        if (t == null) throw new RuntimeException("semantic error: call expression has no return type");

		return null;
	}
}
