package com.tiexiu.rd.confirm;

import com.tiexiu.rd.listener.Provider;
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
