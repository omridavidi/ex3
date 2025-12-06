package ast;

public class AstType extends AstNode
{
	public String type;
	
	public AstType(String name)
	{
		serialNumber = AstNodeSerialNumber.getFresh();
	
		System.out.format("====================== type(%s)\n",type);

		this.type = type;
	}

	public void printMe()
	{

		System.out.format("AST TYPE( %s )\n",type);

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("TYPE : %s",type));
	}
}
