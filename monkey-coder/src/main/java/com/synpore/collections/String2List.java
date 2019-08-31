package com.synpore.collections;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class String2List {


    //jdk1.7以下原生方式//
    public static List<Integer> str2Listrimitive(String ids, String delimeter) {
        List<Integer> list = new ArrayList<Integer>();
        if (StringUtils.isEmpty(ids))
            return list;
        String[] strings = ids.split(",");
        for (String et : strings) {
            list.add(Integer.parseInt(et));
        }
        return list;
    }

    //jdk1.7以下版本使用集合工具类//
    public static List<Integer> str2List(String ids, String delimeter) {
        List<String> idsStringList = Arrays.asList(ids.split(delimeter));
        List<Integer> idsList = new ArrayList();
        CollectionUtils.collect(idsStringList, new Transformer() {
            public Object transform(Object o) {
                return Integer.valueOf(o.toString());
            }
        }, idsList);
        return idsList;
    }

    //使用jdk1.8流
    public static List<Integer> str2ListAbove8(String ids, String delimeter) {
        return Arrays.asList(ids.split(":")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
    }

    //jdk自带字符串拼接
    public static String listJoin2String(List<String> list, String delimeter) {
        return String.join(delimeter, list);
    }

    //工具类字符串拼接
    public static String listJoin2StringUtils(List<Long> list, String delimeter) {
        return StringUtils.join(list, delimeter);
    }


}
