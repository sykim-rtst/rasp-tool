package kr.co.rtst.autosar.ap4x.ide.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.observer.APModelChangeActionHandler;
import kr.co.rtst.autosar.ap4x.core.model.observer.IAPModelChangeActionObserver;

public class ElementModifyActionWrapper extends Action {

	private final IAPProject apProject;
	private final String category;
	private final IAction coreAction;

	public ElementModifyActionWrapper(IAPProject apProject, String catetory, IAction coreAction) throws NotSupportedAPActionException{
		super();
		this.apProject = apProject;
		this.category = catetory;
		this.coreAction = coreAction;
		setText(lookupActionText(coreAction.getText()));
		System.out.println(String.format("--------------------ElementModifyActionWrapper생성: Catetory:%30s,\t\tCoreName:%30s,\t\tChange Name:%30s", catetory, coreAction.getText(), getText()));
	}
	
	@Override
	public void run() {
		APModelChangeActionHandler.apINSTANCE.notifyPreModelChange(apProject);
		super.run();
		this.coreAction.run();
		APModelChangeActionHandler.apINSTANCE.notifyPostModelChange(apProject);
	}
	
	private String lookupActionText(String coreActionText) throws NotSupportedAPActionException {
		switch(coreActionText) {
		// # Adaptive Application SW Component Type
		case "Adaptive Swc Internal Behavior": return "Internal Behavior";
		case "PPort Prototype": return "PPort";
		case "RPort Prototype": return "RPort";
		// RPort
		case "Client Com Spec": return coreActionText;
		case "Persistency Data Required Com Spec": return coreActionText;
		case "Queued Receiver Com Spec": return "Receiver Com Spec";
		case "Nonqueued Receiver Com Spec": throw new NotSupportedAPActionException("'"+category+"->"+coreActionText+"' is not supported Action!");
		// PPort
		case "Server Com Spec": return coreActionText;
		case "Persistency Data Provided Com Spec": return coreActionText;
		case "Queued Sender Com Spec": return "Sender Com Spec";
		case "Nonqueued Sender Com Spec": throw new NotSupportedAPActionException("'"+category+"->"+coreActionText+"' is not supported Action!");
		
		
		// # Service Interface
		case "Variable Data Prototype": return "Event";
		case "Client Server Operation": return "Method";
		case "Application Error": return "Error";
		case "Argument Data Prototype": return "Argument";
		
		
		// # Service Interface Deployment
		case "Someip Event Deployment":
			if("Notifier".equals(category)) { // Someip Field Deployment 의 서브메뉴인 케이스이다.
				throw new NotSupportedAPActionException("'"+category+"->"+coreActionText+"' is not supported Action!");
			}
			return "Event Deployment";
		case "Someip Method Deployment":
			if("Get".equals(category) || "Set".equals(category)) { // Someip Field Deployment 의 서브메뉴인 케이스이다.
				throw new NotSupportedAPActionException("'"+category+"->"+coreActionText+"' is not supported Action!");
			}
			return "Method Deployment";
		case "Someip Field Deployment": return "Field Deployment";
			
		
		// # Machine Design
		case "Ethernet Communication Connector": return "Communication Connector";
		case "Someip Service Discovery": return "Service Discovery Configuration";
		case "Network endpoint": return coreActionText;
		case "Unicast network endpoint": return coreActionText;
		case "Multicast sd ip address": return "Multicast Service Discovery IP address";
		
		
		// # Machine
		case "Mode Declaration Group Prototype":
			if("Machine Mode Machines".equals(category)) { // 속성으로 처리되어 있다.
				throw new NotSupportedAPActionException("'"+category+"->"+coreActionText+"' is not supported Action!");
			}
			return "Function Group";
		}
		return coreActionText;
	}
}
