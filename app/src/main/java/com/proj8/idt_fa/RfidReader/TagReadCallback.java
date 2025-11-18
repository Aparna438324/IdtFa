package com.proj8.idt_fa.RfidReader;

public interface TagReadCallback {
    void onTagReadSuccess(String epc);
    void onTagReadFailure(String reason);
}

