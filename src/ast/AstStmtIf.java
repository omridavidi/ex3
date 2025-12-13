package ast;

import types.*;
import symboltable.*;

public class AstStmtIf extends AstStmt
{
	public AstExp cond;
	public AstStmtList body;
	public AstStmtList elseBody;

	public AstStmtIf(AstExp cond, AstStmtList body)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		this.cond = cond;
		this.body = body;
		this.elseBody = null;
	}

	public AstStmtIf(AstExp cond, AstStmtList body, AstStmtList elseBody)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		this.cond = cond;
		this.body = body;
		this.elseBody = elseBody;
	}

	public void printMe()
	{
		if (elseBody == null) System.out.print("AST NODE STMT IF\n");
		else System.out.print("AST NODE STMT IF ELSE\n");


		if (cond != null) cond.printMe();
		if (body != null) body.printMe();
		if (elseBody != null) elseBody.printMe();

		
		if(elseBody != null) {
			AstGraphviz.getInstance().logNode( serialNumber,
				"IF (left)\nTHEN right\nELSE bottom");
			if (elseBody != null) AstGraphviz.getInstance().logEdge(serialNumber,elseBody.serialNumber);
		}
		else{
		AstGraphviz.getInstance().logNode( serialNumber,
			"IF (left)\nTHEN right");
		}
		if (cond != null) AstGraphviz.getInstance().logEdge(serialNumber,cond.serialNumber);
		if (body != null) AstGraphviz.getInstance().logEdge(serialNumber,body.serialNumber);

	}

	public Type semantMe()
	{
		if (cond.semantMe() != TypeInt.getInstance()) throw new RuntimeException("semantic error: if condition must be of type int");
		
		SymbolTable.getInstance().beginScope();
		if (body != null) body.semantMe();
		SymbolTable.getInstance().endScope();

		if (elseBody != null){
			SymbolTable.getInstance().beginScope();
			elseBody.semantMe();
			SymbolTable.getInstance().endScope();
		}
		return null;		
	}	
}
