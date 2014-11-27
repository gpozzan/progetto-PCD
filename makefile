JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ./engine/Fragment.java \
        ./engine/PuzzlePiece.java \
        ./engine/SetOfPieces.java \
	./engine/Puzzle.java \
	./PuzzleSolver.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
