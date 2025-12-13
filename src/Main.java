import java.io.*;
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
            
            // Perform semantic analysis
            program.semantMe();

            fileWriter.print("OK");
            fileWriter.close();
		
    	}
        catch (Exception e) {
            try {
                PrintWriter w = new PrintWriter(outputFileName);
                
                if (p != null && (e.getMessage().contains("lex error")))
                    w.print("ERROR");
                else if (p != null && (e.getMessage().contains("syntax error") || e.getMessage().contains("semantic error"))) 
                    w.print("ERROR(" + p.errorLine + ")");
                else
                    w.print("ERROR");
                w.close();
            } catch (Exception ignore) {}
        }
    }
}