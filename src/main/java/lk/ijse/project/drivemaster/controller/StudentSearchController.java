package lk.ijse.project.drivemaster.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentSearchController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StudentDTO, String> colAddress;

    @FXML
    private TableColumn<StudentDTO, String> colBirthday;

    @FXML
    private TableColumn<StudentDTO, String> colContact;

    @FXML
    private TableColumn<StudentDTO, String> colEmail;

    @FXML
    private TableColumn<StudentDTO, String> colGender;

    @FXML
    private TableColumn<StudentDTO, LocalDate> colJoinDate;

    @FXML
    private TableColumn<StudentDTO, Long> colStudentId;

    @FXML
    private TableColumn<StudentDTO, String> colStudentName;

    @FXML
    private TableView<StudentDTO> tableView;

    @FXML
    private ComboBox<String> textMonth;

    @FXML
    private ComboBox<String> textYear;

    @FXML
    private TextField txtSearch;

    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);


    @FXML
    void onActionDelete(ActionEvent event) {

    }

    @FXML
    void onActionMonth(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void onActionUpdate(ActionEvent event) {

    }

    @FXML
    void onActionYear(ActionEvent event) {

    }

    @FXML
    void setData(MouseEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("id"));


        colStudentName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName())
        );

        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));

        try {
            loadTableData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private void loadTableData() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                studentBO.getAllStudent().stream().map(StudentDTO ->
                        new StudentDTO(
                                StudentDTO.getId(),
                                StudentDTO.getFirstName(),
                                StudentDTO.getLastName(),
                                StudentDTO.getBirthday(),
                                StudentDTO.getGender(),
                                StudentDTO.getAddress(),
                                StudentDTO.getEmail(),
                                StudentDTO.getPhone(),
                                StudentDTO.getRegDate()
                        )).toList()
        ));

    }
}
