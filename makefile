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
	./PuzzleSolver.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
