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
	//System.out.println("Try " +  Thread.currentThread().getName());
	synchronized(puzzle){
	    //System.out.println(" Enter " + Thread.currentThread().getName());
	    //System.out.print("Sets in lista    ");
	    /*for(int i=0; i<puzzle.getListSize(); i++){
		System.out.print(((AbstractFragment)puzzle.getList(i)).getIdSet() + " ");
	    }
	    System.out.println("**");
	    System.out.println("Piece in considerazione -> " + piece.getId());*/
	    puzzle.addIndex(piece.getId(), piece);
	    ArrayList<String> pieceNeighbor = piece.findNeighbor();	    
	    if(pieceNeighbor.size() > 0){
		//for(String s : pieceNeighbor) System.out.print(s + " ");
		//System.out.println("");
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
	   
	    //System.out.println("  Exit " + Thread.currentThread().getName());
	}        
    }
}
