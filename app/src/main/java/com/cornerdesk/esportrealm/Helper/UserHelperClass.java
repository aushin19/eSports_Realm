package com.cornerdesk.esportrealm.Helper;

public class UserHelperClass
{
    String Username,Password,Email,RefCode,RefBy, Participation, Last_check_in;


    public UserHelperClass() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRefCode() {
        return RefCode;
    }

    public void setRefCode(String refCode) {
        RefCode = refCode;
    }

    public String getRefBy() {
        return RefBy;
    }

    public void setRefBy(String refBy) {
        RefBy = refBy;
    }

    public String getParticipation() {
        return Participation;
    }

    public void setParticipation(String participation) {
        Participation = participation;
    }

    public String getLast_check_in() {
        return Last_check_in;
    }

    public void setLast_check_in(String last_check_in) {
        Last_check_in = last_check_in;
    }

    public UserHelperClass(String username, String password, String email, String refCode, String refBy, String participation, String last_check_in) {
        this.Username = username;
        this.Password = password;
        this.Email = email;
        this.RefCode = refCode;
        this.RefBy = refBy;
        this.Participation = participation;
        this.Last_check_in = last_check_in;
    }
}
