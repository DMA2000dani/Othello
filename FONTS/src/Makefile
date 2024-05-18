JFLAGS = -g
JC = javac
JVM= java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MainLogIn.java \
	domini/shared/*.java \
	domini/classes/*.java \
	domini/controladors/*.java \
	presentacio/vistes/*.java \
	presentacio/controladors/*.java \
	persistencia/Gestors/*.java \

CLASS = \
	MainLogIn.class \
	domini/shared/*.class \
	domini/classes/*.class \
	domini/controladors/*.class \
	presentacio/vistes/*.class \
	presentacio/controladors/*.class \
	persistencia/Gestors/*.class \

MAIN = MainLogIn

default: classes

classes: $(CLASSES:.java=.class)

jar: $(MAIN).class
	echo Main-class: $(MAIN) > Manifest
	jar cvfm ../../EXE/othello.jar Manifest $(CLASS)
	rm Manifest

clean:
	$(RM) MainLogIn.class \
	$(RM) domini/shared/*.class \
	$(RM) domini/classes/*.class \
	$(RM) domini/controladors/*.class \
	$(RM) presentacio/vistes/*.class \
	$(RM) presentacio/controladors/*.class \
	$(RM) persistencia/Gestors/*.class

reset:
	$(RM) -rf ../../EXE/data \
	
	mkdir ../../EXE/data
	mkdir ../../EXE/data/Tauler
	mkdir ../../EXE/data/Records
	touch ../../EXE/data/Records/highlight.txt
	touch ../../EXE/data/Records/wins.txt
	touch ../../EXE/data/Records/wr.txt
	mkdir ../../EXE/data/Ranking
	touch ../../EXE/data/Ranking/ranking.txt
	mkdir ../../EXE/data/Jugadors
	mkdir ../../EXE/data/Jugadors/Perfils
	mkdir ../../EXE/data/Jugadors/Maquines
	
