package com.ttdevs.flyer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_spilt() {
        String src = "08-31 18:17:20.868  2112  2112 D d>>>>>  : debug";
        String[] items = src.split("\\s+");
        for (String item : items) {
            System.out.println(item);
        }
    }

    @Test
    public void test_activity() {
        String FLAG_START = "{cmp=";
        String FLAG_END = "}";
        String src = "I/ActivityManager: START u0 {cmp=com.ttdevs.demo/.SecondActivity} from uid 10104 pid 24801";
        if (src.contains("ActivityManager") && src.contains("START")) {
            int start = src.indexOf(FLAG_START) + FLAG_START.length();
            int end = src.lastIndexOf(FLAG_END);
            String result = src.substring(start, end);
            System.out.println(result);
        }
    }
}