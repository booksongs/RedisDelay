package io.github.booksongs.rd.listener;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Provider {
    protected long sendTime = System.currentTimeMillis();
    protected String id = UUID.randomUUID().toString();
}
