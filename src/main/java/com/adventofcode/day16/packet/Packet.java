package com.adventofcode.day16.packet;

public interface Packet {
    int getLength();

    int versionSum();

    long value();
}
