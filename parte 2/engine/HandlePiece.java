package engine;

class HandlePiece implements Runnable{
    private PuzzlePiece piece;
    private AbstractPuzzle puzzle;
    public HandlePiece(PuzzlePiece pp, AbstractPuzzle ap){
	piece = pp;
	puzzle = ap;
    }
    public void run(){
	synchronized(puzzle){
	    String pieceNeighbor = piece.findNeighbor();
	    if(pieceNeighbor != null) piece.merge(puzzle.getIndex(pieceNeighbor));
	    else puzzle.addList(piece);
	    puzzle.addIndex(piece.getId(), piece);
	}
    }
}
