import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends  Thread{
    private ArrayList<Client> clientsArrayList;
    Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(Socket socket, ArrayList<Client> clientsArrayList) throws IOException {
        this.socket = socket;
        this.clientsArrayList = clientsArrayList;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }
    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                if (msg.equalsIgnoreCase( "exit")) {
                    break;
                }
                for (Client cl : clientsArrayList) {
                    cl.writer.println(msg);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }
}
