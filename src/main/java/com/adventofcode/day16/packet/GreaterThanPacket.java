package com.adventofcode.day16.packet;

import java.util.Arrays;

public class GreaterThanPacket implements Packet {

    private final CompositePacket compositePacket;

    private GreaterThanPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static GreaterThanPacket of(CompositePacket compositePacket) {
        return new GreaterThanPacket(compositePacket);
    }

    @Override
    public int getLength() {
        return compositePacket.getLength();
    }

    @Override
    public int versionSum() {
        return compositePacket.versionSum();
    }

    @Override
    public long value() {
        return compositePacket.innerPackets().get(0).value() > compositePacket.innerPackets().get(1).value() ? 1 : 0;
    }

    @Override
    public String toString() {
        return value() + ": Greater than " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
