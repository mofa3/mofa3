///*
// * Dian.so Inc.
// * Copyright (c) 2016-2019 All Rights Reserved.
// */
//package io.github.mofa3.lang.bean;
//
//import io.github.mofa3.lang.util.JsonUtil;
//import io.github.mofa3.lang.util.bean.MofaBeanUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * TODO
// *
// * @author baizhang
// * @version: v 0.1 BeanTest.java, 2019-09-29 20:22 Exp $
// */
//public class BeanTest {
//
//    public static void main(String[] args) {
//
////        String sourcePath = BeanTest.class.getResource("/").getPath().split("common-lang")[0];
////        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, sourcePath + "common-lang/gen_code");
//
//        UserBean userBean1 = new UserBean();
//        userBean1.setId("1");
//        userBean1.setId1(11);
//        userBean1.setIds(new Integer[]{1, 2, 3});
//        userBean1.setIdss(new int[]{1, 2, 3, 4, 5, 6});
//        userBean1.setIdx(new long[]{1, 2, 3, 4, 5, 6});
//        userBean1.setName("张三");
//        userBean1.setXxInt(23);
//
//
//        User user1 = new User();
////        private Integer id;
////        private int id1;
////        private int[] ids;
////        private int[] idss;
////        private int[] idx;
////        private String name;
////        private String photo;
////        private String xx;
////        private String gender;
////        private int xInt;
////        private int xxInt;
////        private long xLong;
////        private LocalDateTime birthday;
//
//        MofaBeanUtil.copy(userBean1, user1);
////
//        System.out.println(JsonUtil.beanToJson(user1));
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", "11");
//        map.put("idss", new int[]{1, 2, 3, 4, 5, 6});
//        map.put("name", "李四");
//
//        user1 = new User();
//        MofaBeanUtil.copyNonNull(map, user1);
//        System.out.println(JsonUtil.beanToJson(user1));
//    }
//}