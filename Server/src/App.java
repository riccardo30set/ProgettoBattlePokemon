import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class App {
    private static int playerID = 0;
    public static HashMap<Integer, PlayerHandler> players = new HashMap<Integer, PlayerHandler>();
    public static HashMap<String, Match> games = new HashMap<String, Match>();
    public static void main(String[] args) throws Exception {
        ServerSocket mainSocket = new ServerSocket(1500);
        System.out.println("Main socket listening on port 1500");
        while(true){
            Socket newClient = getNextClient(mainSocket);
            if(newClient!=null){
                players.put(playerID, new PlayerHandler(newClient, playerID));
                players.get(playerID).start();
                playerID++;
            }
        }
        
        
    }

    /**
     * Waits for a connection on the passed ServerSocket and tries to accept it
     * @param mainSocket
     * @return The socket if the connection is successful, null otherwise
     */
    public static Socket getNextClient(ServerSocket mainSocket){
        try {
            return mainSocket.accept();
        } catch (Exception e) {
            System.out.println("Connection with new client failed:\n");
            e.printStackTrace();
            
        }
        return null;
    }
}


