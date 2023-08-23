
cls

del mem*.*

javac -Xlint -d . source/Zinchenko.java -cp ./lib/Zinchenko/*;

echo cls> Zinchenko.bat
echo.>> Zinchenko.bat
echo @java -cp ./lib/Zinchenko/*; Zinchenko>> Zinchenko.bat
echo.>> Zinchenko.bat
copy Zinchenko.bat + lib\pause.txt Zinchenko.bat

 @pause>nul
