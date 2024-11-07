package com.example.springUserAuth.util;

import com.example.springUserAuth.dtos.BaseResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {
    public static BaseResponseDTO getResponse(int code, Boolean error, String status, String message ){
        BaseResponseDTO res = new BaseResponseDTO();
        res.setMessage(message);
        res.setStatus(status);
        res.setError(error);
        res.setCode(code);
        return res;
    }
}
