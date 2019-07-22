gradle clean
clear
gradle build
clear
cp -r ./test/* ./build/libs
cd ./build/libs
java -jar Mani-Stable.jar runTests.mni

cd ../../
gradle clean
clear
gradle fatJar
clear
cp -r ./test/* ./build/libs
cd ./build/libs
java -jar Mani-FatJar.jar runTestsOffline.mni
