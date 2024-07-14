/**
 * Copyright (c) 2018-2028, DreamLu 卢春梦 (qq596392912@gmail.com).
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
package org.springblade.core.tool.utils;


import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * 运行时工具类
 *
 * @author L.cm
 */
public class RuntimeUtil {
	private static volatile int pId = -1;
	private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

	/**
	 * 获得当前进程的PID
	 * <p>
	 * 当失败时返回-1
	 *
	 * @return pid
	 */
	public static int getPId() {
		if (pId > 0) {
			return pId;
		}
		// something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		final int index = jvmName.indexOf(CharPool.AT);
		if (index > 0) {
			pId = NumberUtil.toInt(jvmName.substring(0, index), -1);
			return pId;
		}
		return pId;
	}

	/**
	 * 返回应用启动的时间
	 *
	 * @return {Instant}
	 */
	public static Instant getStartTime() {
		return Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime());
	}

	/**
	 * 返回应用启动到现在的时间
	 *
	 * @return {Duration}
	 */
	public static Duration getUpTime() {
		return Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());
	}

	/**
	 * 返回输入的JVM参数列表
	 *
	 * @return jvm参数
	 */
	public static String getJvmArguments() {
		List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		return StringUtil.join(vmArguments, StringPool.SPACE);
	}

	/**
	 * 获取CPU核数
	 *
	 * @return cpu count
	 */
	public static int getCpuNum() {
		return CPU_NUM;
	}

}
