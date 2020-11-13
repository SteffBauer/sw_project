package de.othr.sw.bank.entity;

public class SessionRequest {
    private String password;
    private String username;

    public SessionRequest() {}

    public SessionRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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
        String s = this.username +this.password;
        return s.hashCode();
    }
}
