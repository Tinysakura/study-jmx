package com.cfh.studyjmx.mbean.model;

import javax.management.*;
import javax.management.modelmbean.*;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 构造modelMBeanInfo的工具类
 */

public class ModelMBeanUtils {
    private static final boolean READABLE = true;
    private static final boolean WRITABLE = true;
    private static final boolean BOOLEAN = true;
    private static final String STRING_CLASS = "java.lang.String";

    /**
     * 构造ModelMBeanInfo
     * @return
     */
    public static ModelMBeanInfo createModelMBeanInfo(){
        /**
         * 构造属性信息
         */
        /* 构造name属性描述信息 */
        Descriptor nameAttrDesc = new DescriptorSupport();
        nameAttrDesc.setField("name", "Name");
        nameAttrDesc.setField("descriptorType", "attribute");
        nameAttrDesc.setField("displayName", "Name");
        nameAttrDesc.setField("getMethod", "getName");
        nameAttrDesc.setField("setMethod", "setName");

        ModelMBeanAttributeInfo nameAttrInfo = new ModelMBeanAttributeInfo(
                "Name", // 属性名，大驼峰写法
                STRING_CLASS, // 属性全类名
                "name", // 描述文字
                WRITABLE, READABLE, !BOOLEAN, // 读, 写, 是否是布尔值
                nameAttrDesc // desc信息
        );

        /**
         * 构造方法信息
         */
        /* 构造getName方法描述信息 */
        Descriptor getStateDesc = new DescriptorSupport(new String[] {
                "name=getName", // 方法名
                "descriptorType=operation", // desc类型
                "class=com.cfh.studyjmx.mbean.model.Test", // 所在类的全类名
                "role=operation" // role 类型
        });

        ModelMBeanOperationInfo getName = new ModelMBeanOperationInfo(//
                "getName", // 方法名
                "get name attribute", // 描述文字
                null, //
                "java.lang.String", // 返回值类型
                MBeanOperationInfo.ACTION, //
                getStateDesc //desc信息
        );

        /* 构造setName方法描述信息 */
        Descriptor setStateDesc = new DescriptorSupport(new String[] {
                "name=setName",
                "descriptorType=operation",
                "class=com.cfh.studyjmx.mbean.model.Test",
                "role=operation" });

        // 有入参的方法需要构造MBeanParameterInfo
        MBeanParameterInfo[] setStateParms = new MBeanParameterInfo[] {(new MBeanParameterInfo(
                "name", "java.lang.String", "new name value")) };

        ModelMBeanOperationInfo setName = new ModelMBeanOperationInfo(//
                "setName", //
                "set name attribute", //
                setStateParms, //
                "void", //
                MBeanOperationInfo.ACTION, //
                setStateDesc //
        );

        /*构造 printHello()操作的信息*/
        ModelMBeanOperationInfo print1Info = new ModelMBeanOperationInfo(//
                "printHello", //
                null, //
                null, //
                "void", //
                MBeanOperationInfo.INFO, //
                null //
        );
        /* 构造printHello(String whoName)操作信息 */
        ModelMBeanOperationInfo print2Info;
        MBeanParameterInfo[] param2 = new MBeanParameterInfo[1];
        param2[0] = new MBeanParameterInfo("whoName", STRING_CLASS, "say hello to who");
        print2Info = new ModelMBeanOperationInfo(//
                "printHello", //
                null,//
                param2,//
                "void", //
                MBeanOperationInfo.INFO, //
                null//
        );

        /**
         * 整合AttributeInfo和OperationInfo
         */ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport(//
                RequiredModelMBean.class.getName(), // MBean类
                null, // 描述文字
                new ModelMBeanAttributeInfo[] { // 所有的属性信息（数组）
                        nameAttrInfo },//只有一个属性
                null, // 所有的构造函数信息
                new ModelMBeanOperationInfo[] { // 所有的操作信息（数组）
                        getName,
                        setName,
                        print1Info,
                        print2Info },// 这里有四个方法
                null, // 所有的通知信息(本例无)
                null//MBean描述
         );

         return mbeanInfo;
    }

    /**
     * 根据ModelMBeanInfo创建ModelBean并与托管的资源对象关联
     * @return
     */
    public static RequiredModelMBean createModelMBean() {
        RequiredModelMBean modelMBean = null;

        try {
            modelMBean = new RequiredModelMBean();
            // 设置ModelMBeanInfo
            modelMBean.setModelMBeanInfo(createModelMBeanInfo());
            // 设置托管对象
            modelMBean.setManagedResource(new Test(), "ObjectReference");
        } catch (MBeanException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidTargetObjectTypeException e) {
            e.printStackTrace();
        }

        return modelMBean;
    }
}