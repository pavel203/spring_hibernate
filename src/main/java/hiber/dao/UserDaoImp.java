package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
   public Optional<User> getUserByCar(String model, int series) {
      String HQL="SELECT user FROM User AS user WHERE user.car.model =:paramModel and user.car.series =:paramSeries";
      User user = sessionFactory.getCurrentSession().createQuery(HQL, User.class).
              setParameter("paramModel", model).setParameter("paramSeries", series).uniqueResult();
      return user == null ? Optional.empty() : Optional.of(user);
   }

}
