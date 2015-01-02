package engine;

import java.util.ArrayList;

class HandlePiece implements Runnable{
    private PuzzlePiece piece;
    private AbstractPuzzle puzzle;
    public HandlePiece(PuzzlePiece pp, AbstractPuzzle ap){
	piece = pp;
	puzzle = ap;
    }
    public void run(){
	synchronized(puzzle){
	    ArrayList<String> pieceNeighbor = piece.findNeighbor();
	    if(pieceNeighbor.size() > 0) 
		for(String id : pieceNeighbor)
		    piece.merge(puzzle.getIndex(id));
	    else puzzle.addList(piece);
	    puzzle.addIndex(piece.getId(), piece);
	}
    }
}
