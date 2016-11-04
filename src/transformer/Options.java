package transformer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.kohsuke.args4j.Option;

/**
 * @author rodykers
 *
 */
public class Options {

	@Option(name = "-in", usage = "Select input directory", required = true)
	private String in = "";
	
	@Option(name = "-out", usage = "Select output directory", required = true)
	private String out = "";
	
	public Path getInPath() {
		return this.getPath(in);
	}
	
	public Path getOutPath() {
		Path file = this.getPath(out);
		try {
			Files.createDirectories(file);
		} catch (Exception e) {
			throw new RuntimeException("Error creating directories: " + file);
		}
		return file;
	}
	
	private Path getPath(String filename) {
		if (filename == null || filename.isEmpty())
			throw new RuntimeException("Invalid file name: " + filename);
		Path file = Paths.get(filename);
		return file;
	}
	
	private static Options options;

	public static void resetInstance() {
		options = null;
	}

	public static Options v() {
		if (null == options) {
			options = new Options();
		}
		return options;
	}

	private Options() {
	}
}
