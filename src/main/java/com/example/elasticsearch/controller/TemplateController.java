package com.example.elasticsearch.controller;

import com.example.elasticsearch.model.User;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/template/es")
public class TemplateController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private static List<User> list = new ArrayList<>();
    static {
        for (int i = 0 ;i <100 ;i++){
            list.add(new User(i,"name "+i,i));
        }
    }

    @RequestMapping(value = "/create/index",method = RequestMethod.POST)
    public boolean createIndex(){
        return elasticsearchTemplate.createIndex("userindex");
    }

    @RequestMapping(value = "/add/index",method = RequestMethod.POST)
    public boolean addDocument() {
        elasticsearchTemplate.bulkIndex(list.stream()
                .map(user -> new IndexQueryBuilder().withType("user").withIndexName("userindex").withObject(user).build())
                .collect(Collectors.toList()));
        return true;
    }

    @RequestMapping(value = "/query/index",method = RequestMethod.GET)
    public List<User> queryDocument(){
        return elasticsearchTemplate.queryForList(
                new NativeSearchQueryBuilder().withPageable(new PageRequest(0,100))
                        .withQuery((QueryBuilders.matchQuery("name","name")))
                        .withTypes("user").withIndices("userindex").build(),User.class
        );
    }

}
