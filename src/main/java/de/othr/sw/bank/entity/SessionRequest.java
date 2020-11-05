package de.othr.sw.bank.entity;

public class SessionRequest {
    private String password;
    private String iban;

    public SessionRequest() {}

    public SessionRequest(String password, String iban) {
        this.password = password;
        this.iban = iban;
    }

    public String getPassword() {
        return password;
    }

    public String getIban() {
        return iban;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(SessionRequest.class))
            return false;
        SessionRequest other=(SessionRequest)o;

        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode(){
        String s = this.iban+this.password;
        return s.hashCode();
    }
}
