@echo off
chcp 65001 > NUL

:start
java --enable-preview -jar "Quark Server-jar-with-dependencies.jar"

if %errorlevel% == 2 (
	goto :start
)

pause