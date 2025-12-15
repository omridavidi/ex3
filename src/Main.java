import java.io.*;

import Exception.*;
import java_cup.runtime.*;
import ast.*;

public class Main {
    public static void main(String argv[]) {
        Lexer l;
        Parser p = null;
        FileReader fileReader;
        PrintWriter fileWriter;
        String inputFileName = argv[0];
        String outputFileName = argv[1];
        
        try
		{
			fileReader = new FileReader(inputFileName);
			fileWriter = new PrintWriter(outputFileName);

            l = new Lexer(fileReader);
            p = new Parser(l);

            AstProgram program = (AstProgram) p.parse().value;
            
            // Generate AST visualization
            AstGraphviz.getInstance();
            program.printMe();
            AstGraphviz.getInstance().finalizeFile();
            
            // Perform semantic analysis
            program.semantMe();

            fileWriter.print("OK");
            fileWriter.close();
		
    	}

        catch (LexicalException lexError) {
            try {
                PrintWriter w = new PrintWriter(outputFileName);
                w.print("ERROR");
                w.close();
            } catch (Exception ignore) {}
        }
        catch (SyntacticException syntacticError) {
            try {
                PrintWriter w = new PrintWriter(outputFileName);
                //w.print("ERROR(" + syntacticError.getLine() + ")" + "reason: " + syntacticError.getMessage());
                w.print("ERROR(" + syntacticError.getLine() + ")");
                w.close();
            } catch (Exception ignore) {}
        }
        catch (SemanticException semanticError) {
            try {
                PrintWriter w = new PrintWriter(outputFileName);
                //w.print("ERROR(" + semanticError.getLine() + ")" + "reason: " + semanticError.getMessage());
                w.print("ERROR(" + semanticError.getLine() + ")");
                w.close();
            } catch (Exception ignore) {}
        }
        catch (Exception e) {
            try {
                PrintWriter w = new PrintWriter(outputFileName);
                //w.print("errERROR(" + "for debug only" + ")" + "reason: " + e.getMessage());
                w.print("errERROR(" + "for debug only" + "): " + e.getMessage());
                w.close();
            } catch (Exception ignore) {}
        }

    }
}