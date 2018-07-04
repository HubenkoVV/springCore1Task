package ua.epam.spring.hometask.aspect;

import org.apache.commons.io.FileUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vladyslava_Hubenko on 7/4/2018.
 */
@Aspect
public class CounterAspect {

    private String filename = "priceCalling.txt";
    private int priceCalling = 0;

    @Pointcut("execution(* *.getTicketsPrice(..))")
    public void priceMethod() {
    }

    @After("priceMethod()")
    public void priceCalling() {
        priceCalling++;
    }

    @After("execution(* *.menu(..))")
    public void writeToFile() {
        try {
            FileUtils.writeStringToFile(new File(filename), "Price calling - " + priceCalling, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
