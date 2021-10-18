# Scoober Game, Code Challenge
### (Backend - Java Spring)
A game with two players which are independant and can be played by single or both on neither player.
# Description
When a player starts, it incepts a random (whole) number and sends it to the second player as an approach of starting the game. The receiving player can now always choose between adding one of `{-1, 0, 1}` to get to a number that is divisible by 3. Divide it by three. The resulting whole number is then sent back to the original sender. The same rules are applied until one player reaches the number 1 (after the division). Both players should be able to play automatically without user input. One of the players should optionally be adjustable by a user.
# Technology Stack
- Java 1.8
- Spring Boot 2.2.1.RELEASE
- Spring Data
- Hibernate
- JPA Repository
- h2 in memory database
# Setup
To setup the environment you need to have `java` version 8 and `maven` 3 plus installed in order to run.
First clean install using
```
mvc clean install -Dmaven.test.skip
```
After that just run the application with
```
mvn spring-boot:run
```
OR
```
java -jar scoober-game-1.0-SNAPSHOT.jar
```
Once the application is running you can check whether your application is up and running or not, by requesting:
```
localhost:8081/api/v1/game/health
```    
# API
## Start a Game
To start a game, send a ***POST*** request to:
```
localhost:8081/api/v1/game
```
#### Body:
```json
{
    "game_type":"SINGLEPLAYER",
    "player_1_name":"player_1_name 1",
    "player_2_name":"player_2_name 2",
    "initial_number": 70
}
```
There are 3 types of games and for each of them `game_type` variable selection is:
- Player vs Player -> `MULTIPLAYER`
- Player vs Computer -> `SINGLEPLAYER`
- Computer vs Computer -> `AUTO`
#### Sample Response:
```json
{
    "game_id": 3,
    "player_1": "1:player_1_name 1",
    "player_2": "2:npc 2",
    "current_number": 23,
    "original_number": 70,
    "moveList": [
        {
            "move_description": "Move number: -1, Player npc 2, NPC Move: 70 + -1 / 3 = 23"
        }
        ...
    ]
}
```
Here, **game_id** is the id of current game, **player_1** is the first player playing the game, and **player 2** is the second. Because the game was in `SINGLEPLAYER` mode here, here the game is waiting for the move from **player 1**
If game starts with `AUTO`, it will completely play the game until a winner is emerges and yes both of the players are `NPC`s
## Move
To make a move in the game, there exists a ***PUT*** request:
```
localhost:8081/api/v1/game
```
#### Body:
```json
{
    "game_id": 3,
    "player_id": 1,
    "move_number": 0
}
```
Here `game_id` is the game id of current playing game, `player_id` is the id of player who is making the move and `move_number` is the number `{-1,0,1}`
#### Sample Response
```json
{
    "game_id": 3,
    "player_1": "1:player_1_name 1",
    "player_2": "2:npc 2",
    "current_number": 3,
    "original_number": 70,
    "moveList": [
        {
            "move_description": "Move number: 1, Player npc 2, NPC Move: 8 + 1 / 3 = 3"
        },
        {
            "move_description": "Move number: 1, Player player_1_name 1, HUMAN Move: 23 + 1 / 3 = 8"
        },
        {
            "move_description": "Move number: -1, Player npc 2, NPC Move: 70 + -1 / 3 = 23"
        }
    ]
}
```
The above response represent the game of `SINGLEPLAYER`, where after player move computer did the move and send the reponse. But in case of `MULTIPLAYER` second player have to make the move as well
## Get the Active Games
To get the current active games we have a get request
```
localhost:8081/api/v1/game/all/active
```
### Sample Response
```json
[
    {
        "createdDate": "2021-10-17T09:54:45.583+0000",
        "id": 3,
        "gameType": "SINGLEPLAYER",
        "firstCurrentPlayer": {
            "createdDate": "2021-10-17T09:54:45.548+0000",
            "id": 1,
            "playerKind": "HUMAN",
            "name": "player_1_name 1"
        },
        "secondCurrentPlayer": {
            "createdDate": "2021-10-17T09:54:45.579+0000",
            "id": 2,
            "playerKind": "NPC",
            "name": "npc 2"
        },
        "nextPlayer": {
            "createdDate": "2021-10-17T09:54:45.548+0000",
            "id": 1,
            "playerKind": "HUMAN",
            "name": "player_1_name 1"
        },
        "currentNumber": 3,
        "originalNumber": 70,
        "gameStatus": "ACTIVE"
    }
]
```
Currently one game is active and if there are multiple games active it will return them
## Join Game
For joining an active game a put request in exposed
```
localhost:8081/api/v1/game/join
```
### Request
```json
{
    "game_id": 3,
    "player_id": 5 
}
```
### Response
```json
{
    "game_id": 3,
    "player_1": "player 1",
    "player_2": "player 2",
    "current_number": 27,
    "original_number": 80,
    "moveList": [
        {
            "move_description": "Move number: 1, Player npc 1, NPC Move: 80 + 1 / 3 = 27"
        }
    ]
}
```
## Exit Game
Similarly there is also an option of leave game with the following url
```
localhost:8081/api/v1/game/exit
```
### Request
```json
{
    "game_id": 3,
    "player_id": 5 
}
```
Response is also similar to the join one.
## Add a player
A player can be created with the following ***POST*** request:
```
localhost:8081/api/v1/player
```
#### Body:
```json
{
    "name": "player efg",
    "kind": "HUMAN"
}
```
This request could be useful in `MULTIPLAYER` games, for creating a player which is not starting the game, but is playing as the second player.
