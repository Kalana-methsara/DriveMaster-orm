package lk.ijse.project.drivemaster.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.PaymentBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PaymentDTO, LocalDateTime> colDateTime;

    @FXML
    private TableColumn<PaymentDTO, BigDecimal> colPaidAmount;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentStatus;

    @FXML
    private TableColumn<PaymentDTO, String> colReceiptNumber;

    @FXML
    private TableColumn<PaymentDTO, String> colStudentId;

    @FXML
    private Label lblDateTime;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblReference;

    @FXML
    private Label lblStudentId;

    @FXML
    private TableView<PaymentDTO> tableView;

    @FXML
    private TextField textAmount;

    @FXML
    private ChoiceBox<String> textMethod;

    @FXML
    private ChoiceBox<String> textStatus;

    private final PaymentBO paymentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.PAYMENT);


    @FXML
    void onActionDelete(ActionEvent event) {
        var selectedPayment = tableView.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            showAlert(Alert.AlertType.WARNING, "No Payment Selected", "Please select a payment before proceeding.");
            return;
        }
        boolean confirmed = showConfirmation("Confirm Delete", "Are you sure you want to delete this payment?");
        if (confirmed) {
            try {
                String paymentId = lblPaymentId.getText();
                boolean isDeleted = paymentBO.deletePayment(paymentId);

                if (isDeleted) {
                    clearPaymentFields();
                    loadPaymentTable();
//                    showAlert(Alert.AlertType.INFORMATION, "Success", "Course deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "No payment found with this ID.");
                }
            } catch (InUseException e) {
                showAlert(Alert.AlertType.ERROR, "In Use", "This payment is currently in use and cannot be deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete payment. Please try again!");
            }
        }
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        var selectedPayment = tableView.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            showAlert(Alert.AlertType.WARNING, "No Payment Selected", "Please select a payment before proceeding.");
            return;
        }

        String paymentId = lblPaymentId.getText();
        String studentId = lblStudentId.getText();

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(lblDateTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Date", "The date format is invalid.");
            return;
        }

        String reference = lblReference.getText();

        BigDecimal amount;
        try {
            amount = new BigDecimal(textAmount.getText().trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert(Alert.AlertType.WARNING, "Invalid Input", "Amount must be greater than 0.");
                return;
            }
        } catch (NumberFormatException | NullPointerException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid payment amount.");
            return;
        }

        String method = textMethod.getValue();
        String status = textStatus.getValue();

        if (method == null || method.isBlank() || status == null || status.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please fill all required fields.");
            return;
        }

        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentId, amount, method, dateTime, status, reference);

        try {
            paymentBO.updatePayment(paymentDTO);
            clearPaymentFields();
            loadPaymentTable();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment updated successfully!");
        } catch (NotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Payment Not Found", "No payment found with this ID!");
        } catch (DuplicateException e) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Payment", "A payment with this ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Update", "Failed to update payment. Please try again!");
        }
    }


    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;");
        alert.showAndWait();
    }

    private boolean showConfirmation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.getDialogPane().setStyle(
                "-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;"
        );

        // Show and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    @FXML
    void onRefresh(MouseEvent event) {
        clearPaymentFields();
    }

    private void clearPaymentFields() {
        lblPaymentId.setText("");
        lblStudentId.setText("");
        lblDateTime.setText("");
        lblReference.setText("");
        textAmount.setText("");
        textMethod.setValue(null);
        textStatus.setValue(null);
        tableView.getSelectionModel().clearSelection();

    }

    @FXML
    void setData(MouseEvent event) {
        PaymentDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblPaymentId.setText(selected.getId());
            lblStudentId.setText(selected.getStudentId());
            lblDateTime.setText(selected.getFormattedCreatedAt());
            lblReference.setText(selected.getReference());
            textAmount.setText(selected.getAmount().toString());
            textMethod.setValue(selected.getMethod());
            textStatus.setValue(selected.getStatus());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("formattedCreatedAt"));
        colReceiptNumber.setCellValueFactory(new PropertyValueFactory<>("reference"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            loadPaymentTable();
            clearPaymentFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        textMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Online"));
        textStatus.setItems(FXCollections.observableArrayList("PENDING", "COMPLETE", "FAILED"));

    }

    private void loadPaymentTable() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                paymentBO.getAllPayments().stream().map(PaymentDTO ->
                        new PaymentDTO(
                                PaymentDTO.getId(),
                                PaymentDTO.getStudentId(),
                                PaymentDTO.getAmount(),
                                PaymentDTO.getMethod(),
                                PaymentDTO.getCreatedAt(),
                                PaymentDTO.getStatus(),
                                PaymentDTO.getReference()
                        )).toList()
        ));
    }
}
