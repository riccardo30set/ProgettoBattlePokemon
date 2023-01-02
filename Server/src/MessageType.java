public class MessageType{
    //Send game name
    public final static int CREATE_GAME = 0;
    //Send game id
    public final static int JOIN_GAME = 1;
    //Send name, description, eventually photo object.
    public final static int SEND_PLAYER_IDENTITY = 2;
    //Get name, description, eventually photo object.
    public final static int GET_OPPONENT_IDENTITY = 3;
    //Send pokemon slot and name
    //the server forwards the information to the other player
    public final static int SET_POKEMON = 4;
    //Send opponent pokemon's as a collection
    public final static int GET_OPPONENT_POKEMONS = 5;
    //No other information to send, when both players
    //sent this code, start the game 
    public final static int START_GAME = 6;
    //Start battle GUI
    public final static int GAME_STARTED = 7;

}