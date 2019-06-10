package kr.co.rtst.autosar.ap4x.core.model.observer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.rtst.autosar.ap4x.core.model.IAPProject;

public class APModelChangeActionHandler {
//	IViewPart view = AP4xIDEActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(AdaptiveAUTOSARNavigator.ID);
//	if(view != null) {
//		AdaptiveAUTOSARNavigator pe = (AdaptiveAUTOSARNavigator)view;
//		pe.getCommonViewer().refresh();
//	}
	
	public static APModelChangeActionHandler apINSTANCE = new APModelChangeActionHandler();
	
	private List<IAPModelChangeActionObserver> observerList;
	private Map<IAPProject, List<IAPModelChangeActionObserver>> observerProjectMap;
	
	private APModelChangeActionHandler(){
		observerList = new ArrayList<>();
		observerProjectMap = new LinkedHashMap<>();
	}
	
	public boolean addObserver(IAPModelChangeActionObserver observer) {
		if(!observerList.contains(observer)) {
			return observerList.add(observer);
		}
		return false;
	}
	
	public boolean removeObserver(IAPModelChangeActionObserver observer) {
		return observerList.remove(observer);
	}
	
	public boolean addObserver(IAPProject apProject, IAPModelChangeActionObserver observer) {
		if(!observerProjectMap.containsKey(apProject)) {
			observerProjectMap.put(apProject, new ArrayList<>());
		}
		if(!observerProjectMap.get(apProject).contains(observer)) {
			observerProjectMap.get(apProject).add(observer);
			return true;
		}
		return false;
	}
	
	public boolean removeObserver(IAPProject apProject, IAPModelChangeActionObserver observer) {
		if(observerProjectMap.containsKey(apProject)) {
			return observerProjectMap.get(apProject).remove(observer);
		}
		return false;
	}
	
	public void notifyPreModelChange(IAPProject apProject) {
		for (IAPModelChangeActionObserver observer : observerList) {
			observer.preModelChange();
		}
		if(apProject != null) {
			if(observerProjectMap.containsKey(apProject)) {
				for (IAPModelChangeActionObserver observer : observerProjectMap.get(apProject)) {
					observer.preModelChange();
				}
			}
		}
	}
	
	public void notifyPostModelChange(IAPProject apProject) {
		for (IAPModelChangeActionObserver observer : observerList) {
			observer.postModelChange();
		}
		if(apProject != null) {
			if(observerProjectMap.containsKey(apProject)) {
				for (IAPModelChangeActionObserver observer : observerProjectMap.get(apProject)) {
					observer.postModelChange();
				}
			}
		}
	}
}
