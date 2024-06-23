/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.smart.cloud.starter.web.validation;

import lombok.Getter;
import org.hibernate.validator.HibernateValidator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * hibernate-validator校验单例
 *
 * @author collin
 * @date 2021-10-31
 */
public class ValidatorSingleton {

    private ValidatorSingleton() {
    }

    public static Validator getInstance() {
        return Holder.INSTANCE.getValidator();
    }

    @Getter
    private enum Holder {
        /**
         * hibernate-validator校验实例
         */
        INSTANCE;
        private Validator validator;

        Holder() {
            try{
                ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                        .configure()
                        .addProperty("hibernate.validator.fail_fast", "true")
                        .buildValidatorFactory();
                validator = validatorFactory.getValidator();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}