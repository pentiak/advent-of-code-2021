package com.adventofcode.day16.packet;

import java.util.Arrays;

public class MaximumPacket implements Packet {

    private final CompositePacket compositePacket;

    private MaximumPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static MaximumPacket of(CompositePacket compositePacket) {
        return new MaximumPacket(compositePacket);
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
        return compositePacket.innerPackets().stream().mapToLong(Packet::value).max().orElseThrow();
    }

    @Override
    public String toString() {
        return value() + ": Maximum of " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
