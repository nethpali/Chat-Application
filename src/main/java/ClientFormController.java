import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;
import java.net.Socket;

public class ClientFormController extends Thread{
    public VBox VboxSendMessage;
    public Label lblUserName;
    public TextField txtFieldMessage;
    public Button btnSend;


    public ImageView imgsend;
    public ImageView emojiLoad;

    PrintWriter writer;

    BufferedReader reader;
    Socket socket;

    private FileChooser fileChooser;
    private File filePath;

    public void initialize(){
        try {
            socket = new Socket("localhost",6000);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void setLable(String text) {
        lblUserName.setText(text);
    }
    @Override
    public void run(){
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);

                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]+" ");
                }

                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }

                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }

                if (firstChars.equalsIgnoreCase("img")) {
                    //for the Images
                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblUserName.getText())) {
                        VboxSendMessage.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);
                        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);
                        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
                    }

                    Platform.runLater(() -> VboxSendMessage.getChildren().addAll(hBox));


                }else {
                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200); //200

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12); //12

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        VboxSendMessage.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
                        flow.setStyle("-fx-background-color:   white;-fx-background-radius:15;-fx-font-size: 15;-fx-font-weight: normal;-fx-text-fill: black;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
                        hBox.getChildren().add(flow);
                    } else {
                        Text text2 = new Text(fullMsg + ": Me");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
                        flow2.setStyle("-fx-background-color:  #CCFF9A;-fx-background-radius:15;-fx-font-size: 15;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
                        hBox.getChildren().add(flow2);
                    }
                    Platform.runLater(() -> VboxSendMessage.getChildren().addAll(hBox));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send(){
        String msg = txtFieldMessage.getText();
        writer.println(lblUserName.getText() + ": " + msg);
        System.out.println(lblUserName.getText() + ": " + msg);
        txtFieldMessage.clear();

        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }
    public void btnSendOnAction(ActionEvent actionEvent) {
        send();
    }

    public void btnImageSendOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblUserName.getText() + " " + "img" + filePath.getPath());
    }

    public void EmojiLoadOnAction(MouseEvent mouseEvent) {
    }
}
