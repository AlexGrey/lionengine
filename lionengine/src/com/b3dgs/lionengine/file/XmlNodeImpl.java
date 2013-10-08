/*
 * Copyright (C) 2013 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Element;

import com.b3dgs.lionengine.Check;

/**
 * XML node implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
final class XmlNodeImpl
        implements XmlNode
{
    /** Node name error. */
    private static final String ERROR_NODE_NAME = "The node name must not be null !";
    /** Child name error. */
    private static final String ERROR_CHILD_NAME = "The child name must not be null !";
    /** Root node error. */
    private static final String ERROR_ROOT_NODE = "The root node must not be null !";
    /** Attribute error. */
    private static final String ERROR_ATTRIBUTE = "The attribute must not be null !";

    /** Root reference. */
    private final Element root;

    /**
     * Create a root node.
     * 
     * @param name The node name.
     */
    XmlNodeImpl(String name)
    {
        Check.notNull(name, XmlNodeImpl.ERROR_NODE_NAME);
        root = new Element(name);
    }

    /**
     * Create a node.
     * 
     * @param root The root reference.
     */
    XmlNodeImpl(Element root)
    {
        Check.notNull(root, XmlNodeImpl.ERROR_ROOT_NODE);
        this.root = root;
    }

    /**
     * Get the original element.
     * 
     * @return The jdom element.
     */
    Element getElement()
    {
        return root;
    }

    /**
     * Get the attribute value.
     * 
     * @param attribute The attribute name.
     * @return The attribute value.
     */
    private String getAttributeValue(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        final String value = root.getAttributeValue(attribute);

        Check.notNull(value, "Can not read the attribute value for: \"", attribute, "\"");
        return value;
    }

    /**
     * Write a data to the root.
     * 
     * @param attribute The attribute name.
     * @param content The content value.
     */
    private void write(String attribute, String content)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        root.setAttribute(attribute, content);
    }

    /*
     * XmlNode
     */

    @Override
    public void add(XmlNode node)
    {
        if (node instanceof XmlNodeImpl)
        {
            root.addContent(((XmlNodeImpl) node).getElement());
        }
    }

    @Override
    public void writeBoolean(String attribute, boolean content)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        root.setAttribute(attribute, String.valueOf(content));
    }

    @Override
    public void writeByte(String attribute, byte content)
    {
        write(attribute, String.valueOf(content));
    }

    @Override
    public void writeShort(String attribute, short content)
    {
        write(attribute, String.valueOf(content));
    }

    @Override
    public void writeInteger(String attribute, int content)
    {
        write(attribute, String.valueOf(content));
    }

    @Override
    public void writeLong(String attribute, long content)
    {
        write(attribute, String.valueOf(content));
    }

    @Override
    public void writeFloat(String attribute, float content)
    {
        write(attribute, String.valueOf(content));
    }

    @Override
    public void writeDouble(String attribute, double content)
    {
        write(attribute, String.valueOf(content));
    }

    @Override
    public void writeString(String attribute, String content)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        if (content == null)
        {
            root.setAttribute(attribute, XmlNode.NULL);
        }
        else
        {
            root.setAttribute(attribute, content);
        }
    }

    @Override
    public boolean readBoolean(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Boolean.parseBoolean(getAttributeValue(attribute));
    }

    @Override
    public byte readByte(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Byte.parseByte(getAttributeValue(attribute));
    }

    @Override
    public short readShort(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Short.parseShort(getAttributeValue(attribute));
    }

    @Override
    public int readInteger(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Integer.parseInt(getAttributeValue(attribute));
    }

    @Override
    public long readLong(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Long.parseLong(getAttributeValue(attribute));
    }

    @Override
    public float readFloat(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Float.parseFloat(getAttributeValue(attribute));
    }

    @Override
    public double readDouble(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        return Double.parseDouble(getAttributeValue(attribute));
    }

    @Override
    public String readString(String attribute)
    {
        Check.notNull(attribute, XmlNodeImpl.ERROR_ATTRIBUTE);
        final String value = getAttributeValue(attribute);
        if (XmlNode.NULL.equals(value))
        {
            return null;
        }
        return value;
    }

    @Override
    public String getText()
    {
        return root.getText();
    }

    @Override
    public XmlNode getChild(String name) throws XmlNodeNotFoundException
    {
        Check.notNull(name, XmlNodeImpl.ERROR_CHILD_NAME);
        final Element child = root.getChild(name);
        if (child == null)
        {
            throw new XmlNodeNotFoundException("The following node " + name + " was not found !");
        }
        return new XmlNodeImpl(child);
    }

    @Override
    public List<XmlNode> getChildren(String name)
    {
        final List<XmlNode> nodes = new ArrayList<>(1);
        for (final Element element : root.getChildren(name))
        {
            nodes.add(new XmlNodeImpl(element));
        }
        return nodes;
    }

    @Override
    public List<XmlNode> getChildren()
    {
        final List<XmlNode> nodes = new ArrayList<>(1);
        for (final Element element : root.getChildren())
        {
            nodes.add(new XmlNodeImpl(element));
        }
        return nodes;
    }

    @Override
    public Map<String, String> getAttributes()
    {
        final Map<String, String> attributes = new HashMap<>();
        for (final Attribute attribute : root.getAttributes())
        {
            final String key = attribute.getName();
            attributes.put(key, getAttributeValue(key));
        }
        return attributes;
    }
}