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

### USB Version

#### V10.2 (latest):
- Re-enabled DUAL input mode
- Added PWM output

#### V10.1:
- Double samples
- Improved stability

#### V9:
- Added support for CS32F103 MCU

### BLT Version
#### V10.3 (latest):
- Added support for CS32F103 chipsets
- Lower noise
- Better Stability
- Re-enabled DUAL input mode
- Added PWM output
- Defined 2 pins to start BLT module configuration procedure (user can select one): PA8 to 3v3 (for HC-06) and PB15 to 3v3 (for HC-05)
#### V9:
- Added support for CS32F103 chipsets
- Lower noise
- Better Stability

#### V8:
- Added another pin for AC/DC coupling
- Silenced unused pins


*These files are part of HScope Project.*

# Additional Resources
- <a href="https://github.com/SUNsung/HScope_PCB" target="_blank">PCB files for compact built for HS101 and HS102 (by Vladimir)</a>
- <a href="https://www.thingiverse.com/thing:3940546" target="_blank">3D Printer files for HS101 boxing (by Magnus)</a>
