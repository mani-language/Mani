gradle clean
clear
gradle build
cp -r ./test/* ./build/libs
cd ./build/libs
clear
java -jar Mani-Stable.jar ./Hospital/main.mn
