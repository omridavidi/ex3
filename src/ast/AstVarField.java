package ast;

import symboltable.SymbolTable;
import types.*;

public class AstVarField extends AstVar
{
	public AstVar var;
	public String fieldName;
	
	public AstVarField(AstVar var, String fieldName)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== var -> var . ID( %s )\n",fieldName);

		this.var = var;
		this.fieldName = fieldName;
	}

	public void printMe()
	{
		System.out.print("AST VAR FIELD\n");

		if (var != null) var.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("VAR FIELD(%s)\n",fieldName));
		
		if (var != null) AstGraphviz.getInstance().logEdge(serialNumber,var.serialNumber);
	}

	public Type semantMe()
	{
		Type varType = var.semantMe();
		if (!(varType instanceof TypeClass)) throw new RuntimeException("semantic error");

		TypeClass varClass = (TypeClass)varType;
        while (varClass != null) {
            if (varClass.dataMembers != null) {
                Type found = varClass.dataMembers.find(fieldName);
                if (found != null)
                    return found;
            }
            varClass = varClass.father;
        }

		throw new RuntimeException("semantic error");
	}
}