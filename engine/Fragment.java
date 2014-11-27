package engine;

abstract class Fragment {
    private String idSet;
    private Puzzle puzzleRef;
    Fragment(String id, Puzzle pr){
	idSet = id;
	puzzleRef = pr;
    }
    abstract String findNeighbor(); // restituisce l'id di un PuzzlePiece confinante con questo Fragment
    abstract void merge(Fragment f); // unisce due Fragment
    abstract String print(); // crea una stringa formattata secondo le specifiche date
    String getIdSet(){
	return idSet;
    }
    Puzzle getPuzzle(){
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
