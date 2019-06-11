package kr.co.rtst.autosar.common.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * 이미지 리소스 관리를 위한 객체이다.
 * 이 객체를 사용하는 곳에서는 직접 생성할 수 없고 Activator 객체를 이용한다.
 * 이클립스 내부적으로도 이미지 레지스트리 객체를 제공하고 있으나 원하는 기능을 임의 로 구현하기 위하여 별도의 객체를 구현하였다.
 * 
 * @author user
 *
 */
public class CommonUIImageRegistry {

	private Map<String, Image> fImageMap;
	private Map<String, ImageDescriptor> fImageDescMap;
	
	CommonUIImageRegistry() {
		fImageMap = new HashMap<String, Image>();
		fImageDescMap = new HashMap<String, ImageDescriptor>();
	}
	
	public Image getImage(String imageFilePath){
		Image image = fImageMap.get(imageFilePath);
		if(image == null){
			// 새로운 이미지의 요청이므로 생성하여 등록한다.
			image = CommonUIActivator.getImageDescriptor(imageFilePath).createImage();
			fImageMap.put(imageFilePath, image);
		}
		return image;
	}
	
	public ImageDescriptor getImageDescriptor(String imageFilePath){
		ImageDescriptor imageDesc = fImageDescMap.get(imageFilePath);
		if(imageDesc == null){
			// 새로운 이미지의 요청이므로 생성하여 등록한다.
			imageDesc = CommonUIActivator.getImageDescriptor(imageFilePath);
			fImageDescMap.put(imageFilePath, imageDesc);
		}
		return imageDesc;
	}
	
	public Image getImage(String pluginId, String imageFilePath){
		Image image = fImageMap.get(pluginId+"."+imageFilePath);
		if(image == null){
			// 새로운 이미지의 요청이므로 생성하여 등록한다.
			image = CommonUIActivator.imageDescriptorFromPlugin(pluginId, imageFilePath).createImage();
			fImageMap.put(pluginId+"."+imageFilePath, image);
		}
		return image;
	}
	
	public ImageDescriptor getImageDescriptor(String pluginId, String imageFilePath){
		ImageDescriptor imageDesc = fImageDescMap.get(pluginId+"."+imageFilePath);
		if(imageDesc == null){
			// 새로운 이미지의 요청이므로 생성하여 등록한다.
			imageDesc = CommonUIActivator.imageDescriptorFromPlugin(pluginId, imageFilePath);
			fImageDescMap.put(pluginId+"."+imageFilePath, imageDesc);
		}
		return imageDesc;
	}
	
	/**
	 * 생성된 모든 이미지 객체를 제거한다.
	 */
	void dispose(){
		fImageDescMap.clear();
		fImageDescMap = null;
		
		Iterator<Image> images = fImageMap.values().iterator();
		Image image = null;
		while(images.hasNext()){
			image = images.next();
			image.dispose();
			image = null;
		}
		fImageMap.clear();
		fImageMap = null;
	}
	
}
