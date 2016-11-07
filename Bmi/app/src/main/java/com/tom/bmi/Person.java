package com.tom.bmi;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Person {
    float weight;
    float height;
    public float bim(){
        return weight/(height*height);
    }

    public Person(float weight,float height){
        this.weight = weight;
        this.height = height;
    }

    public static void main(String[] args) {
        Person p = new Person(65f,1.7f);
        //p.weight = 65f;
        //p.height = 1.7f;
        System.out.println(p.bim());
    }
}
