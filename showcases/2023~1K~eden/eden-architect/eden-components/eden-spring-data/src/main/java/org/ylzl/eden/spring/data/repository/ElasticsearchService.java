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

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Elasticsearch 业务接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface ElasticsearchService<T, ID extends Serializable>
	extends org.ylzl.eden.spring.data.repository.PagingAndSortingService<T, ID> {

	<S extends T> S index(S entity);

	Iterable<T> search(QueryBuilder queryBuilder);

	Page<T> search(QueryBuilder queryBuilder, Pageable pageable);

	Page<T> searchSimilar(T entity, String[] var, Pageable pageable);

	void refresh();
}
