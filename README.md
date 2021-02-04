# Catalogue of Chords

`catalogue.txt` contains a catalogue of every named three to six note chord which can be constructed by stacking intervals from semitones to fourths, and each following chord that the voicing can move to by shifting each note up or down by up to three semitones.

Each chord contains an entry such as the following:

```
                                                      m    M    m
.min7.                                             .r   b3    5   b7.

  .   .   .   .                 I  min7             r   b3    5   b7
                            +bIII  Maj6             6    r    3    5
  x   .   .   .                 I  Maj                   r    3    5
  .   .   .   x                 I  min              r   b3    5
                            +bIII  Maj6             6    r    3
 -1   .   .   .              -bII  augMaj7          r    3   #5    7
  .  -1   .   .               +II  7sus4b13        b7    r    4  b13
                            +bVII  Maj6/9           9    3    6    r
  .   .  -1   .                 I  min7b5           r   b3   b5   b7
                            +bIII  min6             6    r   b3    5
  .   .   .  -1                 I  min6             r   b3    5    6
                              +VI  min7b5          b3   b5   b7    r
[...]
```

which follows the following template:

```
                                                   <intervals>
.<quality>.                                        .<voicing>.

<note movement>             <root  <new chord       <new chord voicing>
                             mvmt>  quality>
```

The quality and voicing for each chord are wrapped in `.` to facilitate searching.

Some voicings of some interval vectors do not correspond to a well-known chord quality and are excluded from the both the primary list and the lists of movements; the catalogue includes only chords with a named quality.

The primary only includes root position voicings, because all other chords accessible via a movement from an initial chord are determined by the relative movement of notes between the chords; inclusion of inversions and other voicings would cause unnecessary duplication.

The following qualities are included in the catalogue:

```
sus2        dim         min         Maj         aug         sus4        dim7
min7b5      dimMaj7     aug7        augMaj7     aug7b9      dim9        min9b5
dimMaj9     aug9        augMaj9     aug7#9      dim11       min11b5     dimMaj11
dim7b13     min7b5b13   dimMaj7b13  dimMaj13    aug7#11     7b13        aug13
aug7b9#11   7b9b13      aug13b9     dim11       min11b5     dimMaj11    dim9b13
min9b5b13   dimMaj9b13  dimMaj13    aug9#11     9b13        aug13       aug13#9
aug7#9#11   7#9b13      dim11b13    min11b5b13  dimMaj11b13 7#11b13     aug13#11
7b9#11b13   aug13b9#11  dim11b13    min11b5b13  dimMaj11b13 dimMaj13    9#11b13
aug13#11    7#9#11b13   aug13#9#11  min6        min7        minMaj7     Maj6
7           Maj7        7sus4       7b9         7sus4b9     min6/9      min9
minMaj9     Maj6/9      9           Maj9        9sus4       7#9         min11
minMaj11    min13       minMaj13    7#11        Maj7#11     13          Maj13
7sus4b13    13sus4      7b9#11      13b9        7sus4b9b13  13sus4b9    min11
minMaj11    min13       minMaj13    9#11        Maj9#11     13          Maj13
9sus4b13    13sus4      7#9#11      13#9        min13       minMaj13    13#11
Maj13#11    13b9#11     min13       minMaj13    13#11       Maj13#11    13#9#11
```

The voicing use the symbol `r` to indicate the root and uses scale factors determined from an Ionian chord `r 3 5 7 9 11 13`, with lowered intervals represented with a `b` symbol. Exceptions are made for various chord qualities on a case-by-case basis, e.g. `aug` uses `#5` in place of `b13`. Voicings are aligned on the root to facilitate comparison of scale factors across different interpretations of the same interval vector.

The interval vector uses the following symbols:

* `h` - double diminished third, or a half step / minor second
* `d` - diminished third, or a major second
* `m` - minor third
* `M` - major third
* `A` - augmented third, or a perfect fourth

By considering the seconds and fourth as extensions of the third, we are able to represent all chord inversions and synthesise quartal and secundal harmony into a single paradigm.

The list of movements is sorted first by the number of movements, and then by the total distance of the movements, so movements of a single note up for down by one semitone appear first, followed by movements of a single note up or down by two semitones, and then movements involving two notes, then three, and so on.

The note movement vector indicates how many semitones each note in the voicing must be moved up (`+`) or down (`-`) to achieve a movement to the new indicated chord. Movements up to three semitones are included which provides coverage of all chromatic and diatonic note movements, including scales which include minor third intervals (e.g. pentatonic scales).

In some cases a note is removed from the voicing, and this is indicated with an `x` in the movement vector. Lack of movement is represented with `.` instead of `0` to make the list more scannable, with moved notes more prominent.

The root movement indicates how the root of the new chord relates to the root of the original chord. It includes a sign and a Roman numeral representation of the degree of the root of the new chord, if we consider the original chord as a `I`. e.g. `+IV` represents movement of a root up by a fourth, as from G to C. This is enharmonically equivalent to a `-V`, and we capture the additional information on root/bass movement via the sign.

## Examples

The catalogue can be used to show how common chord progressions can be achieved using smooth voice leading. Let's consider a `ii7 V7 I7`. First, find the `.min7.` chord corresponding to root position:

```
                                                      m    M    m
.min7.                                             .r   b3    5   b7.
```

Then look down the list of movements for a `+IV  7`, indicating a move up a fourth from `ii` to `V` where the resulting chord has a dominant quality:

```
  .   .  -2  -1               +IV  7                5   b7    r    3
```

We can move to a dominant 7 in second inversion by moving the top two notes down. Since the catalogue includes only entries for root position chords, we must find the dominant 7 by searching for `.r    3    5   b7.` or `.7.`:

```
                                                      M    m    m
.7.                                                .r    3    5   b7.
```

and then we can find a movement to take us to the `I` as `+IV  Maj7`:

```
  .   .  -2  -1               +IV  Maj7             5    7    r    3
```

Since the V7 we land on from the ii7 is in second inversion, we must take an additional step to interpret the root movement. We move from V7 to I7 by lowering the fifth and seventh degrees, which moves the root to the now-lowered fifth. If we perform this movement on the second inversion, we move the lowest two notes and interpret the bottom note as the root, so we arrive at a I7 in root position, which is a `-V` root movement.

## Generation

The catalogue is generated by a Clojure script which can be run with the deps.edn tooling to produce the text file:

```
clj -m catalogue-of-chords.generate > catalogue.txt
```

The script accepts a single argument which indicates the number of notes up to which chords should be generated, which defaults to 6. The following can be used to generate a catalogue which includes chords of up to five notes only:

```
clj -m catalogue-of-chords.generate 5 > catalogue_5.txt
```

## License

Public domain, do what thou wilt.
