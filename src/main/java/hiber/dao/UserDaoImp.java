package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {

      String HQL="FROM User u JOIN car c ON u.car_id=c.id WHERE c.model=:model and c.series =:series";
      //String HQL="FROM Car car LEFT OUTER JOIN FETCH car.user WHERE car.model=: paramModel and car.series =: paramSeries";
      //Car car = sessionFactory.getCurrentSession().createQuery(HQL, Car.class).setParameter("id", 1).uniqueResult();
      Query query = sessionFactory.getCurrentSession().createQuery(HQL, Car.class);
              query.setParameter("model", model);
      query.setParameter("series", series);
      return (User) query.getResultList().get(0);
   }

}
