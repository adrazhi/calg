JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        HCompressor.java \
        HDecompressor.java \
        HNode.java \
        HTree.java \
        InStream.java \
        MaineHCGenerator.java \
        Outstream.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class