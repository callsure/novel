package com.novel.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

import java.net.URI;

/**
 * Created by runshu.lin on 16/11/29.
 */
public class NovelHttpGet extends HttpGet {
	public NovelHttpGet() {
	}

	public NovelHttpGet(URI uri) {
		super(uri);
	}

	public NovelHttpGet(String uri) {
		super(uri);
		setDefaultConfig();
	}

	private void setDefaultConfig(){
		this.setConfig(RequestConfig.custom()
				.setSocketTimeout(2_000)
				.setConnectTimeout(10_000)
				.setConnectionRequestTimeout(10_000)
				.build());
	}
}
