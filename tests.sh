gradle clean
clear
gradle build
clear
echo "READY TO START TESTING"
cp -r ./test/* ./build/libs
cd ./build/libs
for f in *.mni
do
	java -jar Mani-Stable.jar $f
done
