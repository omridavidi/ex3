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
        }
        else
        {
            System.out.format("====================== classDec -> CLASS ID(%s) { cFieldList } \n",name);
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

		if (symTable.findInCurrentScope(name) != null)
		{
			throw new RuntimeException("semantic error");
		}

		TypeClass superClassType = null;
		if (superName != null)
		{
			Type superType = symTable.find(superName);
			if (!(superType instanceof TypeClass)) throw new RuntimeException("semantic error");
			superClassType = (TypeClass)superType;
		}

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

		if (superClassType != null) {
            for (AstcFieldList it = cFieldList; it != null; it = it.cFieldList) {
                AstDec dec = it.cField.dec;

                if (dec instanceof AstVarDec) {
                    AstVarDec vd = (AstVarDec) dec;
                    if (findInParentChain(superClassType, vd.name) != null) {
                        throw new RuntimeException("semantic error");
                    }
                } else if (dec instanceof AstFuncDec) {
                    AstFuncDec fd = (AstFuncDec) dec;
                    Type parentMember = findInParentChain(superClassType, fd.name);

                    if (parentMember != null) {
                        if (!(parentMember instanceof TypeFunction)) {
                            throw new RuntimeException("semantic error");
                        }

                        TypeFunction parentFunc = (TypeFunction) parentMember;
                        Type retType = fd.retType.semantMe();
                        TypeList params = (fd.args != null ? fd.args.buildTypeList() : null);

                        if (!signaturesMatch(parentFunc, retType, params)) {
                            throw new RuntimeException("semantic error");
                        }
                    }
                }
            }
        }

		/*
		- TODO
		- Finish code below to cover all checks from line 85 Omer
		- Add check for field shadowing a method in superclass
		- Change findInParentChain call to match omri implementation
		*/

		TypeClass classType = new TypeClass(name, superClassType);
		symTable.enter(name, classType);

		symTable.beginScope();

		if (cFieldList != null)
		{
			cFieldList.semantMe(classType);
		}

		symTable.endScope();

		return classType;
	}
}








