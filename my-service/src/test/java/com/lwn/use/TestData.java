package com.lwn.use;


import com.lwn.BaseTest;
import com.lwn.model.entity.People;
import com.lwn.model.entity.Student;
import com.lwn.model.mapper.PeopleMapper;
import com.lwn.model.mapper.StudentMapper;
import common.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestData extends BaseTest {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testBeanUtils() {

        Student stu = new Student();
        stu.setName("学生姓名:小李");

        People people = new People();
        BeanUtils.acceptObject(stu, people);
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
            BeanUtils.acceptObject(stu, people);

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
        testConsumer(p -> {
            // 谓语 p
        });

    }

    // 函数
    public String testFunction(Function<Student, People> function) {

        Student student = new Student();
        student.setName("wannian");
        People people = new People();
        BeanUtils.acceptObject(student, people);

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
    @Rollback(false)
    public void testCRUD() {

        Student stu = new Student();
        stu.setName("斗尊强者");
        studentMapper.insert(stu);

    }
}
