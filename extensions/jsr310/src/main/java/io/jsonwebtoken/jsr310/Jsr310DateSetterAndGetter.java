package io.jsonwebtoken.jsr310;

import io.jsonwebtoken.DateGetter;
import io.jsonwebtoken.DateSetter;
import io.jsonwebtoken.RequiredTypeException;

public final class Jsr310DateSetterAndGetter implements DateSetter, DateGetter {

    private Jsr310DateSetterAndGetter() throws Jsr310PackageNotPresent {
        if (Package.getPackage("java.time") == null) {
            throw new Jsr310PackageNotPresent();
        }
    }

    @Override
    public boolean accepts(Class<?> dateClass) {
        return java.time.Instant.class.isAssignableFrom(dateClass) ||
                java.time.OffsetDateTime.class.isAssignableFrom(dateClass) ||
                java.time.ZonedDateTime.class.isAssignableFrom(dateClass);
    }

    @Override
    public long getDateAsSeconds(Object date) {
        return (long) ((java.time.temporal.TemporalAccessor) date).getLong(java.time.temporal.ChronoField.INSTANT_SECONDS);
    }

    @Override
    public boolean generates(Class<?> dateClass) {
        return dateClass.isAssignableFrom(java.time.Instant.class) ||
                dateClass.isAssignableFrom(java.time.OffsetDateTime.class) ||
                dateClass.isAssignableFrom(java.time.ZonedDateTime.class);
    }

    @Override
    public <T> T getSecondsAsDate(long seconds, Class<T> dateClass) {
        if (!generates(dateClass)) {
            throw new IllegalArgumentException();
        }

        java.time.Instant instant = java.time.Instant.ofEpochSecond(seconds);
        if (dateClass.isAssignableFrom(java.time.Instant.class)) {
            return (T) instant;
        } else if (dateClass.isAssignableFrom(java.time.OffsetDateTime.class)) {
            java.time.ZoneId systemDefaultZoneId = java.time.ZoneId.systemDefault();
            return (T) java.time.OffsetDateTime.ofInstant(instant, systemDefaultZoneId);
        } else if (dateClass.isAssignableFrom(java.time.ZonedDateTime.class)) {
            java.time.ZoneId systemDefaultZoneId = java.time.ZoneId.systemDefault();
            return (T) java.time.ZonedDateTime.ofInstant(instant, systemDefaultZoneId);
        }
        throw new RequiredTypeException("Unsupported date class " + dateClass + " requested");
    }
}
