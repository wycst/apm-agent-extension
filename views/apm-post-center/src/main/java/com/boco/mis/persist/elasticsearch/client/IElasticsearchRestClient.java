package com.boco.mis.persist.elasticsearch.client;

import com.boco.mis.persist.model.ApmModelIndex;

/**
 * es客户端rest服务
 * @author wangyunchao
 *
 * 2018年8月21日 下午3:17:50
 */
public interface IElasticsearchRestClient {

	/**
	 * 初始化索引，一般启动时调用且只调用一次
	 */
	public void initApmIndexs() ;
	
	/**
	 * 添加文档索引
	 * @param model
	 */
	public void addApmIndex(ApmModelIndex model);
	
}
