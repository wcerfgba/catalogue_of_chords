(ns catalogue-of-stacked-thirds.generate
  (:require [clojure.set :as set]
            [clojure.string :as string]))

(def shell-pc-set->qualities
  "These pc-sets define chord qualities which may optionally include the fifth."
  {#{0 2} ["sus2"]
   #{0 3} ["min"]
   #{0 4} ["Maj"]
   #{0 5} ["sus4"]
   #{0 1 3} ["minaddb9"]
   #{0 1 4} ["Majaddb9"]
   #{0 1 5} ["sus4addb9"]
   #{0 2 3} ["minadd9"]
   #{0 2 4} ["Majadd9"]
   #{0 2 5} ["sus2add11" "sus4add9"]
   #{0 2 8} ["b6sus2"]
   #{0 2 9} ["6sus2"]
   #{0 2 10} ["7sus2"]
   #{0 2 11} ["Maj7sus2"]
   #{0 3 5} ["minadd11"]
   #{0 3 8} ["minb6"]
   #{0 3 9} ["min6"]
   #{0 3 10} ["min7"]
   #{0 3 11} ["minMaj7"]
   #{0 4 5} ["Majadd11"]
   #{0 4 9} ["Maj6"]
   #{0 4 10} ["7"]
   #{0 4 11} ["Maj7"]
   #{0 5 8} ["b6sus4"]
   #{0 5 9} ["6sus4"]
   #{0 5 10} ["7sus4"]
   #{0 5 11} ["Maj7sus4"]
   #{0 1 3 8} ["minb6addb9"]
   #{0 1 4 9} ["Maj6addb9"]
   #{0 1 5 8} ["b6sus4addb9"]
   #{0 1 5 9} ["6sus4addb9"]
   #{0 1 5 10} ["7sus4addb9"]
   #{0 2 3 8} ["minb6add9"]
   #{0 2 4 9} ["Maj6add9"]
   #{0 2 4 10} ["9"]
   #{0 2 5 8} ["b6sus2add11" "b6sus4add9"]
   #{0 2 5 9} ["6sus2add11" "6sus4add9"]
   #{0 2 5 10} ["7sus2add11" "7sus4add9"]
   #{0 2 8 10} ["7sus2addb6"]
   #{0 3 5 8} ["minb6add11"]
   #{0 3 5 9} ["min6add11"]
   #{0 3 5 10} ["min7add11"]
   #{0 3 8 10} ["min7addb6"]
   #{0 3 8 11} ["minMaj7addb6"]
   #{0 4 5 9} ["Maj6add11"]
   #{0 4 5 10} ["7add11"]
   #{0 4 9 10} ["7add6"]
   #{0 4 9 11} ["Maj7add6"]
   #{0 5 9 10} ["7sus4add6"]
   #{0 5 9 11} ["Maj7sus4add6"]})

(def exact-pc-set->qualities
  "These pc-sets define exact chord qualities, which we would interpret differently with the addition of a perfect fifth."
  {#{0 2 6} ["sus2b5" "dimsus2" "sus2add#11"]
   #{0 2 6 7} ["sus2add#11"]
   #{0 3 6} ["dim" "minadd#11"]
   #{0 3 6 7} ["minadd#11"]
   #{0 4 6} ["Majb5" "Majadd#11"]
   #{0 4 6 7} ["Majadd#11"]
   #{0 4 8} ["Aug" "Majb6"]
   #{0 4 7 8} ["Majb6"]
   #{0 1 4 8} ["Augaddb9" "Majb6addb9"]
   #{0 1 4 7 8} ["Majb6addb9"]
   #{0 2 4 6} ["Majb5add9" "Majadd9add#11"]
   #{0 2 4 6 7} ["Majadd9add#11"]
   #{0 2 4 8} ["Augadd9" "Majb6add9"]
   #{0 2 4 7 8} ["Majb6add9"]
   #{0 2 6 8} ["b6sus2b5" "b6sus2add#11"]
   #{0 2 6 7 8} ["b6sus2add#11"]
   #{0 2 6 9} ["dim7sus2" "6sus2b5" "6sus2add#11"]
   #{0 2 6 7 9} ["6sus2add#11"]
   #{0 2 6 10} ["7sus2b5" "7sus2add#11"]
   #{0 2 6 7 10} ["7sus2add#11"]
   #{0 2 6 11} ["Maj7sus2b5" "Maj7sus2add#11"]
   #{0 2 6 7 11} ["Maj7sus2add#11"]
   #{0 3 6 8} ["dimaddb6" "minb6b5" "minb6add#11"]
   #{0 3 6 7 8} ["minb6add#11"]
   #{0 3 6 9} ["dim7" "min6b5" "min6add#11"]
   #{0 3 6 7 9} ["min6add#11"]
   #{0 3 6 10} ["min7b5" "min7add#11"]
   #{0 3 6 7 10} ["min7add#11"]
   #{0 3 6 11} ["minMaj7b5" "minMaj7add#11"]
   #{0 3 6 7 11} ["minMaj7add#11"]
   #{0 4 5 8} ["Augadd11" "Majb6add11"]
   #{0 4 5 7 8} ["Majb6add11"]
   #{0 4 6 8} ["Majb6b5" "Majb6add#11"]
   #{0 4 6 7 8} ["Majb6add#11"]
   #{0 4 6 9} ["Maj6b5" "Maj6add#11"]
   #{0 4 6 7 9} ["Maj6add#11"]
   #{0 4 6 10} ["7b5" "7add#11"]
   #{0 4 6 7 10} ["7add#11"]
   #{0 4 6 11} ["Maj7b5" "Maj7add#11"]
   #{0 4 6 7 11} ["Maj7add#11"]
   #{0 4 8 9} ["Augadd6" "Maj6#5"]
   #{0 4 8 10} ["Aug7" "7addb6"]
   #{0 4 7 8 10} ["7addb6"]
   #{0 4 8 11} ["AugMaj7" "Maj7#5" "Maj7addb6"]
   #{0 4 7 8 11} ["Maj7addb6"]
   #{0 5 6 8} ["b6sus4b5" "dimsus4addb6" "b6sus4add#11"]
   #{0 5 6 7 8} ["b6sus4add#11"]
   #{0 5 6 9} ["6sus4b5" "dimsus4addb6" "6sus4add#11"]
   #{0 5 6 7 9} ["6sus4add#11"]
   #{0 5 6 10} ["7sus4b5" "7sus4add#11"]
   #{0 5 6 7 10} ["7sus4add#11"]
   #{0 5 8 9} ["6sus4#5"]
   #{0 5 8 10} ["7sus4#5" "7sus4addb6"]
   #{0 5 7 8 10} ["7sus4addb6"]
   #{0 5 8 11} ["Maj7sus4#5" "Maj7sus4addb6"]
   #{0 5 7 8 11} ["Maj7sus4addb6"]})

(assert (= #{} (set/intersection (set (keys shell-pc-set->qualities))
                                 (set (keys exact-pc-set->qualities))))
        "Overlap in pc-set->qualities maps.")

(def pc-set->qualities
  (merge (into {} (mapcat (fn [[pc-set qualities]]
                            [[pc-set qualities]
                             [(conj pc-set 7) qualities]])
                          shell-pc-set->qualities))
         exact-pc-set->qualities))

(defn- incr-vec-counter
  [min max counter]
  (let [counter (vec counter)
        top (last counter)]
    (if (nil? top)
      nil
      (if (< top max)
        (conj (vec (drop-last counter)) (inc top))
        (when-let [lower (incr-vec-counter min max (vec (drop-last counter)))]
          (conj lower min))))))

(defn- vec-counter
  [min max length]
  (take-while some? (iterate (partial incr-vec-counter min max)
                             (vec (repeat length min)))))

(def intervals
  {2 "d"
   3 "m"
   4 "M"
   5 "A"})

(defn chords
  [length]
  (vec-counter (apply min (keys intervals)) (apply max (keys intervals)) length))

(defn interval+
  [& xs]
  (mod (apply + xs) 12))

(defn interval-
  [& xs]
  (mod (apply - xs) 12))

(defn interpretation->pc-vec
  [interpretation]
  (let [{:keys [chord root]} interpretation
        [below above] (split-at root chord)]
    (vec (remove nil?
                 (concat (when (seq below)
                           (reverse (rest (reduce (fn [pc-vec interval]
                                                    (conj pc-vec (interval- (last pc-vec) interval)))
                                                  [0]
                                                  (reverse below)))))
                         [0]
                         (when (seq above)
                           (rest (reduce (fn [pc-vec interval]
                                           (conj pc-vec (interval+ (last pc-vec) interval)))
                                         [0]
                                         above))))))))

(defn chord-interpretation
  [chord root]
  (as-> {:chord (vec chord) :root root} interpretation
    (assoc interpretation :pc-vec (interpretation->pc-vec interpretation))
    (assoc interpretation :quality (pc-set->qualities (set (:pc-vec interpretation))))))

(defn chord-interpretations
  [chord]
  (map (partial chord-interpretation chord) (range (inc (count chord)))))

(def max-movement 5)

(defn movements
  [length]
  (let [distance (fn [movement] (apply + (map #(Math/abs %) movement)))]
    (sort #(- (distance %1) (distance %2))
          (vec-counter (- max-movement) max-movement length))))

(defn move-chord
  [chord movement]
  (assert (= (inc (count chord)) (count movement))
          (str "Tried to move a chord containing " (inc (count chord)) " notes using a movement containing " (count movement) " notes."))
  (reduce (fn [chord [note-i note-movement]]
            (cond-> chord
              (< 0 note-i) (update (dec note-i) #(+ % note-movement))
              (< note-i (count chord)) (update note-i #(- % note-movement))))
          chord
          (map-indexed #(vector %1 %2) movement)))

(defn root-movement
  [old-root movement new-interpretation]
  (let [movement (vec movement)
        {:keys [chord root]} new-interpretation
        ascending? (<= old-root root)
        [bottom-root top-root] (sort [old-root root])
        root-distance (- top-root bottom-root)
        inter-root-intervals (take root-distance (drop bottom-root chord))]
    (+ (get movement old-root)
       (* (if ascending? 1 -1)
          (apply + inter-root-intervals)))))

(defn normalise-shrunk-interpretation
  [interpretation]
  (let [{:keys [chord root]} interpretation
        zeroes-before-root (count (filter zero? (take root chord)))
        normalised-chord (remove zero? chord)]
    (merge interpretation
           (chord-interpretation normalised-chord (- root zeroes-before-root)))))

(defn move-interpretation
  [interpretation movement]
  (let [{:keys [chord root]} interpretation
        new-chord (move-chord chord movement)
        valid? (and (every? (set/union #{0} (set (keys intervals))) new-chord)
                    (not-every? zero? new-chord))]
    (when valid?
      (->> new-chord
           (chord-interpretations)
           (map #(assoc % :root-movement (root-movement root movement %)))
           (map normalise-shrunk-interpretation)
           (distinct)
           (remove (comp nil? :quality))))))

(defn interpretations
  [chord-length]
  (let [movements (movements (inc chord-length))]
    (->> (chords chord-length)
         (mapcat chord-interpretations)
         (map (fn [interpretation]
                (assoc interpretation
                       :movements (->> movements
                                       (map (fn [movement]
                                              [movement (move-interpretation
                                                         interpretation
                                                         movement)]))
                                       (remove (comp nil? second)))))))))
(def base-root-movement-str
  {1 "bII"
   2 "II"
   3 "bIII"
   4 "III"
   5 "IV"
   6 "bV"
   7 "V"
   8 "bVI"
   9 "VI"
   10 "bVII"
   11 "VII"})

(defn root-movement-str
  [root-movement]
  (if (= 0 (mod root-movement 12))
    ""
    (str (if (pos? root-movement) "+" "-")
         (base-root-movement-str (mod (Math/abs root-movement) 12)))))

(defn interpretation-str
  [interpretation]
  (let [{:keys [root chord]} interpretation
        chord-str (map intervals chord)
        before-root (take root chord-str)
        padding (- 11 (* 2 (count before-root)))]
    (string/join " " (concat (repeat padding "")
                             before-root
                             ["r"]
                             (drop root chord-str)))))

(defn quality-str
  [interpretation]
  (let [{:keys [quality]} interpretation]
    (if (seq quality)
      (string/join " / " quality)
      "<unknown>")))

(defn format-interpretation
  [interpretation]
  (let [{:keys [movements]} interpretation]
    (str
     (format "%-34s %-20s %-21s\n"
             (str (quality-str interpretation) ".")
             ""
             (str (interpretation-str interpretation) "."))
     "\n"
     (apply str
            (mapcat (fn [[movement interpretations]]
                      (let [movement-str (string/join " " (map #(if (pos? %)
                                                                  (format "+%1d" %)
                                                                  (format "%2d" %))
                                                               movement))]
                        (map-indexed (fn [i interpretation]
                                       (let [{:keys [root-movement]} interpretation]
                                         (format "%-11s  %-5s  %-34s  %-21s\n"
                                                 (if (zero? i) movement-str "")
                                                 (root-movement-str root-movement)
                                                 (quality-str interpretation)
                                                 (interpretation-str interpretation))))
                                     interpretations)))
                    movements))
     "\n")))

(defn -main
  [& _]
  (run! #(println (format-interpretation %)) (interpretations 2))
  (run! #(println (format-interpretation %)) (interpretations 3)))
