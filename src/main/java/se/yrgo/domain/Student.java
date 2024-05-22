package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Objects;

//@SecondaryTable(name="TBL_ADDRESS")
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //This line is optional
    private int id;
    private String enrollmentID;
    private String name;
    private String tutorName; // This will become a class soon
    @Column(name="NUM_COURSES")
    private int numberOfCourses;


//    @ManyToOne
//    @JoinColumn(name="TUTOR_FK")
//    private Tutor tutor;
@Embedded
private Address address;

    public Student(String name, int numberOfCourses) {
        this.name = name;
        this.numberOfCourses = numberOfCourses;
    }

    public Student(String name, String enrollmentID, String street, String city,
                   String zipCode){
        this.name = name;
        this.enrollmentID= enrollmentID;
        this.address = new Address(street,city,zipCode);
    }

    public Student() {
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String toString() {
        return String.format("Name: %s%n Live at: %s%n", name,address);
    }
}
