import Model.User;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
    public Label lblUserId;
    public PasswordField txtUserPassword;
    public Button btnSave;
    public Button btnBack;
    public TextField txtUserName;

    private final static  String URL="jdbc:mysql://localhost:3306/chatapplication";
    private final static Properties props=new Properties();


    static{
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        generateUserId();
    }



    public void generateUserId(){
        try {
            String nextId = User.generateNextUserId();
            lblUserId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String id = lblUserId.getText();
        String name = txtUserName.getText();
        String password =txtUserPassword.getText();

 
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO user(User_Id,User_Name,Password) VALUES(?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3,password);

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!!").show();
            }
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("welcome Page");
        stage.centerOnScreen();
    }

}
