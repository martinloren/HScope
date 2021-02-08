# HS402 Oscilloscope V3.0 (BETA)
HS402 oscilloscope + Wifi capabilities = HS402 V3.0!

Note: this version is still in development, schematic and PCB is available for consultation only and they may change according the current tests.

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
Download the files from the Firmware folder. Connect the ESP32 development board to the PC with the USB cable.

### By Command line (change COM port number and filenames)
    esptool.py -p COM15 -b 460800 --before default_reset --after hard_reset --chip esp32 write_flash --flash_mode dio --flash_freq 80m --flash_size 2MB 0x8000 partition-table.bin 0x1000 bootloader.bin 0x10000 ESP32_firmware_V1.0_Beta.bin

(when the procedure start press both RESET (EN) and BOOT buttons, then release first the RESET (EN) button and then also the BOOT button)

### By using Espressif Flash Download Tool (Windows)
1. Download tool <a href="https://www.espressif.com/en/support/download/other-tools" target="_blank">here</a>, or from the ESP32 Firmware folder.
2. Load the files in the Download Tool as following. Set correctly the addresses and the COM port.
![Flash Download Tools](ESP32-Firmware/Flash_Download_Tool.png)

## Changelog for PCB V3.0
- New option with TPS73733 to generate 3.3v reference
- New Wifi module added to PCB (optional)
- Fixed PCB pads dimension for variable capacitors
- Updated components list with pictures of the components
- Total 3 hardware options for basic HS402 (USB) and 2 Hardware Options for WIFI module
