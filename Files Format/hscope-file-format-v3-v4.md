# HScope waveform file format — V3 and V4

How HScope stores a recorded waveform. Covers the `.zip` container, the XML metadata (kept in the ZIP
comment), the per-channel sample data, and the differences between **format version 3** (`.hxml` inside
the archive) and **format version 4** (metadata only in the ZIP comment, samples in separate entries).

Everything here was taken from the writer/reader in the app (`File_HXML_V3`, `File_HXML_V4`,
`IOFileData`, `HScopeZIPFormat`) and checked against real recordings.

---

## 1. Version history

| Format | Introduced | Stored as | Sample data |
| --- | --- | --- | --- |
| plain XML | before v150 (2018) | `.xml` | decimal text, comma separated |
| HXML v1 / v2 | v150 (2018) | `.zip` with `data.hxml` | float32 binary **inside** the XML |
| **v3** | 2021 | `.zip` with `data.hxml` | float32 binary **inside** the XML |
| **v4** | 2024 (current) | `.zip`, metadata in the **ZIP comment** | one `dataN` entry per channel, 16-bit |

The extension of the finished capture is `.zip`; `.hxml` is the XML document that v1–v3 keep inside the
archive (and the extension HScope uses when a single XML is exchanged). An archive is recognised by its
content, not by its name.

---

## 2. Container

A capture is a normal ZIP file:

```
waveform.zip
├── preview.png          waveform screenshot (optional but always written by the app)
├── data0                channel 0 samples   (v4 only, one entry per channel)
├── data1                channel 1 samples
├── data2, data3 …       further channels (as many as were recorded)
├── data.hxml            the XML document    (v1–v3 only)
├── 1792238900.jpg       attached photo, named with the unix timestamp in seconds (0..n of them)
└── <ZIP comment>        the same XML metadata, without the sample data
```

* **The ZIP comment carries the metadata in every version.** It is what the file list shows (date,
  device, rate, channel count, notes, score…) without touching the archive contents.
* The comment is XML with the same element names as the document, minus the channel samples. It must
  stay under the ZIP limit of 65 535 bytes, which is why the bulky per-channel blocks live in the
  document/entries instead.
* No compression is required; the app writes entries with the default DEFLATE.
* Unknown entries must be ignored (older builds left `data.hxml` behind in the temp folder, and every
  file in that folder is packaged).

### Version detection

1. Read the ZIP comment (the end-of-central-directory record, last ≤ 65 557 bytes of the file).
2. Parse `<version>` from it:
   * `4` → **V4**
   * `3` (or absent) → **V3**
   * `1` / `2` → legacy readers
3. Do not infer the version from the entry list alone — but as a sanity check, a V4 archive has
   `data0…dataN` and no `data.hxml`, while a V3 archive has `data.hxml`.

The app writes `IOFileData.CURRENT_FILE_VERSION = 4`.

---

## 3. XML metadata

Common document skeleton (UTF-8, the `DOCTYPE` is not shipped and can be ignored):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE data SYSTEM "roles.dtd">
<data>
 <format>HScope</format>          <!-- v4 only -->
 <version>4</version>
 <date>July 14, 2026 at 11:36</date>
 <timestamp>1792238900</timestamp>
 <author>Nickname</author>
 <type>1</type>
 <device>HS514</device>
 <rate>100000.0</rate>
 <adc_bits>10</adc_bits>
 <samples>4453626</samples>
 <page_samples>4453626</page_samples>   <!-- only when the capture is paged -->
 ... channel blocks ...
 <CH-MATH> ... </CH-MATH>               <!-- only when the math module is on -->
 ... overlay blocks (notes, flags, cursors, …) ...
 <machine> ... </machine>               <!-- automotive capture metadata -->
</data>
```

### Top-level elements

| Element | Type | Meaning |
| --- | --- | --- |
| `format` | text | `HScope`; written by V4 only |
| `version` | int | file format version (see §2) |
| `date` | text | human readable recording date |
| `timestamp` | long | unix seconds |
| `author` | text | author / nickname, may be empty |
| `type` | int | `0` scope, `1` automotive, `2` logger, `3` automotive pages |
| `device` | text | oscillator model as the driver reports it (`HS514`, `OSC482`, …) |
| `rate` | double | samples per second (Sa/s), **not** kHz |
| `adc_bits` | int | ADC resolution of that capture |
| `samples` | int | samples per channel stored in the capture || `page_samples` | int | samples in one page, only for paged recordings (`type` 3) |
| `photos_count` | int | number of `*.jpg` entries (comment only) |
| `notes` | text | free text note (comment only) |
| `rep_title` | text | report title (comment only) |
| `test_result` | text | result label used by the report export (comment only) |
| `view_start_idx` / `view_end_idx` | int | last visible window, V4 only (comment only) |

### Automotive block

Written only for automotive captures, inside the comment:

```xml
 <machine>
  <category>…</category> <maker>…</maker> <model>…</model> <year>…</year>
  <total_power>…</total_power> <engine_type>…</engine_type>
  <engine>…</engine> <cc>…</cc> <cylinders>…</cylinders> <fire_order>1-8-4-3-6-5-7-2</fire_order>
 </machine>
```

### Overlay blocks

Measurement tools drawn over the waveform (notes, flags, cursors, order/cylinder overlays…) serialize
as additional sibling blocks after the channels. They are presentation only: a reader that does not
know them can skip every element it does not recognise.

---

## 4. Channel blocks

Analog channels are `<CH0>`, `<CH1>`, `<CH2>`, `<CH3>`; the math channel is `<CH-MATH>` (internal id 9);
the digital/logic channel is `<CH20>` (internal id 20). `ch_num` is the number in the tag.

### Analog channel

```xml
 <CH0>
  <name>CH1</name>
  <unit>V</unit>
  <input_range>5.0</input_range>       <!-- volts/div of the capture -->
  <coupling>DC</coupling>              <!-- DC | AC -->
  <filter>…</filter>                   <!-- optional DSP description -->
  <max>5.1759</max>                    <!-- value range of the stored samples -->
  <min>-5.4387</min>
  <y_offset>0</y_offset>               <!-- display only -->
  <y_zoom>1.0</y_zoom>                 <!-- display only -->
  <color>#FFFF00</color>               <!-- v4; v3 writes the raw ARGB integer -->
  <probe>
   <name>x1</name>
   <description>Standard x1 Probe</description>
   <icon>0</icon>
   <offset>0.0</offset>
   <multiplier>1.0</multiplier>
   <unit>V</unit>
  </probe>
  <data_file>data0</data_file>         <!-- v4 only -->
  <data> …raw samples… </data>         <!-- v3 only -->
 </CH0>
```

* `max`/`min` describe the **samples as stored**. In V4 they are exactly the header of the `dataN`
  entry; in V3 they are documentation (the samples are float32 anyway).
* `y_offset` / `y_zoom` and `color` are view state — ignore them for analysis.

#### The probe block

Every **analog** channel carries a `<probe>` block (V3 and V4 alike, always written — default
`x1` / `Standard x1 Probe`). It records the probe preset the channel was recorded with:

| Element | Type | Meaning |
| --- | --- | --- |
| `name` | text | probe preset name, e.g. `x1`, `x10`, `PS16PSI` |
| `description` | text | free description, often empty |
| `icon` | int | icon index (reserved, always `0` today) |
| `offset` | double | additive offset in the probe's unit (zeroing/trim) |
| `multiplier` | double | scale factor from the channel's volts to the probe's unit |
| `unit` | text | unit name produced by the probe; **often empty** — see the note below |
| `auto_calibrate_zero` | `1`/`0` | only written when enabled |
| `lpf` | int | low-pass filter cutoff in Hz, only written when > 0 |

The samples in the channel are already expressed in the probe's unit, i.e. they were captured as

```
value = hardware_volts * multiplier + offset
```

so a reader does **not** have to apply the probe again — use it to label the axis and to convert back
to volts (`volts = (value - offset) / multiplier`).

Two practical notes:

* The probe's `<unit>` comes from the probe's internal unit id
  (`DataUnits.getXUnit(1, id)`); it is frequently an empty string even when the channel has a real unit.
  **The authoritative unit for the samples is the channel's own `<unit>`** (`V`, `PSIG`, `A`, `BAR`,
  `%`, `G`, `W`, `inHg`, …).
* Real example, from a pressure channel of an automotive capture:

```xml
 <CH3>
  <name>PRES</name>
  <unit>PSIG</unit>
  <max>231.361</max>
  <min>-19.1635</min>
  <probe>
   <name>PS16PSI</name>
   <description></description>
   <icon>0</icon>
   <offset>0.294</offset>
   <multiplier>49.3</multiplier>
   <unit></unit>
  </probe>
  <data_file>data3</data_file>
 </CH3>
```

A 16 PSI sensor on a 5 V input: 5 V × 49.3 ≈ 246 PSIG full scale, which is the range the samples span
(−19…231 PSIG).

### Digital channel

```xml
 <CH20>
  <name>L0</name>
  <color>#00FF00</color>
  <data_file>data4</data_file>     <!-- v4 -->
 </CH20>
```

One byte per sample, bit *n* = logic line *n* (V3 writes the bytes inline; V4 runs the logic bytes
through the same 16-bit encoder as analog channels — see the caveat in §6).

### Math channel

```xml
 <CH-MATH>
  <formula>CH1 - CH2</formula>
  <y_offset>0</y_offset>
  <y_zoom>1.0</y_zoom>
  <filter> … </filter>              <!-- optional DSP settings -->
 </CH-MATH>
```

Configuration only: no samples are stored for the math channel.

---

## 5. Sample encoding — V3

`data.hxml` is one XML document whose `<data>` elements contain **raw binary floats**, not text:

```
<CH0> … <data>
    <!-- samples × 4 bytes, IEEE-754 float32, little endian, in volts -->
  </data>
 </CH0>
```

Rules:

* `samples` floats per channel, 4 bytes each, little endian, no padding.
* Values are already scaled to the channel `<unit>` (volts for a scope channel).
* The digital channel stores `samples` **bytes** instead of floats.
* The binary block starts immediately after `<data>` followed by a **newline**, and it is followed by
  `\n  </data>`: take exactly `samples * 4` bytes from after that newline instead of slicing up to
  `</data>`, or the last four floats will be shifted by the whitespace. Older readers of this document
  got that wrong; the app itself writes with a one-byte `\n` after the opening tag.
* Legacy note: format **v1** stored the floats **big endian**; v2 and v3 use little endian (the reader
  switches on the version for exactly this reason).

---

## 6. Sample encoding — V4

Each channel has its own entry `dataN`, in the order the channels appear in the XML
(`<data_file>dataN</data_file>` gives the mapping, never assume the order):

```
dataN
├── 0..3    float32 min   (little endian)   – lower end of the encoded range
├── 4..7    float32 max   (little endian)   – upper end
└── 8..      uint16 codes, little endian, one per sample
```

Decoding (this is what the app does on import):

```
value[i] = min + code[i] * (max - min) / 65535.0
```

Encoding (for reference):

```
code = round((value - min) * 65535.0 / (max - min)), clamped to 0..65535
      (when max == min the range is taken as 1.0 to avoid a division by zero)
```

Properties worth knowing:

* Resolution is `(max - min) / 65535`. For a 10-bit ADC capture this is finer than the ADC, so the
  round trip is lossless in practice; for a 16-bit source it is not.
* `min`/`max` are per channel and per file — they must be read from the header every time.
* The number of samples is `(entry_size - 8) / 2`; it can differ from the `<samples>` element in the
  XML when the capture was clamped on load (see §7).
* **Digital channels** are written through the same encoder by the current writer. Their "values" are
  the raw line bits as numbers, so a logic line recovered from `dataN` is
  `round((code * (max - min) / 65535) + min)` — the source has a `TODO` to store them as bytes and
  this may change; treat the digital entry defensively and prefer the bit pattern of the recovered
  integers.

---

## 7. Reading an archive — reference implementation

```python
import struct, zipfile, xml.etree.ElementTree as ET

def read_hscope_zip(path):
    z = zipfile.ZipFile(path)
    comment = z.comment.decode("utf-8", "replace")
    if not comment:
        raise ValueError("no metadata in the ZIP comment")
    root = ET.fromstring(comment)

    version = int(root.findtext("version", "3"))
    rate    = float(root.findtext("rate", "0"))
    samples = int(root.findtext("samples", "0"))
    channels = {}

    for ch in root:
        if not ch.tag.startswith("CH") or ch.tag == "CH-MATH":
            continue
        ch_num = ch.tag[2:]
        data_file = ch.findtext("data_file")
        if data_file:                                  # v4
            raw = z.read(data_file)
            lo, hi = struct.unpack_from("<ff", raw, 0)
            n = (len(raw) - 8) // 2
            codes = struct.unpack_from("<%dH" % n, raw, 8)
            scale = (hi - lo) / 65535.0 if hi != lo else 1.0 / 65535.0
            values = [lo + c * scale for c in codes]
        else:                                          # v2/v3: samples inside data.hxml
            doc = z.read("data.hxml")
            start = doc.index(b"<" + ch.tag.encode() + b">")
            d0 = doc.index(b"<data>", start) + len(b"<data>")
            if doc[d0:d0 + 1] == b"\n":                 # the writer puts one newline after <data>
                d0 += 1
            d1 = doc.index(b"</data>", d0)
            body = doc[d0:d1].rstrip()                  # and whitespace before </data>
            if ch.tag == "CH20":                        # digital: one byte per sample, bit n = line n
                values = list(body)
            else:
                n = len(body) // 4
                values = list(struct.unpack("<%df" % n, body))
        channels[ch_num] = {
            "name": ch.findtext("name"), "unit": ch.findtext("unit"),
            "values": values,
        }
    return {"version": version, "rate": rate, "samples": samples, "channels": channels}
```

Notes on the snippet: it assumes the metadata is valid UTF-8 XML (the app writes it unescaped, so a
note containing `<` produces a document a strict parser rejects — sanitize first if that matters), and
it ignores the overlay and `machine` blocks.

---

## 8. Writing a compatible archive

Minimum for a V4 capture the app will open:

1. `preview.png` — any PNG sized like the waveform view (the list shows it as the thumbnail).
2. `data0 … dataN` — the channel samples, encoded as in §6.
3. A ZIP comment with `<version>4</version>`, `<rate>`, `<adc_bits>`, `<samples>` and one `<CHn>`
   block per entry, each with `<name>`, `<unit>`, `<max>`, `<min>`, `<data_file>dataN</data_file>`.

For V3: the same metadata inside `data.hxml` as the document, samples inline (§5), and the same
metadata (without samples) in the comment.

---

## 9. Gotchas

* **`<samples>` is not always the stored length.** On import the app clamps a capture to the native
  capture buffer and keeps the clamped value; always prefer the entry length (`(size-8)/2` for V4).
* **Paged recordings** (`type` 3) carry `page_samples`; `samples` is the whole capture.
* **`rate` is Sa/s**, not kHz, and is a decimal number; the WAV export uses it verbatim as the WAV
  sample rate.
* **Image/photo entries** (`preview.png`, `<unix>.jpg`) are media, never samples.
* **Comments are limited to 64 KiB** by the ZIP format; the app keeps the heavy per-channel XML out of
  it for that reason. A reader must not require every top-level element to be present.
* **Encoding**: text is UTF-8; the binary blocks are little endian on every platform.
* **`color`** is `#RRGGBB` in V4 and the raw integer in V3.
* **Escaping**: the writer does not XML-escape notes/authors, so treat the parsed text as untrusted and
  fall back to a lenient parse (or a regex) when `ElementTree` fails.
