package transformer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;

/**
 * The Scene Loader.
 * Provides a set of utility functions to load a soot scene.
 * 
 * @author schaef
 */
public final class SceneLoader {

	public static void loadFromClassDirs(List<String> classDirs, String classPath) {
		Options sootOpt = soot.options.Options.v();
		for (String s : classDirs) {
			File classDir = new File(s);
			if (!classPath.contains(classDir.getAbsolutePath())) {
				classPath += File.pathSeparator + classDir.getAbsolutePath();
			}
		}
		sootOpt.set_soot_classpath(classPath);

		List<String> processDirs = new LinkedList<String>();
		processDirs.addAll(classDirs);
		sootOpt.set_process_dir(processDirs);
		loadSootScene();
	}

	public static void loadFromClassDir(File classDir, String classPath) {
		List<String> processDirs = new LinkedList<String>();
		processDirs.add(classDir.getAbsolutePath());
		loadFromClassDirs(processDirs, classPath);
	}

	public static void runPointsToAnalysis(SootClass entryPoint) {
		Options sootOpt = soot.options.Options.v();
		sootOpt.set_whole_program(true);
		sootOpt.setPhaseOption("cg.spark", "on");
		for (SootClass sc : new LinkedList<SootClass>(Scene.v().getClasses())) {
			if (sc.resolvingLevel() == SootClass.SIGNATURES) {
				Scene.v().forceResolve(sc.getName(), SootClass.BODIES);
			} else if (sc.resolvingLevel() < SootClass.SIGNATURES) {
				Scene.v().forceResolve(sc.getName(), SootClass.SIGNATURES);
			}
		}

		Options.v().set_main_class(entryPoint.getName());
		PackManager.v().runPacks();

		// Happily use the callgraph
		System.out.println(Scene.v().getCallGraph().size());
	}

	private static void loadSootScene() {
		Options sootOpt = Options.v();
		// general soot options
		sootOpt.set_keep_line_number(true);
		sootOpt.set_allow_phantom_refs(true);
		sootOpt.set_prepend_classpath(true); // -pp
		sootOpt.set_output_format(Options.output_format_none);
		sootOpt.set_src_prec(Options.src_prec_class);

		sootOpt.set_java_version(Options.java_version_1_8);
		sootOpt.set_asm_backend(true);

		// disable jb.a and jop.cpf to prevent soot from
		// adding strange exceptions.
		sootOpt.setPhaseOption("jb.a", "enabled:false");
		// sootOpt.setPhaseOption("jop.cpf", "enabled:false");
		sootOpt.setPhaseOption("jb", "use-original-names:true");

		Scene.v().loadBasicClasses();
		Scene.v().loadNecessaryClasses();
	}

}
