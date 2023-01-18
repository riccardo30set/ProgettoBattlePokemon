import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerHandler extends Thread {

    private Player player;
    private int playerID;
    private Socket socket;
    private boolean isReady = false;
    public PrintWriter socketOut;
    public Scanner socketIn;
    private Match game;
    private Action action;
    /**
     * 
     * @param socket the player's socket
     * @param playerID
     */
    public PlayerHandler(Socket socket, int playerID){
        this.socket = socket;
        this.playerID = playerID;
        try {
            this.socketIn = new Scanner(socket.getInputStream());
            this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Unable to get socket I/O streams, disconnecting player "+playerID);
            disconnect();
        }
        setPlayerInfo();
    }




    //Methods for handling player connection and identification
    /**
     * Gets the player's name and description from the socket and 
     * saves them in the object. 
     * Only call this method in the PlayerHandler object construction
     */
    private void setPlayerInfo(){
        if(Integer.parseInt(socketIn.nextLine())==MessageType.SEND_PLAYER_IDENTITY){
            this.player = new Player(socketIn.nextLine(), socketIn.nextLine(), this.playerID);
            System.out.println("Giocatore: "+player.getName()+" connesso");
        }else{
            System.out.println("Protocol error: user did not send name and description.");
        }
    }

    public void run(){
        while(true){
            switch(Integer.parseInt(socketIn.nextLine())){
                case MessageType.CREATE_GAME:
                    createGame(socketIn.nextLine());
                    break;
                case MessageType.JOIN_GAME:
                    joinGame(socketIn.nextLine());
                    break;
                case MessageType.GET_OPPONENT_IDENTITY:
                    sendOpponentIdentity();
                    break;
                case MessageType.SET_POKEMON:
                    setPokemon(Integer.parseInt(socketIn.nextLine()),socketIn.nextLine());
                    break;
                case MessageType.SET_READY:
                    setReady(socketIn.nextBoolean());
                    break;
                case MessageType.ACTION:
                    this.writeAction(socketIn.nextLine());
                    this.game.playerMove(playerID);
                    break;
            }
        }
    }

    /**
     * Creates a game and sets this player as its host
     * @param name
     * @return
     */
    public void createGame(String name){
        game = new Match(name);
        game.addPlayer(0, this);
        App.games.put(name, game);
    }

    /**
     * Joins a game id the given name corrisponds to a created game
     * @param name
     */
    public void joinGame(String name){
        if(App.games.containsKey(name)){
            game = App.games.get(name);
            game.addPlayer(1, this);
            getOpponent().socketOut.println(MessageType.GUEST_JOINED);
            getOpponent().socketOut.println(player.getName());
        }
    }

    /**
     * Sets a pokemon in a given slot and informs the other player
     * @param slot
     * @param pokemon
     */
    public void setPokemon(int slot, String pokemon){
        System.out.println(player.getName()+" imposta pokemon "+pokemon+" in slot "+slot);
        this.player.setPokemon(slot, pokemon);
        PlayerHandler opponent = getOpponent();
        opponent.socketOut.println(MessageType.GET_OPPONENT_POKEMON);
        opponent.socketOut.println(slot);
        opponent.socketOut.println(pokemon);

    }

    private PlayerHandler getOpponent(){
        return game.getOpponent(this);
    }

    public void sendOpponentIdentity(){
        System.out.println(player.getName()+" richiede il profilo avversario");
        PlayerHandler opponent =getOpponent();
        socketOut.println(opponent.getPlayer().getName());
        socketOut.println(opponent.getPlayer().getDescription());

    }

    public void setReady(boolean state){
        if(state){
            System.out.println(player.getName()+" è pronto");
        }else {
            System.out.println(player.getName()+" NON è pronto");
        }
        this.isReady = state;
        if(getOpponent().isReady()&&isReady){
            System.out.println("La partita inizia");
            game.start();
        }
    }



    public Player getPlayer(){
        return this.player;
    }

    public int getPlayerId(){
        return this.playerID;
    }
    public Action getPlayerAction(){
        return this.action;
    }
    public boolean isReady(){
        return this.isReady;
    }
    // USED_MOVE|CHANGED_PK:PKNAME:HP:[MV_NAME]
    public void writeAction(String act){
        String[] actElements = act.split(":");
        if(actElements.length==4){
            this.action=new Action(Integer.parseInt(actElements[0]),actElements[1],actElements[2],actElements[3]);
        }else{
            this.action=new Action(Integer.parseInt(actElements[0]),actElements[1],actElements[2]);
        }
    }



    /**
     * Tries closing I/O streams and deletes the socket
     */
    private void disconnect(){
        try {
            socketIn.close();
            socketOut.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Unable to close player "+playerID+"'s socket or I/O streams");
        }
        App.players.remove(playerID);        
    }
}
