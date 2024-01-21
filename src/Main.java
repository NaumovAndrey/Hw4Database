import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Course.class)
            .buildSessionFactory();

    public static void main(String[] args) {
        Course course = new Course("Java-разработчик", 36);

        addToTable(course); //добавить в таблицу

        List<Course> courseList = getData(); //показать все курсы (запрос всех данных)
        for (Course cours : courseList) System.out.println(cours);

        setData(1, 36); //изменить данные

        delData(2); //удалить по id
    }

    /**
     * метод добавления данных в таблицу
     * @param course
     */
    static void addToTable(Course course) {
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }

    /**
     * Получить все данные из таблицы
     */
    static List<Course> getData() {//5.38
        List courseList;
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            courseList = session.createQuery("from Course").getResultList();
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
        return courseList;
    }

    /**
     * Изменить данные (количество) по id
     * @param id
     * @param numberMonths количество
     */
    static void setData(int id, int numberMonths){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Course course = session.get(Course.class, id);
        course.setDuration(numberMonths);
        session.getTransaction().commit();
    }

    /**
     * Удалить по id
     * @param id
     */
    static void delData(int id){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Course course = session.get(Course.class, id);
        session.delete(course);
        session.getTransaction().commit();
    }
}