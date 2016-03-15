/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.editor.utility;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.UtilFile;
import com.b3dgs.lionengine.Verbose;
import com.b3dgs.lionengine.editor.Activator;
import com.b3dgs.lionengine.editor.project.Project;
import com.b3dgs.lionengine.editor.project.Property;
import com.b3dgs.lionengine.game.object.ObjectConfig;
import com.b3dgs.lionengine.stream.Xml;
import com.b3dgs.lionengine.stream.XmlNode;

/**
 * Series of tool functions around the editor related to classes.
 */
public final class UtilClass
{
    /**
     * Get the class from media file, by reading the attribute {@link ObjectConfig#CLASS} attribute.
     * 
     * @param media The media descriptor.
     * @return The class reference.
     * @throws LionEngineException If not able to create the class.
     */
    public static Class<?> get(Media media)
    {
        final XmlNode root = Xml.load(media);
        final String className = root.getChild(ObjectConfig.CLASS).getText();
        return Project.getActive().getClass(className);
    }

    /**
     * Get all classes that implements the specified type.
     * 
     * @param <C> The class type.
     * @param type The type to check.
     * @return The implementing class list.
     */
    public static <C> Collection<Class<? extends C>> getImplementing(Class<C> type)
    {
        final Collection<Class<? extends C>> found = new HashSet<>();
        final Collection<File> places = new HashSet<>();

        places.addAll(getPotentialClassesContainers(Project.getActive().getClassesPath()));
        places.addAll(getPotentialClassesContainers(Project.getActive().getLibrariesPath()));

        for (final File file : places)
        {
            if (isJar(file))
            {
                found.addAll(getImplementingJar(type, file));
            }
            else if (file.isDirectory())
            {
                found.addAll(getImplementing(type, file));
            }
        }

        found.addAll(getImplementing(type, Activator.getLocation()));

        return found;
    }

    /**
     * Get all classes that implements the specified type.
     * 
     * @param <C> The class type.
     * @param type The type to check.
     * @param root The folder or jar to search.
     * @return The implementing class list.
     */
    public static <C> Collection<Class<? extends C>> getImplementing(Class<C> type, File root)
    {
        final Collection<Class<? extends C>> found = new HashSet<>();
        if (root.isDirectory())
        {
            final Collection<File> folders = new HashSet<>();
            folders.add(root);
            while (!folders.isEmpty())
            {
                final Collection<File> foldersToDo = new HashSet<>();
                for (final File folder : folders)
                {
                    checkImplementing(folder, found, foldersToDo, type, root);
                }
                folders.clear();
                folders.addAll(foldersToDo);
                foldersToDo.clear();
            }
        }
        return found;
    }

    /**
     * Check if file is a jar.
     * 
     * @param file The file to check.
     * @return <code>true</code> if jar, <code>false</code> else.
     */
    public static boolean isJar(File file)
    {
        return UtilFile.isType(file, Constant.TYPE_JAR);
    }

    /**
     * Get all classes that implements the specified type.
     * 
     * @param <C> The class type.
     * @param folder The current folder.
     * @param found The list of class found.
     * @param foldersToDo The next folders to check.
     * @param type The type to check.
     * @param root The folder or jar to search.
     */
    private static <C> void checkImplementing(File folder,
                                              Collection<Class<? extends C>> found,
                                              Collection<File> foldersToDo,
                                              Class<C> type,
                                              File root)
    {
        for (final File current : UtilFile.getFiles(folder))
        {
            if (current.isDirectory())
            {
                foldersToDo.add(current);
            }
            else if (current.isFile())
            {
                final int prefix = Project.getActive().getClassesPath().getPath().length() + 1;
                if (prefix < current.getPath().length())
                {
                    checkAddClass(found, type, root, current.getPath().substring(prefix));
                }
            }
        }
    }

    /**
     * Get the list of potential file descriptor which may contains classes (could be folder or jar).
     * 
     * @param file The file root.
     * @return The collection of places.
     */
    private static Collection<File> getPotentialClassesContainers(File file)
    {
        final Collection<File> places = new HashSet<>();
        places.add(file);
        if (file.isDirectory())
        {
            places.addAll(getJars(UtilFile.getFiles(file)));
        }
        return places;
    }

    /**
     * Get all jar in files.
     * 
     * @param files The files.
     * @return The jars.
     */
    private static Collection<File> getJars(Collection<File> files)
    {
        final Collection<File> jars = new HashSet<>();
        for (final File current : files)
        {
            if (isJar(current))
            {
                jars.add(current);
            }
        }
        return jars;
    }

    /**
     * Check for classes inside jar.
     * 
     * @param <C> The class type.
     * @param type The type to check.
     * @param file The jar file.
     * @return The implementing class list.
     */
    private static <C> Collection<Class<? extends C>> getImplementingJar(Class<C> type, File file)
    {
        final Collection<Class<? extends C>> found = new HashSet<>();
        try (final JarFile jar = new JarFile(file))
        {
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements())
            {
                final JarEntry entry = entries.nextElement();
                if (!entry.isDirectory())
                {
                    checkAddClass(found, type, null, entry.getName());
                }
            }
        }
        catch (final IOException exception)
        {
            Verbose.exception(exception);
        }
        return found;
    }

    /**
     * Get class that implements the specified type.
     * 
     * @param <C> The class type.
     * @param type The type to check.
     * @param root The folder to search.
     * @param current The current class file to check.
     * @return The implementing class reference.
     */
    private static <C> Class<? extends C> getImplementing(Class<C> type, File root, String current)
    {
        String name = current.replace(Property.EXTENSION_CLASS, Constant.EMPTY_STRING)
                             .replace(File.separator, Constant.DOT)
                             .replace(Constant.SLASH, Constant.DOT);
        if (root != null)
        {
            name = name.replace(root.getPath(), Constant.EMPTY_STRING);
        }
        if (name.charAt(0) == '.')
        {
            name = name.substring(1);
        }

        final Project project = Project.getActive();
        final Class<?> clazz = project.getClass(name);
        if (type.isAssignableFrom(clazz) && clazz != type)
        {
            return clazz.asSubclass(type);
        }
        return null;
    }

    /**
     * Check if can add class to collection, and add it if possible.
     * 
     * @param <C> The class type.
     * @param found The current classes found.
     * @param type The type to check.
     * @param root The folder or jar to search.
     * @param name The class name.
     */
    private static <C> void checkAddClass(Collection<Class<? extends C>> found, Class<C> type, File root, String name)
    {
        if (name.endsWith(Property.EXTENSION_CLASS))
        {
            try
            {
                final Class<? extends C> clazz = getImplementing(type, root, name);
                if (clazz != null)
                {
                    found.add(clazz);
                }
            }
            catch (final LionEngineException exception)
            {
                return;
            }
        }
    }

    /**
     * Private constructor.
     */
    private UtilClass()
    {
        throw new LionEngineException(LionEngineException.ERROR_PRIVATE_CONSTRUCTOR);
    }
}
