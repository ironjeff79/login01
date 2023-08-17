package com.cls.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;

    public static <T> Result<T> success(String message){return new Result<>(20000,message);
    }
    public static <T>  Result<T> fail(String message){
        return new Result<>(20001,message);
    }

}


