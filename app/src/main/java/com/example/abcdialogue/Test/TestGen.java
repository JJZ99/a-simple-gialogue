package com.example.abcdialogue.Test;

import java.util.ArrayList;
import java.util.function.Function;

import kotlin.jvm.functions.Function1;

public class TestGen {
    public static void main(String[] args) {
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("abc");
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(123);
        System.out.println(arrayList1.getClass() == arrayList2.getClass());
        Function f1 = greet -> greet + " Jack!";
        f1.apply("dasdas");
    }
}