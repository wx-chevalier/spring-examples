package com.taotao.cloud.rpc.core.util;

import com.taotao.cloud.rpc.common.enums.PackageType;
import com.taotao.cloud.rpc.common.exception.UnrecognizedException;
import com.taotao.cloud.rpc.common.protocol.RpcRequest;
import com.taotao.cloud.rpc.common.protocol.RpcResponse;
import com.taotao.cloud.rpc.common.serializer.CommonSerializer;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectReader {

	private static final int MAGIC_NUMBER = 0xCAFEBABE;

	public static Object readObject(InputStream in) throws IOException, UnrecognizedException {
		byte[] numberBytes = new byte[4];
		in.read(numberBytes);
		int magic = bytes2Int(numberBytes);
		if (magic != MAGIC_NUMBER) {
			log.error("Unrecognized protocol package: {}", magic);
			throw new UnrecognizedException("Unrecognized protocol package error");
		}
		in.read(numberBytes);
		int packageCode = bytes2Int(numberBytes);
		Class<?> packageClass;
		if (packageCode == PackageType.REQUEST_PACK.getCode()) {
			packageClass = RpcRequest.class;
		} else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
			packageClass = RpcResponse.class;
		} else {
			log.error("Unrecognized data package: {}", packageCode);
			throw new UnrecognizedException("Unrecognized data package error");
		}
		in.read(numberBytes);
		int serializerCode = bytes2Int(numberBytes);
		CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
		if (serializer == null) {
			log.error("Unrecognized deserializer : {}", serializerCode);
			throw new UnrecognizedException("Unrecognized deserializer error");
		}
		in.read(numberBytes);
		int length = bytes2Int(numberBytes);
		byte[] bytes = new byte[length];
		in.read(bytes, 0, length);
		return serializer.deserialize(bytes, packageClass);
	}

	private static int bytes2Int(byte[] value) {
		int result = 0;
		int mark = 0xFF;
		if (value.length == 4) {
			int a = (value[0] & mark) << 24;
			int b = (value[1] & mark) << 16;
			int c = (value[2] & mark) << 8;
			int d = value[3] & mark;
			result = a | b | c | d;
		} else {
			log.error("Illegal size in bytes");
		}
		return result;
	}

}
