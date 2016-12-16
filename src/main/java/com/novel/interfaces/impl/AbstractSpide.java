package com.novel.interfaces.impl;

import com.novel.exceptions.CrawlException;
import com.novel.utils.NovelHttpGet;
import com.novel.utils.NovelSpiderUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Created by runshu.lin on 16/11/29.
 */
public abstract class AbstractSpide {

	/**
	 * 抓取数据
	 * @param url
	 * @return
	 */
	protected String cwal(String url) throws CrawlException {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			 CloseableHttpResponse httpResponse = httpClient.execute(new NovelHttpGet(url))) {
			String response = EntityUtils.toString(httpResponse.getEntity(), NovelSpiderUtil.getContext(url).getChapterCharset());
			return response;
		}catch (Exception e){
			throw new CrawlException(e);
		}
	}
}
