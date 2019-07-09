(ns sudoku)
(require '[clojure.string :as str])
(require '[clojure.set :as set])

(defn to-possibles [num]
  (if (re-matches #"[1-9]" num) (set (list num)) (set '("1" "2" "3" "4" "5" "6" "7" "8" "9"))))

(defn confirmed? [possibles]
  (= (count possibles) 1))

(defn rows [i]
  (range i (+ i 9)))

(defn str-to-board [input-board]
    (mapv to-possibles
      (take 81
        (str/split input-board #"" 82))))

(defn input [] "...28.94.1.4...7......156.....8..57.4.......8.68..9.....196......5...8.3.43.28...")

(str-to-board (input))

(map-indexed (fn [i possible]
  i
  )
    (str-to-board (input)))


; Done: converts each char of an input string into a set of possibilities and adds vector positionality
; TODO:
; Given a position, return all the confirmed possibles based on column, row and box
; Turn all confirmed possibles from col, row and box into a set
; Return the difference between the full set of nums and confirmed possibles