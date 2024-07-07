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

package com.taotao.cloud.auth.infrastructure.persistent.authorization.jackson2;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.taotao.cloud.security.springsecurity.authentication.login.form.sms.Oauth2FormSmsLoginAuthenticationToken;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Custom deserializer for {@link UsernamePasswordAuthenticationToken}. At the time of
 * deserialization it will invoke suitable constructor depending on the value of
 * <b>authenticated</b> property. It will ensure that the token's state must not change.
 * <p>
 * This deserializer is already registered with {@link FormOAuth2PhoneAuthenticationTokenMixin} but
 * you can also registered it with your own mixin class.
 *
 * @author Jitendra Singh
 * @author Greg Turnquist
 * @author Onur Kagan Ozcan
 * @see FormOAuth2PhoneAuthenticationTokenMixin
 * @since 4.2
 */
public class FormOAuth2PhoneAuthenticationTokenDeserializer
	extends JsonDeserializer<Oauth2FormSmsLoginAuthenticationToken> {

	private static final TypeReference<List<GrantedAuthority>> GRANTED_AUTHORITY_LIST =
		new TypeReference<List<GrantedAuthority>>() {
		};

	private static final TypeReference<Object> OBJECT = new TypeReference<Object>() {
	};

	/**
	 * This method construct {@link UsernamePasswordAuthenticationToken} object from serialized
	 * json.
	 *
	 * @param jp   the JsonParser
	 * @param ctxt the DeserializationContext
	 * @return the user
	 * @throws IOException             if a exception during IO occurs
	 * @throws JsonProcessingException if an error during JSON processing occurs
	 */
	@Override
	public Oauth2FormSmsLoginAuthenticationToken deserialize(JsonParser jp,
		DeserializationContext ctxt)
		throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);

		Boolean authenticated = readJsonNode(jsonNode, "authenticated").asBoolean();
		JsonNode principalNode = readJsonNode(jsonNode, "principal");
		Object principal = getPrincipal(mapper, principalNode);

		String type = getText(readJsonNode(jsonNode, "type"));
		String captcha = getText(readJsonNode(jsonNode, "captcha"));

		//		JsonNode credentialsNode = readJsonNode(jsonNode, "credentials");
		//		Object credentials = getCredentials(credentialsNode);

		List<GrantedAuthority> authorities =
			mapper.readValue(readJsonNode(jsonNode, "authorities").traverse(mapper),
				GRANTED_AUTHORITY_LIST);

		Oauth2FormSmsLoginAuthenticationToken token = (!authenticated)
			? Oauth2FormSmsLoginAuthenticationToken.unauthenticated(principal, captcha, type)
			: Oauth2FormSmsLoginAuthenticationToken.authenticated(principal, captcha, type,
				authorities);
		JsonNode detailsNode = readJsonNode(jsonNode, "details");
		if (detailsNode.isNull() || detailsNode.isMissingNode()) {
			token.setDetails(null);
		}
		else {
			Object details = mapper.readValue(detailsNode.toString(), OBJECT);
			token.setDetails(details);
		}
		return token;
	}

	private String getText(JsonNode jsonNode) {
		if (jsonNode.isNull() || jsonNode.isMissingNode()) {
			return "";
		}
		return jsonNode.asText();
	}

	private Object getCredentials(JsonNode credentialsNode) {
		if (credentialsNode.isNull() || credentialsNode.isMissingNode()) {
			return null;
		}
		return credentialsNode.asText();
	}

	private Object getPrincipal(ObjectMapper mapper, JsonNode principalNode)
		throws IOException, JsonParseException, JsonMappingException {
		if (principalNode.isObject()) {
			return mapper.readValue(principalNode.traverse(mapper), Object.class);
		}
		return principalNode.asText();
	}

	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
		return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
	}
}
