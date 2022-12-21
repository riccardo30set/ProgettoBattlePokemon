import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerHandler extends Thread {
    private Socket socket;
    private int playerID;
    private PrintWriter socketOut;
    private Scanner socketIn;
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
    }

    /**
     * Tries closing I/O streams, 
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
