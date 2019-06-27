package com.example.elasticsearch.controller;

import com.example.elasticsearch.dao.EmployeeRepository;
import com.example.elasticsearch.model.Employee;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/es")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @RequestMapping("/add")
    public String add(){
        Employee employee=new Employee();
        employee.setId("1");
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        employeeRepository.save(employee);
        System.out.println("add a obj");
        return "success";
    }
    @RequestMapping("/add-query")
    public String addQueryIndex(){
        Employee employee=new Employee();
        employee.setId("2");
        employee.setFirstName("yiyi");
        employee.setLastName("hh");
        employee.setAge(23);
        employee.setAbout("i am in pig");
        return elasticsearchTemplate.index(new IndexQueryBuilder().withIndexName("projectname").withType("employee").withObject(employee).build());
    }

    @RequestMapping("/delete")
    public String delete(){
        Employee employee=employeeRepository.queryEmployeeById("1");
        employeeRepository.delete(employee);
        return "success";
    }
    @RequestMapping("/update")
    public String update(){
        Employee employee=employeeRepository.queryEmployeeById("1");
        employee.setFirstName("哈哈");
        employeeRepository.save(employee);
        return "success";
    }
    @RequestMapping("/query")
    public List<Employee> query(@RequestParam String id){
        SearchQuery searchQuery=new NativeSearchQueryBuilder()
                .withIndices("projectname")
                .withTypes("employee")
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withQuery(QueryBuilders.matchQuery("about","i am in pig")).build();
        return elasticsearchTemplate.queryForList(searchQuery,Employee.class);
    }
}
