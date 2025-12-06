package ast;

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
}
