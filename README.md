# XqkjBasicTools

#### 介绍
工程下载后 先使用 mvn clean install -Dmaven.test.skip=true 对项目进行一次编译，这样注解处理器会生成一些依赖的代码；
本工程包含两个项目，其中XqkjAnnotionProcesser为注解及其编译期处理器；XqkjCommons这个为组件类工程

基础服务组件工具包，目前只包含下面一个小插件

1。简单excel文件导出插件


#### 使用说明
1。简单excel文件导出插件使用说明

spring工程下推荐使用方法为如下所示

首先定义一个导出服务类
```java
@service
public class SimpleExcelExportServiceImpl implements SimpleExcelExportService{
    //获取一个简单excel文件生成分发管理接口的实例，支持使用单例接口获取，也可以new一个，建议使用单例模式
    private SimpleExcelExportFacade simpleExcelExportFacade = SingletonDefSimpleExcelExportFacade.getInstance();
    //SimpleExcelExportFacade simpleExcelExportFacade=new DefSimpleExcelExportFacade();
    //可以直接实现一个进度存储器--自行实现
    //@Autowired
    //private RunInforDao runInfoDao;
    //也可使用默认的进度管理器--目前简单存储了导出进度，需要注入一个redis操作模版
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @PostConstruct
    public void init() {
        ExcelProxFactory.loadSelf();//最好调用一下
        SimpleExcelExportConfigVO configVO=new SimpleExcelExportConfigVO();
        configVO.setStringRedisTemplate(stringRedisTemplate);
        //使用前必须先初始化
        simpleExcelExportFacade.init(configVO);
        //如果需要可以添加切面，对导出功能进行扩展   
        //添加切面的方法-01--直接实现ProxyMethodIntecepter接口，然后显示添加
        ProxyMethodIntecepterChainManagerContainer.addIntecepterToManager(
                IntecepterManagerNames.ExcelFileWriter_WriteHeaderRowData,
                new ExcelFileWriterWriteHeaderRowDataIntecepter()
        );
        //添加切面的方法-02--继承基础类BasicMethodIntecepterImpl，然后调用init方法隐式添加
        new ExcelFileWriterWriteBodyRowDataIntecepter().init();
    }
    
    //成功时HandleResult的entity为进度对象的key值
    @Override
    public HandleResult<String> doExportExcel(ExportExcelRequestVO requestVO) {
        //同步导出接口
        //HandleResult<String> handleResult=simpleExcelExportFacade.synExportExcel(requestVO);
        //异步导出接口
        HandleResult<String> handleResult=simpleExcelExportFacade.asyExportExcel(requestVO);
        return handleResult;
    }
    
    //获取导出进度，导出完成后的URL地址也在里面，如果是本地导出，文件的绝对路径也在里面。
    @Override
    public List<RuninforModel> listRunInfoModel(RuninforQuery query) {
        //单例模式下有个静态方法，也可使用实例获取
        //List<RunInforModel> modelList = SingletonDefSimpleExcelExportFacade.listRunInfoModel(query);
        List<RunInforModel> modelList = simpleExcelExportFacade.queryRunInfoList(query);
        return modelList;
    }
}


```

同时根据需要定义多个导出处理器，每个处理器都应该继承 BasicAnnoDataExcelExportHandler，在spring框架下，
可以自动调用BasicAnnoDataExcelExportHandler#init方法--加了@PostConstruct注解
```java
@Component
public class TestModelDataExcelExportHandler extends BasicAnnoDataExcelExportHandler<TestModel,TestPageQuery> {

    public static final String keyName="TestModelDataExcelExportHandler";

    private static final int pageSize=20;

    @Override
    protected TestPageQuery getPageQueryByRequest(ExportExcelRequestVO requestVO) {
        TestPageQuery query=new TestPageQuery();
        query.setPageSize(pageSize);
        query.setPageNo(1);
        return query;
    }

    @Override
    protected Class<TestModel> getDataTypeClass(ExportExcelRequestVO requestVO) {
        return TestModel.class;
    }

    @Override
    protected ExcelWriteExtInfoVO getExcelWriteExtInfoVO(ExportExcelRequestVO requestVO) {
        //这个是导出excel文件，调用写excel文件的参数，这里可以传递一些定制化配置等；具体参见该类
        //例如 传递-excelHeaderRowWriterName--一个已经向ExcelProxFactory注册过的ExcelHeaderRowWriter组件来替换默认的表头写组件
        //    传递-excelRowCellWriterName--一个已经向ExcelProxFactory注册过的ExcelRowCellWriter组件来替换默认的表体单元格写组件
        ExcelWriteExtInfoVO excelWriteExtInfoVO = new ExcelWriteExtInfoVO();
        excelWriteExtInfoVO.setZipAble(false);
        excelWriteExtInfoVO.setExportType("test");
        return excelWriteExtInfoVO;
    }

    @Override
    protected FileUpLoadExtInfoVO getFileUpLoadExtInfoVO(ExportExcelRequestVO requestVO) {
        return null;
    }

    @Override
    protected PageListDateMaker<TestModel,TestPageQuery> getPageListMaker(ExportExcelRequestVO requestVO) {
        Map<String, Object> globalInfoMap = new HashMap<>();
        int sumCount = 223;
        globalInfoMap.put("currentCount", 0);
        //数据生成方法,这个param就是上面getPageQueryByRequest返回的结果
        return (PageListDateMaker) param -> {

            int currentCount = (Integer) globalInfoMap.get("currentCount");
            List<TestModel> modelList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                TestModel testModel = new TestModel();
                testModel.setId(currentCount);
                testModel.setName("测试-" + currentCount);
                testModel.setRemarks("remarker-" + currentCount);
                modelList.add(testModel);
                currentCount++;
                if (currentCount > sumCount) {
                    break;
                }
            }
            globalInfoMap.put("currentCount", currentCount);
            PageInforModel<TestModel> pageInforModel = new PageInforModel<>();
            pageInforModel.setPageSize(pageSize);
            pageInforModel.setCurrentPage(param.getPageNo());
            pageInforModel.setDate(modelList);
            pageInforModel.setSumCount(sumCount);
            //
            return pageInforModel;
        };
    }

    @Override
    public String handlerKey() {
        return keyName;
    }

    @Override
    protected FileUpLoader getCoustomerFileUpLoader(){
        //返回一个文件上传处理器，fileUpLoadExtInfoVO 来自于上面的-getFileUpLoadExtInfoVO
        return (excelFile, showFileName, fileUpLoadExtInfoVO) -> {
            System.out.println("doUploadFlie uid="+fileUpLoadExtInfoVO.getUid());
            FileUpLoadResult fileUpLoadResult=new FileUpLoadResult();
            fileUpLoadExtInfoVO.setUid(fileUpLoadExtInfoVO.getUid());
            fileUpLoadExtInfoVO.setUploadServiceUrl("getCoustomerFileUpLoader/test.xlsx");
            fileUpLoadResult.setFileUrl("downloadUrl/test.xlxs");
            return HandleResult.success(fileUpLoadResult);
        };
    }
}

```

导出插件技术文档

导出的过程大致为 生成一个文件或者流--》循环生成数据并写入excel，并将数据刷入文件或者流 --》数据生成完毕，开始上传文件到中央文件服务器
--》文件上传完成 --》请求下载文件，通过进度监视器使用的持久化工具获取文件下载地址

主要包括 excel文件生成组件 文件上传组件 导出进度监视器组件 数据导出请求处理器 数据导出请求分发器

1。excel文件生成组件 主要包含下面两组接口

向excel文件写数据的接口-com.xqkj.commons.export.ExcelFileWriter和com.xqkj.commons.export.AnnoDataExcelFileWriter，
该接口可以完成向给定的excel表格的某个sheet页写入数据，该接口接口添加类代理切面注解（插件字节的代理方案），在代理模式下可以被包裹
在拦截器中，默认的添加了多个事件发送拦截器，用来向进度监控器报告写数据进度。

该接口的默认实现为-com.xqkj.commons.export.impl.DefExcelFileWriter以及com.xqkj.commons.export.impl.DefAnnoDataExcelFileWriter
默认实现支持动态切换表头写实现和数据行单元格写实现--动态切换方法为在excelWriteExtInfoVO中指定要使用的表头或者行数据的组件的名字，
前提是该组件托管给了代理工厂（ExcelProxFactory）；DefExcelFileWriter和DefAnnoDataExcelFileWriter的区别是后者的数据格式与前者不同，实现
的时候，后者通过转换后调用了前者的写数据方法。

生成文件并写数据接口-com.xqkj.commons.export.ExcelFileMaker和com.xqkj.commons.export.AnnoDataExcelFileMaker，
该接口可以生成一个excel文件，并使用excel文件写数据的接口（ExcelFileWriter或者AnnoDataExcelFileWriter）写入数据。

2。文件上传组件 

相关接口-com.xqkj.commons.export.FileUpLoader，这个一般需要使用者自行实现，默认提供了一个http上传实现com.xqkj.commons.export.impl.DefFileUploader

3。导出进度监视器组件

接口为-com.xqkj.commons.progress.RunProgressMonitor，建议使用事件机制响应进度处理。默认的实现为com.xqkj.commons.progress.impl.DefExportExcelRunProgressMonitor
该实现是通过响应excel文件表格导出过程中发出的事件来监控进度。进度监视器持有一个进度持久化工具类--RunInforDao，该类负责存取进度信息，提供的默认实现
使用redis存取数据，参见--com.xqkj.commons.progress.impl.DefRedisRunInforDao

4。数据导出请求处理器

数据导出处理器是导出数据的处理模版，该模版主要用于生成数据，模版为--com.xqkj.commons.export.handler.BasicAnnoDataExcelExportHandler
和com.xqkj.commons.export.handler.BasicNormalDataExcelExportHandler；两个模版的区别为查询后数据的格式不同，一般使用前者基本够用。、
开发过程中每新增加一个类型的数据基本就要实现一个处理器。

5。数据导出请求分发器

数据分发处理器会将请求提分发给相关的数据导出处理器--根据请求参数的handerKey去寻找对应拥有相同handerKey(handlerKey()方法返回)的数据处理器
相关的类有--com.xqkj.commons.export.handler.SimpleExcelExportHandlerDispatcher 以及 相关的门面接口 
com.xqkj.commons.export.impl.DefSimpleExcelExportFacade，平时开发中基本使用门面模式即可；分发器支持将请求先返送到处理器中--比如异步
处理器，然后由处理器回调处理接口，对数据进行异步处理--处理器器可以检查请求是否可以被接受等。