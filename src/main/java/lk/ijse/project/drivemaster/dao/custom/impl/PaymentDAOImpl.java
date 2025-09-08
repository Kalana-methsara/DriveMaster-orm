package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.entity.Payment;


import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public boolean save(Payment payment) {
        return false;
    }

    @Override
    public boolean update(Payment payment) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
