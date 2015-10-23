package com.itheima.core.solr.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.common.junt.SpringJunitTest;

public class SolrTest extends SpringJunitTest{

	@Autowired
	private SolrServer solrServer;
	
	//查询
		@Test
		public void testSolrForSearch() throws Exception {
			SolrQuery params =new SolrQuery();
			params.set("q", "*:*");
			QueryResponse response = solrServer.query(params);
						//结果集
			SolrDocumentList docs = response.getResults();
			long count = docs.getNumFound();
				
			System.out.println("结果集有 ："+count);
				for (SolrDocument doc : docs) {
					String id = (String) doc.get("id");
					String name=(String) doc.get("name");
					System.out.println("id :"+id+"---"+"name :"+name);
				}
		}
	
	@Test
	public void test2() throws Exception{
		SolrInputDocument doc = new SolrInputDocument();
		//ID
		doc.setField("id", "3");
		//名称
		doc.setField("name", "何灵");
		
		//添加
		solrServer.add(doc);
		//提交
		solrServer.commit();
	}

	@Test
	public void test1() throws Exception{
		String baseURL = "http://192.168.19.128:8080/solr";
		
		SolrServer solr=new HttpSolrServer(baseURL);
		SolrInputDocument doc=new SolrInputDocument();
		
		doc.setField("id", "2");
		doc.setField("name", "kobe");
		
		solr.add(doc);
		//提交
		solr.commit();
	}
}
