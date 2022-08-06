/**
*
*@Date:2022年8月03日
*
*@Author:Lori Shu
*
*/
package test;

public enum Season {
    SPRING("春暖花开"),
    SUMMER("夏日炎炎"),
    AUTUMN("秋高气爽"),
    WINTER("白雪皑皑");

    private String des;

    private Season(String str) {
        this.des = str;
    }

    public String getDes() {
        return this.des;
    }
}
