package com.conarflib.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ObjectUtils {

    /**
     * Check if array is empty.
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Return if object is empty.
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Optional) {
            return !((Optional<?>) object).isPresent();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        } else {
            return object instanceof Map ? ((Map<?, ?>) object).isEmpty() : false;
        }
    }

}