package engine;

import java.util.ArrayList;

class HandlePiece implements Runnable{
    private PuzzlePiece piece;
    private ConcurrentPuzzleImpl puzzle;
    HandlePiece(PuzzlePiece pp, ConcurrentPuzzleImpl ap){
	piece = pp;
	puzzle = ap;
    }
    public void run(){	
	synchronized(puzzle){	    
	    puzzle.addIndex(piece.getId(), piece);
	    ArrayList<String> pieceNeighbor = piece.findNeighbor();	    
	    if(pieceNeighbor.size() > 0){
		for(String id : pieceNeighbor){
		    Fragment neighbor = puzzle.getIndex(id);
		    if(piece.getId().equals(piece.getIdSet()))
			piece.merge(neighbor);
		    else
			neighbor.merge(puzzle.getIndex(piece.getIdSet()));
		}
	    } 		      
	    else{
		puzzle.addList(piece);
		puzzle.notify();
	    }   
	    
	}        
    }
}
