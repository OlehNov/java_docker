package com.vinsguru.util;

public class Demo {
    public static void main(String[] args){
        System.setProperty("browser", "chrome");
        Config.initialize();
        System.out.println(
                Config.get("selenium.grid.hubHost")
        );
    }
}
