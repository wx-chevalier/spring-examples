/*
 * Copyright 2012-2019 the original author or authors.
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
package org.ylzl.eden.commons.image;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.ylzl.eden.commons.env.Charsets;

import java.io.*;

/**
 * Batik 工具类
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class BatikUtils {

	public static void toPng(@NonNull String svgCode, @NonNull String destPath)
		throws IOException, TranscoderException {
		File file = new File(destPath);
		if (file.createNewFile()) {
			try (FileOutputStream out = new FileOutputStream(file);) {
				toPng(svgCode, out);
			}
		}
	}

	public static void toPng(@NonNull String svgCode, @NonNull OutputStream outputStream)
		throws IOException, TranscoderException {
		byte[] bytes = svgCode.getBytes(Charsets.UTF_8);
		PNGTranscoder transcoder = new PNGTranscoder();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		TranscoderInput input = new TranscoderInput(byteArrayInputStream);
		TranscoderOutput output = new TranscoderOutput(outputStream);
		transcoder.transcode(input, output);
		outputStream.flush();
	}
}
