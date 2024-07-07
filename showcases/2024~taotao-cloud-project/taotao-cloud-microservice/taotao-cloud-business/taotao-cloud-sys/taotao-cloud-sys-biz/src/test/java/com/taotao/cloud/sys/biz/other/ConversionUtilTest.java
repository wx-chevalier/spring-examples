/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.sys.biz.other;

import static shorturl.server.server.application.util.ConversionUtil.decode;
import static shorturl.server.server.application.util.ConversionUtil.encode;

import com.taotao.cloud.common.utils.log.LogUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import shorturl.server.server.application.util.DefaultIdWorker;

public class ConversionUtilTest {

    @Test
    public void UtilTest() {
        Base64 base64 = new Base64();
        long l = new DefaultIdWorker().generate();
        LogUtils.info("id:" + l);
        String encodeStr = encode(l, 11);
        int length = encodeStr.length();
        LogUtils.info("62进制：" + encodeStr);
        if ((length <= 8)) {
            LogUtils.info("62进制：" + encodeStr);
        }
        String substring = encodeStr.substring(length - 8, length);
        LogUtils.info("62进制：" + substring);
        LogUtils.info("10进制：" + decode(encodeStr));
    }
}
