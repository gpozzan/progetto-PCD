package engine;

import java.nio.file.Path;

public class ConcurrentPuzzleImpl implements Puzzle {
    private PuzzleImpl pi;
    synchronized void addIndex(String index, Fragment reference){
	pi.addIndex(index,reference);
    }
    synchronized void removeIndex(String index){
	pi.removeIndex(index);
    }
    synchronized Fragment getIndex(String index){
	return pi.getIndex(index);
    }
    synchronized void addList(Fragment reference){
	pi.addList(reference);
    }
    synchronized void removeList(Fragment reference){
	pi.removeList(reference);
    }
    public String solve(Path inputPath){return "";}
}
