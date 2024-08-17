package com.imooc.log.stack.chapter3;

/**
 * <h1>只有在必要的时候才使用异常</h1>
 * */
public class EssentialException {

    // 第一种情况
    public static class Imooc {

        private final String[] courses = {"广告", "优惠券"};

        public String course(int index) {

//            try {
//                return courses[index - 1];
//            } catch (ArrayIndexOutOfBoundsException ex) {
//                return null;
//            }
            return index > courses.length ? null : courses[index - 1];
        }
    }

    // 第二种情况
    public static void sum(int[] nums) {

        int _sum = 0;

//        try {
//            int i = 0;
//            while (true) {
//                _sum += nums[i++];
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//            System.out.println("_sum is: " + _sum);
//        }

        for (int i = 0; i != nums.length; ++i) {
            _sum += nums[i];
        }

        System.out.println("_sum is: " + _sum);
    }

    // 第三种情况
    public static void printCourseLen(int index) {

        String course = new Imooc().course(index);

        try {
            System.out.println(course.length());
        } catch (NullPointerException ex) {
            System.out.println(0);
        }

        // 两种方式去对待这样的问题
        // 1. 与第一种情况类似, 判断 course
        // 2. 直接使用 course, 不做任何判断, 让异常尽早抛出
    }
}
