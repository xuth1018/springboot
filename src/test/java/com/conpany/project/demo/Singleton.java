package com.conpany.project.demo;

public class Singleton {

    private Singleton() {

    }

    private static volatile Singleton singleton;

    private static class SingletonInstance{
        private static final Singleton SING = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonInstance.SING;
    }

}

