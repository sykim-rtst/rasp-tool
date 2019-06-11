package kr.co.rtst.autosar.ap4x.ide.externalservice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IEditorPart;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

/**
 * IDE에서 ARObject와 관련되어 발생되는 이벤트를 등록된 리스너에 전파하는 객체
 * 외부 플러그인에서 이벤트를 받을 필요가 있을때 이 객체를 이용한다.
 * @author thkim
 *
 */
public class ARObjectEventNotifier {

	private static ARObjectEventNotifier instance;
	
	public static ARObjectEventNotifier getInstance() {
		if(instance == null) {
			instance = new ARObjectEventNotifier();
		}
		return instance;
	}
	
	private List<IARObjectEventListener> arObjectEventListeners;
	
	private ARObjectEventNotifier() {
		arObjectEventListeners = new ArrayList<>(); 
		
	}
	
	public void handle(int event, ARObject inputObject) {
		Object result = null;
		for (IARObjectEventListener arObjectEventListener : arObjectEventListeners) {
			result = arObjectEventListener.handle(event, inputObject);
			
			if(result != null) {
				// TODO
			}
		}
	}
	
	public void addARObjectEventListener(IARObjectEventListener serviceListener) {
		arObjectEventListeners.add(serviceListener);
	}
	
	public void removeARObjectEventListener(IARObjectEventListener serviceListener) {
		arObjectEventListeners.remove(serviceListener);
	}
	
}
