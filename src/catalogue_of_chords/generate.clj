(ns catalogue-of-chords.generate
  (:require [clojure.set :as set]
            [clojure.string :as string]))

(def pc-sets
  "Common pc-sets and their interpretations."
  (let [exact-pc-set {#{0 2 7} ["sus2" {2 "2"}]
                      #{0 3 6} ["dim"]
                      #{0 3 7} ["min"]
                      #{0 4 7} ["Maj"]
                      #{0 4 8} ["aug" {8 "#5"}]
                      #{0 5 7} ["sus4" {5 "4"}]
                      #{0 3 6 9} ["dim7" {9 "bb7"}]
                      #{0 3 6 10} ["min7b5"]
                      #{0 3 6 11} ["dimMaj7"]
                      #{0 4 8 10} ["aug7" {8 "#5"}]
                      #{0 4 8 11} ["augMaj7" {8 "#5"}]
                      #{0 1 4 8 10} ["aug7b9"]
                      #{0 2 3 6 9} ["dim9" {9 "bb7"}]
                      #{0 2 3 6 10} ["min9b5"]
                      #{0 2 3 6 11} ["dimMaj9"]
                      #{0 2 4 8 10} ["aug9" {8 "#5"}]
                      #{0 2 4 8 11} ["augMaj9" {8 "#5"}]
                      #{0 3 4 8 10} ["aug7#9" {3 "#9"}]
                      #{0 3 5 6 9} ["dim11" {9 "bb7"}]
                      #{0 3 5 6 10} ["min11b5"]
                      #{0 3 5 6 11} ["dimMaj11"]
                      #{0 3 6 8 9} ["dim7b13" {9 "bb7"}]
                      #{0 3 6 8 10} ["min7b5b13"]
                      #{0 3 6 8 11} ["dimMaj7b13"]
                      #{0 3 6 9 11} ["dimMaj13"]
                      #{0 4 6 8 10} ["aug7#11" {6 "#11"}]
                      #{0 4 7 8 10} ["7b13"]
                      #{0 4 8 9 10} ["aug13" {8 "#5"}]
                      #{0 1 4 6 8 10} ["aug7b9#11" {6 "#11"}]
                      #{0 1 4 7 8 10} ["7b9b13"]
                      #{0 1 4 8 9 10} ["aug13b9" {8 "#5"}]
                      #{0 2 3 5 6 9} ["dim11" {9 "bb7"}]
                      #{0 2 3 5 6 10} ["min11b5"]
                      #{0 2 3 5 6 11} ["dimMaj11"]
                      #{0 2 3 6 8 9} ["dim9b13" {9 "bb7"}]
                      #{0 2 3 6 8 10} ["min9b5b13"]
                      #{0 2 3 6 8 11} ["dimMaj9b13"]
                      #{0 2 3 6 9 11} ["dimMaj13"]
                      #{0 2 4 6 8 10} ["aug9#11" {6 "#11"}]
                      #{0 2 4 7 8 10} ["9b13"]
                      #{0 2 4 8 9 10} ["aug13" {8 "#5"}]
                      #{0 3 4 8 9 10} ["aug13#9" {3 "#9" 8 "#5"}]
                      #{0 3 4 6 8 10} ["aug7#9#11" {3 "#9" 6 "#11"}]
                      #{0 3 4 7 8 10} ["7#9b13" {3 "#9"}]
                      #{0 3 5 6 8 9} ["dim11b13" {9 "bb7"}]
                      #{0 3 5 6 8 10} ["min11b5b13"]
                      #{0 3 5 6 8 11} ["dimMaj11b13"]
                      #{0 4 6 7 8 10} ["7#11b13" {6 "#11"}]
                      #{0 4 6 8 9 10} ["aug13#11" {6 "#11" 8 "#5"}]
                      #{0 1 4 6 7 8 10} ["7b9#11b13" {6 "#11"}]
                      #{0 1 4 6 8 9 10} ["aug13b9#11" {6 "#11" 8 "#5"}]
                      #{0 2 3 5 6 8 9} ["dim11b13" {9 "bb7"}]
                      #{0 2 3 5 6 8 10} ["min11b5b13"]
                      #{0 2 3 5 6 8 11} ["dimMaj11b13"]
                      #{0 2 3 5 6 9 11} ["dimMaj13"]
                      #{0 2 4 6 7 8 10} ["9#11b13" {6 "#11"}]
                      #{0 2 4 6 8 9 10} ["aug13#11" {6 "#11" 8 "#5"}]
                      #{0 3 4 6 7 8 10} ["7#9#11b13" {3 "#9" 6 "#11"}]
                      #{0 3 4 6 8 9 10} ["aug13#9#11" {3 "#9" 6 "#11" 8 "#5"}]}
        shell-pc-set {#{0 3 9} ["min6" {9 "6"}]
                      #{0 3 10} ["min7"]
                      #{0 3 11} ["minMaj7"]
                      #{0 4 9} ["Maj6" {9 "6"}]
                      #{0 4 10} ["7"]
                      #{0 4 11} ["Maj7"]
                      #{0 5 10} ["7sus4" {5 "4"}]
                      #{0 1 4 10} ["7b9"]
                      #{0 1 5 10} ["7sus4b9" {5 "4"}]
                      #{0 2 3 9} ["min6/9" {9 "6"}]
                      #{0 2 3 10} ["min9"]
                      #{0 2 3 11} ["minMaj9"]
                      #{0 2 4 9} ["Maj6/9" {9 "6"}]
                      #{0 2 4 10} ["9"]
                      #{0 2 4 11} ["Maj9"]
                      #{0 2 5 10} ["9sus4" {5 "4"}]
                      #{0 3 4 10} ["7#9" {3 "#9"}]
                      #{0 3 5 10} ["min11"]
                      #{0 3 5 11} ["minMaj11"]
                      #{0 3 9 10} ["min13"]
                      #{0 3 9 11} ["minMaj13"]
                      #{0 4 6 10} ["7#11" {6 "#11"}]
                      #{0 4 6 11} ["Maj7#11" {6 "#11"}]
                      #{0 4 9 10} ["13"]
                      #{0 4 9 11} ["Maj13"]
                      #{0 5 8 10} ["7sus4b13" {5 "4"}]
                      #{0 5 9 10} ["13sus4" {5 "4"}]
                      #{0 1 4 6 10} ["7b9#11" {6 "#11"}]
                      #{0 1 4 9 10} ["13b9"]
                      #{0 1 5 8 10} ["7sus4b9b13" {5 "4"}]
                      #{0 1 5 9 10} ["13sus4b9" {5 "4"}]
                      #{0 2 3 5 10} ["min11"]
                      #{0 2 3 5 11} ["minMaj11"]
                      #{0 2 3 9 10} ["min13"]
                      #{0 2 3 9 11} ["minMaj13"]
                      #{0 2 4 6 10} ["9#11" {6 "#11"}]
                      #{0 2 4 6 11} ["Maj9#11" {6 "#11"}]
                      #{0 2 4 9 10} ["13"]
                      #{0 2 4 9 11} ["Maj13"]
                      #{0 2 5 8 10} ["9sus4b13" {5 "4"}]
                      #{0 2 5 9 10} ["13sus4" {5 "4"}]
                      #{0 3 4 6 10} ["7#9#11" {3 "#9" 6 "#11"}]
                      #{0 3 4 9 10} ["13#9" {3 "#9"}]
                      #{0 3 5 9 10} ["min13"]
                      #{0 3 5 9 11} ["minMaj13"]
                      #{0 4 6 9 10} ["13#11" {6 "#11"}]
                      #{0 4 6 9 11} ["Maj13#11" {6 "#11"}]
                      #{0 1 4 6 9 10} ["13b9#11" {6 "#11"}]
                      #{0 2 3 5 9 10} ["min13"]
                      #{0 2 3 5 9 11} ["minMaj13"]
                      #{0 2 4 6 9 10} ["13#11" {6 "#11"}]
                      #{0 2 4 6 9 11} ["Maj13#11" {6 "#11"}]
                      #{0 3 4 6 9 10} ["13#9#11" {3 "#9" 6 "#11"}]}
        filled-shell-pc-set (into {} (map (fn [[pc-set v]]
                                            [(conj pc-set 7) v])
                                          shell-pc-set))]
    (let [pc-set-intersection (set/union (set/intersection (set (keys shell-pc-set))
                                                           (set (keys filled-shell-pc-set)))
                                         (set/intersection (set (keys shell-pc-set))
                                                           (set (keys exact-pc-set)))
                                         (set/intersection (set (keys filled-shell-pc-set))
                                                           (set (keys exact-pc-set))))]
      (assert (= #{} pc-set-intersection)
              (str "Overlap in pc-set maps: " pc-set-intersection)))
    (merge shell-pc-set
           filled-shell-pc-set
           exact-pc-set)))

(defn- incr-vec-counter
  [values counter]
  (let [counter (vec counter)
        top (last counter)
        value->next (zipmap values (concat (rest values) [(first values)]))]
    (if (nil? top)
      nil
      (if (not= top (last values))
        (conj (vec (drop-last counter)) (value->next top))
        (when-let [lower (incr-vec-counter values (vec (drop-last counter)))]
          (conj lower (first values)))))))

(defn- vec-counter
  [values length]
  (take-while some? (iterate (partial incr-vec-counter values)
                             (vec (repeat length (first values))))))

(def intervals
  {1 "h"
   2 "d"
   3 "m"
   4 "M"
   5 "A"})

(defn chords
  [length]
  (vec-counter (sort (keys intervals)) length))

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
    (assoc interpretation :quality (pc-sets (set (:pc-vec interpretation))))))

(defn chord-interpretations
  [chord]
  (map (partial chord-interpretation chord) (range (inc (count chord)))))

(def max-movement 3)

(defn movement-<
  [a b]
  (let [numerify-remove (fn [movement] (map #(if (= :remove %) 0.5 %) movement))
        a (numerify-remove a)
        b (numerify-remove b)
        distance (fn [movement] (apply + (map #(Math/abs %1) movement)))
        movements (fn [movement] (count (remove zero? movement)))]
    (cond (< (movements a) (movements b)) true
          (> (movements a) (movements b)) false
          :else (< (distance a) (distance b)))))

(defn movements
  [length]
  (->> (vec-counter (concat [:remove] (range (- max-movement) max-movement)) length)
       (sort (comparator movement-<))
       (filter #(<= (count (remove (fn [x] (= 0 x)) %)) (Math/floor (/ length 2))))))

(defn move-chord
  [chord movement]
  (assert (= (inc (count chord)) (count movement))
          (str "Tried to move a chord containing " (inc (count chord)) " notes using a movement containing " (count movement) " notes."))
  (let [notes (->> chord
                   (reduce #(conj %1 (+ (last %1) %2)) [0])
                   (map #(if (= :remove %1) nil (+ %1 %2)) movement)
                   (remove nil?))]
    (mapv - (rest notes) notes)))

(defn root-movement
  [old-root movement new-interpretation]
  (let [movement (vec movement)
        {:keys [chord root]} new-interpretation
        ascending? (<= old-root root)
        [bottom-root top-root] (sort [old-root root])
        root-distance (- top-root bottom-root)
        inter-root-intervals (take root-distance (drop bottom-root chord))
        root-shift (get movement old-root)
        root-shift (if (= :remove root-shift) 0 root-shift)]
    (+ root-shift
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
        valid? (every? (set (keys intervals)) new-chord)]
    (when valid?
      (->> new-chord
           (chord-interpretations)
           (map #(assoc % :root-movement (root-movement root movement %)))
           (map normalise-shrunk-interpretation)
           (remove (comp nil? :quality))
           (distinct)))))

(defn interpretations
  [chord-length]
  (let [movements (movements (inc chord-length))]
    (->> (chords chord-length)
         (map #(chord-interpretation % 0))
         (remove (comp nil? :quality))
         (map (fn [interpretation]
                (assoc interpretation
                       :movements (remove (comp nil? second)
                                          (map #(vector % (move-interpretation interpretation %))
                                               movements))))))))

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
    "I"
    (str (if (pos? root-movement) "+" "-")
         (base-root-movement-str (mod (Math/abs root-movement) 12)))))

(def pc->note
  {0 "r"
   1 "b9"
   2 "9"
   3 "b3"
   4 "3"
   5 "11"
   6 "b5"
   7 "5"
   8 "b13"
   9 "13"
   10 "b7"
   11 "7"})

(defn voicing-str
  [interpretation movement]
  (let [{[_ pc->note-overrides] :quality
         :keys [pc-vec]} interpretation
        pc->note (merge pc->note pc->note-overrides)
        chord-str (reduce (fn [chord-str i]
                            (if (= :remove (get movement i))
                              (concat (take i chord-str) [""] (drop i chord-str))
                              chord-str))
                          (map pc->note pc-vec)
                          (range (count movement)))]
    (string/join " " (map #(format "%4s" %) chord-str))))

(defn intervals-str
  [interpretation]
  (let [{:keys [chord]} interpretation
        chord-str (map intervals chord)]
    (string/join " " (map #(format "%4s" %) chord-str))))

(defn movement-str
  [movement]
  (string/join " " (map #(cond (= :remove %) "  x"
                               (pos? %) (format " +%1d" %)
                               (zero? %) "  ."
                               :else (format "%3d" %))
                        movement)))

(defn format-interpretation
  [interpretation]
  (let [{[quality] :quality
         :keys [movements]} interpretation]
    (str
     (format "%-51s%-29s\n"
             ""
             (intervals-str interpretation))
     (format "%-12s%-39s%-29s\n"
             (str "." quality ".")
             ""
             (str "." (string/trim (voicing-str interpretation nil)) "."))
     "\n"
     (apply str
            (mapcat (fn [[movement interpretations]]
                      (map-indexed (fn [i interpretation]
                                     (let [{[quality] :quality
                                            :keys [root-movement]} interpretation]
                                       (format "%-23s     %5s  %-12s  %-29s\n"
                                               (if (zero? i) (movement-str movement) "")
                                               (root-movement-str root-movement)
                                               quality
                                               (voicing-str interpretation movement))))
                                   interpretations))
                    movements))
     "\n")))

(defn -main
  [& args]
  (let [max-notes (Integer/parseInt (or (first args) "6"))]
    (run! (fn [i]
            (run! #(print (format-interpretation %)) (interpretations i)))
          (range 2 max-notes))))
