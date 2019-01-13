
Project 4:
Author: Yarden Ben-Amitai
ID: 312575384

in this project i built a game field for a packman game with slightly different rules,
the game contains various game pieces such as packmans, ghosts, fruits, obstacles and one player.
the player goal is to eat as many fruits as he can while avoiding the ghosts and not touching the obstacles,
meanwhile the packmans eat the fruits too.
the player can also eat packmans and gain points as well as removing the risks for the fruits.
the player controls his game piece with the arrow keys.
in order to play, the player loads a game from a given list, use the mouse to set the initial location of the player
then press the "Tab" key (solution found on StackOverFlow, in order to draw constant attention to the KeyListener 
when a different program is running in the background. for lack of time, i did not have time to fix it properly in the code).
the game starts when the player press any arrow key.

the game also has an Automatic Pilot mode, where the player choose a game from the given list, press in the menu "Automatic Pilot"
and places the player on the board with the mouse.
the algorithm for auto pilot generates a new path for every fruit, in a away for the player to avoid the obstacles
(the algorithm ignores the ghosts).

in the project i made use of three given classes: LatLonAlt ,Cords and Play.
i used LatLonAlt and Cord as they were , and updated Packman, Fruit, Map and Game to work with these classes, as well as added a new class Obstacle.
i also made changes according to remarks in the previous submission.
in Play i made slight alterations for it to work with my game pieces and not the given ones.


Ex4 additions:

AutoPilot package:
holds two classes, Node and PlayerAlgo.
containing the algorithm of the auto pilot.

Algorithms package:
contains Play class that is in charge of performing the actual game.
the class moves all the game pieces around, holds the score and keep the outcome in a database.

Coords package:
i made alteration to Map class, such as: to improve the accuracy of the calculations from pixels to coordinates and back.

GamePieces package:
Obstacle class was created to represent obstacles in the game field.
Game was updated to include all the different game pieces the game now has.
Packman and Fruit were altered: to use LatLonAlt, to represent all the different types of moving objects, 
and also to be used in Play class.

GUI package:
i added MyFrame another menu for project 4 and bettered the use of swing tools, 
as well as improved the graphics of the display per the remarks given previously.
Board class, to avoid confusion and to maintain an orderly and understandable code i created another JPanel for the project
when MyFrame menu for project 4 is pressed, the JFrame creates Board accordingly, and loads the desired game.
it also takes the players' id numbers and store them in Play.


///////////////////////////////////////////////////////////
for lack of time, did not implement a class that reads from the database and evaluate the score.
///////////////////////////////////////////////////////////







/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Summary of project 3 : 

Authors:
Caroline Jubran - 318696150
Yarden Ben Amitai - 312575384


In this project we built a simple pacman game.
the background of the game is a picture of a map.
when the game starts, a blank window appear with two menus: "Game Set"- that containes already written csv files that run the game
and "New Game" in which the player can create his own game and set pacmans and fruits as he likes.
when choosing one of the game sets, pacmans and fruits appear on the sceen. 
each packman on the map eats the fruits closest to it until there are no fruits left,
every fruit must be eaten by only one pacman, and then disappear. 
the score of each pacman is defined by he sum of weights of the fruits he ate.
the goal is to eat all the fruits as quickly as possible.

****In the GIS package** 
 
////////CLASS MAP//////////
 This class represents a map which will be our game background
it consists of : an image, gps coordinates of top-left and bottom-right corners and the picture's bounds.
1- constructor -> Map 
2-methods -> CoordsToPixels + PixelsToCoords + PixelDistance 

//////CLASS PACMAN///////
This class represents a robot (packman) that can move on the map in a certain velocity.
it consists of :speed, radius, gps coordinates, and id.
1-constructor -> Pacman
2-getters and setters -> for the speed and radius of each pacman

//////CLASS FRUIT/////////
This class represents a "goal" (fruit) that can't move.
it consists of : weight, gps coordinates and id.
1-constructor ->Fruit 
2-getters and setters ->for the weight of each fruit


///////CLASS GAME/////////////
This class represents a group of pacmans and a group of fruits , it has the ability to 
gather information from a csv file and save to it.
it consists of :pacmans list, fruits list
1-constructor -> Game
2-default  constructor -> Game()
3-methods -> GameToCSV + MetaData  
4-getters and setters -> methods for packman and fruit fields


///////CLASS GAME DATA//////////
This class gives the information you need to know about each pacman
like its : ID , location and its utc
it consists of a :
1-constructor->Game_Data
2-getters and setters -> to get the utc , location ...
 

///////CLASS PATH///////////
this class  represents a path where the first element is the pacman and the rest of the list is fruits he needs to eat.
it consists of :a list as describes above, the speed and radius of the pacman as well as path's id.
1-construcor ->Path
2-method -> DistanceSpeedTime 
3-getters and setters -> ID + radius + path .. 


**in the ALGORITHEMS package*****

/////CLASS SHORTEST PATH ALGO/////
This class represents the main algorithm that the game depends on 
the algorithm calculates the shortest path each pacman should take to eat the fruits in its path in the shortest 
period of time possible.
it consists of : a list of paths for each pacman
1-constructor ->ShortestPathAlgo(Game)
2 ->SmartPathCalculator/SimplePathCalculator- the constructor receives a game and then decides to which method to send 
it to according to the number of fruits. for games with under 21 fruits the class uses the smart algorithm. and simple algorithm otherwise.
3 ->getters and setters


****in the GUI package********

///////CLASS MY FRAME////////
this is a graphic class that represents our pacmans and fruits on a map , display the process of the game according to the algorithm,
saves the game process to csv file, creates a new game by setting game pieces on the board etc.
it consists of :a GameBoard and an empty game piece for personal alterations. 
1-constructor ->My Frame
2-methods -> paint component, animate, LoadBoard

  
***in the FILE FORMAT package******

//////CLASS PATH2KML//////////
this class helps us get the path of each pacman according to a real time zone  and saves it 
in a kml file that we can run on google earth and watch our packman's eat their fruits
it consists of a:
