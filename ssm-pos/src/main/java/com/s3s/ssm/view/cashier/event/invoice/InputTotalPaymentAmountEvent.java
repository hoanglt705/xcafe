package com.s3s.ssm.view.cashier.event.invoice;

public class InputTotalPaymentAmountEvent {
    private String strInputTotalPaymentAmount;

    public InputTotalPaymentAmountEvent(String strInputTotalPaymentAmount) {
        this.strInputTotalPaymentAmount = strInputTotalPaymentAmount;
    }

    public String getStrInputTotalPaymentAmount() {
        return strInputTotalPaymentAmount;
    }

}
