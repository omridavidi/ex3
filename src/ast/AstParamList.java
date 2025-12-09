package ast;

import symboltable.SymbolTable;
import types.*;

public class AstParamList extends AstNode
{
	public AstParam param;
	public AstParamList paramList;

	public AstParamList(AstParam param, AstParamList paramList)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		if (paramList != null) System.out.print("====================== params -> param params\n");
		if (paramList == null) System.out.print("====================== params -> param      \n");

		this.param = param;
		this.paramList = paramList;
	}

	public void printMe()
	{
		System.out.print("AST PARAM LIST\n");

		if (param != null) param.printMe();
		if (paramList != null) paramList.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			"PARAM LIST\n");
		
		if (param != null) AstGraphviz.getInstance().logEdge(serialNumber,param.serialNumber);
		if (paramList != null) AstGraphviz.getInstance().logEdge(serialNumber,paramList.serialNumber);
	}
	
	public Type semantMe(){
		Type paramType = param.semantMe();
		if (paramType == TypeVoid.getInstance()) throw new RuntimeException("semantic error");

		TypeList paramListType = null;
		if (paramList != null) paramListType = paramList.semantMe();

		return new TypeList(paramType, paramListType);
	}
}
