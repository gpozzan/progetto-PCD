package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ConcurrentPuzzleImpl extends AbstractPuzzle {
    private static final ExecutorService exec = Executors.newCachedThreadPool();
    synchronized void addIndex(String index, Fragment reference){	
	super.addIndex(index,reference);
    }
    synchronized void removeIndex(String index){	
	super.removeIndex(index);
    }
    synchronized Fragment getIndex(String index){
	return super.getIndex(index);
    }
    synchronized void addList(Fragment reference){
	super.addList(reference);
    }
    synchronized void removeList(Fragment reference){
	super.removeList(reference);
    }
    void finishWork() throws InterruptedException{
	wait();
    }
    public String solve(Path inputPath){
	addIndex("VUOTO", getBoundary());
	try (BufferedReader reader = Files.newBufferedReader(inputPath, getCharset())){
		String line = null;
		while((line = reader.readLine()) != null){
		    String[] pt = line.split("\t");
		    if (pt[1].length() > 1){
		        return error("Errore nel file di input: una tessera contiene più di un carattere");
		    }
		    Fragment pp = new PuzzlePiece(this, pt[0], pt[1], pt[2], pt[3], pt[4], pt[5]);
		    HandlePiece hp = new HandlePiece((PuzzlePiece)pp, this);
		    exec.execute(hp);		    
		}		
	    } catch (IOException e){
	    System.err.println(e);
	} catch (IndexOutOfBoundsException e){
	    return error("Errore nel file di input: scorretta formattazione, sono forse presenti righe vuote nel file?");
	}	
	exec.shutdown();
        try {
	    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	} catch (InterruptedException e) {}
	while(getListSize() > 1){	    
	    int sizeCheck = getListSize();
	    Fragment f1 = getList(0);   
	    ArrayList<String> n = f1.findNeighbor();
	    for(String s : n) System.out.print(s + " ");
	    f1.merge(getIndex(n.get(0)));	    
	    if(getListSize() >= sizeCheck){		
		return error("Errore nella risoluzione del puzzle: non è stato trovato match per qualche id");
	    }
	}
        return (getList(0)).print();  
    }
}
