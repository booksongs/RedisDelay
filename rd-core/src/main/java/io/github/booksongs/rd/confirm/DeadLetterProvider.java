package io.github.booksongs.rd.confirm;

import io.github.booksongs.rd.listener.Provider;
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
