package com.company.template.client;

/**
 * @author Idan Rozenfeld
 */
public final class EnumUtils {

    private EnumUtils() {
    }

    public static <T, E extends Enum<E> & IdentifierType<T>> E getByValue(final Class<E> enumClass, T value) {
        for (final E e : enumClass.getEnumConstants()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }

        throw new IllegalArgumentException("Wrong value " + value.toString() + " for type " + enumClass.getName());
    }

}
