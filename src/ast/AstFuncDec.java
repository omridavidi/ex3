package ast;

import symboltable.SymbolTable;
import types.*;
import java.util.HashSet;

public class AstFuncDec extends AstDec
{
	public AstType type;
	public String name;
    public AstParamList paramList;
	public AstStmtList stmtList;

	public AstFuncDec(AstType type, String name, AstParamList paramList, AstStmtList stmtList)
	{
		serialNumber = AstNodeSerialNumber.getFresh();
	
		if (paramList == null) System.out.format("====================== funcDec -> type ID(%s) () {stmtList}\n", name);
        /* int retFive() {
				return 5;
			}
			int -> type
			retFive -> ID
			null -> paramList
			{ return 5; } -> stmtList
		*/
		
		else System.out.format("====================== funcDec -> type ID(%s) (paramList) {stmtList}\n", name);
		/* int add(int x, int y) {
				return x + y;
			}
		
			int -> type
			add -> ID
			(int x, int y) -> paramList
			{ return x + y; } -> stmtList
		*/

		this.type = type;
		this.name = name;
        this.paramList = paramList;
		this.stmtList = stmtList;
	}

	public void printMe()
	{
		System.out.format("AST FUNC DEC NAME: %s\n", name);

        if (type != null) type.printMe();
        if (paramList != null) paramList.printMe();
        if (stmtList != null) stmtList.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("FUNC DEC : %s",name));
        
        if(type != null) AstGraphviz.getInstance().logEdge(serialNumber,type.serialNumber);
        if(paramList != null) AstGraphviz.getInstance().logEdge(serialNumber,paramList.serialNumber);
        if(stmtList != null) AstGraphviz.getInstance().logEdge(serialNumber,stmtList.serialNumber);

	}

	public Type semantMe()
	{
		Type returnType = null;
		TypeList type_list = null;

		returnType = type.semantMe();
		if (returnType == null) throw new RuntimeException("semantic error: function return type is invalid");

		// defining multiple methods with the same name but different signatures in the same class) is illegal.
		if (SymbolTable.getInstance().findInCurrentScope(name) != null) throw new RuntimeException("semantic error: function '" + name + "' already declared in current scope");
		
		// Check for duplicate parameter names
		HashSet<String> paramNames = new HashSet<>();
		for (AstParamList paramNode = paramList; paramNode != null; paramNode = paramNode.paramList)
		{
			if (!paramNames.add(paramNode.param.name))
			{
				throw new RuntimeException("semantic error: duplicate parameter name '" + paramNode.param.name + "' in function '" + name + "'");
			}
		}

		SymbolTable.getInstance().beginScope();

		if (paramList != null)
		{
			type_list = (TypeList) paramList.semantMe();
		}

		stmtList.semantMe();

		SymbolTable.getInstance().endScope();

		TypeFunction funcType = new TypeFunction(returnType, name, type_list);
		SymbolTable.getInstance().enter(name, funcType);

		return funcType;
	}
}
