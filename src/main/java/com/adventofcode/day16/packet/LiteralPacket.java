package com.adventofcode.day16.packet;

public record LiteralPacket(int version, long literal, int length) implements Packet {

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int versionSum() {
        return version;
    }

    @Override
    public long value() {
        return literal;
    }

    @Override
    public String toString() {
        return "" + value();
    }
}
