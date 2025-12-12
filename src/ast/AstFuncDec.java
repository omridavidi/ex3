package ast;

import symboltable.SymbolTable;
import types.*;

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
        /* int getFive() {
				return 5;
			}
		*/
		
		else System.out.format("====================== funcDec -> type ID(%s) (paramList) {stmtList}\n", name);
		/* int add(int x, int y) {
				return x + y;
			}
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
		Type t;
		Type returnType = null;
		TypeList type_list = null;

		/*******************/
		/* [0] return type */
		/*******************/
		returnType = SymbolTable.getInstance().find(type.type);
		if (returnType == null) throw new RuntimeException("semantic error");
	
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SymbolTable.getInstance().beginScope();

		/***************************/
		/* [2] Semant Input Params */
		/***************************/
		for (AstParamList it = paramList; it  != null; it = it.paramList)
		{
			t = SymbolTable.getInstance().find(it.param.name);
			if (t == null) throw new RuntimeException("semantic error");
			else
			{
				type_list = new TypeList(t,type_list);
				SymbolTable.getInstance().enter(it.param.name,t);
			}
		}

		/*******************/
		/* [3] Semant Body */
		/*******************/
		stmtList.semantMe();

		/*****************/
		/* [4] End Scope */
		/*****************/
		SymbolTable.getInstance().endScope();

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SymbolTable.getInstance().enter(name,new TypeFunction(returnType,name,type_list));

		/************************************************************/
		/* [6] Return value is irrelevant for function declarations */
		/************************************************************/
		return null;		
	}
}
