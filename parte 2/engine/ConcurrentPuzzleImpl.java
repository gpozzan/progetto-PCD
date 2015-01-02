package engine;

import java.nio.file.Path;

public class ConcurrentPuzzleImpl extends AbstractPuzzle {
    synchronized void addIndex(String index, Fragment reference){
	super.addIndex(index,reference);
    }
    synchronized void removeIndex(String index){
	super.removeIndex(index);
    }
    synchronized Fragment getIndex(String index){
	return super.getIndex(index);
    }
    synchronized void addList(Fragment reference){
	super.addList(reference);
    }
    synchronized void removeList(Fragment reference){
	super.removeList(reference);
    }
    public String solve(Path inputPath){
	return "";
    }
}
