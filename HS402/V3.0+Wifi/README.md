# HS402 (WiFi) Oscilloscope V3.0+
HS402 oscilloscope + Wifi capabilities = HS402 V3.0!

## How to start
### Hardware Requirements
- HS402 oscilloscope
- ESP32 Develpment board (suggested that one in the Component List PDF)

### To test this version you need to
- Install new Firmware on STM32 (the latest in STM32-Firmware folder)
- Flash the ESP32 with latest Firmware (the latest in ESP32-Firmware folder)
- Connect the STM32 to the ESP32 according the Connections PDF files
- Power from the STM32 or from the ESP32 USB port
- Install the latest HScope Beta version, enable the Wifi within HScope Settings and connect the wifi to the ESP32 access point (name like: HS402-F62684)

## Flashing ESP32
Download the files from the Firmware folder. Follow the flashing procedure <a href="https://www.martinloren.com/guides/fashing-esp32/" target="\_blank">here</a>.

## ESP32 Firmware Changelog 
### Version 1.4
- Added possibility to have 2 Wifi configuration for STA mode (ie. home & office). On startup it try the first, if it doesn't work then try the second.
- Fixed STA connection issue
- Fixed information web page giving wrongly HS404 instead of HS402

### Version 1.3 (bugged, no use)
- Added possibility to have 2 Wifi configuration for STA mode (ie. home & office). On startup it try the first, if it doesn't work then try the second.

### Version 1.2
- Increased stability
- Requires STM32 with firmware v1.7

### Version 1.1 Beta
- Decreased CPU speed to 160Mhz
- Decreased FLASH SPI speed to 40Mhz
- Power consumption (with HS402): 100mA
- Increased stability
- Requires HScope version 216 or higher
- Tested with ESP32-WROOM-32D and ESP32-WROOM-32E

### Version 1.0
First stable release

## Schematic/PCB Changelog 
### V3.1
- Updated Inductor L1 to support more size of inductors (big and small). 
- Added C12 as for STM instructions. 
- Removed R10, R11, R12, R14, L10. Changed value of R13.
  
### V3.0
- New option 3 with TPS73733 to generate 3.3v reference (lowest noise option)
- New Wifi module added to PCB (optional)
- Fixed PCB pads dimension for variable capacitors
- Updated components list with pictures of the components
- Total 3 hardware options for basic HS402 (USB) and 2 Hardware Options for WIFI module
