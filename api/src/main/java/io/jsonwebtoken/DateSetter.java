package io.jsonwebtoken;

public interface DateSetter {
    boolean accepts(Class<?> dateClass);

    long getDateAsSeconds(Object date);
}
