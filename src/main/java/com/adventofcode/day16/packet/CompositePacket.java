package com.adventofcode.day16.packet;

import java.util.List;

public record CompositePacket(int version, List<Packet> innerPackets, int length) implements Packet {

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int versionSum() {
        return version + innerPackets.stream().mapToInt(Packet::versionSum).sum();
    }

    @Override
    public long value() {
        throw new UnsupportedOperationException("Composite packets do not calculate value");
    }
}
