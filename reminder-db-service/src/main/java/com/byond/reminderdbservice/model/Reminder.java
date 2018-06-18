package com.byond.reminderdbservice.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "reminders")
@XmlRootElement
/*@NamedQueries({
    @NamedQuery(name = "Reminders.findAll", query = "SELECT r FROM Reminders r")
    , @NamedQuery(name = "Reminders.findById", query = "SELECT r FROM Reminders r WHERE r.id = :id")
    , @NamedQuery(name = "Reminders.findByName", query = "SELECT r FROM Reminders r WHERE r.name = :name")
    , @NamedQuery(name = "Reminders.findByDate", query = "SELECT r FROM Reminders r WHERE r.date = :date")
    , @NamedQuery(name = "Reminders.findByIsComplete", query = "SELECT r FROM Reminders r WHERE r.isComplete = :isComplete")})
    */
public class Reminder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "isComplete")
    private Boolean isComplete;
    @Column(name = "userid")
    private Integer userid;

    

    public Reminder() {
    }

    public Reminder(Integer id) {
        this.id = id;
    }

    public Reminder(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reminder)) {
            return false;
        }
        Reminder other = (Reminder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Reminder [id=" + id + ", name=" + name + ", date=" + date + ", isComplete=" + isComplete + ", userid="
				+ userid + "]";
	}

    

    
    
}
