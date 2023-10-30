# Really simple wrapper on Maven's command to build, test, & run your project
# Maven is a package builder and runner tool for Java
#  https://maven.apache.org/

build:
	@echo "Building Game package - results in target/ directory"
	mvn package -DskipTests

test:
	@echo "Running all tests"
	mvn test

mvn-run:
	@echo "Running Final Game main - see pom.xml for arguments passed"
	mvn exec:java

run: build
	@echo "Running Game's main without maven overhead"
	java -jar target/finalgame-*.jar

fast-run:
	java -jar target/finalgame-*.jar

spellcheck:
	-codespell src/

setup-dependencies:
	apt update
	apt install -y maven python3-pip 
	pip3 install codespell

javadoc:
	@echo "Creating javadoc materials"
	@echo "These go into: target/site/apidocs/"
	@echo "Load up index.html to read them"
	-mvn javadoc:javadoc

lint:
	@echo "Running spotless linter to check source files"
	mvn spotless:check

lint-autofix:
	@echo "Autofixing linting errors"
	mvn spotless:apply
