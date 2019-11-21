package kr.co.rtst.autosar.ap4x.core.model;

/**
 * 프로젝트 네비게이터에 나타날 항목중 실제 ARXML항목이 아닌 논리적 계층관계를 위해 필요로 하는 요소를 표현하는 객체이다.
 * 이 가상요소는 최대 2단계의 깊이를 가지며, 프로젝트와 AP도구에서 관리하는 실제 ARXML요소들 사이에 존재한다.
 * @author thkim
 *
 */
public interface IAPVirtualElement {
	
	String VE_NAME_TYPE							= "Types";
	String VE_NAME_APPLICATION					= "Applications";
	String VE_NAME_SERVICE						= "Services";
	String VE_NAME_SYSTEM						= "System";
	
	String VE_NAME_TYPE_IMPL					= "ImplementationDataTypes";
	String VE_NAME_TYPE_APP						= "ApplicationDataTypes";
	String VE_NAME_APPLICATION_SWC				= "SwComponents";
	String VE_NAME_APPLICATION_PLATFORM			= "PlatformApplications";
	String VE_NAME_SERVICE_INTERFACE			= "Interfaces";
	String VE_NAME_SERVICE_DEPLOYMENT			= "Deployments";
	String VE_NAME_SERVICE_INSTANCE				= "Instances";
	String VE_NAME_SERVICE_INS_PROVIDED_SOMEIP  = "ProvidedSomeipServiceInstance";
	String VE_NAME_SERVICE_INS_REQUIRED_SOMEIP   = "RequiredSomeipServiceInstance";
	String VE_NAME_MACHINE_DESIGN				= "MachineDesigns";
	String VE_NAME_MACHINE						= "Machines";
	
	public int getIndex();
	
	public int getDepth();
	
	public IAPProject getApProject();
	
	public String getCategoryName();
	
	public String getName();
	
	public String getIconPath();
	
//	public String getPackageShortName();
	
//	public String getPackageFullName();
	
	public IAPVirtualElement getParent();
	
	public void setChildren(IAPVirtualElement[] children);
	
	public IAPVirtualElement[] getChildren();
}
