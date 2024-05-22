
import jakarta.persistence.*;

import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //Uppgift 1
        TypedQuery<Student> q1 = em.createQuery(
                "SELECT s FROM Tutor t JOIN t.teachingGroup s JOIN t.subjectsToTeach subj " +
                        "WHERE lower(subj.subjectName) = 'science'",
                Student.class
        );
        List<Student> myStudents = q1.getResultList();
        for (Student student : myStudents) {
            System.out.println(student);
        }

        //Uppgift 2
        List<Object[]> q3 = em.createQuery("SELECT s.name, t.name FROM Tutor t JOIN t.teachingGroup s")
                .getResultList();
        for (Object[] obj : q3) {
            System.out.println("Student Name: " + obj[0] + "\nTutor Name: " + obj[1]);

        }
        //Uppgift 3
        double averageSemester = (Double) em.createQuery("select AVG(sub.numberOfSemesters) FROM Subject sub")
                .getSingleResult();
        System.out.println("Average Semester Count: " + averageSemester);

        //Uppgift 4
        int maxSalary = (int) em.createQuery("select MAX(t.salary) FROM Tutor t").getSingleResult();
        System.out.println("Max Salary: "+maxSalary);

        //Uppgift 5
        List<Tutor> salaryResult = em.createNamedQuery("searchByName", Tutor.class)
                .setParameter("highSalary",10000).getResultList();
        for(Tutor tutor : salaryResult){
            System.out.println("Tutors with salary over 10k: \n"+tutor);
        }
        tx.commit();
        em.close();
    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
        em.persist(mathematics);
        em.persist(science);
        em.persist(programming);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);
        t1.addSubjectsToTeach(science);


        Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
        t2.addSubjectsToTeach(mathematics);
        t2.addSubjectsToTeach(science);

        // This tutor is the only tutor who can teach History
        Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
        t3.addSubjectsToTeach(programming);

        em.persist(t1);
        em.persist(t2);
        em.persist(t3);


        t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 1", "1212");
        t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

        tx.commit();
        em.close();
    }


}
