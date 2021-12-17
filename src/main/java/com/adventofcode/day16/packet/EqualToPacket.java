package com.adventofcode.day16.packet;

import java.util.Arrays;

public class EqualToPacket implements Packet {

    private final CompositePacket compositePacket;

    private EqualToPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static EqualToPacket of(CompositePacket compositePacket) {
        return new EqualToPacket(compositePacket);
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
        return compositePacket.innerPackets().get(0).value() == compositePacket.innerPackets().get(1).value() ? 1 : 0;
    }

    @Override
    public String toString() {
        return value() + ": Equal to " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
