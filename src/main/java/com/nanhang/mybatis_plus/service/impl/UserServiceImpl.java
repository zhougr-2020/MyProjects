package com.nanhang.mybatis_plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanhang.mybatis_plus.dao.UserMapper;
import com.nanhang.mybatis_plus.pojo.User;
import com.nanhang.mybatis_plus.service.UserService;
import com.nanhang.mybatis_plus.style.factory.PayService;
import com.nanhang.mybatis_plus.style.factory.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {


    @Value("${savePath}")
    private String savePath;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PayService payService;

    //雪花算法
//    @Autowired
//    private Snowflake snowflake;

//    @Resource
//    private User user;


    @Override
    public List<User> findAll() {
       // Object leftPop = redisTemplate.opsForList().leftPop("");
        // new QueryWrapper<User>().lambda().eq(StringUtils.isEmpty("he"), user -> user.getUsername(),"name");
//        log.info(savePath);
//        log.info(String.valueOf(snowflake.nextId()));
//        log.info("findAll");
//        log.debug("--------->findAll");
        IPage<User> page = new Page<>(1, 5); //当前页 每页几条
        //CollectionUtils.isEmpty()
        IPage<User> userIPage = userMapper.selectPage(page, null);
//        log.info("page"+userIPage.getRecords().toString());//当前页数据
//        log.info("count"+userIPage.getTotal()); //总数
//        log.info("page"+userIPage.getPages()+"   size "+  userIPage.getSize()); //page2   size 5
        Result result = payService.pay("weixin");
        return userMapper.selectList(null);
    }

    @Override
    public User findOne(Integer id) {
//        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(new Configuration());
//        SqlSession session = sqlSessionFactory.openSession();
        return userMapper.findOne(id);
    }

    @Override
    public int save() {
        // FileOutputStream fos =new FileOutputStream(new File("/aa"));
        User user1 = new User();
        user1.setUsername("wangwu");
        User user2 = new User();
        user2.setUsername("zhaoliu");
        userMapper.insert(user1);
        int i = 1 / 0;
        userMapper.insert(user2);

        return 1;
    }

    @Override
    public User findByUser(String username) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda().eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
//        if (user==null){
//            throw new UsernameNotFoundException("用户名错误");
//        }
        return user;
    }


    @Override
    public void update(List<User> list) {
        List<String> telephoneList = list.stream().map(User::getTelephone).collect(Collectors.toList());
        List<Integer> idList = list.stream().map(User::getId).collect(Collectors.toList());
        userMapper.updateUsers(list);
    }

    @Override
    public boolean insert(Integer number) {
        if (number >= 1) {
            List<User> list = new ArrayList<>();
            for (Integer integer = 0; integer < number; integer++) {
                User user = new User();
                user.setUsername("雷霆嘎巴");
                user.setPassword("leitinggaba");
                user.setAddress("广州");
                user.setTelephone("18508450750");
                user.setRole("admin");
                user.setSex("1");
                user.setPhone("07392902645");
                user.setIdcard("43058199504163013");
                user.setSchool("加里敦大学");
                user.setCity("广州");
                user.setCreatetime(LocalDateTime.now());

                list.add(user);
            }
            userMapper.insertList(list);
        }
        return true;
    }

    @Override
    public Result check(User user) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda().eq(User::getUsername, user.getUsername());
        return new Result(0,"success",userMapper.selectOne(queryWrapper));

    }


//    public static void main(String[] args) {
//        System.out.println(10/3);
//        System.out.println(10%3);
//    }

//    public static void main(String[] args) {
////        List<String> list =new ArrayList<>();
////        for (int i = 0; i < list.size(); i++) {
////            String s = list.get(0);
////        }
//
////        List<User> list=new ArrayList<>();
////        User user = new User(1, "zhangsan");
////        User user1=new User(2,"lisi");
////        list.add(user);
////        list.add(user1);
////        System.out.println(list.stream().map(User::getUsername).anyMatch(name -> name.equals("lisi")));
//
//    }

}
