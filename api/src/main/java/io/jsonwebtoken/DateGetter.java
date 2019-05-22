package io.jsonwebtoken;

public interface DateGetter {
    boolean generates(Class<?> dateClass);

    <T> T getSecondsAsDate(long seconds, Class<T> dateClass);
}
