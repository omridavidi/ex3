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
		// rule 1:  When performing division (using the / operator), if the divisor is a constant, it must not be 0


		// Equality Testing: Equality testing (=) is legal only when the two expressions are comparable.
			// rule 2: For primitive types and arrays, this requires that both expressions have exactly the same type.
			// rule 3: For class types, this requires that either both expressions have exactly the same class type, or that the
					// type of one expression is a subclass of the type of the other expression. For example, if class Son is
					// derived from class Father, then an expression of type Father can be compared for equality with an
					// expression of type Son, and vice versa.
			// rule 4: Recall that an expression of an array or class type may be tested for equality with nil. However,
					// comparing nil to variables of type int or string is illegal.


		Type t1 = null;
		Type t2 = null;
		
		if (left  != null) t1 = left.semantMe();
		if (right != null) t2 = right.semantMe();
		
		switch(this.op){
			case OP_PLUS:
				// both integers or both strings
                if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance())
					return TypeInt.getInstance();

                if (t1 == TypeString.getInstance() && t2 == TypeString.getInstance())
					return TypeString.getInstance();
                throw new RuntimeException("semantic error");

            case OP_MINUS:
				// only integers
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance())
					return TypeInt.getInstance();
				throw new RuntimeException("semantic error");
            
			case OP_TIMES:
				// only integers
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance())
					return TypeInt.getInstance();
				throw new RuntimeException("semantic error");
            
			case OP_DIVIDE:
				// only integers
                if (t1 != TypeInt.getInstance() || t2 != TypeInt.getInstance())
                    throw new RuntimeException("semantic error");
                
				// rule 1:  When performing division (using the / operator), if the divisor is a constant, it must not be 0

                if (right instanceof AstExpInt) {
                    AstExpInt rightInt = (AstExpInt) right;
                    if (rightInt.value == 0)
                        throw new RuntimeException("semantic error");
                }
                return TypeInt.getInstance();
            
			case OP_LT:
				// only integers
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
				throw new RuntimeException("semantic error");
			
			case OP_GT:
				// only integers
				if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance()) return TypeInt.getInstance();
				throw new RuntimeException("semantic error");

			case OP_EQ:
				// rule 2
                if (t1 == t2 && (t1 == TypeInt.getInstance() || t1 == TypeString.getInstance()))
                    return TypeInt.getInstance();

				// rule 3
                if (t1.isCompatibleWith(t2) || t2.isCompatibleWith(t1))
                    return TypeInt.getInstance();

				// rule 4
                if (((t1 instanceof TypeArray || t1 instanceof TypeClass) && t2 == TypeNil.getInstance()) ||
                    ((t2 instanceof TypeArray || t2 instanceof TypeClass) && t1 == TypeNil.getInstance()))
                    return TypeInt.getInstance();

                throw new RuntimeException("semantic error");

			default:
                throw new RuntimeException("semantic error");
		}
	}
}
