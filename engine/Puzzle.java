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

public class Puzzle {
    private Charset charset = StandardCharsets.UTF_8;
    private HashMap<String, Fragment> fragmentIndex = new HashMap<String, Fragment>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    final PuzzlePiece boundary = new PuzzlePiece(this, "VUOTO", "", "", "", "", "");  // rappresenta l'esterno del puzzle
    public Puzzle(){}
    void addIndex(String index, Fragment reference){
	fragmentIndex.put(index, reference);
    }
    void removeIndex(String index){
	fragmentIndex.remove(index);
    }
    Fragment getIndex(String index){
	return fragmentIndex.get(index);
    }
    void addList(Fragment reference){
	fragmentList.add(reference);
    }
    void removeList(Fragment reference){
	fragmentList.remove(reference);
    }
    String error(String outputText){
	System.err.println(outputText);
	return outputText;
    }
    public String solve(Path inputPath){
	fragmentIndex.put("VUOTO", boundary);
	try (BufferedReader reader = Files.newBufferedReader(inputPath, charset)){
		String line = null;
		while((line = reader.readLine()) != null){
		    String[] pt = line.split("\t");
		    if (pt[1].length() > 1){
			//potenziale errore nel file di input: invece di un carattere trovo una stringa			
			return error("Errore nel file di input: una tessera contiene più di un carattere");
		    }
		    PuzzlePiece pp = new PuzzlePiece(this, pt[0], pt[1], pt[2], pt[3], pt[4], pt[5]);
		    //controllo se posso già collegare la tessera a un frammento esistente
		    String ppNeighbor = pp.findNeighbor();
		    if(ppNeighbor != null) pp.merge(fragmentIndex.get(ppNeighbor));
		    else fragmentList.add(pp);
		    fragmentIndex.put(pt[0], pp);		    		    
		}
	    } catch (IOException e){
	    System.err.println(e);
	} catch (IndexOutOfBoundsException e){
	    //potenziale errore nel file di input: il file di input contiene righe vuote	    
	    return error("Errore nel file di input: scorretta formattazione, sono forse presenti righe vuote nel file?");
	}	
	while(fragmentList.size() > 1){	    
	    int sizeCheck = fragmentList.size();
	    Fragment f1 = fragmentList.get(0); //recupero il primo frammento nella lista	   
	    String n = f1.findNeighbor(); //trovo un vicino di tale frammento
	    f1.merge(fragmentIndex.get(n)); //unisco i due frammenti	    
	    if(fragmentList.size() >= sizeCheck){		
		return error("Errore nella risoluzione del puzzle: non è stato trovato match per qualche id");
	    }
	}	
	return (fragmentList.get(0)).print();   
    }
}
