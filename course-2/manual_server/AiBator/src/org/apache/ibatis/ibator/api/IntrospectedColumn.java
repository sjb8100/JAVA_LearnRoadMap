/*
 * Copyright 2005 The Apache Software Foundation Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.apache.ibatis.ibator.api;

import java.sql.Types;
import java.util.Properties;

import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.config.IbatorContext;
import org.apache.ibatis.ibator.internal.util.StringUtility;

/**
 * This class holds information about an introspected column. The class has
 * utility methods useful for generating iBATIS objects.
 * 
 * @author Jeff Butler
 */
public class IntrospectedColumn
{
	protected String actualColumnName;

	protected int jdbcType;
	/**
	 * 是否需要jdbctype
	 */
	private boolean jdbcTypeable;

	protected String jdbcTypeName;

	protected boolean nullable;

	protected int length;

	protected int scale;

	protected boolean identity;

	protected String javaProperty;

	protected FullyQualifiedJavaType fullyQualifiedJavaType;

	protected String tableAlias;

	protected String typeHandler;

	protected IbatorContext ibatorContext;

	protected boolean isColumnNameDelimited;

	protected IntrospectedTable introspectedTable;

	protected Properties properties;

	// any database comment associated with this column. May be null
	protected String remarks;

	protected String defaultValue;

	/**
	 * Constructs a Column definition. This object holds all the information
	 * about a column that is required to generate Java objects and SQL maps;
	 */
	public IntrospectedColumn()
	{
		super();
		properties = new Properties();
	}

	/**
	 * 是否需要jdbctype
	 */
	public boolean isJdbcTypeable()
	{
		return jdbcTypeable;
	}

	/**
	 * 是否需要jdbctype
	 */
	public void setJdbcTypeable(boolean jdbcTypeable)
	{
		this.jdbcTypeable = jdbcTypeable;
	}

	public int getJdbcType()
	{
		return jdbcType;
	}

	public void setJdbcType(int jdbcType)
	{
		this.jdbcType = jdbcType;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public boolean isNullable()
	{
		return nullable;
	}

	public void setNullable(boolean nullable)
	{
		this.nullable = nullable;
	}

	public int getScale()
	{
		return scale;
	}

	public void setScale(int scale)
	{
		this.scale = scale;
	}

	/*
	 * This method is primarily used for debugging, so we don't externalize the
	 * strings
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("Actual Column Name: "); //$NON-NLS-1$
		sb.append(actualColumnName);
		sb.append(", JDBC Type: "); //$NON-NLS-1$
		sb.append(jdbcType);
		sb.append(", Nullable: "); //$NON-NLS-1$
		sb.append(nullable);
		sb.append(", Length: "); //$NON-NLS-1$
		sb.append(length);
		sb.append(", Scale: "); //$NON-NLS-1$
		sb.append(scale);
		sb.append(", Identity: "); //$NON-NLS-1$
		sb.append(identity);

		return sb.toString();
	}

	public void setActualColumnName(String actualColumnName)
	{
		this.actualColumnName = actualColumnName;
		isColumnNameDelimited = StringUtility
				.stringContainsSpace(actualColumnName);
	}

	/**
	 * @return Returns the identity.
	 */
	public boolean isIdentity()
	{
		return identity;
	}

	/**
	 * @param identity
	 *            The identity to set.
	 */
	public void setIdentity(boolean identity)
	{
		this.identity = identity;
	}

	public boolean isBLOBColumn()
	{
		String typeName = getJdbcTypeName();

		return "BINARY".equals(typeName) || "BLOB".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
				|| "CLOB".equals(typeName) || "LONGVARBINARY".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
				|| "LONGVARCHAR".equals(typeName) || "VARBINARY".equals(typeName); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean isStringColumn()
	{
		return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
				.getStringInstance());
	}

	public boolean isJdbcCharacterColumn()
	{
		return jdbcType == Types.CHAR || jdbcType == Types.CLOB
				|| jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR;
	}

	public String getJavaProperty()
	{
		return getJavaProperty(null);
	}

	public String getJavaProperty(String prefix)
	{
		if (prefix == null)
		{
			return javaProperty;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(javaProperty);

		return sb.toString();
	}

	public void setJavaProperty(String javaProperty)
	{
		this.javaProperty = javaProperty;
	}

	public boolean isJDBCDateColumn()
	{
		return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
				.getDateInstance()) && "DATE".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
	}

	public boolean isJDBCTimeColumn()
	{
		return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
				.getDateInstance()) && "TIME".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
	}

	public String getTypeHandler()
	{
		return typeHandler;
	}

	public void setTypeHandler(String typeHandler)
	{
		this.typeHandler = typeHandler;
	}

	public String getActualColumnName()
	{
		return actualColumnName;
	}

	public void setColumnNameDelimited(boolean isColumnNameDelimited)
	{
		this.isColumnNameDelimited = isColumnNameDelimited;
	}

	public boolean isColumnNameDelimited()
	{
		return isColumnNameDelimited;
	}

	public String getJdbcTypeName()
	{
		if (jdbcTypeName == null)
		{
			return "OTHER"; //$NON-NLS-1$
		}

		return jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName)
	{
		this.jdbcTypeName = jdbcTypeName;
	}

	public FullyQualifiedJavaType getFullyQualifiedJavaType()
	{
		return fullyQualifiedJavaType;
	}

	public void setFullyQualifiedJavaType(
			FullyQualifiedJavaType fullyQualifiedJavaType)
	{
		this.fullyQualifiedJavaType = fullyQualifiedJavaType;
	}

	public String getTableAlias()
	{
		return tableAlias;
	}

	public void setTableAlias(String tableAlias)
	{
		this.tableAlias = tableAlias;
	}

	public IbatorContext getIbatorContext()
	{
		return ibatorContext;
	}

	public void setIbatorContext(IbatorContext ibatorContext)
	{
		this.ibatorContext = ibatorContext;
	}

	public IntrospectedTable getIntrospectedTable()
	{
		return introspectedTable;
	}

	public void setIntrospectedTable(IntrospectedTable introspectedTable)
	{
		this.introspectedTable = introspectedTable;
	}

	public Properties getProperties()
	{
		return properties;
	}

	public void setProperties(Properties properties)
	{
		this.properties.putAll(properties);
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
}
