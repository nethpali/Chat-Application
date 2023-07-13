import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static ArrayList<Client> clientsArrayList = new ArrayList<>();

    public static void Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(6000);
        Socket socket;
        int index = 1;
        while (true) {
            System.out.println("Waiting for Client ...");
            socket = serverSocket.accept();
            System.out.println("Client " + index + " Connected");
            Client clients = new Client(socket, clientsArrayList);
            clientsArrayList.add(clients);
            clients.start();
            index++;
        }
    }

}
