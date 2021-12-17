package com.adventofcode.day16.packet;

import java.util.Arrays;

public class LessThanPacket implements Packet {

    private final CompositePacket compositePacket;

    private LessThanPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static LessThanPacket of(CompositePacket compositePacket) {
        return new LessThanPacket(compositePacket);
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
        return compositePacket.innerPackets().get(0).value() < compositePacket.innerPackets().get(1).value() ? 1 : 0;
    }

    @Override
    public String toString() {
        return value() + ": Less than " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
