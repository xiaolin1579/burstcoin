CP=conf/:classes/:lib/*
SP=src/java/
echo "burst.jar Compiling Initiated."
echo "classes Compiling Started.."
/bin/mkdir -p classes/
javac -sourcepath $SP -classpath $CP -d classes/ src/java/nxt/*.java src/java/nxt/*/*.java src/java/fr/cryptohash/*.java || exit 1
echo "classes generated successfully..."
echo "burst.jar Compiling Started...."
/bin/rm -f burst.jar 
jar cf burst.jar -C classes . || exit 1
/bin/rm -rf classes
echo "burst.jar generated successfully!"
