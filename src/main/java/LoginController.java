import Model.ClientModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends Application implements Initializable {
    public Button btnLogIn;
    public TextField txtUserName;
    public Button btnSignUp;
    public Button btnRegister;
    public ComboBox cmbUserName;
    public AnchorPane root;
    private String userId;
    String name;
    ClientFormController clientFormController;
    public ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("View/Login.fxml"))));
        stage.show();

        new Thread(()->{
            Server server = new Server();
            try {
                server.Server();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserName();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/ClientForm.fxml"));
        AnchorPane anchorPane = loader.load();
        clientFormController=loader.getController();
        clientFormController.setLable((String) cmbUserName.getValue());
        cmbUserName.setValue(" ");
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Chat Space");
        stage.show();
    }
    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/RegisterForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Register Form");
        stage.show();
        root.getScene().getWindow().hide();
    }
    public boolean searchArray(String username){
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }

    public void CmbUserNameOnAction(ActionEvent actionEvent) {
    }

    private void loadUserName() {
        try {
            List<String> codes = ClientModel.getUserName();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String code : codes) {
                obList.add(code);
            }
            cmbUserName.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
