package transformer;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.JimpleBody;

/**
 * @author rodykers
 */
public class ResourceContractTransformer {
	public void applyTransformation() {
		for (JimpleBody body : this.getSceneBodies()) {
			transform(body);
		}
	}
	
	private void transform(Body b) {
		// TODO
		System.out.println("Transforming method " + b);
	}
	
	private Set<JimpleBody> getSceneBodies() {
		Set<JimpleBody> bodies = new LinkedHashSet<JimpleBody>();
		for (SootClass sc : new LinkedList<SootClass>(Scene.v().getClasses())) {
			if (sc.resolvingLevel() >= SootClass.BODIES) {
				for (SootMethod sm : sc.getMethods()) {
					if (sm.isConcrete()) {
						bodies.add((JimpleBody) sm.retrieveActiveBody());
					}
				}
			}
		}
		return bodies;
	}

}
