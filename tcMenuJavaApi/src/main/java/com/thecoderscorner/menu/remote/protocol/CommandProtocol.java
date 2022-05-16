package com.thecoderscorner.menu.remote.protocol;

public enum CommandProtocol {
    INVALID(0), TAG_VAL_PROTOCOL(1), RAW_BIN_PROTOCOL(2);

    private final byte protoNum;

    CommandProtocol(int num) {
        protoNum = (byte)num;
    }

    public byte getProtoNum() {
        return protoNum;
    }

    public static CommandProtocol fromProtocolId(byte num) {
        return num == 1 ? TAG_VAL_PROTOCOL : RAW_BIN_PROTOCOL;
    }
}
