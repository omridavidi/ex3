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
        else System.out.format("====================== funcDec -> type ID(%s) (paramList) {stmtList}\n", name);

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
}
