package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;
import java.util.HashSet;

public class AstClassDec extends AstDec
{
	public String name;
    public String superName;
    public AstcFieldList cFieldList;

	public AstClassDec(String name, String superName, AstcFieldList cFieldList, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

        if (superName != null)
        {
            System.out.format("====================== classDec -> CLASS ID(%s) EXTENDS ID(%s) { cFieldList } \n",name, superName);
			// class animal {}
		}
        else
        {
            System.out.format("====================== classDec -> CLASS ID(%s) { cFieldList } \n",name);
			// class dog extends animal {}
		}

        this.name = name;
        this.superName = superName;
        this.cFieldList = cFieldList;

	}

	public void printMe()
	{
        System.out.print("AST CLASS DEC\n");

        if(cFieldList != null)
            cFieldList.printMe();

        String temp;

        if (superName != null)
            temp = "AST CLASS DEC ("+ name +") EXTENDS ("+superName+")\n";
        else
            temp = "AST CLASS DEC ("+name+")\n";

        AstGraphviz.getInstance().logNode(serialNumber, temp);

        if(cFieldList != null) AstGraphviz.getInstance().logEdge(serialNumber,cFieldList.serialNumber);


	}


    public Type semantMe()
	{	
		SymbolTable symTable = SymbolTable.getInstance();

		// Check for reserved keywords
		if (SymbolTable.isReservedKeyword(name)) throw new SemanticException(this.getLine(), "class '" + name + "' cannot use reserved keyword");

		// Class declarations can only appear in global scope
		if (!symTable.getScope().equals("GLOBAL")) throw new SemanticException(this.getLine(), "class '" + name + "' can only be declared in global scope");

		//Assert no existing function/variable with same name
		if (symTable.lookupLocal(name) != null)
		{
			throw new SemanticException(this.getLine(), "class '" + name + "' already declared in current scope");
		}

		//Assert super class is really a class
		TypeClass superClassType = null;
		if (superName != null)
		{
			Type superType = symTable.find(superName);
			if (!(superType instanceof TypeClass)) throw new SemanticException(this.getLine(), "superclass '" + superName + "' is not a class type");
			superClassType = (TypeClass)superType;
		}

		//Assert no overloading + shadowing
		if (cFieldList != null){
			HashSet<String> fieldNames = new HashSet<String>();
			for (AstcFieldList cFieldNode = cFieldList; cFieldNode != null; cFieldNode = cFieldNode.tail){
				String fieldName = null;
				if (cFieldNode.head.dec instanceof AstVarDec) fieldName = ((AstVarDec)cFieldNode.head.dec).name;
				else if (cFieldNode.head.dec instanceof AstFuncDec) fieldName = ((AstFuncDec)cFieldNode.head.dec).name;
				else if (cFieldNode.head.dec instanceof AstClassDec) fieldName = ((AstClassDec)cFieldNode.head.dec).name;
				else if (cFieldNode.head.dec instanceof AstArrayTypeDec) fieldName = ((AstArrayTypeDec)cFieldNode.head.dec).name;
		
				// now fieldName is set to the name of the field
				// check for duplicates within the current class
				if (fieldName == null || fieldNames.contains(fieldName)) throw new SemanticException(cFieldNode.head.dec.getLine(), "duplicate field name '" + fieldName + "' in class '" + name + "'");
				fieldNames.add(fieldName);
			}
		}


		TypeClass classType = new TypeClass(superClassType, name, null);
		symTable.enter(name, classType);
		
		//Assert no shadowing on parent classes
		if (superClassType != null) {
            for (AstcFieldList cFieldNode = cFieldList; cFieldNode != null; cFieldNode = cFieldNode.tail) {
                AstDec dec = cFieldNode.head.dec;

                if (dec instanceof AstVarDec) {
                    AstVarDec vd = (AstVarDec) dec;
					if (superClassType.findElementInClassHierarchy(vd.name) != null) throw new SemanticException(vd.getLine(), "variable '" + vd.name + "' shadows inherited member in class '" + name + "'");
                }
				else if (dec instanceof AstFuncDec) {
                    AstFuncDec fd = (AstFuncDec) dec;
                    Type ParentField = superClassType.findElementInClassHierarchy(fd.name);

                    if (ParentField != null) {
						if (!(ParentField instanceof TypeFunction)) throw new SemanticException(fd.getLine(), "method '" + fd.name + "' shadows non-function member in superclass");

                        TypeFunction parentFunc = (TypeFunction) ParentField;

                        Type retType = fd.type.semantMe();
                        TypeList params = (fd.paramList != null ? fd.paramList.buildTypeList() : null);

                        if (!signaturesMatch(parentFunc, retType, params)) throw new SemanticException(fd.getLine(), "method '" + fd.name + "' signature does not match overridden method in superclass");
                    }
                }
            }
        }



		symTable.beginScope();

		TypeClass prevClass = symTable.getCurrentClass();
        symTable.setCurrentClass(classType);

		if (cFieldList != null) cFieldList.semantMe();

		symTable.setCurrentClass(prevClass);

		symTable.endScope();

		classType.dataMembers = TypeClassVarDecList.classVarDecListFromAst(cFieldList);
		return classType;
	}

	private boolean signaturesMatch(TypeFunction parentFunc, Type retType, TypeList params) {
        if (parentFunc.returnType != retType) return false;
        
        TypeList p1 = parentFunc.params;
        TypeList p2 = params;
        
        while (p1 != null && p2 != null) {
            if (p1.head != p2.head) return false;
            p1 = p1.tail;
            p2 = p2.tail;
        }
        
        return p1 == null && p2 == null;
    }
}








