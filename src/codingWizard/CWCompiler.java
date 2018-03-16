package codingWizard;

import bsh.Interpreter;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CodingWizard compiler.
 * Created by PC on 10.05.2017.
 */
public class CWCompiler {

    private CWCompiler() {

    }

    public static boolean compileANDrun( String fileLoc, String outputLoc) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        FileWriter writer = new FileWriter( outputLoc);
        ArrayList<StringToClassFileObject> compilationUnits = new ArrayList<StringToClassFileObject>();

        boolean didRun = false;

        Scanner scan = new Scanner( new BufferedReader( new FileReader( new File( fileLoc))));
        String sourceCode = "";

        while( scan.hasNextLine()) {
            sourceCode = sourceCode + scan.nextLine() + "\n";
        }
        scan.close();

        compilationUnits.add( new StringToClassFileObject( "SourceC", sourceCode));
        boolean compilationResult = compiler.getTask( writer, null, null, null, null, compilationUnits).call();

        if ( compilationResult && sourceCode.replaceAll(" ", "").contains( "main(String[]args)")) {
            int lastIndex = sourceCode.lastIndexOf("}");
            int firstIndex = sourceCode.indexOf("{");
            int secondIndex = sourceCode.indexOf("{", firstIndex + 1);

            int realStartIndex = secondIndex + 1;
            int realEndIndex = lastIndex - 2;

            sourceCode = sourceCode.substring( realStartIndex, realEndIndex);
            Interpreter i = new Interpreter();
            Interpreter.redirectOutputToFile( outputLoc);

            try {
                i.eval( sourceCode);

                didRun = true;
            } catch ( Exception e) {
                e.printStackTrace();
            }

        }

        return compilationResult && didRun;
    }
}
