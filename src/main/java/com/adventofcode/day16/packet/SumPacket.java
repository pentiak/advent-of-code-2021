package com.adventofcode.day16.packet;

import java.util.Arrays;

public class SumPacket implements Packet {

    private final CompositePacket compositePacket;

    private SumPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static SumPacket of(CompositePacket compositePacket) {
        return new SumPacket(compositePacket);
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
        return compositePacket.innerPackets().stream().mapToLong(Packet::value).sum();
    }

    @Override
    public String toString() {
        return value() + ": Sum of " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
