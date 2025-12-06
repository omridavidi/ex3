package ast;

public class AstVarSimple extends AstVar
{
	public String name;
	
	public AstVarSimple(String name)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== var -> ID( %s )\n",name);

		this.name = name;
	}

	public void printMe()
	{
		System.out.format("AST VAR SIMPLE( %s )\n",name);

		AstGraphviz.getInstance().logNode(serialNumber, String.format("SIMPLE VAR\n(%s)",name));
	}
}
