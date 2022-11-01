package com.my.forum;

/**
 * 用户类
 */
public class User {
    //用户id号
    private int id;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //性别
    private char gender ='m';
    //年龄
    private int age = 0;
    //地址
    private  String address = null;



    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    public int setUserName(String userName) {
        if (userName.length() <= 20)
        {
            this.userName = userName;
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public String getPassWord() {
        return passWord;
    }


    public int setPassWord(String passWord) {
        if (passWord.length() <= 20)
        {
            this.passWord = passWord;
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public char getGender() {
        return gender;
    }

    public int setGender(char gender) {
        if (gender == 'm' ||  gender == 'f')
        {
            this.gender = gender;
            return 1;
        }
        else {
            return -1;
        }
    }

    public int getAge() {
        return age;
    }

    public int setAge(int age) {
        if (age > 0 && age <120)
        {
            this.age = age;
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String userName, String passWord, char gender, int age, String address) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.gender = gender;
        this.age = age;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public int setAddress(String address) {
        if (address.length() <= 30)
        {
            this.address = address;
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public User() {
    }
}
