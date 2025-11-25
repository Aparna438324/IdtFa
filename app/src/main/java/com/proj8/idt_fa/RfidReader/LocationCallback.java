package com.proj8.idt_fa.RfidReader;

public interface LocationCallback {
    void onLocationValue(int value, boolean flag);
    void onError(String message);
}

