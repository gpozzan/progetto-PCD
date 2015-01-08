package engine;

import java.util.ArrayList;

class PuzzlePiece extends AbstractFragment {
    private final String id; private final String character;    
    private final String[] neighbors;    
    private PuzzlePiece[] neighborsRef;    
    PuzzlePiece(AbstractPuzzle pr, String i, String ch, String n, String e, String s, String w){
	super(i, pr);
	id = i; character = ch;
	neighbors = new String[]{n, e, s, w};
	neighborsRef = new PuzzlePiece[]{null, null, null, null};
	for(int j = 0; j < 4; j++){	    
	    if(neighbors[j].equals("VUOTO")) neighborsRef[j] = getBoundary();
	}	
    }
    public ArrayList<String> findNeighbor() {
	ArrayList<String> res = new ArrayList<String>();
	for(int i = 0; i < 4; i++){	    
	    if(neighborsRef[i] == null){		
		Fragment f = getIndex(neighbors[i]);
		if(f != null){
		    PuzzlePiece pp = (PuzzlePiece)f;		
		    if(!res.contains(pp.getIdSet())) res.add(pp.getIdSet());
		}		
	    }
	}
	return res;	
    }
    boolean isBoundaryPiece() {
	return (neighborsRef[0] == null || neighborsRef[1] == null || neighborsRef[2] == null || neighborsRef[3] == null);
    }
    void connect(PuzzlePiece pp, int i){	
	setNeighbor(pp, i);
	pp.setNeighbor(this, (i+2)%4);
    }
    private int indexOfNeighbor(String nid) {	
	for(int i = 0; i < 4; i++) {
	    if(neighbors[i].equals(nid)) return i;
	}
	return -1;
    }
    public void merge(Fragment f){
	if(f instanceof PuzzlePiece){	    
	    PuzzlePiece fpp = (PuzzlePiece)f;
	    connect(fpp, indexOfNeighbor(fpp.getId()));
	    String newIdSet = id + "set";
	    SetOfPieces sop = new SetOfPieces(newIdSet, getPuzzle());
	    removeFromList(); fpp.removeFromList();
	    sop.addPiece(this); sop.addPiece(fpp);	    
	    sop.addToList(); sop.addToIndex();	     	    
	}
	else if(f instanceof SetOfPieces) {	   
	    SetOfPieces fsop = (SetOfPieces)f;
	    for(int i = 0; i < 4; i++){
		PuzzlePiece pp = fsop.getPiece(neighbors[i]);
	        if(pp != null && neighborsRef[i] == null){ 
		    connect(pp, i);
		    if(!pp.isBoundaryPiece()) fsop.removeBoundaryPiece(pp);		    
		}
	    }
	    setIdSet(fsop.getIdSet());
	    fsop.addPiece(this);	    
	    removeFromList();	    
	}
    }
    String getId(){
	return id;
    }
    String getCharacter(){
	return character;
    }    
    String getNeighbor(int i){
	return neighbors[i];
    }
    PuzzlePiece getNeighborRef(int i){
	return neighborsRef[i];
    }  
    void setNeighbor(PuzzlePiece pp, int i){
	neighborsRef[i] = pp;
    }    
    public String print(){
	String s = character + "\n" + "\n" + character + "\n" + "\n" + "1 1";
	return s;
    }
}
