package com.sys1yagi.swipe.core.util;

import java.util.List;

public class ListUtils {

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
