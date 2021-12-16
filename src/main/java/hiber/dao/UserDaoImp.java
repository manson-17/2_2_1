package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().saveOrUpdate(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByModelAndSeries(String model, int series) {

      Session session = sessionFactory.getCurrentSession();

//      Метод курильщика, тут изначально я получал User по Car. Потом понял, что все равно надо будет переделывать
//      поолучение по модели и серии для начала получил
//      Car car = session.createQuery("from Car where model =:carModel and series =:carSeries", Car.class)
//              .setParameter("carModel", model)
//              .setParameter("carSeries", series)
//              .uniqueResult();
//
//      User user = session.createQuery("from User where car_id = :carId", User.class)
//              .setParameter("carId", session.get(Car.class, car.getId()))
//              .uniqueResult();


      User user = session.createQuery("from User user where user.car.model = :carModel and user.car.series = :carSeries", User.class)
              .setParameter("carModel", model)
              .setParameter("carSeries", series)
              .uniqueResult();


     return user;

   }
}
