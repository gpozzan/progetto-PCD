package engine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

abstract class AbstractPuzzle implements Puzzle {
    private final Charset charset = StandardCharsets.UTF_8;
    private HashMap<String, Fragment> fragmentIndex = new HashMap<String, Fragment>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private final PuzzlePiece boundary = new PuzzlePiece(this, "VUOTO", "", "", "", "", "");
    int getListSize(){
	return fragmentList.size();
    }
    Fragment getList(int i){
	return fragmentList.get(i);
    }
    Charset getCharset(){
	return charset;
    }
    PuzzlePiece getBoundary(){
	return boundary;
    }
    void addIndex(String index, Fragment reference){
	fragmentIndex.put(index, reference);
    }
    void removeIndex(String index){
	fragmentIndex.remove(index);
    }
    Fragment getIndex(String index){
	return fragmentIndex.get(index);
    }
    void addList(Fragment reference){
	fragmentList.add(reference);
    }
    void removeList(Fragment reference){
	fragmentList.remove(reference);
    }
    String error(String outputText){
	System.err.println(outputText);
	return outputText;
    }
    public abstract String solve(Path inputPath);
    
}
