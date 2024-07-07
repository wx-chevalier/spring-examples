/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
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
package com.taotao.cloud.payment.biz.jeepay.components.oss.constant;

import lombok.Getter;

/*
* oss 服务枚举值
* @author terrfly
* @site https://www.jeequan.com
* @since 2021/7/12 10:48
*/
@Getter
public enum OssServiceTypeEnum {

    LOCAL("local"), //本地存储

    ALIYUN_OSS("aliyun-oss");  //阿里云oss

    /** 名称 **/
    private String serviceName;

    OssServiceTypeEnum(String serviceName){
        this.serviceName = serviceName;
    }
}
