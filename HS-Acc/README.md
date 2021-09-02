# ESP32-ACC WiFi Accellerometer
 An easy-to-build device to record and analyse accelleration in real-time with HScope.
 
 ![HS-ACC](HS-ACC%20Intro.jpg)
 
 - 1 or 2 sensors model
 - Sampling rate up to 3200Sa/s, 10bit resolution
 - WiFi STA or AP supported
 - Full specifications and build instructions [here](https://www.martinloren.com/hs-acc-wifi-accelerometer/). 
 
 ## Flashing the ESP32 Firmware
 Check out the guide [here](https://www.martinloren.com/guides/fashing-esp32/).
 
### V1.0-8 (Beta)
 - Added check on WiFi password lenght
   
### V1.0-7 (Beta)
 - Fixed base configuration
 - Fixed configuration reset button

### V1.0-6 (Beta)
 - Can use Sigle sensor both on Port 1 and on Port 2
 - Can configure Sensor Axis for each sensor in Dual Model

### V1.0-5 (Beta)
 - Implemented Battery Monitor Circuit
 
### V1.0-4 (Beta)
 - Can configure channels name
 
 ### V1.0-2-DEBUG (Beta)
 - For debugging on USB serial port (115200 baud)
 - Sensors calibration NOT supported
 
 ### V1.0-1 (Beta)
 - Fixed typo in Web Page, fixed styles, added info
 - First Release
 - Sensors calibration NOT supported

 ## How to use
 When the ESP is powered it creates an Wifi access point with SSID like ESP-ACC-F62684. Connect to it with the phone or PC (password: 123456789) and open the browser (everything except Internet Explorer) at address 192.168.4.1, to access the device settings. Open HScope to use it.
 
 
