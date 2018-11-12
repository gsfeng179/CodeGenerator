package com.codegen.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class CustomAbstractXmlElementGenerator extends AbstractXmlElementGenerator {
    @Override
    public void addElements(XmlElement parentElement) {
        // 增加base_query
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "Base_Query"));
        //在这里添加where条件
        XmlElement selectTrimElement = new XmlElement("trim"); //设置trim标签
        selectTrimElement.addAttribute(new Attribute("prefix", "WHERE"));
        selectTrimElement.addAttribute(new Attribute("prefixOverrides", "AND | OR")); //添加where和and
        StringBuilder sb = new StringBuilder();
        for(IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            XmlElement selectNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append("null != ");
            sb.append(introspectedColumn.getJavaProperty());
            selectNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            sb.setLength(0);
            // 添加and
            sb.append(" and ");
            // 添加别名t
//            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            // 添加等号
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            selectNotNullElement.addElement(new TextElement(sb.toString()));
            selectTrimElement.addElement(selectNotNullElement);
        }
        sql.addElement(selectTrimElement);
        parentElement.addElement(sql);

        // 公用select
        sb.setLength(0);
//        sb.append("select ");
//        sb.append("* ");
        sb.append("from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
//        sb.append(" t");
        TextElement selectTextSelect = new TextElement("select ");

        TextElement selectTextFrom = new TextElement(sb.toString());

        // 公用include Base_Query
        XmlElement includeBaseQuery = new XmlElement("include");
        includeBaseQuery.addAttribute(new Attribute("refid", "Base_Query"));

        // 公用include Base_Column_List
        XmlElement includeBaseColumnList = new XmlElement("include");
        includeBaseColumnList.addAttribute(new Attribute("refid", "Base_Column_List"));


        // 增加find
        /*XmlElement find = new XmlElement("select");
        find.addAttribute(new Attribute("id", "find"));
        find.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        find.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        find.addElement(selectTextSelect);
        find.addElement(includeBaseColumnList);
        find.addElement(selectTextFrom);
        find.addElement(includeBaseQuery);
        parentElement.addElement(find);*/

        // 增加list
        XmlElement list = new XmlElement("select");
        list.addAttribute(new Attribute("id", "selectList"));
        list.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        list.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        list.addElement(selectTextSelect);
        list.addElement(includeBaseColumnList);
        list.addElement(selectTextFrom);
        list.addElement(includeBaseQuery);
        parentElement.addElement(list);

        // 增加pageList
        /*XmlElement pageList = new XmlElement("select");
        pageList.addAttribute(new Attribute("id", "pageList"));
        pageList.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        pageList.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        pageList.addElement(selectTextSelect);
        pageList.addElement(includeBaseColumnList);
        pageList.addElement(selectTextFrom);
        pageList.addElement(includeBaseQuery);
        parentElement.addElement(pageList);*/
    }
}
