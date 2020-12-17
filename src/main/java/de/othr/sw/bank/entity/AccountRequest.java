package de.othr.sw.bank.entity;

public class AccountRequest {

    private String username;
    private String iban;
    private String reason;
    private long monthlyIncome;

    public AccountRequest() {}

    public AccountRequest(String username,String iban) {
        this.username = username;
        this.iban = iban;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(long monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(AccountRequest.class))
            return false;
        AccountRequest other=(AccountRequest)o;

        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode(){
        String s = this.username +this.iban;
        return s.hashCode();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
