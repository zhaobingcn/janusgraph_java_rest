import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.HttpSessionIdListener;

import org.junit.Test;

public class ClasspathTest
{
    @Test
    public void testJavaServlet() throws IOException
    {
        ClassLoader cl = this.getClass().getClassLoader();
        String classAsResource = HttpSessionIdListener.class.getName().replace('.','/') + ".class";
        Enumeration<URL> urls = cl.getResources(classAsResource);
        System.out.printf("Looking for: %s%n",classAsResource);
        while (urls.hasMoreElements())
        {
            URL url = urls.nextElement();
            System.out.printf("Found: %s%n",url.toExternalForm());
        }
    }
}
