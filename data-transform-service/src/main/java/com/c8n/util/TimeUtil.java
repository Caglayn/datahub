package com.c8n.util;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeUtil {
    private long startTime;
    private long finishTime;

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        this.finishTime = System.currentTimeMillis();
    }

    public double getTimeAsSecond() {
        return (double)(System.currentTimeMillis() - this.startTime) / 1000.0;
    }

    public long getTimeAsMiliSecond() {
        return System.currentTimeMillis()-this.startTime;
    }
}
