package com.adventofcode.day16.packet;

import java.util.Arrays;

public class ProductPacket implements Packet {

    private final CompositePacket compositePacket;

    private ProductPacket(CompositePacket compositePacket) {
        this.compositePacket = compositePacket;
    }

    public static ProductPacket of(CompositePacket compositePacket) {
        return new ProductPacket(compositePacket);
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
        return compositePacket.innerPackets().stream().mapToLong(Packet::value).reduce(1, (left, right) -> left * right);
    }

    @Override
    public String toString() {
        return value() + ": Product of " + Arrays.toString(compositePacket.innerPackets().stream().mapToLong(Packet::value).toArray());
    }
}
