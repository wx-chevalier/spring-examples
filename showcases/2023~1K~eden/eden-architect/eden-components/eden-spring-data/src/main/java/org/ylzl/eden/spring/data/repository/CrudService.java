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

package org.ylzl.eden.spring.data.repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * 增删查改业务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface CrudService<T, ID extends Serializable> {

	long count();

	@Deprecated
	void delete(ID id);

	void delete(T entity);

	@Deprecated
	void delete(Iterable<? extends T> entities);

	void deleteAll();

	void deleteAll(Iterable<? extends T> entities);

	void deleteById(ID id);

	boolean exists(ID id);

	boolean existsById(ID id);

	Iterable<T> findAll();

	@Deprecated
	Iterable<T> findAll(Iterable<ID> ids);

	Iterable<T> findAllById(Iterable<ID> ids);

	@Deprecated
	Optional<T> findOne(ID id);

	Optional<T> findById(ID id);

	<S extends T> S save(S entity);

	@Deprecated
	<S extends T> Iterable<S> save(Iterable<S> entities);

	<S extends T> Iterable<S> saveAll(Iterable<S> entities);
}
