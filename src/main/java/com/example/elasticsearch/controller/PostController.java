package com.example.elasticsearch.controller;

import com.example.elasticsearch.model.Employee;
import com.example.elasticsearch.model.Post;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

@RestController
public class PostController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @PostConstruct
    public void add(){
     //   elasticsearchTemplate.deleteIndex("projectname");
        if (!elasticsearchTemplate.indexExists("projectname")){
            elasticsearchTemplate.createIndex("projectname");
        }
        List<Post> posts=elasticsearchTemplate.queryForList(new NativeSearchQueryBuilder()
                .withIndices("projectname")
                .withTypes("post")
                .build(),Post.class);
        if (!CollectionUtils.isEmpty(posts)){
            return;
        }
        List<IndexQuery> indexQueries=new ArrayList<>();
        for (int i=0;i<40;i++){
            Post post=new Post();
            post.setId(i);
            post.setTitle(getTitle().get(i));
            post.setContent(getContent().get(i));
            post.setWeight(i);
            post.setUserId(i%10);
            indexQueries.add(new IndexQueryBuilder().withObject(post).build());
        }
        elasticsearchTemplate.bulkIndex(indexQueries);
    }


    private List<String> getTitle() {
        List<String> list = new ArrayList<>();
        list.add("???????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("?????????????????????????????");
        list.add("?????????????????????????????");
        list.add("???????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("?????????????????[1]????????????");
        list.add("?????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("???????????? ?????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("???????????????????????????????????");
        list.add("?????????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("?????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("???????????????????????????????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("??????????????????????????????????????");
        list.add("???????????????");
        list.add("???????????????");
        list.add("???????????????????????????????????");
        return list;
    }

    private List<String> getContent() {
        List<String> list = new ArrayList<>();
        list.add("?????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("???????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("???????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("???????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("??? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("???????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("???????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("???????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add("????????? ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ??????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        list.add(" ?????????????? ????????????????????????????????????????????????????????????????????????????????????????????????");
        return list;
    }

    @RequestMapping("/demo")
    public List<Post> demo(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        ScrolledPage<Post> employees=null;
        try {
            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title", "*");
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withIndices("projectname").withTypes("post")
                    .withQuery(queryBuilder).withPageable(new PageRequest(pageNo-1, pageSize))
                    .withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
            SearchQuery searchQuery = nativeSearchQueryBuilder.build();
//            employees = (ScrolledPage<Post>) elasticsearchTemplate.startScroll(3000, searchQuery, Post.class);
//            List<Post> list=new ArrayList<>();
//            list.addAll(employees.getContent());
//            while (employees.hasContent()){
//                employees=(ScrolledPage<Post>) elasticsearchTemplate.continueScroll(employees.getScrollId(),3000,Post.class);
//
//                list.addAll(employees.getContent());
//            }
//            elasticsearchTemplate.continueScroll()
            List<Post>   list=elasticsearchTemplate.queryForPage(searchQuery,Post.class).getContent();
//        elasticsearchTemplate.delete
            return list;
        }finally {
            if (employees!=null){
                elasticsearchTemplate.clearScroll(employees.getScrollId());
            }
        }
    }

    @RequestMapping(value = "/post-user",method = RequestMethod.GET)
    public boolean buildIndex(){

        Post post=new Post();
        post.setId(100);
        post.setUserId(1);
        post.setWeight(100);
        post.setContent("??????????????????");
        post.setTitle("?????????");
        Employee employee=new Employee();
        employee.setId("4213");
        employee.setFirstName("?????????");
        employee.setAbout("??????");
        employee.setAge(53);
        employee.setLastName("????????????");
        post.setEmployee(employee);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId("100")
                .withIndexName("projectname")
                .withType("post")
                .withParentId("4213")
                .withObject(post)
                .build();
        IndexQuery indexQuery1=new IndexQueryBuilder().withId("4213").withIndexName("projectname").withType("employee").withObject(post.getEmployee()).build();
        elasticsearchTemplate.index(indexQuery1);
        elasticsearchTemplate.index(indexQuery);
        return true;
    }

    @RequestMapping("/update")
    public String update(){
        return "";
    }
}