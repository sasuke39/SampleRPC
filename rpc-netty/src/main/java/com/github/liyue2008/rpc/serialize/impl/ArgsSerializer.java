package com.github.liyue2008.rpc.serialize.impl;

import com.github.liyue2008.rpc.serialize.SerializeSupport;
import com.github.liyue2008.rpc.serialize.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author xianhong
 * @date 2022/4/12
 */
public class ArgsSerializer implements Serializer<Object[]> {


    //长度可变 写0
    @Override
    public int size(Object[] entry) {
        return 0;
    }

    @Override
    public void serialize(Object[] entry, byte[] bytes, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, length);
        for (Object arg : entry) {
            byte[] serialize = SerializeSupport.serialize(arg);
            buffer.put(serialize);
        }
    }

    @Override
    public Object[] parse(byte[] bytes, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        byte count = buffer.get();
        offset++;
        Object[] objects = new Object[count];
        for (int i = 0; i < count; i++) {
            byte typeLength = buffer.get();
            offset++;
            byte[] objectBytes = new byte[typeLength];
            buffer.get(objectBytes,0, typeLength);
            offset+=typeLength;
            Object parse = SerializeSupport.parse(objectBytes);
            objects[i]=parse;
        }

        return objects;
    }

    @Override
    public byte type() {
        return Types.TYPE_ARGS;
    }

    @Override
    public Class<Object[]> getSerializeClass() {
        return Object[].class;
    }
}
