import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientFormController {
    public VBox VboxSendMessage;
    public Label lblUserName;
    public TextField txtFieldMessage;
    public Button btnSend;


    ServerSocket serverSocket;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String  message="";

    public void setLable(String text) {

        lblUserName.setText(text);
    }


    public void btnSendOnAction(ActionEvent actionEvent) {
    }
}
