gradle clean
clear
gradle build
clear
echo "READY TO START TESTING"
cp -r ./test/* ./build/libs
cd ./build/libs
java -jar Mani-Stable.jar runTests.mni
