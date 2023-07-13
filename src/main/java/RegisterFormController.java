import Model.ClientModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {

    private final static String URL = "jdbc:mysql://localhost:3306/chatapplication";
    private final static Properties props = new Properties();


    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public TextField txtUser_Id;
    public TextField txtUserName;
    public PasswordField txtUserPassword;
    public Button btnRegisterOnAction;
    public AnchorPane root;
    public Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateNextUserId();
    }


    public void btnRegisterOnAction(ActionEvent actionEvent) throws SQLException {
        String user_Id = txtUser_Id.getText();
        String name = txtUserName.getText();
        String password = txtUserPassword.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO user(User_Id,User_Name,Password) VALUES(?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, user_Id);
            pstm.setString(2, name);
            pstm.setString(3, password);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "user Added!!").show();
            }
            clear();
        }
    }

    public void clear() {
        txtUser_Id.setText("");
        txtUserName.setText("");
        txtUserPassword.setText("");
    }

    private void generateNextUserId() {
        try {
            String nextId = ClientModel.generateNextUserId();
            txtUser_Id.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Login.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
    }

}
