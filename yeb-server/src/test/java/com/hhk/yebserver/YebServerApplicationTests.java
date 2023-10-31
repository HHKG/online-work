package com.hhk.yebserver;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class YebServerApplicationTests {

    @Test
    void contextLoads() {
        List<Person> list = new ArrayList<>();
        list.add(new Person(1, "Tom"));
        list.add(new Person(2, "Jerry"));
        int index = list.indexOf(new Person("Tom")); // index的值将是0
        System.out.println(index);
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @RequiredArgsConstructor // 有参构造,会生成一个包含常量，和标识了NotNull的变量的构造方法。
    public class Person {
        @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
        private int id;
        @NonNull
        private String name;
        // 省略构造方法和get/set方法
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Person)) {
                return false;
            }
            Person p = (Person) obj;
            return this.name.equals(p.name);
        }
    }

}
