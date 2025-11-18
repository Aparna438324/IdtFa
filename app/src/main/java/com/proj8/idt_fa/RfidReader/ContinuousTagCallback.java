package com.proj8.idt_fa.RfidReader;

public interface ContinuousTagCallback {
    void onTagRead(String epc, String rssi);
    void onError(String reason);
    void onStopped();
}
