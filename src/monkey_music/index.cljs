(ns monkey-music.index
  (:require [monkey-music.wrapper :as wrapper]
            [monkey-music.core :as core]))

(set! *main-cli-fn* (fn []))

(defn args->clj [f] (fn [& args] (apply f (map js->clj args))))
(def return->js (partial comp clj->js))

;; Functions returning Clojure values

(defn parse-command [command]
  (wrapper/json->command (js->clj command)))
(aset js/exports "parseCommand" parse-command)

(defn create-game-state [player-names level]
  (wrapper/create-game-state (js->clj player-names) (js->clj level)))
(aset js/exports "createGameState" create-game-state)

(defn run-commands [state commands]
  (core/run-commands* state (js->clj commands)))
(aset js/exports "runCommands" run-commands)

;; Functions returning JS values

(defn game-state-for-team [state team-name]
  (clj->js (wrapper/game-state->json-for-team state team-name)))
(aset js/exports "gameStateForTeam" game-state-for-team)

(aset js/exports "isGameOver" core/game-over?)