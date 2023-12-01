package com.tiexiu.dp.example;


import com.tiexiu.dq.listener.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order extends Provider {
    private String name;
    private String xx;

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", xx='" + xx + '\'' +
                ", sendTime=" + sendTime +
                ", id='" + id + '\'' +
                '}';
    }
}