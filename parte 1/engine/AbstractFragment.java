package engine;

abstract class AbstractFragment implements Fragment {
    private String idSet;
    private final PuzzleImpl puzzleRef;
    AbstractFragment(String id, PuzzleImpl pr){
	idSet = id;
	puzzleRef = pr;
    }
    public abstract String findNeighbor(); // restituisce l'id di un PuzzlePiece confinante con questo Fragment
    public abstract void merge(Fragment f); // unisce due Fragment
    public abstract String print(); // crea una stringa formattata secondo le specifiche date
    String getIdSet(){
	return idSet;
    }
    PuzzleImpl getPuzzle(){
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
	return puzzleRef.boundary;
    }
}
