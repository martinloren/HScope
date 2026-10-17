# HScope Automotive Module

## Import / Export data format
It export data in compressed .zip files. It can import file in the following formats: .xml, .hxml, .zip

### Before Version 150
HScope saved automotive data in plain .xml format.

### After Version 150
HScope save automotive data in the optimized .hxml format.
Inside the .zip package there is also a preview.png file with the waveform preview.
XML Metadata is also contained in the .zip package comment.

## File format specification

Current formats, in detail:

- [HScope waveform file format — V3 and V4](./hscope-file-format-v3-v4.md) — container, ZIP comment
  metadata, per-channel XML blocks, the V3 inline float32 samples and the V4 16-bit `dataN` encoding,
  plus a reference reader.

Historical samples of the XML payloads are kept in
[hxml_data_format0.txt](./hxml_data_format0.txt) and [xml_data_format.txt](./xml_data_format.txt).
