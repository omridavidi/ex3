package ast;

import types.*;
public class AstExpBinop extends AstExp
{
	public int op;
	public AstExp left;
	public AstExp right;
	
    public static final int OP_PLUS   = 1;
    public static final int OP_MINUS  = 2;
    public static final int OP_TIMES  = 3;
    public static final int OP_DIVIDE = 4;
    public static final int OP_LT = 5;
    public static final int OP_GT = 6;
    public static final int OP_EQ = 7;



	public AstExpBinop(AstExp left, AstExp right, int op)
	{
		serialNumber = AstNodeSerialNumber.getFresh();
		System.out.print("====================== exp -> exp BINOP exp\n");
		// x = a + b
		
		this.left = left;
		this.right = right;
		this.op = op;
	}
	
	public String getOpString() {
		switch (this.op) {
			case OP_PLUS:
				return "+";
			case OP_MINUS:
				return "-";
			case OP_TIMES:
				return "*";
			case OP_DIVIDE:
				return "/";
			case OP_LT:
				return "<";
			case OP_GT:
				return ">";
			case OP_EQ:
				return "=";
			default:
				return "unknown";
		}
	}
	
	@Override
	public void printMe()
	{

		String sop="";
		sop = getOpString();
		
		System.out.print("AST BINOP EXP\n");

		if (left != null) left.printMe();
		if (right != null) right.printMe();
		
		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("BINOP(%s)\n",sop));
		
		if (left  != null) AstGraphviz.getInstance().logEdge(serialNumber,left.serialNumber);
		if (right != null) AstGraphviz.getInstance().logEdge(serialNumber,right.serialNumber);
	}

	public Type semantMe()
	{
		Type t1 = null;
		Type t2 = null;
		
		if (left  != null) t1 = left.semantMe();
		if (right != null) t2 = right.semantMe();
		
		switch(this.op){
			case OP_PLUS:
                if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
                if (t1 == TypeString.getInstance() && t2 == TypeString.getInstance()) return TypeString.getInstance();
                throw new RuntimeException("semantic error");

            case OP_MINUS:
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
				throw new RuntimeException("semantic error");
            
			case OP_TIMES:
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
				throw new RuntimeException("semantic error");
            
			case OP_DIVIDE:
                if (t1 != TypeInt.getInstance() || t2 != TypeInt.getInstance())
                    throw new RuntimeException("semantic error");
                
                if (right instanceof AstExpInt) {
                    AstExpInt rightInt = (AstExpInt) right;
                    if (rightInt.value == 0)
                        throw new RuntimeException("semantic error");
                }
                return TypeInt.getInstance();
            
			case OP_LT:
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
				throw new RuntimeException("semantic error");
			
			case OP_GT:
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
				throw new RuntimeException("semantic error");

            
			case OP_EQ:
                if (t1 == t2 &&
                (t1 == TypeInt.getInstance() || t1 == TypeString.getInstance()))
                    return TypeInt.getInstance();

                if (t1.isCompatibleWith(t2) || t2.isCompatibleWith(t1))
                    return TypeInt.getInstance();

                if ((t1 instanceof TypeArray && t2 == TypeNil.getInstance()) ||
                    (t2 instanceof TypeArray && t1 == TypeNil.getInstance()))
                    return TypeInt.getInstance();

                throw new RuntimeException("semantic error");

			default:
                throw new RuntimeException("semantic error");
		}
	}
}
