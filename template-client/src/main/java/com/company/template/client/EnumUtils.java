/**
 * Copyright (C) 2018 user name (user@email.com) the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.company.template.client;

import java.util.function.BiPredicate;

/**
 * @author Idan Rozenfeld
 */
public final class EnumUtils {

    private EnumUtils() {
    }

    public static <T, E extends Enum<E> & IdentifierType<T>> E getByValue(final Class<E> enumClass, T value) {
        return getByValue(enumClass, value, Object::equals);
    }

    public static <E extends Enum<E> & IdentifierType<String>> E getByValueIgnoreCase(final Class<E> enumClass, String value) {
        return getByValue(enumClass, value, String::equalsIgnoreCase);
    }

    public static <T, E extends Enum<E> & IdentifierType<T>> E getByValue(final Class<E> enumClass, T value, BiPredicate<T, T> predicate) {
        for (final E e : enumClass.getEnumConstants()) {
            if (predicate.test(e.getValue(), value)) {
                return e;
            }
        }

        throw new IllegalArgumentException("Wrong value " + value.toString() + " for type " + enumClass.getName());
    }

}
