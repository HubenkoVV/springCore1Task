package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Random;

/**
 * Created by Vladyslava_Hubenko on 7/4/2018.
 */
@Aspect
public class LuckyWinnerAspect {

    @Before("execution(* *.bookTickets(..))")
    public void bookMethod() {
        if (luckyMethod())
            System.out.println("You are so LUCKY. Price for tickets is 0$");
    }

    private boolean luckyMethod() {
        int randomNumber = new Random().nextInt(1000);
        return randomNumber % 10 == 0;
    }

}
