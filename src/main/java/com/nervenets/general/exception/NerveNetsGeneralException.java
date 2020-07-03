package com.nervenets.general.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NerveNetsGeneralException extends RuntimeException {
    private int code = 400;

    public NerveNetsGeneralException(String message) {
        super(message);
    }

    public NerveNetsGeneralException(int code, String message) {
        super(message);
        this.code = code;
    }

    public NerveNetsGeneralException(int code) {
        super("错误代码" + code);
        this.code = code;
    }

    public NerveNetsGeneralException(String message, Exception e) {
        super(message, e);
    }
}
