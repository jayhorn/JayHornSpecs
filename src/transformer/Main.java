package transformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import soot.Scene;
import soot.SootClass;
import soot.util.JasminOutputStream;

/**
 * @author rodykers
 */
public class Main {

	public static void main(String[] args) {
		
		System.out.println("--- Resource Contract Transformer ---");
		
		// parse command line options
		CmdLineParser parser = new CmdLineParser(Options.v());
		try {
			// parse command-line arguments
			parser.parseArgument(args);			
		} catch (CmdLineException e) {
			System.err.println(e.toString());
			parser.printUsage(System.err);
		}
		
		// load classes
		SceneLoader.loadFromClassDir(Options.v().getInPath().toFile(), ".");
		
		// transform
		ResourceContractTransformer rct = new ResourceContractTransformer();
		rct.applyTransformation();
		
		// output classes
		writeOutput();
		
		System.out.println("--- Done ---");
	}
	
	private static void writeOutput() {
		Path out = Options.v().getOutPath();
		System.out.println("Outputting classes to: " + out);
		for (SootClass sc : Scene.v().getApplicationClasses()) {
			outputClass(sc, out);
		}
	}

    private static void outputClass(SootClass sc, Path out) {

    	String classFile = sc.getName().replace('.', System.getProperty("file.separator").toCharArray()[0]) + ".class";
        try {
        	String outFile = out.toString() + System.getProperty("file.separator") + classFile;
        	String outDir = dirFromPath(outFile);
        	System.out.println("Writing " + outFile);
        	Files.createDirectories(Paths.get(outDir));
        	Files.createFile(Paths.get(outFile));
        	OutputStream stream = new JasminOutputStream(new FileOutputStream(outFile.toString()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(stream));

            new soot.jimple.JasminClass(sc).print(writer);

            writer.flush();
            stream.close();

        } catch (IOException ex) {
            throw new RuntimeException("Could not produce class file", ex);
        }
    }

    private static String dirFromPath(String path) {
    	if (path.contains(System.getProperty("file.separator")))
    		return path.substring(0, path.lastIndexOf(System.getProperty("file.separator")));
    	return path;
    }
}
