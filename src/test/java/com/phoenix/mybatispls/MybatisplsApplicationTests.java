package com.phoenix.mybatispls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenix.mybatispls.entity.User;
import com.phoenix.mybatispls.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class MybatisplsApplicationTests {
    @Autowired
    UserMapper mapper;

    @Test
    void contextLoads() {
        List<User> users = mapper.selectList(null);
        System.out.println(users);
    }

    @Test
    void add() {
        User user = new User();
        user.setAge(11);
        user.setName("lucy");
        user.setEmail("23423@11.com");
        int insert = mapper.insert(user);
        System.out.println(insert);
    }

    @Test
    void update() {
        User user = new User();
        user.setId(1371425997836627969L);
        user.setAge(32);

        int i = mapper.updateById(user);
        System.out.println("update:"+i);

    }

    //自动填充
    @Test
    void testFill() {
        User user = new User();
        user.setAge(23);
        user.setName("逻辑删除");
        user.setEmail("hfh@wer.com");

        int insert = mapper.insert(user);
        System.out.println(insert);
        System.out.println(user);

    }

    @Test
    void testOptimisticLocker() {
        User user = mapper.selectById(1371436133749637122L);
        user.setEmail("hello@qq.com");
        mapper.updateById(user);
    }

    @Test
    void testOptimisticLockerFail() {
        User user = mapper.selectById(1371436133749637122L);
        user.setName("hel Yao");
        user.setEmail("hel@qq.com");
        user.setVersion(user.getVersion()-1);
        mapper.updateById(user);
    }

    @Test
    void testSelectId() {
        User user = mapper.selectById(1371436133749637122L);
        System.out.println(user);
    }

    @Test
    void testSelectBatchIds() {
        List<User> users = mapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    @Test
    void testSelectByMap() {
        //map中的key对应的是数据库中的列名;例如数据库user_id，实体类是userId，这时map的key需要填写user_id
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 12);
        List<User> users = mapper.selectByMap(map);
        users.forEach(System.out::println);

    }

    @Test
    void testSelectPage() {
        Page<User> page = new Page<>();
        mapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    @Test
    void testSelectMapsPage() {
        //此版本的selectMapsPage:
        IPage<Map<String, Object>> page = new Page<>(1, 5);

       mapper.selectMapsPage(page, null);
        page.getRecords().forEach(System.out ::println);
        System.out .println(page.getCurrent());
        System.out .println(page.getPages());
        System.out .println(page.getSize());
        System.out .println(page.getTotal());
    }

    @Test
    void testDeleteById() {
        int result = mapper.deleteById(3L);
        System.out.println(result);
    }

    @Test
    public void testDeleteBatchByIds() {
        int result = mapper.deleteBatchIds(Arrays.asList(8, 9, 10));
        System.out.println(result);
    }

    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 12);

        int i = mapper.deleteByMap(map);
        System.out.println(i);
    }

    @Test
    void testLogicDelete() {
        int result = mapper.deleteById(1371450667461783554L);
        System.out.println(result);
    }

    @Test
    void testLogicDeleteSelect() {
        List<User> users = mapper.selectList(null);
        users.forEach(System.out::println);
    }


    /**
     * 复杂Sql用Wrapper
     * Wrapper ：条件构造抽象类，最顶端父类
     * AbstractWrapper ：用于查询条件封装，生成 sql 的 where 条件
     * QueryWrapper ： Entity 对象封装操作类，不是用lambda语法
     * UpdateWrapper ： Update 条件封装，用于Entity对象更新操作
     * AbstractLambdaWrapper ： Lambda 语法使用 Wrapper统一处理解析 lambda 获取 column。
     * LambdaQueryWrapper ：看名称也能明白就是用于Lambda语法使用的查询Wrapper
     * LambdaUpdateWrapper ： Lambda 更新封装Wrapper
     */

}
