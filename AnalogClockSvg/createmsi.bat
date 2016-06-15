@echo off

javapackager -deploy -native msi ^
-v ^
-outdir dist -outfile AnalogClockSvg ^
-srcdir dist -srcfiles AnalogClockSvg.jar ^
-appclass analogclocksvg.AnalogClockSvg ^
-name "AnalogClock" ^
-BappVersion=0.3.1 ^
-title "Analog Clock with SVG" ^
-vendor Takahashi ^
-description "Analog Clock on desktop"

