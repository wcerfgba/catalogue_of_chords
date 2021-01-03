# Catalogue of Chords

`catalogue.txt` contains a catalogue of every three and four note chord which can be constructed by stacking intervals from semitones to fourths, along with the quality and voicing of each chord when each note is taken as the root, and each following chord that the voicing can move to by shifting each note up or down by up to five semitones. As such, this catalogue contains all 575 three and four note chords, with an average of ~1400 movements per chord.

Each chord contains an entry such as the following:

```
                                                                    M    m    m
.7.                                                              .r    3    5   b7.

 0  0  0  0          I  7                                         r    3    5   b7
 0 -1  0  0          I  min7                                      r   b3    5   b7
                 +bIII  Maj6                                 6    r    3    5
 0  0 -1  0          I  7#11                                      r    3  #11   b7
                   +bV  7#11                          #11   b7    r    3
 0  0  0 -1          I  Maj6                                      r    3    5    6
                   +VI  min7                      b3    5   b7    r
[...]
```

which follows the following template:

```
                                                                 <intervals>
.<quality>.                                                      .<voicing>.

<note movement> <root   <new chord quality>                       <new chord voicing>
                 mvmt>
```

The quality and voicing for each chord are wrapped in `.` to facilitate searching.

Some voicings of some interval vectors do not correspond to a well-known chord quality: these unusual voicings appear in the listing with quality `..` and are excluded from the list of movements generated from other chords; the list of movements for each chord includes only chords with a named quality.

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

The list of movements is sorted by edit distance, so movements of a single note up or down by one semitone are listed first, followed by movements of one note by two semitones or two notes by one semitone each, and so on.

The note movement vector indicates how many semitones each note in the voicing must be moved up (`+`) or down (`-`) to achieve a movement to the new indicated chord.

The root movement indicates how the root of the new chord relates to the root of the original chord. It includes a sign and a Roman numeral representation of the degree of the root of the new chord, if we consider the original chord as a `I`. e.g. `+IV` represents movement of a root up by a fourth, as from G to C. This is enharmonically equivalent to a `-V`, and we capture the additional information on root/bass movement via the sign.

## Examples

The catalogue can be used to show how common chord progressions can be achieved using appropriate inversions. Let's consider a `ii7 V7 I7` starting in root position. First, find the `.min7.` chord corresponding to root position:

```
                                                                    m    M    m
.min7.                                                           .r   b3    5   b7.
```

Then look down the list of movements for a `+IV  7`, indicating a move up a fourth from `ii` to `V` where the resulting chord has a dominant quality:

```
 0  0 -2 -1        +IV  7                               5   b7    r    3
```

We can move to a dominant 7 in second inversion by moving the top two notes down. Now we can find the entry for this voicing by searching for `.5   b7    r    3.`:

```
                                                          m    d    M
.7.                                                    .5   b7    r    3.
```

and then we can find a movements to take us to the `I` as `-V   Maj7`:

```
-2 -1  0  0         -V  Maj7                                      r    3    5    7
```

which we see is back to root position.

## Generation

The catalogue is generated by a Clojure script which can be run with the deps.edn tooling to produce the text file:

```
clj -m catalogue-of-chords.generate > catalogue.txt
```

The script accepts a single argument which indicates the number of notes up to which chords should be generated, which defaults to 4. The following can be used to generate a catalogue which includes 5 note chords, but this generates a file ~2.1G in size and containing over 28 million lines!

```
clj -m catalogue-of-chords.generate 5 > catalogue_five.txt
```

## License

Public domain, do what thou wilt.
