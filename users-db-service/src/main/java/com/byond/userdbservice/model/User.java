package com.byond.userdbservice.model;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "users")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "username")
    private String username;
    @Column(name = "hashpassword")
    private String hashpassword;
    @Column(name = "isActive")
    private Boolean isActive;
	

	public User() {
		super();
	}

	public User(Integer id, @Size(max = 100) String username, String hashpassword, Boolean isActive) {
		super();
		this.id = id;
		this.username = username;
		this.hashpassword =  hashpassword;
		this.isActive = isActive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashpassword() {
		return hashpassword;
	}

	public void setHashpassword(String hashpassword) {
		this.hashpassword = hashpassword;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", hashpassword=" + hashpassword + ", isActive=" + isActive
				+ "]";
	}

	
   

	
	

    
    
    
}
