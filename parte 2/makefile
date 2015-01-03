JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ./engine/Fragment.java \
	./engine/AbstractFragment.java \
        ./engine/PuzzlePiece.java \
        ./engine/SetOfPieces.java \
	./engine/Puzzle.java \
	./engine/PuzzleImpl.java \
	./engine/ConcurrentPuzzleImpl.java \
	./engine/AbstractPuzzle.java \
	./engine/HandlePiece.java \
	./engine/FixFragments.java \
	./PuzzleSolver.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) ./engine/*.class
	$(RM) *.txt
