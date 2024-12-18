JAVAC = javac
JAVA = java
SRCDIR = src/main/java
BINDIR = bin
PACKAGEDIR = code
MAINCLASS = code.Main

SOURCES = $(wildcard $(SRCDIR)/code/*.java $(SRCDIR)/exception/*.java)

all: compile

compilation:
	mkdir -p $(BINDIR)
	$(JAVAC) -d $(BINDIR) $(SOURCES)

run: compile
	$(JAVA) -cp $(BINDIR) $(MAINCLASS) $(ARGS)

clean:
	rm -rf $(BINDIR)/*