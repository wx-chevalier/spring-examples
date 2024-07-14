package org.ylzl.eden.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.ylzl.eden.demo.api.dto.UserResponseDTO
import org.ylzl.eden.demo.web.UserController
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse
import spock.lang.Specification
import spock.lang.Unroll
/**
 * 应用启动集成测试
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationITCase extends Specification {

	@Autowired
	private UserController userController;

	@Unroll
	def "get User By Id where id=#id then expect: #expectedResult"() {
		expect:
		userController.getUserById(id) == expectedResult

		where:
		id || expectedResult
		1l || SingleResponse.of(new UserResponseDTO(1l, "admin", "1813986321@qq.com"))
	}
}
