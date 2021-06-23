package com.nanhang.mybatis_plus.lambdaInterface;

import lombok.Data;

/**
 * @author: immortal
 * @CreateDate: 2021/6/22 10:32
 * @Description:
 */
@Data
public class UserLambda implements UserInterface {

    private UserInterface userInterface;


    public UserLambda(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public static void main(String[] args) {
        new UserLambda(name -> {
        }).query("name");


        UserInterface userInterface = name -> {
        };


    }

    public UserInterface get() {
        return name -> {
        };
    }

    @Override
    public void query(String name) {

    }
}
