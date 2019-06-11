package kr.co.rtst.autosar.ap4x.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class AP4xCoreActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "kr.co.rtst.autosar.ap4x.core"; //$NON-NLS-1$

	// The shared instance
	private static AP4xCoreActivator plugin;
	
	/**
	 * The constructor
	 */
	public AP4xCoreActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AP4xCoreActivator getDefault() {
		return plugin;
	}
	
	/**
	 * 바이너리 형식의 파일을 읽어 내용을 byte[] 타입으로 반환한다. (바이너리 파일에 사용한다.)
	 * @param filepath
	 * @return
	 */
	public byte[] readEmbeddedFileToBytes(String filepath) {
		BufferedInputStream stream = null;
        try
        {
            URL url = getDefault().getBundle().getEntry((new StringBuilder("/")).append(filepath).toString());
            if(url != null)
            {
            	stream = new BufferedInputStream(url.openStream());                
                int avail = stream.available();
                byte buffer[] = new byte[avail];
                stream.read(buffer);                
                return buffer;
            }
        } catch(MalformedURLException e) { 
        	e.printStackTrace();
        } catch(IOException e) { 
        	e.printStackTrace();
        } finally {        	
        	if(stream != null){
        		try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return null;
    }
	
	/**
	 * 바이너리 형식의 파일을 읽어 내용을 byte[] 타입으로 반환한다. (바이너리 파일에 사용한다.)
	 * @param filepath
	 * @return
	 */
	public InputStream readEmbeddedFileToStream(String filepath) {
        try
        {
            URL url = getDefault().getBundle().getEntry((new StringBuilder("/")).append(filepath).toString());
            if(url != null)
            {
            	return url.openStream();
            }
        } catch(MalformedURLException e) { 
        	e.printStackTrace();
        } catch(IOException e) { 
        	e.printStackTrace();
        } finally {        	
        	
        }
        return null;
    }

}
