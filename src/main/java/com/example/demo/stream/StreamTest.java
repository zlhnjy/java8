package com.example.demo.stream;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Auther: zhangliang
 * @Date: 2019/4/22 19:39
 * @Description:
 */
public class StreamTest {

    public static void main(String[] args) {

//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    private static void test1() {
        List<String> titleList = Arrays.asList("aaa", "bbb", "ccc");
        titleList.forEach(System.out::println);
    }

    private static void test2() {
        List<String> words = Arrays.asList("aaabbb", "cccddd");
        List<String> list2 = words.stream().map(w -> w.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        list2.forEach(System.out::println);

        List<Integer> num1 = Arrays.asList(1, 2, 3);
        List<Integer> num2 = Arrays.asList(4, 5);
        List<int[]> pairs = num1.stream().flatMap(i -> num2.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
        pairs.forEach(p -> System.out.println(p[0] + " " + p[1]));
    }

    private static void test3() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        int sum = nums.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        int product = nums.stream().reduce(1, (a, b) -> a * b);
        System.out.println(product);

        int sum2 = nums.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        Optional<Integer> sum3 = nums.stream().reduce((a, b) -> a * b);
        sum3.ifPresent(System.out::println);

        nums.stream().sorted((a, b) -> b - a).forEach(System.out::println);
    }

    private static void test4() {
        IntStream.range(1, 10).forEach(System.out::println);
        IntStream.rangeClosed(1, 10).filter(i -> i % 2 == 0).forEach(System.out::println);
    }

    private static void test5() {
        Stream<String> stream1 = Stream.of("aaa", "bbb", "ccc");
        stream1.forEach(System.out::println);

        int[] numbers = {1, 2, 3, 4, 5};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);

        long wordCount = 0;
        try {
            File file = ResourceUtils.getFile("classpath:a.txt");
            Stream<String> lines = Files.lines(Paths.get(file.getPath()));
            wordCount = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println(wordCount);
            Files.lines(Paths.get(file.getPath())).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);
        Stream.generate(Math::random).limit(5).forEach(System.out::println);

        //斐波那契数列
        Stream.iterate(new int[]{0, 1}, a -> new int[]{a[1], a[0] + a[1]}).limit(10).map(b -> b[0]).forEach(System.out::println);
        IntSupplier fib = new IntSupplier() {
            private int pre = 0;
            private int cur = 1;
            @Override
            public int getAsInt() {
                int oldPre = pre;
                int next = pre + cur;
                this.pre = cur;
                this.cur = next;
                return oldPre;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }

}
