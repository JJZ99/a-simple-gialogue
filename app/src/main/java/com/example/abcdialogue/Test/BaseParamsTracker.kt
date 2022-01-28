package com.example.abcdialogue.Test

import java.util.Random
fun main() {
    var a = "aaaa"
    var map1 = HashMap<String,String>().apply {
        put("1234",a)
        put("2345","bbbb")
        put("3456","cccc")
        put("4567","dddd")
    }
    var map2 = map1.clone()
    println(map1.toString())
    a = "rrrr"
    (map2 as HashMap<String,String>)["1234" ]= a
    println(map2.toString())


}

object BaseParamsTracker {
    var mTimes = 10000
    var mBaseParamCount = 300
    var mBuildParamCount = 70
    var mKeyLength = 18
    var mValueLength = 18

    val mBaseParams = HashMap<String, String>()
    fun build(baseParamCount: Int = 300, buildParamCount: Int = 70, times: Int = 10000) {


    }
    fun getRandomString(minLength: Int, maxLength: Int): String {
        val random = Random()
        return "1"


    }

//    public static String getRandomString(int minLength, int maxLength) {
//        // 定义随机数生成器，用来产生长度和字符
//        Random random = new Random();
//
//        // 获得字符串的长度（限定在最大长度内）
//        int length = (int) (maxLength * random.nextDouble());
//
//        // 限制字符串的最小长度
//        length = length > minLength ? length : minLength;
//
//        // 定义字符数组存储生成的字符
//        char[] charArray = new char[length];
//
//        // 生成length个字符，放入charArray中
//        for (int i = 0; i < length; i++) {
//
//            // 生成一个32-126之间的整数，代表了ASCII码表中常用的字符
//            int tempInt = 32 + (int) (94 * random.nextDouble());
//
//            // 将整数转为字符型数据储存
//            charArray[i] = (char) (tempInt);
//        }
//
//        // 将字符数组转为字符串返回
//        return new String(charArray);
//    }
}