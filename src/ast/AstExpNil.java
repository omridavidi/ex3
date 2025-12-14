package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;


public class AstExpNil extends AstExp
{
	
	public AstExpNil(int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== exp -> NIL\n");
		// nil
		
	}

	public void printMe()
	{
		System.out.format("AST NIL\n");

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("NIL"));
	}

	public Type semantMe(){
        return TypeNil.getInstance();
    }
}
