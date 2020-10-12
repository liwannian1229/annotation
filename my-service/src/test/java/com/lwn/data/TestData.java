package com.lwn.data;


import com.lwn.BaseTest;
import com.lwn.common.BeanUtil;
import com.lwn.model.entity.People;
import com.lwn.model.entity.Student;
import com.lwn.model.mapper.PeopleMapper;
import com.lwn.model.mapper.StudentMapper;
import com.lwn.model.mapper.UserInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestData extends BaseTest {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testBeanUtils() {

        Student stu = new Student();
        stu.setName("学生姓名:小李");

        People people = new People();
        BeanUtil.acceptObject(stu, people);
        System.out.println(people.getName());
    }

    @Test
    public void testFunctionalInterface() {

        String c1 = "";
        // accept()有一个入参,没有返回值
        Consumer<String> consumer = c -> {

        };
        // get()没有入参,有返回值
        Supplier<String> supplier = () -> {

            return null;
        };
        // test()有一个入参,返回值为boolean
        Predicate<String> predicate = p -> {

            return false;
        };
        // apply()有一个入参,有返回值,将入参类型转为返回值类型
        Function<String, String> function = (f) -> {

            return "";
        };
        /**
         * 测试Function
         */
        People people = new People();
        // stu就是Student,第一个泛型类型
        testFunction(stu -> {
            stu.setName("120");
            BeanUtil.acceptObject(stu, people);

            // 返回值是第二个泛型类型
            return people;
        });
        /**
         * 测试supplier
         */
        testSupplier(() -> {

            // 返回值为泛型类型
            return "供应者";
        });
        /**
         * 测试consumer
         */
        testConsumer(c -> {
            System.out.println(c);
            // System.out::println;
        });
        /**
         * 测试predicate
         */
        // 谓语 p
        testPredicate(Objects::isNull);

    }

    // 函数
    public String testFunction(Function<Student, People> function) {

        Student student = new Student();
        student.setName("wannian");
        People people = new People();
        BeanUtil.acceptObject(student, people);

        // 入参类型是第一个泛型类型Student,返回值类型是第二个泛型类型People
        People apply = function.apply(student);

        return apply.toString();

    }

    //供应者
    public String testSupplier(Supplier<String> supplier) {

        // 返回值为泛型类型String,无参数
        return supplier.get();

    }

    // 消费者
    public void testConsumer(Consumer<String> consumer) {

        // 无返回值, 参数为泛型类型String
        consumer.accept("消费者");

    }

    // 谓语 predicate
    public boolean testPredicate(Predicate<String> predicate) {

        // 参数类型为泛型类型String,返回值为boolean
        return predicate.test("谓语");

    }

    @Test
    public void testSelect() {

        // Student stu = new Student();
//        stu.setName("斗尊强者");
//        studentMapper.insert(stu);
        /*QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "is_del");
        List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);*/
       /* QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,name,password,sex,is_del");
        List<UserInfo> select = userInfoMapper.selectList(queryWrapper);
        UserInfo userInfo = select.get(0);
        System.out.println(userInfo.toString());*/

    }

    @Test
    @Rollback(false)
    public void testInsert() {
       /* Student userInfo = new Student();
        for (int i = 0; i < 10; i++) {

            userInfo.setName("第" + i + 1 + "人");
            studentMapper.insert(userInfo);
        }*/
    }

    @Test
    @Rollback(false)
    public void testDelete() {
       /* Student student = new Student();
        student.setId(1);

        studentMapper.deleteById(student.getId());*/
    }

    @Test
    @Rollback(false)
    public void testUpdate() {
        /*Student student = new Student();
        student.setId(1);
        student.setName("大乱斗亚索变成瑞文");
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();*/
        //  studentMapper.updateById(student);
    }

    @Test
    public void testMD5() {
    }

    // 测试分页
    @Test
    public void testSelectPage() {

        /*Page<UserInfo> page = new Page<>(1, 10, true);  //当前页对象，每页3个
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id");
        System.out.println(userInfoMapper.selectCount(queryWrapper));
        userInfoMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println); //获取当前页对象遍历打印*/

    }
}
