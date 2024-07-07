package com.taotao.cloud.rpc.core.util;

import com.taotao.cloud.rpc.common.enums.PackageType;
import com.taotao.cloud.rpc.common.exception.UnSupportBodyException;
import com.taotao.cloud.rpc.common.protocol.RpcRequest;
import com.taotao.cloud.rpc.common.protocol.RpcResponse;
import com.taotao.cloud.rpc.common.serializer.CommonSerializer;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectWriter {

	private static int MAGIC_NUMBER = 0xCAFEBABE;

	public static void writeObject(OutputStream outputStream, Object object,
		CommonSerializer serializer) throws IOException, UnSupportBodyException {

		outputStream.write(int2Bytes(MAGIC_NUMBER));
		if (object instanceof RpcRequest) {
			outputStream.write(int2Bytes(PackageType.REQUEST_PACK.getCode()));
		} else if (object instanceof RpcResponse) {
			outputStream.write(int2Bytes(PackageType.RESPONSE_PACK.getCode()));
		} else {
			log.error("Only request body and response body are supported");
			throw new UnSupportBodyException(
				"Only request body and response body are supported Exception");
		}
		outputStream.write(int2Bytes(serializer.getCode()));
		byte[] bytes = serializer.serialize(object);
		int length = bytes.length;
		outputStream.write(int2Bytes(length));
		log.info("encode object length [{}] bytes", length);
		outputStream.write(bytes);
		// 流 经过了 优化，如果 缓存太小的话，不会立即 写入 输出流，而是 保存到了缓存中， 会在 写入一定 大小后，再 调用 flush() 方法 批量 写入 输出流
		outputStream.flush();
	}

	/**
	 * int 4 字节 byte 1 字节
	 *
	 * @param value
	 * @return
	 */
	private static byte[] int2Bytes(int value) {
		byte[] src = new byte[4];
		int mark = 0xFF;
		src[0] = (byte) (value >> 24 & mark);
		src[1] = (byte) (value >> 16 & mark);
		src[2] = (byte) (value >> 8 & mark);
		src[3] = (byte) (value & mark);
		return src;
	}


}
