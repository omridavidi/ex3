package ast;

import symboltable.SymbolTable;
import types.*;
import java.util.HashSet;

public class AstClassDec extends AstDec
{
	public String name;
    public String superName;
    public AstcFieldList cFieldList;

	public AstClassDec(String name, String superName, AstcFieldList cFieldList)
	{
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

		//Assert no existing function/variable with same name
		if (symTable.findInCurrentScope(name) != null)
		{
			throw new RuntimeException("semantic error");
		}

		//Assert super class is really a class
		TypeClass superClassType = null;
		if (superName != null)
		{
			Type superType = symTable.find(superName);
			if (!(superType instanceof TypeClass)) throw new RuntimeException("semantic error");
			superClassType = (TypeClass)superType;
		}

		//Assert no overloading + shadowing
		if (cFieldList != null){
			HashSet<String> fieldNames = new HashSet<String>();
			for (AstcFieldList it = cFieldList; it != null; it = it.cFieldList){
				String fieldName = null;
				if (it.cField.dec instanceof AstVarDec) fieldName = ((AstVarDec)it.cField.dec).name;
				else if (it.cField.dec instanceof AstFuncDec) fieldName = ((AstFuncDec)it.cField.dec).name;
				else if (it.cField.dec instanceof AstClassDec) fieldName = ((AstClassDec)it.cField.dec).name;
				else if (it.cField.dec instanceof AstArrayTypeDec) fieldName = ((AstArrayTypeDec)it.cField.dec).name;
				if (fieldName == null || fieldNames.contains(fieldName)) throw new RuntimeException("semantic error");
				fieldNames.add(fieldName);
			}
		}

		//Assert no shadowing on parent classes
		if (superClassType != null) {
            for (AstcFieldList it = cFieldList; it != null; it = it.cFieldList) {
                AstDec dec = it.cField.dec;

                if (dec instanceof AstVarDec) {
                    AstVarDec vd = (AstVarDec) dec;
                    if (superClassType.findElementInClassHierarchy(vd.name) != null) throw new RuntimeException("semantic error");
                }
				else if (dec instanceof AstFuncDec) {
                    AstFuncDec fd = (AstFuncDec) dec;
                    Type ParentField = superClassType.findElementInClassHierarchy(fd.name);

                    if (ParentField != null) {
                        if (!(ParentField instanceof TypeFunction)) throw new RuntimeException("semantic error");

                        TypeFunction parentFunc = (TypeFunction) ParentField;
                        Type retType = fd.type.semantMe();
                        TypeList params = (fd.paramList != null ? fd.paramList.buildTypeList() : null);

                        if (!signaturesMatch(parentFunc, retType, params)) throw new RuntimeException("semantic error");
                    }
                }
            }
        }

		TypeClass classType = new TypeClass(superClassType, name, null);
		symTable.enter(name, classType);

		symTable.beginScope();

		TypeClass prevClass = symTable.getCurrentClass();
        symTable.setCurrentClass(classType);

		if (cFieldList != null) cFieldList.semantMe();

		symTable.setCurrentClass(prevClass);

		symTable.endScope();

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








