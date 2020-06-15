# Material for HS10X Oscilloscope Built

Devices introduction: http://www.martinloren.com/hs10x-diy-oscilloscopes

## Hardware vs Noise Considerations

- HS101 PRO Blue Pill (USB): 0.4% noise
- HS101 PRO Black Pill (USB): 0.2% noise @ 900KSa/s (0.1% @ 100KSa/s)
- HS101 PRO Blue Pill (BLT - Battery Powered): 0.3% noise @ 900KSa/s (0.08% @ 100KSa/s)
- HS101 PRO Black Pill (BLT - Battery Powered): < 0.1% noise @ 900KSa/s (<0.03% @ 100KSa/s)

- HS101 PRO Black Pill (BLT - Battery Powered). Firmware V9: < 0.08% noise @ 900KSa/s (<0.03% @ 100KSa/s)

## Firmware (.bin)

Universal Firmware for HS101 and HS102 and all input versions. Device and Input type can be configured within <a href="http://hscope.martinloren.com/HS102-oscilloscope.html#flash_firmware" target="_blank">HScope Settings</a>.

### Latest Releases

#### V10.1 (USB Version):
- Double samples
- Improved stability

#### V9 (BLT Version):
- Added support for CS32F103 chipsets
- Lower noise
- Better Stability

#### V9 (USB Version):
- Added support for CS32F103 chipsets

#### V8 BLT:
- Added another pin for AC/DC coupling
- Silenced unused pins

### Old Releases

#### V6 BLT:
- Firmware for Bluetooth module, just for HS101 (20V, 5V, DUAL and PRO)
- Save calibration data inside STM32
- Added reset AT codes for HC-05 Bluetooth module
Note: has still stability issues

#### V8 (USB Version):
- Added support for HS102 PRO
- Added another pin for AC/DC coupling
- Silenced unused pins

#### V5 BLT (Sperimental):
- Firmware for Bluetooth module, just for HS101 (20V, 5V, DUAL and PRO)

#### V5 (Stable):
- Added new Input Types: DUAL and PRO
- Added hardware AC/DC coupling option, possible for all models
- Added 1KHz square wave generation option for reference and HS101 PRO calibration
- Reduced noise for all models
- Higher sampling rates  
- Fixed bug to let it work on Android 4.4

#### V3, V4
Stable Releases.

*These files are part of HScope Project.*

# Additional Resources
- <a href="https://github.com/SUNsung/HScope_PCB" target="_blank">PCB files for compact built for HS101 and HS102 (by Vladimir)</a>
- <a href="https://www.thingiverse.com/thing:3940546" target="_blank">3D Printer files for HS101 boxing (by Magnus)</a>
