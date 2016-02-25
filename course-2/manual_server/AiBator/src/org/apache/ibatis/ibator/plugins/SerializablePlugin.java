/*
 * Copyright 2008 The Apache Software Foundation Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.apache.ibatis.ibator.plugins;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

/**
 * This plugin adds the java.io.Serializable marker interface to all generated
 * model objects.
 * <p>
 * This plugin demonstrates adding capabilities to generated Java artifacts, and
 * shows the proper way to add imports to a compilation unit.
 * <p>
 * Important: This is a simplistic implementation of serializable and does not
 * attempt to do any versioning of classes.
 * 
 * @author Jeff Butler
 */
public class SerializablePlugin extends IbatorPluginAdapter
{

	private FullyQualifiedJavaType serializable;
	//private FullyQualifiedJavaType serializable1;

	public SerializablePlugin()
	{
		super();
		serializable = new FullyQualifiedJavaType("java.io.Serializable"); //$NON-NLS-1$
	//	serializable1 = new FullyQualifiedJavaType("org.ace.dao.model.IModelSample"); //$NON-NLS-1$

	}

	public boolean validate(List<String> warnings)
	{
		// this plugin is always valid
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable)
	{
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable)
	{
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	protected void makeSerializable(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable)
	{
		topLevelClass.addImportedType(serializable);
		topLevelClass.addSuperInterface(serializable);
	//	topLevelClass.addImportedType(serializable1);
	//	topLevelClass.addSuperInterface(serializable1);

		Field field = new Field();
		field.setFinal(true);
		field.setInitializationString("1L"); //$NON-NLS-1$
		field.setName("serialVersionUID"); //$NON-NLS-1$
		field.setStatic(true);
		field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
		field.setVisibility(JavaVisibility.PRIVATE);
		ibatorContext.getCommentGenerator().addFieldComment(field,
				introspectedTable);

		topLevelClass.addField(0, field);
		
		String entityName = topLevelClass.getType().getShortName();
		for (char x = 'A'; x <= 'Z'; x++)
		{
			entityName = StringUtils.replace(entityName, x + "", "_" + x);
		}
		entityName = StringUtils.removeStart(entityName, "_").toUpperCase();
		
		/*Method m=new Method();
		m.addJavaDocLine("@Override");
		m.setName("selectId");
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setReturnType(new FullyQualifiedJavaType("String"));
		m.addBodyLine("return \""+entityName+".select\"");
		topLevelClass.addMethod(m);
		
		
		m=new Method();
		m.addJavaDocLine("@Override");
		m.setName("insertId");
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setReturnType(new FullyQualifiedJavaType("String"));
		m.addBodyLine("return \""+entityName+".insert\"");
		topLevelClass.addMethod(m);
		

		m=new Method();
		m.addJavaDocLine("@Override");
		m.setName("updateId");
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setReturnType(new FullyQualifiedJavaType("String"));
		m.addBodyLine("return \""+entityName+".update\"");
		topLevelClass.addMethod(m);
		
		m=new Method();
		m.addJavaDocLine("@Override");
		m.setName("deleteId");
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setReturnType(new FullyQualifiedJavaType("String"));
		m.addBodyLine("return \""+entityName.toUpperCase()+".delete\"");
		topLevelClass.addMethod(m);*/
	}
}
