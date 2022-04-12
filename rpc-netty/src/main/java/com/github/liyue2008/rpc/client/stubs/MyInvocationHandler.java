package com.github.liyue2008.rpc.client.stubs;

/**
 * @author xianhong
 * @date 2022/4/12
 */
import com.github.liyue2008.rpc.serialize.SerializeSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class MyInvocationHandler implements InvocationHandler {

    private final Class proxied;

    private final ProxyStub proxyStub;

    public <T> MyInvocationHandler(Class<T> proxied, ProxyStub proxyStub) {
        this.proxied = proxied;
        this.proxyStub = proxyStub;
    }

    /**
     * 在此处实现远程接口的调用并且返回结果
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int length =0;
        byte count = 0;
        for (Object arg : args) {
            byte[] serialize = SerializeSupport.serialize(arg);
            length=serialize.length+length;
            count++;
        }
        byte[] bytes = new byte[length+count+2];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte type = 111;
        buffer.put(type);
        buffer.put(count);
        for (Object arg : args) {
            byte[] serialize = SerializeSupport.serialize(arg);
            buffer.put((byte) serialize.length);
            buffer.put(serialize);
        }
        RpcRequest rpcRequest = new RpcRequest(proxied.getName(), method.getName(),bytes);
        return SerializeSupport.parse(proxyStub.doInvokeRemote(rpcRequest));
    }
}

