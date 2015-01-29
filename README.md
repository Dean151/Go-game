Go-game
=======

Simple Go game in Java

Contributors: Thomas BUICK, Thomas DURAND, Loïc MOLLET-PADIER

Sprites are from the Wikipedia Go-game page, they are in the public domain.

## Features

### Two players game
This game features an amazing GUI for all of your two players go games !
You can save and load games, and there is also a full handling of undo/redo.

![Go game image](http://dean.cafelembas.com/public/Jeux/GoGame.tiff)

The game is fully functionnal, with all of the classical rules.
You can set up 3 sizes of Goban : 9x9, 13x13 and 19x19, and choose how much handicap white have against black.

Ko rules, and captured stones rules are also handled.
The game end when there is three consecutive pass move.

### Score calculation
When there is three consecutive pass moves, the game is over.
Then, you are asked to select each deads stones chains, and then the score and the winner prompt.

![Go game image](http://dean.cafelembas.com/public/Jeux/GoScore.tiff)
