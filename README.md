# mofa3
mofa二方包，代码内容尽与业务系统无关，引入后能快速上手开发工程，约定统一开发规范。

- ~~common-error~~
统一异常处理模块，避免嵌套依赖问题，整合到`common-lang`中

- common-lang
公共工具lang模块

- cache-starter
缓存模块

- common-client
http client模块

- log-starter
aop日志模块

- common-validator
校验处理模块

- common-template
Controller Service Dao模板引擎模块

- db-starter
mybatis数据库模块


## 模块说明及使用
### ~~mofa-common-error~~
    异常处理模块，统一异常处理，自定义异常类型，CheckParamException、BizProcessException、SystemRunningException、CustomCodeExceptio接口返回只有提示异常信息，不包含异常堆栈信息，
#### 常用自定义异常使用场景
  
- CheckParamException 

  参数校验异常，在Controller，或者接口参数方法中使用，对非空，格式校验等信息，使用该异常抛出

- BizProcessException 

  业务处理异常，在Service层中的异常处理，需要往上层抛出的异常，使用该异常抛出

- SystemRunningException 

  系统运行时异常，抛出必须把异常信息往上抛出

- CustomCodeExceptio
 
  自定义异常码异常，调用服务这需要判断返回状态码进行特殊处理的返回，定义状态码需要遵循响应异常枚举类中区间值，业务处理相关3001-3999，业务码统一，是比较难维护，很复杂的，目前是进行大类归类，开放自定义异常码，在使用实践过程中规范后再统一常量化维护管理。
  
  
```
  /**
 * 服务不可用
 */
SERVICE_IS_NOT_AVAILABLE("1000", "SERVICE_IS_NOT_AVAILABLE", "服务不可用", "服务不可用"),
/**
 * 参数校验失败
 */
PARAMETER_CHECK_FAIL("2001", "PARAMETER_CHECK_FAIL", "参数校验失败", "参数校验失败"),
/**
 * 业务处理失败
 */
BIZ_PROCESS_FAIL("3001", "BIZ_PROCESS_FAIL", "业务处理失败", "业务处理失败"),
/**
 * 第三方调用异常
 */
THIRD_PARTY_FAIL("3002", "THIRD_PARTY_FAIL", "第三方调用异常", "第三方调用异常"),
/**
 * 运行时异常
 */
SYSTEM_RUNNING_THROWABLE("4001", "THIRD_PARTY_FAIL", "第三方调用异常", "第三方调用异常"),

```
  
  
#### 特殊场景自定义异常使用

- ThirdPartyException

  第三方接口调用异常，第三方接口调用，包括不局限于http、rpc、webservice等调用异常，使用该异常抛出。
  
- ServiceIsNotAvailableException

  服务不可用异常，在网关层，或者熔断处理中，服务不可用的情况下，使用该异常抛出。


#### mofa-common-lang
公共工具模块，模块下包名：
 
- constant

    ①Constants公共常量，常用的字符串，如MD5、RSA、GBK、连接符号=、（）、{}、等，具体参见代码：[Constants.java](https://git.dian.so/bigdata/mofa3/blob/master/mofa-common-lang/src/main/java/so/dian/mofa3/lang/constant/Constants.java)

    ②HttpConstants http常量，常用的http header context-type参数，如：multipart/form-data、application/json、text/plain等，具体参见代码：[HttpConstants.java](https://git.dian.so/bigdata/mofa3/blob/master/mofa-common-lang/src/main/java/so/dian/mofa3/lang/constant/HttpConstants.java)
    


- domain

  ErrorContext 错误上下文
  Result Controller返回result对象

- enums

  这的枚举类都是mofa二方包中使用的枚举类，DateEnum 时间工具类中使用的枚举

- money

  货币，金额处理工具类，强烈推荐涉及到钱的计算和使用都使用该类，统一使用该类处理，统一计算口径，数据库存储单位统一为`分`，币种默认CNY，使用：

```
// 构造一个货币对象，单位为元
MultiCurrencyMoney amount1= new MultiCurrencyMoney("10");
MultiCurrencyMoney amount2= new MultiCurrencyMoney("5");
// 相加
MultiCurrencyMoney amount3= amount1.add(amount2);

// 构造函数没有构造单位为分的方法，可以通过如下方法初始化一个分的货币
// 实例化一个空对象
MultiCurrencyMoney amount= new MultiCurrencyMoney();
// 设置金额，分
amount.setCent(10);
```
在计算中，如果涉及到精度计算，以`BigDecimal`计算

```
MultiCurrencyMoney money= new MultiCurrencyMoney("100000");
BigDecimal lv= new BigDecimal("0.051");
BigDecimal day= new BigDecimal("178");
BigDecimal year= new BigDecimal("365");
// 实际收益 = 本金×年化收益率×投资天数 / 365
MultiCurrencyMoney all= money.multiply(lv).multiply(day).divide(year);
System.out.println(all.getAmount().doubleValue());
```
计算工具类，参见代码：[money工具类](https://git.dian.so/bigdata/mofa3/blob/master/mofa-common-lang/src/main/java/so/dian/mofa3/lang/money/MultiCurrencyMoneyUtil.java)


简单举几个使用示例，使用过程中有疑问尽快提出，详情参见代码：[money](https://git.dian.so/bigdata/mofa3/tree/master/mofa-common-lang/src/main/java/so/dian/mofa3/lang/money)

- util

  ①CacheHessianUtils 序列化工具类，标记为不推荐，将来会删除

  ②CommonConverter 对象属性拷贝工具类，缓存化cglib实现提高拷贝效率，不支持深度拷贝

  ③CustomConverter 自定义转换器，针对Cglib不能转换的类型手动处理

  ④DateBuild 时间构造器，使用java8实现，包含开始时间、结束时间、年月日时分秒±n、构建，避免多样化的格式处理，构造函数只有Date和LocalDateTime类型，有自定义的字符串转换成时间可以使用。
  
  DateUtil.strToDate转换后在作为参数传入。默认提供格式化实现：

```

 public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter SIMPLE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter SIMPLE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter SIMPLE_SHORT_DATE = DateTimeFormatter.ofPattern("yyMMdd");

// 如果格式不满足需要，可以自定义DateTimeFormatter作为参数传入使用
// 几个使用示例
// 获取当天开始时间 yyyy-MM-dd 00:00:00，Date类型返回
new DateBuild().start().toDate();
 
// 获取当天结算时间yyyy-MM-dd 23:59:59，字符串类型返回
// DateTimeFormatter可以自定义格式化
new DateBuild().end().formatter();
 
// 增加为正数，减少为负数
// 当前时间增加1年2个月3天
new DateBuild().addToYear(1).addToMonth(2).addToDay(3);
 
// 当前时间减15分钟
new DateBuild().addToMin(-15);

```

  Tips：由于java8对 年月日和时分秒是分开处理的，所以在使用`LocalDateTime`直接处理`yyyyMMdd`会有问题，所以构造方法参数的值约定了两个类型，个性化时间字符串转换使用`DateUtil.strToDate`转换后处理。代码参见:[DateBuild.java](https://git.dian.so/bigdata/mofa3/blob/master/common-lang/src/main/java/so/dian/mofa3/lang/util/DateBuild.java)

  ⑤DateUtil 时间工具类，分开写的原因，一个是负责构建，一个是常用的方法，实现写法上有一些差距，所以分开写了，目前包含的方法时差计算，时间比较，字符串转date，时间戳（秒、毫秒）获取。

  ⑥ExtendPropUtil 扩展属性工具类，格式化Map和字符串直接的相互转换，代码参见：[ExtendPropUtil](https://git.dian.so/bigdata/mofa3/tree/master/mofa-common-lang/src/main/java/so/dian/mofa3/lang/util)

  ⑦MD5Util MD5加密工具类

  ⑧Profiler 性能监控埋点工具类，代码块性能监控，会在性能日志log中输出

  ⑨RadomCodeUtil 随机码生成，包括数字，字母，数字字母，大小写等等自定义长度组合字符串工具类，可以作为验证码，短信码等随机组合字符串生成，不支持排重。参见代码：[RadomCodeUtil.java](https://git.dian.so/bigdata/mofa3/blob/master/mofa-common-lang/src/main/java/so/dian/mofa3/lang/util/RadomCodeUtil.java)


  ⑩SequenceUtil 序号生成工具类，不推荐使用，以后可能会删除

  ⑪SpringUtil spring bean获取工具类

  ⑫XmlUtil xml工具类，针对微信支付的报文解析写的，注意XEE访问外部实体的问题（已经禁止访问外部实体）

#### mofa-cache-starter
~~缓存模块，目前有redis支持，提供的redis方法，系统默认配置过期时间30天，需要延长存储时间的元素，手动设置过期时间，提供方法： `put` 存入 `get` 获取 `putExpired` 存入并设置过期时间 `delete` 删除 `incrBy` 自定义自增数量 `llen` 获取集合长度 `rPop` 移出并返回最右边元素 `rPopPipeline` 批量操作，移出并返回最右边元素 `rPushPipeline` 批量操作，队列存入元素。
缓存模块使用自动装配，只需要做如下配置即可使用：~~
  
  在`0.01.01`版本中，redis操作实现改为`Redisson`实现，默认配置过期时间30天，需要长时间存放的对象，可以使用`putExpire`设置`Integer.MAX`实现长时间存储。

#### mofa-common-client
  client模块，目前基于Apache httpClient实现，包括标准RSETFul 请求、文件下载等，使用示例（post请求）：

```
/**
 * http header
 */
private static final Header[] HEADERS = HttpHeader.custom().contentType(HttpConstants.APP_FORM_URLENCODED).build();
HttpBuilder httpBuilder = HttpClientPoolSingle.getInstance();
HttpConfig cfg =HttpConfig.custom().headers(HEADERS).client(httpBuilder.build()).url(url).json(xml);
// post
String respStr = HttpClientUtil.post(cfg);
```

#### mofa-log-starter
目前有代码块性能监控日志和mybatis sql执行日志，默认已配置到logback。

#### mofa-validator
  1、对需要检验的属性添加注解，比如@NotNull，@Length。

  2、使用ValidationUtils的静态进行检验。
ValidationUtils.validateEntityThrowParameterException 进整个对象所有带有注解的属性进检验。
ValidationUtils.validatePropertyThrowParameterException针对某个属性进行检验


#### mofa-common-template

  模板引擎模块，封装Controller 模板方法，统一Controller编写规范，根据业务逻辑，请求参数验证、参数转换、幂等控制、执行，模板流程化处理逻辑，原则上写入操作必须严格按照在对应的方法中完善内容。（是否需要幂等根据业务需要或者项目评审结果来决定），如果有回调响应等需要符合第三方要求的返回，可以不是用该模板方法，但是实现上必须遵守该流程：

```
@RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Result createPay() {
        return template.execute(new ControllerCallback<String>() {
            // 参数检查
            @Override
            public void checkParam() {
                if(1==1){
                    throw new CheckParamException("交易订单号不能为空");
                }
            }

            // 参数构建
            @Override
            public void buildContext() {
                System.out.println("buildContext");
            }

            // 幂等处理，非写操作和非关键业务，可以不用实现幂等操作
            @Override
            public void checkConcurrent(){

            }

            // 执行
            @Override
            public String execute() {


                return "";
            }
        });
    }
```
####




