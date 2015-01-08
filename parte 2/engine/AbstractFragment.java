package engine;

import java.util.ArrayList;

abstract class AbstractFragment implements Fragment {
    private String idSet;
    private final AbstractPuzzle puzzleRef;
    AbstractFragment(String id, AbstractPuzzle pr){
	idSet = id;
	puzzleRef = pr;
    }
    public abstract ArrayList<String> findNeighbor(); 
    public abstract void merge(Fragment f);
    public abstract String print();
    String getIdSet(){
	return idSet;
    }
    AbstractPuzzle getPuzzle(){
	return puzzleRef;
    }
    void setIdSet(String nid){	
	idSet = nid;
    }
    void addToIndex(){
	puzzleRef.addIndex(idSet, this);
    }
    void removeFromIndex(String index){
	puzzleRef.removeIndex(index);
    }
    void addToList(){
	puzzleRef.addList(this);
    }
    void removeFromList(){
	puzzleRef.removeList(this);
    }
    Fragment getIndex(String index){
	return puzzleRef.getIndex(index);
    }
    PuzzlePiece getBoundary(){
	return puzzleRef.getBoundary();
    }
}
