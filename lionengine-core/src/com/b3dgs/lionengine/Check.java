package com.b3dgs.lionengine;

/**
 * Utility class check.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * Check.argument(value &gt; 0, &quot;Value was not positive:&quot;, String.valueOf(value));
 * final Object object = null;
 * Check.notNull(object, &quot;Object is null !&quot;);
 * </pre>
 */
public final class Check
{
    /**
     * Check if the condition if <code>true</code>. Throws a {@link LionEngineException} if <code>false</code>.
     * 
     * @param condition The condition to check.
     * @param messages The messages to put in the exception.
     */
    public static void argument(boolean condition, String... messages)
    {
        if (!condition)
        {
            throw new LionEngineException(messages);
        }
    }

    /**
     * Check if the object is not <code>null</code>. Throws a {@link LionEngineException} if <code>null</code>.
     * 
     * @param object The object to check.
     * @param messages The messages to put in the exception.
     */
    public static void notNull(Object object, String... messages)
    {
        if (object == null)
        {
            throw new LionEngineException(messages);
        }
    }

    /**
     * Private constructor.
     */
    private Check()
    {
        throw new RuntimeException();
    }
}
