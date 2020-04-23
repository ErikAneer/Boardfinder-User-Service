package Boardfinder.Auth.Domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Entity class for storing application users and their role in the database. 
 * @author Erik
 */
@Entity
public class BoardfinderUser implements Serializable {
    
    private final static long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @NotNull
    @Column(unique = true)
    private String username;
    
    private String password;
        
    private String role;

    public BoardfinderUser() {
    }

    public BoardfinderUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public BoardfinderUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    } 
}
