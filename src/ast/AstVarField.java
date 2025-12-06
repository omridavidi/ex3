package ast;

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
}