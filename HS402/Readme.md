# Material for Building HS402 DIY Oscilloscope

Oscilloscope introduction: https://www.martinloren.com/hs40x-diy-oscilloscopes/

## PCB Changelog
### V3.0
- Provide 3 hardware options for main HS402 PCB board (option 3 for lowest noise)
- Include WiFi extension board with 2 hardware options
- Other details in the folder

### V2.0
- Provide 2 hardware options for main HS402 PCB board (option 2 for lower noise)

### V1.4-1.7
- inside OLD folder

## STM32 Firmware Changelog
This versioning is not related to the PCB versioning. Any PCB version can use the latest STM32 Firmware.
### V1.8
- Fixed bug on I2C input buttons (they works just for USB connection mode, not WiFi)

### V1.7
- Stable release, supports both USB and WiFi connection

### V1.6 (Beta)
- Addeed Wifi capabilities
- UART working at 4.000.000 baud (cannot be used for debug anymore)
- USB and Wifi are both supported, communication automatically switches according where commands comes from
(check more under V3.0_Wifi_Beta folder)

### V1.5
- Fixed I2C Input Buttons enable/disable
