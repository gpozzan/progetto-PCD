package engine;

import java.util.ArrayList;

class FixFragments implements Runnable{
    private AbstractPuzzle puzzle;
    FixFragments(AbstractPuzzle ap){
	puzzle = ap;
    }
    void fix() throws InterruptedException{
	synchronized(puzzle){
	    while(puzzle.getListSize() <= 1){
		puzzle.wait();
	    }
	    for(Fragment f : puzzle.getList()){
		ArrayList<String> n = f.findNeighbor();
		for(String neighbor : n)
		    f.merge(puzzle.getIndex(neighbor));		    
	    }
	}
    }    
    public void run(){
	try{
	    fix();
	}catch(InterruptedException ie){}
    }
}
