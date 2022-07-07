/*
*
*@Date:2022年5月08日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloudtest;

import org.junit.jupiter.api.Test;

public class MyTest {
    @Test
    public void testIndexOf(){
        String str="abcdefg";
        int index=str.indexOf("f");
        String subString=str.substring(0, index);
        System.out.println(subString);
    }

}
