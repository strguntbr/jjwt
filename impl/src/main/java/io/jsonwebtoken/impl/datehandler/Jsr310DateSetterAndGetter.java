package io.jsonwebtoken.impl.datehandler;

import io.jsonwebtoken.DateGetter;
import io.jsonwebtoken.DateSetter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public final class Jsr310DateSetterAndGetter implements DateSetter, DateGetter {

    public static final Jsr310DateSetterAndGetter instance;

    static {
        instance = createInstance();
    }

    private static Jsr310DateSetterAndGetter createInstance() {
        try {
            return new Jsr310DateSetterAndGetter();
        } catch (Jsr310ClassesNotPresent e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private final Class<?> Instant;
    private final Class<?> OffsetDateTime;
    private final Class<?> ZonedDateTime;

    private final Method TemporalAccessor_getLong;
    private final Method Instant_ofEpochSecond;
    private final Method OffsetDateTime_ofInstant;
    private final Method ZonedDateTime_ofInstant;
    private final Method ZoneId_systemDefault;
    private final Object ChronoField_INSTANT_SECONDS;

    private Jsr310DateSetterAndGetter() throws Jsr310ClassesNotPresent {
        Class<?> TemporalAccessor;
        try {
            TemporalAccessor = Class.forName("java.time.temporal.TemporalAccessor");
        } catch (ClassNotFoundException e) {
            throw new Jsr310ClassesNotPresent();
        }
        try {
            Class<?> ChronoField = Class.forName("java.time.temporal.ChronoField");
            Class<?> TemporalField = Class.forName("java.time.temporal.TemporalField");
            Class<?> ZoneId = Class.forName("java.time.ZoneId");

            Instant = Class.forName("java.time.Instant");
            OffsetDateTime = Class.forName("java.time.OffsetDateTime");
            ZonedDateTime = Class.forName("java.time.ZonedDateTime");

            TemporalAccessor_getLong = TemporalAccessor.getMethod("getLong", TemporalField);
            Instant_ofEpochSecond = Instant.getMethod("ofEpochSecond", long.class);
            OffsetDateTime_ofInstant = OffsetDateTime.getMethod("ofInstant", Instant, ZoneId);
            ZonedDateTime_ofInstant = ZonedDateTime.getMethod("ofInstant", Instant, ZoneId);
            ZoneId_systemDefault = ZoneId.getMethod("systemDefault");
            ChronoField_INSTANT_SECONDS = ChronoField.getField("INSTANT_SECONDS").get(null);
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean accepts(Class<?> dateClass) {
        return Instant.isAssignableFrom(dateClass) ||
                OffsetDateTime.isAssignableFrom(dateClass) ||
                ZonedDateTime.isAssignableFrom(dateClass);
    }

    @Override
    public long getDateAsSeconds(Object date) {
        try {
            return (long) TemporalAccessor_getLong.invoke(date, ChronoField_INSTANT_SECONDS);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean generates(Class<?> dateClass) {
        return dateClass.isAssignableFrom(Instant) ||
                dateClass.isAssignableFrom(OffsetDateTime) ||
                dateClass.isAssignableFrom(ZonedDateTime);
    }

    @Override
    public <T> T getSecondsAsDate(long seconds, Class<T> dateClass) {
        if (!generates(dateClass)) {
            throw new IllegalArgumentException();
        }

        try {
            Object instant = Instant_ofEpochSecond.invoke(null, seconds);
            if (dateClass.isAssignableFrom(Instant)) {
                return (T) instant;
            } else if (dateClass.isAssignableFrom(OffsetDateTime)) {
                Object systemDefaultZoneId = ZoneId_systemDefault.invoke(null);
                return (T) OffsetDateTime_ofInstant.invoke(null, instant, systemDefaultZoneId);
            } else if (dateClass.isAssignableFrom(ZonedDateTime)) {
                Object systemDefaultZoneId = ZoneId_systemDefault.invoke(null);
                return (T) ZonedDateTime_ofInstant.invoke(null, instant, systemDefaultZoneId);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }
}
