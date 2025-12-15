package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public class AstVarDec extends AstDec
{
    public AstType type;
	public String name;
    public AstExp exp;

	public AstVarDec(AstType type, String name, AstExp exp, int lineNumber)
	{
		super(lineNumber);
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
		// Check for reserved keywords
		if (SymbolTable.isReservedKeyword(name)) throw new SemanticException(this.getLineNumber(), "variable '" + name + "' cannot use reserved keyword");
		
		Type t = type.semantMe();
		if (t == null || t == TypeVoid.getInstance())
            throw new SemanticException(this.getLineNumber(), "variable '" + name + "' cannot be of type void or null");
		
		if (SymbolTable.getInstance().lookupLocal(name) != null)
            throw new SemanticException(this.getLineNumber(), "variable '" + name + "' already declared in current scope");

		if (exp != null){
			Type expType = exp.semantMe();
			if (expType == TypeNil.getInstance()){
				if(!(t instanceof TypeClass) && !(t instanceof TypeArray))
                    throw new SemanticException(this.getLineNumber(), "cannot assign nil to non-class/array type");
			}
			
			// Special case for arrays: if exp is "new T[e]", check if var is an array defined over T
			// This handles: array IntArray = int[]; IntArray arr := new int[5];
			if (t instanceof TypeArray && expType instanceof TypeArray && exp instanceof AstNewExpArr)
    {
				TypeArray arrayType = (TypeArray) t;
				TypeArray newArrayType = (TypeArray) expType;
				// Check if the var's array is defined over the same element type as the new expression
				if (arrayType.arrayDataType != newArrayType.arrayDataType) {
					throw new SemanticException(this.getLineNumber(), "variable initialization type mismatch for '" + name + "'");
				}
				// Special case satisfied, skip the general isCompatibleWith check
			} else {
				// General case
				if (!t.isCompatibleWith(expType))
                    throw new SemanticException(this.getLineNumber(), "variable initialization type mismatch for '" + name + "'");
			}
		}		
		SymbolTable.getInstance().enter(name, t);
		return t;		
	}
}