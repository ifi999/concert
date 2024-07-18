package com.hhp.concert.domain.token;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TokenQueue {

    private static final int MAX_ACTIVE_TOKEN = 100;
    private static final Queue<Token> TOKEN_QUEUE = new ConcurrentLinkedQueue<>();

    public static synchronized void add(Token token) {
        if (TOKEN_QUEUE.size() < MAX_ACTIVE_TOKEN && !TOKEN_QUEUE.contains(token)) {
            TOKEN_QUEUE.add(token);
        }
    }

    public static int getExtraQueueSpaceSize() {
        return MAX_ACTIVE_TOKEN - TOKEN_QUEUE.size();
    }

    public static void cleanQueue() {
        TOKEN_QUEUE.clear();
    }

}
