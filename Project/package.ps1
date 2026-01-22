# CoffeeShop Professional - Release packager
# This script bundles the application into a standalone folder for Windows distribution.

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  CoffeeShop Professional v2 - EXPORT" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan

$RELEASE_DIR = "release"
$OUT_DIR = "out/production/Project-Starter"

# 1. Clean and Create Release structure
if (Test-Path $RELEASE_DIR) { Remove-Item -Path $RELEASE_DIR -Recurse -Force }
New-Item -ItemType Directory -Path "$RELEASE_DIR/bin" -Force | Out-Null
New-Item -ItemType Directory -Path "$RELEASE_DIR/lib" -Force | Out-Null
New-Item -ItemType Directory -Path "$RELEASE_DIR/data" -Force | Out-Null

# 2. Compile fresh
Write-Host "Re-compiling for production..." -ForegroundColor Yellow
$proc = Start-Process javac -ArgumentList "-d", $OUT_DIR, "-cp", "lib/*", "-sourcepath", "src", "-encoding", "UTF-8", "src/com/coffee/Main.java" -Wait -NoNewWindow -PassThru
if ($proc.ExitCode -ne 0) { Write-Host "Build failed." -ForegroundColor Red; exit 1 }

# 3. Copy dependencies
Write-Host "Packaging assets..." -ForegroundColor Yellow
Copy-Item -Path "lib/*" -Destination "$RELEASE_DIR/lib" -Recurse
# Using 'jar' to create a single app file
$jarCmd = "jar --create --file $RELEASE_DIR/bin/CoffeeApp.jar --main-class com.coffee.Main -C $OUT_DIR ."
Invoke-Expression $jarCmd

# 4. Create Windows Launcher (.bat)
$batContent = @"
@echo off
setlocal
cd /d "%~dp0"
java -cp "bin\CoffeeApp.jar;lib\*" com.coffee.Main
pause
"@
$batContent | Out-File -FilePath "$RELEASE_DIR\Launch_CoffeeShop.bat" -Encoding ascii

Write-Host ""
Write-Host "DONE! Your Windows release is ready in the '$RELEASE_DIR' folder." -ForegroundColor Green
Write-Host "Users just need to run 'Launch_CoffeeShop.bat'." -ForegroundColor Cyan
