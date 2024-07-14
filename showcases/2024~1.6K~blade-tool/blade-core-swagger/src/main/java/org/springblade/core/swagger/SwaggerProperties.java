/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.core.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerProperties
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {
	/**
	 * 是否开启swagger
	 */
	private boolean enabled = true;
	/**
	 * swagger会解析的包路径
	 **/
	private List<String> basePackages = new ArrayList<>(Collections.singletonList(AppConstant.BASE_PACKAGES));
	/**
	 * swagger会排除解析的包路径
	 **/
	private List<String> excludePackages = new ArrayList<>();
	/**
	 * swagger会解析的url规则
	 **/
	private List<String> basePath = new ArrayList<>();
	/**
	 * 在basePath基础上需要排除的url规则
	 **/
	private List<String> excludePath = new ArrayList<>();
	/**
	 * 标题
	 **/
	private String title = "SpringBlade 接口文档系统";
	/**
	 * 描述
	 **/
	private String description = "SpringBlade 接口文档系统";
	/**
	 * 版本
	 **/
	private String version = AppConstant.APPLICATION_VERSION;
	/**
	 * 许可证
	 **/
	private String license = "Powered By SpringBlade";
	/**
	 * 许可证URL
	 **/
	private String licenseUrl = "";
	/**
	 * 服务条款URL
	 **/
	private String termsOfServiceUrl = "";
	/**
	 * host信息
	 **/
	private String host = "";
	/**
	 * 联系人信息
	 */
	private Contact contact = new Contact();
	/**
	 * 全局统一鉴权配置
	 **/
	private Authorization authorization = new Authorization();

	@Data
	@NoArgsConstructor
	public static class Contact {

		/**
		 * 联系人
		 **/
		private String name = "翼宿";
		/**
		 * 联系人email
		 **/
		private String email = "bladejava@qq.com";
		/**
		 * 联系人url
		 **/
		private String url = "https://gitee.com/smallc";

	}

	@Data
	@NoArgsConstructor
	public static class Authorization {

		/**
		 * 鉴权策略ID，需要和SecurityReferences ID保持一致
		 */
		private String name = "";

		/**
		 * 需要开启鉴权URL的正则
		 */
		private String authRegex = "^.*$";

		/**
		 * 接口匹配地址
		 */
		private List<String> tokenUrlList = new ArrayList<>();
	}


}
