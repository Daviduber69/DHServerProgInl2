package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.*;

import jakarta.persistence.CascadeType;

@Entity
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tutorId;
    private String name;
    private int salary;
    //    @OneToMany
//    @MapKey(name="enrollmentID")
//    private Map<String,Student> teachingGroup;
//    @ManyToMany(mappedBy="tutors")
//    private Set<Subject> subjectsToTeach;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TUTOR_FK")
    private Set<Student> teachingGroup;

    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;

    public Tutor() {
    }

    public Tutor(String tutorId, String name, int salary) {
        this.tutorId = tutorId;
        this.name = name;
        this.salary = salary;
        this.teachingGroup = new HashSet<>();
        this.subjectsToTeach = new HashSet<Subject>();


    }

    public void createStudentAndAddtoTeachingGroup(String studentName,
                                                   String enrollmentID, String street, String city,
                                                   String zipcode) {
        Student student = new Student(studentName, enrollmentID,
                street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);
    }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public Set<Student> getTeachingGroup() {
        Set<Student> unmofifiable = Collections.unmodifiableSet(this.teachingGroup);
        return unmofifiable;
    }

    public Set<Subject> getSubjects() {
        return subjectsToTeach;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public String toString() {
        return String.format("Tutor ID: %s%nName: %s%nSalary: %d%n", tutorId, name, salary);
    }
}
