package engine;

import java.util.ArrayList;

class SetOfPieces extends AbstractFragment {
    private ArrayList<PuzzlePiece> listOfPieces;
    private ArrayList<PuzzlePiece> boundaryPieces;
    private PuzzlePiece first = null;    
    SetOfPieces(String id, AbstractPuzzle pr){
	super(id, pr);
	listOfPieces = new ArrayList<PuzzlePiece>();
	boundaryPieces = new ArrayList<PuzzlePiece>();	
    }
    void addPiece(PuzzlePiece pp){
	listOfPieces.add(pp);
	pp.setIdSet(getIdSet());
	if(pp.isBoundaryPiece()) boundaryPieces.add(pp);
	if((pp.getNeighbor(0)).equals("VUOTO") && (pp.getNeighbor(3).equals("VUOTO"))) first = pp;		
    }
    void addList(SetOfPieces sop){
	listOfPieces.addAll(sop.listOfPieces);	
	boundaryPieces.addAll(sop.boundaryPieces);	
	if(sop.getFirst() != null) first = sop.getFirst();	
    }    
    public ArrayList<String> findNeighbor() {
	ArrayList<String> res = new ArrayList<String>();
	if(!boundaryPieces.isEmpty()){
	    for(PuzzlePiece pp : boundaryPieces){
		ArrayList<String> partRes = pp.findNeighbor();
		for(String s : partRes){
		    if(!res.contains(s)) res.add(s);
		}		
	    }
	}
	return res;
    }
    PuzzlePiece getPiece(String id){	
	for(PuzzlePiece pp : listOfPieces){	    
	    if(id.equals(pp.getId())) return pp;
	}
	return null;	    
    }    
    private PuzzlePiece getFirst(){
	return first;
    }
    void removeBoundaryPiece(PuzzlePiece pp){	
	boundaryPieces.remove(pp);
        pp.removeFromIndex(pp.getId());
    }
    private ArrayList<String> createMatrix(){
	if(!boundaryPieces.isEmpty()) return null;
	ArrayList<String> matrix = new ArrayList<String>();
	PuzzlePiece ver = first;
	do {
	    PuzzlePiece hor = ver;	    
	    String s = new String();	   
	    do {
		s += hor.getCharacter();
		hor = hor.getNeighborRef(1);
	    } while(!(hor.getId()).equals("VUOTO"));
	    matrix.add(s);
	    ver = ver.getNeighborRef(2);	    
	} while(!(ver.getId()).equals("VUOTO"));
	return matrix;
    } 
    public String print(){
	String sent = new String();
	String tab = new String();	
	PuzzlePiece pp = first;	
	ArrayList<String> matrix = createMatrix();
	if(matrix == null) return getPuzzle().error("Errore nella risoluzione del puzzle: non è stato trovato match per qualche id");
	int nRow = matrix.size();
	int nCol = (matrix.get(0)).length();
	for(String s : matrix){
	    sent += s;
	    tab += s + "\n";
	}
	return sent + "\n" + "\n" + tab + "\n" + nRow + " " + nCol;
    }
    private void fixBoundaries(ArrayList<PuzzlePiece> toRemove){
	for(PuzzlePiece pp : toRemove){	    
	    boundaryPieces.remove(pp);
	}	
    }
    private void fixIdSet(SetOfPieces toFixId){
	for(PuzzlePiece pp : toFixId.listOfPieces){
	    pp.setIdSet(getIdSet());
	}
    }
    public void merge(Fragment f){
	if(f instanceof PuzzlePiece){	   
	    PuzzlePiece pp = (PuzzlePiece)f;
	    pp.merge(this);	    
	}
	else if(f instanceof SetOfPieces){	   
	    ArrayList<PuzzlePiece> toRemove = new ArrayList<PuzzlePiece>();
	    SetOfPieces fsop = (SetOfPieces)f;	    
	    for(PuzzlePiece bp : boundaryPieces){		
		for(int i = 0; i<4; i++){		    
		    String iNeighbor = bp.getNeighbor(i);
		    AbstractFragment neighbor = (AbstractFragment)getIndex(iNeighbor);		    
		    String neighborSet = "";		    
		    if(neighbor != null) neighborSet = neighbor.getIdSet();		   
		    if(neighborSet.equals(fsop.getIdSet())){			
			PuzzlePiece pp = fsop.getPiece(iNeighbor);		
			bp.connect(pp, i);		
			if(!bp.isBoundaryPiece()) toRemove.add(bp);
			if(!pp.isBoundaryPiece()) toRemove.add(pp);			
		    }
		}
	    }
	    addList(fsop); 
	    fixIdSet(fsop);
	    fixBoundaries(toRemove);
	    fsop.removeFromList();
	    fsop.removeFromIndex(fsop.getIdSet());
	}
    }
}
