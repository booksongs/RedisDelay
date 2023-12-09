package io.github.booksongs.rd.confirm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeadLetterProvider {
    private Object provide;
    private String topic;
}
