/*
 * Copyright 2020 lujing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mofa3.lang.jmh;

//import ma.glasnost.orika.BoundMapperFacade;
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import io.github.mofa3.lang.util.CommonConverter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author ${baizhang}
 * @version $Id: BeanCopyBenchmark.java, v 0.1 2019-02-19 2:30 PM Exp $
 * @BenchmarkMode Mode.Throughput
 * 在有时限的迭代里头，该方法能被调用多少次。整体吞吐量，例如“1秒内可以执行多少次调用”。
 * Mode.AverageTime
 * 方法平均执行时间
 * Mode.SampleTime
 * 对方法执行时间进行采样计算，随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
 * Mode.SingleShotTime
 * 方法的单次调用时间/一次批处理的总调用时间。
 * 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。
 * <p>
 * Iteration 是 JMH 进行测试的最小单位。在大部分模式下，一次 iteration 代表的是一秒，
 * JMH 会在这一秒内不断调用需要 benchmark 的方法，然后根据模式对其采样，计算吞吐量，计算平均执行时间等。
 * @Warmup 程序预热，iterations 预热轮数
 * @Measurement
 * @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
 * iterations 进行测试的轮次
 * time 每轮进行的时长
 * timeUnit 时长单位
 * @Threads 测试中线程数
 * @Fork fork次数
 * @OutputTimeUnit 测试结果时间类型，秒、毫秒、微秒
 * @State 从@State对象读取测试输入并返回计算的结果，方便JMH对冗余代码进行消除；
 * 如果是测试方法的性能，则避免通过在方法内循环（重复执行方法内原来代码），
 * 这样造成方法方法调用次数的减少，结果不准确，应该把循环调用放在方法外头。
 * 接受一个 Scope 参数用来表示该状态的共享范围。 因为很多 benchmark 会需要一些表示状态的类，
 * JMH 允许你把这些类以依赖注入的方式注入到 benchmark 函数里。Scope 主要分为三种：
 * Thread: 该状态为每个线程独享。
 * Group: 该状态为同一个组里面所有线程共享。
 * Benchmark: 该状态在所有线程间共享。
 * @Setup 方法注解，运行前初始化
 * @TearDown 方法注解，资源回收
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 2)
@Measurement(iterations = 5, time = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class BeanCopyBenchmark {
//    private MapperFactory mapperFactory;

//    @Setup
//    public void prepare() {
//         mapperFactory = new DefaultMapperFactory.Builder().build();
//    }
//    @TearDown
//    public void doow(){
//
//    }

    @Benchmark
    public void set() {
        CmsUser user = new CmsUser();
        user.setUserName("baizhang");
        user.setUserId(10000);
        user.setCreateTime(new Date());
        user.setPassword("123456");

        CmsUser1 user1 = new CmsUser1();
        user1.setUserName(user.getUserName());
        user1.setUserId(user.getUserId());
        user1.setCreateTime(user.getCreateTime());
        user1.setPassword(user.getPassword());
    }

    //    @Benchmark
//    public void orika(){
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//
//        CmsUser user = new CmsUser();
//        user.setUserName("baizhang");
//        user.setUserId(10000);
//        user.setCreateTime(new Date());
//        user.setPassword("123456");
////        mapperFactory.classMap(CmsUser.class, CmsUser1.class).exclude("abc").byDefault().register();
//        BoundMapperFacade<CmsUser, CmsUser1>
//                boundMapper = mapperFactory.getMapperFacade(CmsUser.class, CmsUser1.class);
//
//        CmsUser1 cmsUser1 = boundMapper.map(user);
//    }
    @Benchmark
    public void convertCglib() {
        CmsUser user = new CmsUser();
        user.setUserName("baizhang");
        user.setUserId(10000);
        user.setCreateTime(new Date());
        user.setPassword("123456");

        CmsUser1 user1 = new CmsUser1();

        CommonConverter.convert(user, user1);
    }

    @Benchmark
    public void javassistCopySynonyms() {
        CmsUser user = new CmsUser();
        user.setUserName("baizhang");
        user.setUserId(10000);
        user.setCreateTime(new Date());
        user.setPassword("123456");

        CmsUser1 user1 = new CmsUser1();

        FastCopy.copySynonyms(user, user1);
    }

    @Benchmark
    public void javassistCopyProperties() {
        CmsUser user = new CmsUser();
        user.setUserName("baizhang");
        user.setUserId(10000);
        user.setCreateTime(new Date());
        user.setPassword("123456");

        CmsUser1 user1 = new CmsUser1();

        FastCopy.copyProperties(user, user1);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BeanCopyBenchmark.class.getSimpleName())
                .output("/Users/mc/backupSpaces/mofa3/common-lang/target/BeanCopyBenchmark.log")
                .build();
        new Runner(options).run();

    }

}