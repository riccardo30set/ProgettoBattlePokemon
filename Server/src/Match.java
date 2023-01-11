public class Match{
    private String name;
    private PlayerHandler[] players = new PlayerHandler[2];


    public Match(String name){
        System.out.println("Creata partita "+name);
        this.name = name;
    }

    /**
     * Adds a player to the match in the specified position.
     * Use position 0 for game host
     * @param playerNumber
     * @param player
     */
    public void addPlayer(int playerNumber, PlayerHandler player){
        System.out.println(player.getPlayer().getName()+" partecipa alla partita "+name);
        players[playerNumber] = player;
    }

    /**
     * 
     * @param me The player asking for the opponent
     * @return
     */
    public PlayerHandler getOpponent(PlayerHandler me){
        if(me==players[0]){
            return players[1];
        }
        return players[0];
    }

    public void start(){
        for (PlayerHandler player : players) {
            player.socketOut.println(MessageType.GAME_STARTED);
        }
    }

}