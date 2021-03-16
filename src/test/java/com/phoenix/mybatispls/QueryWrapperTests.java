package com.phoenix.mybatispls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.phoenix.mybatispls.entity.User;
import com.phoenix.mybatispls.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryWrapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("name")
                .ge("age", 23)
                .isNotNull("email");
        int delete = userMapper.delete(queryWrapper);
        System.out.println(delete);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Sandy");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age", 0, 24);
        Integer integer = userMapper.selectCount(queryWrapper);
        System.out.println(integer);

    }


    @Test
    public void testSelectlist() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 2);
        map.put("name", "Jack");
        map.put("age", 20);

        queryWrapper.allEq(map);
        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    @Test
    public void testSelectMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike("name", "e")
            .likeRight("email", "t");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    @Test
    public void testSelectObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "Select id from user where id <3");
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void testUpdate1() {
        User user = new User();
        user.setName("Andy");
        user.setAge(23);

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.like("name", "a")
                .or()
                .between("age", 20, 30);

        int update = userMapper.update(user, userUpdateWrapper);
        System.out.println(update);

    }

    /**
     * or 嵌套查询
     */
    @Test
    public void testUpdate2() {
        User user = new User();
        user.setAge(99);
        user.setName("Andy2");

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.like("name", "h")
                .or(i->i.eq("name", "乐观锁").ne("age", 20));

        int update = userMapper.update(user, userUpdateWrapper);
        System.out.println(update);
    }

}
