package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public class AstParamList extends AstNode
{
	public AstParam param;
	public AstParamList paramList;

	public AstParamList(AstParam param, AstParamList paramList, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		if (paramList != null) System.out.print("====================== params -> param params\n");
		// int add(int x, int y) {}

		if (paramList == null) System.out.print("====================== params -> param      \n");
		// int add(int x) {}
		
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
	
	public TypeList semantMe(){
		Type paramType = param.semantMe();
		if (paramType == TypeVoid.getInstance()) throw new SemanticException(this.getLineNumber(), "parameter cannot be of type void");

		TypeList paramListType = null;
		if (paramList != null) paramListType = paramList.semantMe();

		return new TypeList(paramType, paramListType);
	}

	public TypeList buildTypeList() {
        Type headType = param.type.semantMe();
        TypeList tailTypes = (paramList != null ? paramList.buildTypeList() : null);
        return new TypeList(headType, tailTypes);
    }
}
