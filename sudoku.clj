; (ns sudoku)
(require '[clojure.string :as str])
(require '[clojure.set :as set])

(defn to-possibles [num]
  (if (re-matches #"[1-9]" num) (set (list num)) (set '("1" "2" "3" "4" "5" "6" "7" "8" "9"))))

(defn confirmed? [possibles]
  (= (count possibles) 1))

(defn row-start [i]
  (* 9 (quot i 9)))

(defn row-slice [i]
  (vec (range (row-start i) (+ 9 (row-start i)))))

(defn row-values [board i]
  (map board (row-slice i)))

(defn col-start [i]
  (mod i 9))

(defn col-slice [i]
  (mapv (fn [num] (+ num (col-start i))) [0 9 18 27 36 45 54 63 72]))

(defn col-values [board i]
  (map board (col-slice i)))

(defn box-col [i]
  (mod (quot i 3) 3))

(defn box-row [i]
  (quot i 27))

(defn box-start [i]
  (+ (* (box-col i) 3) (* (box-row i) 27)))

(defn box-slice [i]
  (mapv (fn [num] (+ (box-start i) num)) [0 1 2 9 10 11 18 19 20]))

(defn box-values [board i]
  (map board (box-slice i)))

(defn str-to-board [input-board]
    (mapv to-possibles
      (take 81
        (str/split input-board #"" 82))))

(defn input [] "...28.94.1.4...7......156.....8..57.4.......8.68..9.....196......5...8.3.43.28...")

(defn pare-possibles [possibles confirmed-set]
  (set/difference possibles confirmed-set))

(defn confirmed-for [board i]
  (set/union
    (reduce set/union (filter confirmed? (row-values board i)))
    (reduce set/union (filter confirmed? (col-values board i)))
    (reduce set/union (filter confirmed? (box-values board i)))))

; (defn row-col-box-values [board i]
;   )

; (defn possibles-for [board i]
;   (reduce set/union (filter confirmed? (row-values board i))))

; (set/difference ((load-file "sudoku.clj") 0) (reduce set/union (filter confirmed? (row-values (load-file "sudoku.clj") 0))))

(defn deduce [board]
  (vec
    (map-indexed (fn [i possibles]
      (if (confirmed? possibles)
        possibles
        (pare-possibles possibles (confirmed-for board i)))
      ) board)))

(defn solved? [board]
  (every? confirmed? board))

(defn solve [board]
  (if (solved? board)
    board
    (solve (deduce board))))

; (str-to-board (input))

(defn b [] (str-to-board (input)))


; DONE:
; Converts each char of an input string into a set of possibilities and adds vector positionality
; Calculates indices of rows, columns and boxes based on a given index
; TODO:
; Given a position, return all the confirmed possibles based on column, row and box
; Turn all confirmed possibles from col, row and box into a set
; Return the difference between the full set of nums and confirmed possibles