public class MessageType{
    //Send game name
    public final static int CREATE_GAME = 0;
    //Send game name
    public final static int JOIN_GAME = 1;
    //Send name, description, eventually photo object.
    public final static int SEND_PLAYER_IDENTITY = 2;
    //Get name, description, eventually photo object.
    public final static int GET_OPPONENT_IDENTITY = 3;
    //Send pokemon slot and name
    //the server forwards the information to the other player
    public final static int SET_POKEMON = 4;
    //Send opponent pokemon's as a collection
    public final static int GET_OPPONENT_POKEMON = 5;
    //Send true or false
    public final static int SET_READY = 6;
    //Start battle GUI
    public final static int GAME_STARTED = 7;
    //Respond to this with CONNECTIVITY_CHECK_OK
    public final static int CONNECTIVITY_CHECK_REQ = 8;
    public final static int CONNECTIVITY_CHECK_OK = 9;
    public final static int INCOMBAT = 10;
    public final static int WIN = 11;
    public final static int LOSE = 12;
    public final static int FIRST = 13;
    public final static int SECOND = 14;
    public final static int USED_MOVE = 15;
    public final static int CHANGE_POKEMON = 16;
    public final static int ACTION = 17;
    public final static int GUEST_JOINED = 18;
    public final static int ONLY_ONE_ACT = 19;
}