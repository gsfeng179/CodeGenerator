# Java代码生成器
## 介绍
这是一个基于数据库表<br/>用来自动生成 Model & Mapper & Service & ServiceImpl & Controller 等代码的代码生成器<br/>
使用者可以通过修改ftl模板来生成自己所需的基本代码块<br/>
主要是为了解决日常工作、练习中代码的重复工作量<br/>
目前仅支持 Mybatis 底层代码的生成

## 目录结构
```
|——src	
	|——main                                             
|		|——java					// 存放生成的代码
	|
|		|——resource
	|
|		|	|——mapper			// 存放生成的 Mapper.xml 文件
	|
|
	|——test
|		|——java					// 存放生成器的代码
	|
|		|	|——com
	|
|		|	|	|——codegen
	|
|		|	|	|	|——main
	|
|						|——CleanMainPackage.java // 清空src下java、resources包
|						|——CodeGeneratorMain.java // 代码生成器启动项
	|
|		|	|	|	|——service
	|
|						|——CodeGeneratorManager.java // 代码生成器基础项 (通用方法)
	|
|						|——CodeGeneratorConfig.java // 所有的配置信息变量
	|
|						|——CodeGenerator.java // 主要逻辑接口
	|
|		|	|	|	|	|——impl
	|
|							|——ControllerGenerator.java // Controller层 代码生成器
	|
|							|——ServiceGenerator.java // Service层 代码生成器
	|
|							|——ModelAndMapperGenerator.java // Model&Mapper 代码生成器
	|
	|
|		|	|	|	|——util
	|
|						|——StringUtils.java // 字符串操作常用方法集
	|
|		|——resource
	|
|		|	|——generatorConfig.properties // 配置文件
	|
|		|	|——generator
	|
|		|	|	|——template				// 存放ftl模板代码
	|		
|					|——controller.ftl	// Controller 模板代码
	|		
|					|——service.ftl		// Service 模板代码
	|		
|					|——service-impl.ftl	// ServiceImpl 模板代码
```

## 使用说明
#### 运行
进入到 `src/test/java` 目录下<br />
找到`CodeGeneratorMain`类 为生成器的启动项<br />
直接 `Run As Java Application` 运行即可<br />
需要清空src下java、resources包，可以找到`CleanMainPackage`类执行main方法<br/>

#### 修改配置
进入到 `src/test/resources` 目录下<br />
找到 `generatorConfig.properties` 文件<br />
修改对应的参数即可<br />
具体的注释信息可参考 `/src/test/java/com/codegen/service/CodeGeneratorConfig.java` 类<br />

#### Mybatis 通用插件
dev分支，删除了对Mybatis 通用插件的支持，如果需要可以看master分支

#### 入口说明
支持批量生成。map的key为表名，value为实体名<br/>
以表名 gen_test_demo 为例子,<br/>
```
Map<String, String> map = Maps.newHashMap();
map.put("pc_uac_user", "UacUser");
```
#### 模板样式修改
如果需要生成自己所需的 Controller & Service & ServiceImpl 样式<br/>
进入到 `src/test/resources/generator/template` 目录下<br />
修改对应的ftl文件即可

#### 数据库表名规则
推荐表名的格式类似 `gen_test_userinfo` 需要下划线分割<br />
`gen` 作为项目别名<br />
`test` 作为区分字段, 用于分包<br />
`userinfo` 可作为实体类名<br />
当然表名也可以为 `gen_test_user_info` 与 `gen_test_userinfo` 类似<br />

#### 使用过程遇到问题
使用过程仍存在相关Bug，可以提issue，也可以提交解决后的PR<br />

## 相关参考文档
- MyBatis（[查看官方中文文档](http://www.mybatis.org/mybatis-3/zh/index.html)）
