package engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class PuzzleImpl extends AbstractPuzzle {
    public PuzzleImpl(){}
    public String solve(Path inputPath){
	addIndex("VUOTO", getBoundary());
	try (BufferedReader reader = Files.newBufferedReader(inputPath, getCharset())){
		String line = null;
		while((line = reader.readLine()) != null){
		    String[] pt = line.split("\t");
		    if (pt[1].length() > 1){
			//potenziale errore nel file di input: invece di un carattere trovo una stringa			
			return error("Errore nel file di input: una tessera contiene più di un carattere");
		    }
		    Fragment pp = new PuzzlePiece(this, pt[0], pt[1], pt[2], pt[3], pt[4], pt[5]);
		    //controllo se posso già collegare la tessera a un frammento esistente
		    String ppNeighbor = pp.findNeighbor();
		    if(ppNeighbor != null) pp.merge(getIndex(ppNeighbor));
		    else addList(pp);
		    addIndex(pt[0], pp);		    		    
		}
	    } catch (IOException e){
	    System.err.println(e);
	} catch (IndexOutOfBoundsException e){
	    //potenziale errore nel file di input: il file di input contiene righe vuote	    
	    return error("Errore nel file di input: scorretta formattazione, sono forse presenti righe vuote nel file?");
	}	
	while(getListSize() > 1){	    
	    int sizeCheck = getListSize();
	    Fragment f1 = getList(0); //recupero il primo frammento nella lista	   
	    String n = f1.findNeighbor(); //trovo un vicino di tale frammento
	    f1.merge(getIndex(n)); //unisco i due frammenti	    
	    if(getListSize() >= sizeCheck){		
		return error("Errore nella risoluzione del puzzle: non è stato trovato match per qualche id");
	    }
	}	
	return (getList(0)).print();   
    }
}
