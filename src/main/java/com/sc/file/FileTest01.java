package com.sc.file;

import com.sc.entity.Scs;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTest01 {

    public static void main(String[] args) {
        File file = new File("aa.txt");
        BufferedReader br;
        List<String> data = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        List<String> dataOver90 = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            byte[] bytes = new byte[1024];
            String s = null;
            try {
                while ((s = br.readLine()) != null) {
                    String[] rowDetail = s.split(",");
                    if (Double.parseDouble(rowDetail[3]) > 90.0) {
                        dataOver90.add(s);
                        if (!map.containsKey(rowDetail[1])) {
                            map.put(rowDetail[1], 1);
                        } else {
                            map.put(rowDetail[1], map.get(rowDetail[1]) + 1);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        map.forEach((k, v) -> {
            if (v >= 3) {
                data.add(k);
            }
        });
        data.stream().forEach(e -> System.out.println(e));

    }

    @Test
    public void test01() {
        File file = new File("aa.txt");
        BufferedReader br;
        Map<String, Integer> map = new HashMap<>();
        List<Scs> students = new ArrayList<>(); //
        List<Scs> scoreOver90 = new ArrayList<>(); // 有3门成绩>90的学生及其各科目信息
        List<Scs> result = new ArrayList<>(); // 有3门成绩>90的学生信息
        List<Scs> results = new ArrayList<>(); // 有3门成绩>90的学生信息
        try {
            br = new BufferedReader(new FileReader(file));
            String s = null;
            try {
                while ((s = br.readLine()) != null) {
                    Scs scs = new Scs();
                    String[] rowDetail = s.split(",");
                    scs.setId(Integer.parseInt(rowDetail[0]));
                    scs.setNo(rowDetail[1]);
                    scs.setCourse(rowDetail[2]);
                    scs.setScore(Double.parseDouble(rowDetail[3]));
                    students.add(scs);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scoreOver90 = students.stream().filter(e -> e.getScore() > 90).collect(Collectors.toList());
        //scoreOver90.forEach(e -> System.out.println(e)); // >90的所有行
        Map<String, List<Scs>> collect = scoreOver90.stream().collect(Collectors.groupingBy(e -> e.getNo()));
        collect.forEach((k, v) -> {
            if(v.size() >= 3){
                results.addAll(v);
                result.add(new Scs().setNo(k));
            }
        });
        results.forEach(e -> System.out.println(e));
        result.forEach(e -> System.out.println(e));
    }
}
