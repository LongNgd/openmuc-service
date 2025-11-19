package vn.atdigital.iot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.atdigital.iot.common.Constants;
import vn.atdigital.iot.domain.message.ResponseMessage;

public abstract class CommonController {

    protected <T> ResponseEntity<?> toSuccessResult(T data) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setCode(Constants.API_RESPONSE.RETURN_CODE_SUCCESS);
        message.setSuccess(Constants.STATUS_COMMON.RESPONSE_STATUS_TRUE);
        message.setData(data );

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    protected <T> ResponseEntity<?> toExceptionResult(String errorMessage, String code) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setSuccess(Constants.STATUS_COMMON.RESPONSE_STATUS_FALSE);
        message.setCode(code);
        message.setDescription(errorMessage);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<?> toSuccessResultNull() {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setCode(Constants.API_RESPONSE.RETURN_CODE_SUCCESS);
        message.setSuccess(Constants.STATUS_COMMON.RESPONSE_STATUS_TRUE);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
