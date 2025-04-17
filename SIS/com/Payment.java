package SIS.com;

import java.util.*;

public class Payment {
    private String paymentId;
    private Student student;
    private double amount;
    private Date paymentDate;

    public Payment(String paymentId, Student student, double amount, Date paymentDate) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Student getStudent() {
        return student;
    }

    public double getPaymentAmount() {
        return amount;
    }
    public String getPaymentId() {
        return paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }
}
