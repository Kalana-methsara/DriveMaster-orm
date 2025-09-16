package lk.ijse.project.drivemaster.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.custom.UserBO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.dto.UserDTO;
import lk.ijse.project.drivemaster.enums.Role;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserDTO, String> colEmail;

    @FXML
    private TableColumn<UserDTO, Role> colRole;

    @FXML
    private TableColumn<UserDTO, Long> colUserID;

    @FXML
    private TableColumn<UserDTO, String> colUserName;

    @FXML
    private TableView<UserDTO> tableView;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textPassword;

    @FXML
    private ChoiceBox<Role> textRole;

    @FXML
    private Label textUserId;

    @FXML
    private TextField textUserName;

    private final UserBO userBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.USER);


    @FXML
    void onActionDelete(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void onActionUpdate(ActionEvent event) {

    }

    @FXML
    void onRefresh(MouseEvent event) {
        clearCourseFields();

    }

    @FXML
    void setData(MouseEvent event) {
        UserDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            textUserId.setText(String.valueOf(selected.getId()));
            textUserName.setText(selected.getUsername());
            textEmail.setText(selected.getEmail());
            textRole.setValue(Role.valueOf(selected.getRole()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            initComboBoxes();
            loadTableData();
            clearCourseFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initComboBoxes() {
        textRole.getItems().addAll(Role.values());
    }

    private void loadTableData() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                userBO.getAllUsers().stream().map(UserDTO ->
                        new UserDTO(
                                UserDTO.getId(),
                                UserDTO.getUsername(),
                                UserDTO.getEmail(),
                                UserDTO.getRole()
                        )).toList()
        ));
    }

    private void loadNextId() {
        String nextId = String.valueOf(userBO.getNextId());
        textUserId.setText(nextId);
    }

    private void clearCourseFields() {
        loadNextId();
        textUserName.clear();
        textPassword.clear();
        textEmail.clear();
        textRole.getSelectionModel().clearSelection();
    }
}
