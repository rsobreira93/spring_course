package br.com.sobreiraromulo.data.dto.security;


import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private String fullname;

    public AccountCredentialsDTO() {}

    public AccountCredentialsDTO(String userName, String password, String fullname) {
        this.userName = userName;
        this.password = password;
        this.fullname = fullname;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialsDTO that = (AccountCredentialsDTO) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getFullname(), that.getFullname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getFullname());
    }
}