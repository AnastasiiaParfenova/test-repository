package ru.stqa.training.selenium.tests;

import org.junit.Test;


public class AddAndDeleteTest extends TestBase{

    @Test
    public void test(){
        int numberOfProducts = 3;

        app.addProductToCart(numberOfProducts);
        app.removeProductFromCart(numberOfProducts);
    }

}
