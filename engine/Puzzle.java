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
    public String solve(Path inputPath){
	fragmentIndex.put("VUOTO", boundary);
	try (BufferedReader reader = Files.newBufferedReader(inputPath, charset)){
		String line = null;
		while((line = reader.readLine()) != null){
		    String[] pt = line.split("\t");
		    PuzzlePiece pp = new PuzzlePiece(this, pt[0], pt[1], pt[2], pt[3], pt[4], pt[5]);
		    String ppNeighbor = pp.findNeighbor();
		    if(ppNeighbor != null) pp.merge(fragmentIndex.get(ppNeighbor));
		    else fragmentList.add(pp);
		    fragmentIndex.put(pt[0], pp);		    		    
		}
	    } catch (IOException e){
	    System.err.println(e);
	}        
	while(fragmentList.size() > 1){       
	    Fragment f1 = fragmentList.get(0);	   
	    String n = f1.findNeighbor();	    	    
	    f1.merge(fragmentIndex.get(n));
	}	
	return (fragmentList.get(0)).print();   
    }
}
