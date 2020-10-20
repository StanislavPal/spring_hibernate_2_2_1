package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("ВАЗ", 2114);
      Car car2 = new Car("ВАЗ", 2110);
      Car car3 = new Car("ВАЗ", 2108);
      Car car4 = new Car("ВАЗ", 2109);

      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      User user3 = new User("User3", "Lastname3", "user3@mail.ru");
      User user4 = new User("User4", "Lastname4", "user4@mail.ru");

      user1.setCar(car1);
      user2.setCar(car2);
      user3.setCar(car3);
      user4.setCar(car4);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+user.getCar().getModel() + " " + user.getCar().getSeries());
         System.out.println();
      }

      System.out.println("===============Users by his car=================");

      users = userService.getUserByCar("ВАЗ", 2110);
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+user.getCar().getModel() + " " + user.getCar().getSeries());
         System.out.println();
      }
      context.close();
   }
}

/*
1. Скачать и установить jdk 1.8 иначе ошибки java.lang.ClassNotFoundException: javax.xml.bind.JAXBException
в более поздних версиях, т.к. выпилили библиотеки.
2. Обновить МайСКЛ коннектор в пом файле, т.к. сервер уже не работал со старой версией.
3. Не пытаться решить задачу через связь классов Кар и Юзер через технологию Спринг связывания (@Entity + @Componet
+ @AutoWired) - так не делают, и это не работает. Связывание делать "вручную"
4. Не нужно городить огород ДАО/Сервайс для класса Кар. Хибернейт всю работу по мапингу класса в БД берёт на себя, через
связывающие анотации @OneToEne
5. Перебрал разные комбинации Query и способы передачи параметров прежде чем написал правильно запрос HQL
на возврат юзера по модели и серии его машины
 */
