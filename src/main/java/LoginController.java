import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    public Button btnLogIn;
    public TextField txtUserName;
    public Button btnSignUp;
    public ComboBox cmbUserName;
    private String userId;
    String name;
    ClientFormController clientFormController;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/ClientForm.fxml"));
        AnchorPane anchorPane = loader.load();
        clientFormController=loader.getController();
        clientFormController.setLable(txtUserName.getText());
        txtUserName.clear();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Chat Space");
        stage.show();
    }

}
