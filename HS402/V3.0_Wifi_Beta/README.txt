Command for flashing ESP32 (change COM port number and filenames):

esptool.py -p COM15 -b 460800 --before default_reset --after hard_reset --chip esp32 write_flash --flash_mode dio --flash_freq 80m --flash_size 2MB 0x8000 partition-table.bin 0x1000 bootloader.bin 0x10000 ESP32_firmware_V1.0_Beta.bin