package com.nanhang.mybatis_plus.style.factory;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * @author: immortal
 * @CreateDate: 2021/4/20 9:19
 * @Description:
 */
@FunctionalInterface
public interface IPay {


    Result pay();


    static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
        Objects.requireNonNull(cmp);
        return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }

}

