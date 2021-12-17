package com.adventofcode.day16.packet;

import java.util.Arrays;

public class MinimumPacket implements Packet {

    private final CompositePacket compositePacket;

    private MinimumPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static MinimumPacket of(CompositePacket compositePacket) {
        return new MinimumPacket(compositePacket);
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
        return compositePacket.innerPackets().stream().mapToLong(Packet::value).min().orElseThrow();
    }

    @Override
    public String toString() {
        return value() + ": Minimum of " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
