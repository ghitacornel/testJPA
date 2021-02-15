package strange.manytomanyhierachy;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TEST_manytomanyhierachy extends TransactionalSetup {

    Teacher teacher1;
    Teacher teacher2;
    Student student1;
    StudentDistanta student2;
    Student student3;
    Student student4;
    StudentDistanta student5;

    @Before
    public void buildModel() {
        teacher1 = new Teacher();
        teacher1.setId(1);
        teacher2 = new Teacher();
        teacher2.setId(2);

        student1 = new Student();
        student1.setId(1);
        student2 = new StudentDistanta();
        student2.setId(2);
        student3 = new Student();
        student3.setId(3);
        student4 = new Student();
        student4.setId(4);
        student5 = new StudentDistanta();
        student5.setId(5);

    }

    @Test
    public void testPersist() {
        em.persist(teacher1);
        em.persist(teacher2);
        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.persist(student4);
        em.persist(student5);

        teacher1.getStudents().add(student1);
        teacher1.getStudents().add(student2);
        student3.getTeachers().add(teacher2);
        student4.getTeachers().add(teacher2);
        student5.getTeachers().add(teacher2);

        flushAndClear();
        Teacher teacher1 = em.find(Teacher.class, 1);
        System.out.println(teacher1);
        System.out.println(teacher1.getStudents());
        System.out.println(teacher1.getStudentsDistanta());
        System.out.println(em.find(Teacher.class, 2));
        System.out.println(em.find(Teacher.class, 2).getStudents());
        System.out.println(em.find(Teacher.class, 2).getStudentsDistanta());

        System.out.println(em.find(Student.class, 1));
        System.out.println(em.find(Student.class, 1).getTeachers());
        System.out.println(em.find(Student.class, 1).getTip());
        System.out.println(em.find(Student.class, 2));
        System.out.println(em.find(Student.class, 2).getTeachers());
        System.out.println(em.find(Student.class, 2).getTip());

        System.out.println(em.find(Student.class, 3));
        System.out.println(em.find(Student.class, 3).getTeachers());
        System.out.println(em.find(Student.class, 3).getTip());
        System.out.println(em.find(Student.class, 4));
        System.out.println(em.find(Student.class, 4).getTeachers());
        System.out.println(em.find(Student.class, 4).getTip());
        System.out.println(em.find(Student.class, 5));
        System.out.println(em.find(Student.class, 5).getTeachers());
        System.out.println(em.find(Student.class, 5).getTip());

    }
}
