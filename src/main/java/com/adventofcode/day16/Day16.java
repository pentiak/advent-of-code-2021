package com.adventofcode.day16;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.day16.packet.*;
import com.adventofcode.day16.transmission.OffsetTransmission;
import com.adventofcode.day16.transmission.SimpleTransmission;
import com.adventofcode.day16.transmission.Transmission;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Day16 extends AbstractAdventDay {

    @Override
    public Object part1() {
        return buildPacket().versionSum();
    }

    @Override
    public Object part2() {
        Packet packet = buildPacket();
        return packet.value();
    }

    private Packet buildPacket() {
        String input = InputUtils.readInputLines(inputPath).get(0);
        Transmission transmission = new SimpleTransmission(input);
        return parse(transmission);
    }

    private Packet parse(Transmission transmission) {
        int typeId = transmission.getValue(4, 6);

        return switch (typeId) {
            case 0 -> SumPacket.of(parseOperatorPacket(transmission));
            case 1 -> ProductPacket.of(parseOperatorPacket(transmission));
            case 2 -> MinimumPacket.of(parseOperatorPacket(transmission));
            case 3 -> MaximumPacket.of(parseOperatorPacket(transmission));
            case 4 -> parseLiteralPacket(transmission);
            case 5 -> GreaterThanPacket.of(parseOperatorPacket(transmission));
            case 6 -> LessThanPacket.of(parseOperatorPacket(transmission));
            case 7 -> EqualToPacket.of(parseOperatorPacket(transmission));
            default -> parseOperatorPacket(transmission);
        };
    }

    private CompositePacket parseOperatorPacket(Transmission transmission) {
        boolean numberType = transmission.testBit(7);
        if (numberType) {
            return parseNumberTypeOperatorPacket(transmission);
        } else {
            return parseLengthTypeOperatorPacket(transmission);
        }
    }

    private CompositePacket parseLengthTypeOperatorPacket(Transmission transmission) {
        int version = transmission.getValue(1, 3);
        int lengthOfPackets = transmission.getValue(8, 22);
        List<Packet> innerPackets = new ArrayList<>();
        int offset = 23;
        while (offset - 23 < lengthOfPackets) {
            Packet innerPacket = parse(new OffsetTransmission(transmission, offset - 1));
            offset += innerPacket.getLength();
            innerPackets.add(innerPacket);
        }
        return new CompositePacket(version, innerPackets, offset - 1);
    }

    private CompositePacket parseNumberTypeOperatorPacket(Transmission transmission) {
        int version = transmission.getValue(1, 3);
        int numberOfPackets = transmission.getValue(8, 18);
        List<Packet> innerPackets = new ArrayList<>(numberOfPackets);
        int offset = 19;
        while (innerPackets.size() < numberOfPackets) {
            Packet innerPacket = parse(new OffsetTransmission(transmission, offset - 1));
            offset += innerPacket.getLength();
            innerPackets.add(innerPacket);
        }

        return new CompositePacket(version, innerPackets, offset - 1);
    }

    private LiteralPacket parseLiteralPacket(Transmission transmission) {
        int version = transmission.getValue(1, 3);
        boolean hasMore = true;
        long literal = 0;
        int pos = 7;
        while (hasMore) {
            literal = (literal << 4) | transmission.getValue(pos + 1, pos + 4);
            hasMore = transmission.testBit(pos);
            pos += 5;
        }
        return new LiteralPacket(version, literal, pos - 1);
    }
}
