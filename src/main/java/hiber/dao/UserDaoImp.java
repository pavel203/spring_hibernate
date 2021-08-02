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

      //String HQL="FROM Car car LEFT OUTER JOIN FETCH car.user WHERE car.id=: id";
      String HQL="FROM Car car LEFT OUTER JOIN FETCH car.user WHERE car.model=: paramModel and car.series =: paramSeries";
      //Car car = sessionFactory.getCurrentSession().createQuery(HQL, Car.class).setParameter("id", 1L).uniqueResult();
      Car car = sessionFactory.getCurrentSession().createQuery(HQL, Car.class).setParameterList(model, series).uniqueResult();
      return car.getUser();
   }

}
