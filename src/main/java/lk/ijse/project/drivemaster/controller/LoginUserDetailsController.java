package lk.ijse.project.drivemaster.controller;

import lk.ijse.project.drivemaster.dto.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.project.drivemaster.dto.UserDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginUserDetailsController implements Initializable {

    @FXML
    private Label idEmail;

    @FXML
    private Label idHI;

    @FXML
    private Label idName;

    @FXML
    private Label idRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDTO user = Session.getCurrentUser();

        idHI.setText("Hi " + user.getUsername());
        idName.setText(user.getUsername());
        idEmail.setText(user.getEmail());
        idRole.setText(user.getRole());

    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
