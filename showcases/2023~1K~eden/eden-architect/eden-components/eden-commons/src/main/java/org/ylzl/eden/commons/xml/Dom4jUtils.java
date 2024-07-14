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

package org.ylzl.eden.commons.xml;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Dom4j 工具集
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class Dom4jUtils {

	public static Document createDocument() {
		return DocumentHelper.createDocument();
	}

	public static Document parseText(@NonNull String xmlString) throws DocumentException {
		return DocumentHelper.parseText(xmlString);
	}

	public static Document read(@NonNull InputStream inputStream, boolean validation) throws Exception {
		return createSAXReader(validation).read(inputStream);
	}

	public static Document read(@NonNull File file, boolean validation) throws Exception {
		return createSAXReader(validation).read(file);
	}

	public static SAXReader createSAXReader(boolean validation) {
		SAXReader saxReader = new SAXReader();
		saxReader.setValidation(validation);
		return saxReader;
	}

	public static <T> Node selectSingleNode(@NonNull T obj, @NonNull String expression) {
		if (obj instanceof Element) {
			Element element = (Element) obj;
			return element.selectSingleNode(expression);
		}
		if (obj instanceof Document) {
			Document document = (Document) obj;
			return document.selectSingleNode(expression);
		}
		if (obj instanceof Node) {
			Node node = (Node) obj;
			return node.selectSingleNode(expression);
		}
		return null;
	}

	public static <T> List<?> selectNodes(@NonNull T obj, @NonNull String expression) {
		if (obj instanceof Element) {
			Element element = (Element) obj;
			return element.selectNodes(expression);
		}
		if (obj instanceof Document) {
			Document document = (Document) obj;
			return document.selectNodes(expression);
		}
		if (obj instanceof Node) {
			Node node = (Node) obj;
			return node.selectNodes(expression);
		}
		return null;
	}
}
