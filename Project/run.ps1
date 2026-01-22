# CoffeeShop Professional - Quick Run Script
# This script compiles and runs the modernized application

Write-Host "===========================" -ForegroundColor Cyan
Write-Host " CoffeeShop Professional v2" -ForegroundColor Cyan
Write-Host "===========================" -ForegroundColor Cyan

$outDir = "out/production/Project-Starter"
$libPath = "lib/*"
$srcPath = "src"

if (!(Test-Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir -Force | Out-Null
}

Write-Host "Compiling..." -ForegroundColor Yellow
$proc = Start-Process javac -ArgumentList "-d", $outDir, "-cp", $libPath, "-sourcepath", $srcPath, "-encoding", "UTF-8", "src/com/coffee/Main.java" -Wait -NoNewWindow -PassThru

if ($proc.ExitCode -eq 0) {
    Write-Host "Success! Launching..." -ForegroundColor Green
    $cp = "$outDir;lib/*"
    java -cp $cp com.coffee.Main
    Write-Host "Done." -ForegroundColor Yellow
}
else {
    Write-Host "Error: Compilation failed." -ForegroundColor Red
    exit 1
}
