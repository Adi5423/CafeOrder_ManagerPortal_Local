# CoffeeShop Order System - Quick Run Script
# This script compiles and runs the application

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  CoffeeShop Order System" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Compile
Write-Host "Compiling Java files..." -ForegroundColor Yellow
javac -d out -cp "lib/*;src" -encoding UTF-8 src/main/model/*.java src/main/persistence/*.java src/main/ui/*.java

# Check if compilation succeeded
if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilation successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Starting application..." -ForegroundColor Yellow
    Write-Host ""
    
    # Run the application
    java -cp "out/production/Project-Starter;lib/*" ui.Main
    
    # When application closes, show event log
    Write-Host ""
    Write-Host "Application closed." -ForegroundColor Yellow
} else {
    Write-Host "✗ Compilation failed! Please check the errors above." -ForegroundColor Red
    exit 1
}
