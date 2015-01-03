package engine;

import java.util.ArrayList;

class FixFragments implements Runnable{
    private ConcurrentPuzzleImpl puzzle;
    FixFragments(ConcurrentPuzzleImpl ap){
	puzzle = ap;
    }
    void fix() throws InterruptedException{
	System.out.println("FF-Try " +  Thread.currentThread().getName());
	synchronized(puzzle){
	    System.out.println(" FF-Enter " +  Thread.currentThread().getName());
	    System.out.println("FF-Controllo se andare in wait");
	    while(puzzle.getListSize() <= 1){
		System.out.println("FF-ciclo");
		puzzle.wait();
	    }
	    System.out.println("FF-Non sono andato in wait");
	    int i = 0;
	    boolean flag = false;
	    System.out.println("FF-listSize " + puzzle.getListSize());
	    while(!flag){
		Fragment f = puzzle.getList(i);
		ArrayList<String> n = f.findNeighbor();
		for(String neighbor : n)
		    f.merge(puzzle.getIndex(neighbor));
		if(n.size() == 0){i++; System.out.println("FF-no neighbors for " + i);}
		else{System.out.println("FF-neighbors found " + i);}
		if(i == puzzle.getListSize()) flag = true;		
	    }
	    System.out.println("  FF-Exit " +  Thread.currentThread().getName());
	}
    }    
    public void run(){
	while(true){
	    System.out.println("Nuovo giro FixFragments");
	    try{
		fix();
	    }catch(InterruptedException ie){}
	    if(puzzle.getListSize() == 1 && puzzle.getFlag()){
		puzzle.notify();
		return;
	    }
	}    
    }
}
