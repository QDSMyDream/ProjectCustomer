package com.mnn.mydream.cosmetology.utils;


import javax.xml.validation.Schema;

public class GreenDao {

    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
//      Schema schema = new Schema(1, "com.mnn.mydream.cosmetology");
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
//      Schema schema = new Schema(1, "me.itangqi.bean");
//      schema.setDefaultJavaPackageDao("me.itangqi.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
//        addOneToOne(schema);
//        addConfig(schema);
//        addICUConfig(schema);
//        addCallLog(schema);
//        addZoneManager(schema);
//        addwords(schema);
//        addUserInfo(schema);

        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        // new DaoGenerator().generateAll(schema, "I:\\workSpace\\MyDemoYXS\\app\\src\\main");
    }

    /**
     * @param schema
     */
    private static void addOneToOne(Schema schema) {

    }

    private static void addZoneManager(Schema schema) {


    }

    private static void addICUConfig(Schema schema) {

    }

    private static void addConfig(Schema schema) {


    }

    private static void addCallLog(Schema schema) {

//通话类型:
//1 : 主机与家属分机，
//2：家属分机与主机
//3. 会见分机与主机
//4. 主机与会见分机
//5. 家属分机与会见分机
//6.会见分机与家属分机
    }

    //添加留言数据库
    private static void addwords(Schema schema) {

    }


    private static void addUserInfo(Schema schema) {


    }


}
