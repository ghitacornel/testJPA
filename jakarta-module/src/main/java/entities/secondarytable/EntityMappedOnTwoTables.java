package entities.secondarytable;

import jakarta.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
@SecondaryTable(name = "EMPLOYEE_DETAILS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "EMPLOYEE_ID"))
public class EntityMappedOnTwoTables {

    @Id
    private int id;

    @Column(name = "FIRST_NAME")// default table is the primary table
    private String firstName;

    @Column(name = "LAST_NAME", table = "EMPLOYEE")
    private String lastName;

    @Column(name = "EMAILID", table = "EMPLOYEE_DETAILS")
    private String email;

    @Column(table = "EMPLOYEE_DETAILS")// use default column name for the targeted secondary table
    private double salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
