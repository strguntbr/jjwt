package io.jsonwebtoken.impl;

import io.jsonwebtoken.DateGetter;
import io.jsonwebtoken.DateSetter;

import java.util.Date;

public final class LegacyDateSetterAndGetter implements DateSetter, DateGetter {

    public static final LegacyDateSetterAndGetter instance = new LegacyDateSetterAndGetter();

    private LegacyDateSetterAndGetter() {
    }

    @Override
    public boolean generates(Class<?> dateClass) {
        return dateClass.isAssignableFrom(Date.class);
    }

    @Override
    public <T> T getSecondsAsDate(long seconds, Class<T> dateClass) {
        if (!generates(dateClass)) {
            throw new IllegalArgumentException();
        }
        return (T) new Date(seconds * 1000);
    }

    @Override
    public boolean accepts(Class<?> dateClass) {
        return Date.class.isAssignableFrom(dateClass);
    }

    @Override
    public long getDateAsSeconds(Object date) {
        if (!accepts(date.getClass())) {
            throw new IllegalArgumentException();
        }
        return ((Date) date).getTime() / 1000;
    }
}
