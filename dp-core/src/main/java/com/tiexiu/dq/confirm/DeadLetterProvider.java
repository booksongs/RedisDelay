package com.tiexiu.dq.confirm;

import com.tiexiu.dq.listener.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeadLetterProvider {
    private Provider provide;
    private String topic;
}
