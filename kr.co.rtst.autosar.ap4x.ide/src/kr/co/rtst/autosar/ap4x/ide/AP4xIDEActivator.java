package kr.co.rtst.autosar.ap4x.ide;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import kr.co.rtst.autosar.ap4x.ide.externalservice.IARObjectEventListener;

/**
 * The activator class controls the plug-in life cycle
 */
public class AP4xIDEActivator extends AbstractUIPlugin implements IResourceChangeListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "kr.co.rtst.autosar.ap4x.ide"; //$NON-NLS-1$

	// The shared instance
	private static AP4xIDEActivator plugin;
	
	private IDEImageRegistry ideImageRegistry;
	
	/**
	 * The constructor
	 */
	public AP4xIDEActivator() {
	}
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
//		IResource resource = event.getResource();
//		if(resource == null && event.getDelta() != null && event.getDelta().getAffectedChildren() != null && event.getDelta().getAffectedChildren().length>0) {
//			resource = event.getDelta().getAffectedChildren()[0].getResource();
//		}
//		if(resource != null) {
//			try {
//				resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		ideImageRegistry = new IDEImageRegistry();
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		
		plugin = null;
		super.stop(context);
		
		ideImageRegistry.dispose();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AP4xIDEActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public IDEImageRegistry getIdeImageRegistry() {
		return ideImageRegistry;
	}

}
