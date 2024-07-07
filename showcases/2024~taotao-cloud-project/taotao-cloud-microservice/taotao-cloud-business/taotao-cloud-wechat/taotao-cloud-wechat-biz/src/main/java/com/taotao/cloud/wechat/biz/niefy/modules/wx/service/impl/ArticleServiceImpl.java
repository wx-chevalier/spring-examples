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

package com.taotao.cloud.wechat.biz.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.exception.RRException;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.ArticleMapper;
import com.github.niefy.modules.wx.dto.PageSizeConstant;
import com.github.niefy.modules.wx.entity.Article;
import com.github.niefy.modules.wx.enums.ArticleTypeEnum;
import com.github.niefy.modules.wx.service.ArticleService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Nifury
 * @since 2017-10-27
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    private static final String ID_PLACEHOLDER = "${articleId}";
    /** 查询文章列表时返回的字段（过滤掉详情字段以加快速度） */
    private static final String LIST_FILEDS =
            "id,summary,image,sub_category,update_time,title,type,tags,create_time,target_link,open_count,category";

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String type = (String) params.get("type");
        String category = (String) params.get("category");
        String subCategory = (String) params.get("subCategory");
        IPage<Article> page = this.page(
                new Query<Article>().getPage(params),
                new QueryWrapper<Article>()
                        .select(LIST_FILEDS)
                        .eq(StringUtils.hasText(type), "type", type)
                        .like(StringUtils.hasText(category), "category", category)
                        .like(StringUtils.hasText(subCategory), "sub_category", subCategory)
                        .like(StringUtils.hasText(title), "title", title));

        return new PageUtils(page);
    }

    /**
     * 查询文章详情，每次查询后增加点击次数
     *
     * @param id
     * @return
     */
    @Override
    public Article findById(int id) {
        if (id <= 0) {
            return null;
        }
        Article article = articleMapper.selectById(id);
        if (article != null) {
            articleMapper.addOpenCount(id);
        }
        return article;
    }

    /**
     * 添加或编辑文章,同名文章不可重复添加
     *
     * @param article
     */
    @Override
    public boolean saveArticle(Article article) {
        article.setUpdateTime(new Date());
        if (null != article.getId() && article.getId() > 0) {
            articleMapper.updateById(article);
        } else {
            String title = article.getTitle();
            long count = articleMapper.selectCount(new QueryWrapper<Article>()
                    .eq("title", title)
                    .eq("category", article.getCategory())
                    .eq("sub_category", article.getSubCategory()));
            if (count > 0) {
                throw new RRException("同目录下文章[" + title + "]已存在，不可重复添加");
            }
            article.setCreateTime(new Date());
            articleMapper.insert(article);
        }
        String targetLink = article.getTargetLink();
        if (targetLink.indexOf(ID_PLACEHOLDER) > -1) {
            article.setTargetLink(targetLink.replace(ID_PLACEHOLDER, article.getId() + ""));
            articleMapper.updateById(article);
        }
        return true;
    }

    /**
     * 按条件分页查询
     *
     * @param title
     * @param page
     * @return
     */
    @Override
    public IPage<Article> getArticles(String title, int page) {
        return this.page(
                new Page<Article>(page, PageSizeConstant.PAGE_SIZE_SMALL),
                new QueryWrapper<Article>()
                        .like(StringUtils.hasText("title"), "title", title)
                        .select(LIST_FILEDS)
                        .orderBy(true, false, "update_time"));
    }

    /**
     * 查看目录，不返回文章详情字段
     *
     * @param articleType
     * @param category
     * @return
     */
    @Override
    public List<Article> selectCategory(ArticleTypeEnum articleType, String category) {
        return this.list(new QueryWrapper<Article>()
                .select(LIST_FILEDS)
                .eq("type", articleType.getValue())
                .eq("category", category));
    }

    /**
     * 文章查找，不返回文章详情字段
     *
     * @param articleType
     * @param category
     * @param keywords
     * @return
     */
    @Override
    public List<Article> search(ArticleTypeEnum articleType, String category, String keywords) {
        return this.list(new QueryWrapper<Article>()
                .select(LIST_FILEDS)
                .eq("type", articleType.getValue())
                .eq(StringUtils.hasText(category), "category", category)
                .and(i -> i.like("summary", keywords).or().like("title", keywords)));
    }
}
