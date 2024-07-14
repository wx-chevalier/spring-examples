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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * JPA 业务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface JpaService<T, ID extends Serializable>
	extends CrudService<T, ID>, PagingAndSortingService<T, ID> {

	long count(Specification<T> spec);

	void deleteAllInBatch();

	void deleteInBatch(Iterable<T> entities);

	List<T> findAll();

	@Deprecated
	List<T> findAll(Iterable<ID> ids);

	List<T> findAll(Specification<T> spec);

	Page<T> findAll(Specification<T> spec, Pageable pageable);

	List<T> findAll(Specification<T> spec, Sort sort);

	List<T> findAll(Sort sort);

	List<T> findAllById(Iterable<ID> ids);

	Optional<T> findOne(Specification<T> spec);

	void flush();

	T getOne(ID id);

	@Deprecated
	<S extends T> List<S> save(Iterable<S> entities);

	<S extends T> List<S> saveAll(Iterable<S> entities);

	<S extends T> S saveAndFlush(S entity);
}
