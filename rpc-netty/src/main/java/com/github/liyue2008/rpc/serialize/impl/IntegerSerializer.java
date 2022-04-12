package com.github.liyue2008.rpc.serialize.impl;

import com.github.liyue2008.rpc.Util.NumberUtil;
import com.github.liyue2008.rpc.serialize.Serializer;


/**
 * @author xianhong
 * @date 2022/4/12
 */
public class IntegerSerializer implements Serializer<Integer> {

    @Override
    public int size(Integer entry) {
        return Integer.BYTES;
    }

    @Override
    public void serialize(Integer entry, byte[] bytes, int offset, int length) {
        byte [] strBytes = NumberUtil.intToByte4(entry);
        System.arraycopy(strBytes, 0, bytes, offset, strBytes.length);
    }

    @Override
    public Integer parse(byte[] bytes, int offset, int length) {
        return NumberUtil.byte4ToInt(bytes,offset);
    }

    @Override
    public byte type() {
        return Types.TYPE_INTEGER;
    }

    @Override
    public Class<Integer> getSerializeClass() {
        return Integer.class;
    }
}
