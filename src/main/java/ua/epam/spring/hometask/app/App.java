package ua.epam.spring.hometask.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.controller.MainController;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigClass.class);
        MainController controller = (MainController) context.getBean("controller");
        controller.menu();
    }

}
