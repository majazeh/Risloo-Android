package com.mre.ligheh.API;



import java.lang.reflect.InvocationTargetException;


public class Exceptioner {
    public static Class External;

    public static void make(Response callback, Object object) {
        if (External != null) {
            try {
                External.getDeclaredConstructor(Response.class,Object.class).newInstance(callback,object);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        } else {
            new onFailureInitializer(callback,object);
        }
    }
}
