package ast;

import symboltable.SymbolTable;
import types.*;


public class AstVarSubscript extends AstVar
{
	public AstVar var;
	public AstExp subscript;
	
	public AstVarSubscript(AstVar var, AstExp subscript)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== var -> var [ exp ]\n");

		this.var = var;
		this.subscript = subscript;
	}

	public void printMe()
	{
		System.out.print("AST VAR SUBSCRIPT\n");

		if (var != null) var.printMe();
		if (subscript != null) subscript.printMe();
		
		AstGraphviz.getInstance().logNode(
				serialNumber,
			"VAR\nSUBSCRIPT\n...[...]\n");
			
		if (var       != null) AstGraphviz.getInstance().logEdge(serialNumber,var.serialNumber);
		if (subscript != null) AstGraphviz.getInstance().logEdge(serialNumber,subscript.serialNumber);
	}

	public Type semantMe()
	{
		Type varType = var.semantMe();
		if (!(varType instanceof TypeArray)) throw new RuntimeException("semantic error");
		
		Type subscriptType = subscript.semantMe();
		if (!(subscriptType instanceof TypeInt)) throw new RuntimeException("semantic error");
		
		if (subscript instanceof AstExpInt){
			AstExpInt intExp = (AstExpInt)subscript;
			if (intExp.value < 0) throw new RuntimeException("semantic error");
		}

		return ((TypeArray)varType).arrayDataType;
	}
}
