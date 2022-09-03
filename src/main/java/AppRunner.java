import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.UserRepository;
import com.study.spring.ioc.Container;
import com.study.spring.service.UserService;

public class AppRunner {
    public static void main(String[] args) {
        // 1
        // тут ми бачимо що AppRunner контролює як створювати всі потрібні залежності і інджектити у потрібні об'єкти
        
//            ConnectionPool connectionPool = new ConnectionPool();
//            UserRepository userRepository = new UserRepository(connectionPool);
//            CompanyRepository companyRepository = new CompanyRepository(connectionPool);
//            UserService userService = new UserService(userRepository, companyRepository);

        // тепер ми можемо використовувати функціонал userService
        
        
        /*
        чим більша логіка, тим тяжче керувати створенням і впровадженням потрібних залежностей

            тому тут нам допомагає IoC і його реалізація DI якщо ми говоримо про Spring/Spring Boot
            
            є так званий контейнер де спрінг поскладає всі БІНИ (тобто всі наші об'єкти)
            і, наприклад, тут в методі МЕЙН ми просто будемо брати з цього контейнера потрібні нам об'єкти,
            тобто ми тепер не керуємо створенням потрібного об'єкту, ми передаємо це СПРІНГУ
            
         */
        // 2
        Container container = new Container();

        /*
            ми можемо взяти всі потрібні біни, але якщо нам потрібно конкретно наш userService,
            то ми просто беремо його, а всі потрібні залежності і створення їх ми передаємо на спрінг і IoC та DI
         */
        
//        ConnectionPool connectionPool = container.get(ConnectionPool.class);
//        UserRepository userRepository = container.get(UserRepository.class);
//        CompanyRepository companyRepository = container.get(CompanyRepository.class);
        UserService userService = container.get(UserService.class);


    }
}
