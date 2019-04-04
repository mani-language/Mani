gradle clean
clear
gradle build
echo "READY TO START TESTING"
cp -r ./test/* ./build/libs
cd ./build/libs
for f in *.mn
do
	java -jar Mani-Stable.jar main.mn $f
done
