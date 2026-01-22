@echo off
setlocal
cd /d "%~dp0"
java -cp "bin\CoffeeApp.jar;lib\*" com.coffee.Main
pause
