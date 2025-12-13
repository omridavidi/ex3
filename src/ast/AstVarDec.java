package ast;

import symboltable.SymbolTable;
import types.*;

public class AstVarDec extends AstDec
{
    public AstType type;
	public String name;
    public AstExp exp;

	public AstVarDec(AstType type, String name, AstExp exp)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

        if (exp != null)
        {
            System.out.format("====================== varDec -> TYPE ID(%s) := exp;\n", name);
            // int x := 5;
        }
        else
        {
            System.out.format("====================== varDec -> TYPE ID(%s);\n",name);
            // int x;
        }

        this.type = type;
        this.name = name;
        this.exp = exp;

	}

	public void printMe()
	{

        System.out.print("AST VAR DEC\n");

        if(type != null)
            type.printMe();
        
        if(exp != null)
            exp.printMe();


        AstGraphviz.getInstance().logNode(serialNumber, String.format("AST VAR DEC (%s)\n", name));

        if(type != null) AstGraphviz.getInstance().logEdge(serialNumber,type.serialNumber);
        if(exp != null) AstGraphviz.getInstance().logEdge(serialNumber,exp.serialNumber);

	}

    public Type semantMe(){
		Type t = type.semantMe();
		if (t == null || t == TypeVoid.getInstance()) throw new RuntimeException("semantic error: variable '" + name + "' cannot be of type void");
		
		if (SymbolTable.getInstance().findInCurrentScope(name) != null) throw new RuntimeException("semantic error: variable '" + name + "' already declared in current scope");

		if (exp != null){
			Type expType = exp.semantMe();
			if (expType == TypeNil.getInstance()){
				if(!(t instanceof TypeClass) && !(t instanceof TypeArray)) throw new RuntimeException("semantic error: cannot assign nil to non-class/array type");
			}
			if (!t.isCompatibleWith(expType)) throw new RuntimeException("semantic error: variable initialization type mismatch for '" + name + "'");
		}		
		SymbolTable.getInstance().enter(name, t);
		return t;		
	}
}