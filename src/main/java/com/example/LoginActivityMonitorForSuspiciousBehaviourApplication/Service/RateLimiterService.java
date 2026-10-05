package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RateLimiterService {

    private static final int MAX_ATTEMPTS = 5;
    private static final int WINDOW_MINUTES = 1;

    private final Map<String, AttemptData> attempts = new HashMap<>();

    public synchronized boolean isAllowed(String ipAddress) {
        LocalDateTime now = LocalDateTime.now();

        AttemptData data = attempts.get(ipAddress);

        if (data == null || data.getWindowStart().plusMinutes(WINDOW_MINUTES).isBefore(now)) {
            attempts.put(ipAddress, new AttemptData(now, 1));
            return true;
        }

        if (data.getCount() >= MAX_ATTEMPTS) {
            return false;
        }

        data.setCount(data.getCount() + 1);
        return true;
    }

    private static class AttemptData {

        private LocalDateTime windowStart;
        private int count;

        public AttemptData(LocalDateTime windowStart, int count) {
            this.windowStart = windowStart;
            this.count = count;
        }

        public LocalDateTime getWindowStart() {
            return windowStart;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}