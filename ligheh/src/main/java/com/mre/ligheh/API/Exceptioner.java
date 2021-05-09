package com.mre.ligheh.API;



import java.lang.reflect.InvocationTargetException;


public class Exceptioner {
    public static Class External;

    public static void make(Object object) {
        if (External != null) {
            try {
                External.getDeclaredConstructor(Object.class).newInstance(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        } else {
            new onFailureInitializer(object);
        }
    }
}
