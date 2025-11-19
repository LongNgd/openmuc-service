package vn.atdigital.iot.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ResponseMessage<T> extends BaseMessage implements Serializable {
    private T data;

}
