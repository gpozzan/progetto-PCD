import engine.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PuzzleSolver{
    private static Charset charset = StandardCharsets.UTF_8;
    public static void printOutput(String output, Path outputPath){
	try(BufferedWriter writer = Files.newBufferedWriter(outputPath, charset)){
		writer.write(output);
	    } catch(IOException e){
	    System.err.println(e);
	}
    }
    public static void main(String[] args){
	String inputFile = args[0];
	String outputFile = args[1];
	Path inputPath = Paths.get(inputFile);
	Path outputPath = Paths.get(outputFile);
	Puzzle p = new PuzzleImpl();
	String output = p.solve(inputPath);
	printOutput(output, outputPath);
    }
}
