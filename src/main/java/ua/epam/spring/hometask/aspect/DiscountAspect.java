package ua.epam.spring.hometask.aspect;

import org.apache.commons.io.FileUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vladyslava_Hubenko on 7/4/2018.
 */
@Aspect
public class DiscountAspect {

    private String filename = "discounts.txt";
    private int givingDiscount = 0;

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.DiscountServiceImpl.getDiscount(..))")
    public void disMethod() {
    }


    @AfterReturning(value = "disMethod()", returning = "discount")
    public void setBirthDiscount(double discount) {
        if (discount != 0) {
            givingDiscount++;
        }
    }

    @After("execution(* *.menu(..))")
    public void writeToFile() {
        try {
            String s = "Discount was given - " + givingDiscount;
            FileUtils.writeStringToFile(new File(filename), s, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
