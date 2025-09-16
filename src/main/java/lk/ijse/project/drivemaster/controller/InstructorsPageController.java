package lk.ijse.project.drivemaster.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;

import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructorsPageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InstructorDTO, String> colContact;

    @FXML
    private TableColumn<InstructorDTO, String> colEmail;

    @FXML
    private TableColumn<InstructorDTO, String> colNic;

    @FXML
    private TableColumn<InstructorDTO, Long> colInstructorId;

    @FXML
    private TableColumn<InstructorDTO, String> colInstructorName;

    @FXML
    private TableView<InstructorDTO> tableView;

    @FXML
    private TextField textContact;

    @FXML
    private TextField textEmail;

    @FXML
    private Label lblId;

    @FXML
    private TextField textNic;

    @FXML
    private TextField textName;

    private final InstructorBO instructorBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.INSTRUCTOR);


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
    void onKeyContact(KeyEvent event) {

    }

    @FXML
    void onKeyEmail(KeyEvent event) {

    }


    @FXML
    void onKeyNic(KeyEvent event) {

    }

    @FXML
    void onKeyName(KeyEvent event) {

    }

    @FXML
    void onRefresh(MouseEvent event) {
clearCourseFields();
    }

    @FXML
    void setData(MouseEvent event) {
        InstructorDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(String.valueOf(selected.getId()));
            textName.setText(selected.getName());
            textNic.setText(selected.getNic());
            textEmail.setText(selected.getEmail());
            textContact.setText(selected.getPhone());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInstructorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            loadTableData();
            clearCourseFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private void clearCourseFields() {
        loadNextId();
        textName.clear();
        textNic.clear();
        textContact.clear();
        textEmail.clear();
    }

    private void loadNextId() {
        String nextId = String.valueOf(instructorBO.getNextId());
        lblId.setText(nextId);
    }

    private void loadTableData() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                instructorBO.getAllInstructors().stream().map(InstructorDTO ->
                        new InstructorDTO(
                                InstructorDTO.getId(),
                                InstructorDTO.getName(),
                                InstructorDTO.getNic(),
                                InstructorDTO.getEmail(),
                                InstructorDTO.getPhone()
                        )).toList()
        ));
    }
}
