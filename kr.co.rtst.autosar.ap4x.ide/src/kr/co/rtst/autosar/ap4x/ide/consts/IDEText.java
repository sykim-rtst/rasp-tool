package kr.co.rtst.autosar.ap4x.ide.consts;

public interface IDEText {
	
	String WIZARD_NEW_ADAPTIVE_AUTOSAR_PROJECT_TITLE	= "New Adaptive AUTOSAR project";
	// 프로젝트 마법사 첫페이지
	String WIZARD_MAIN_PAGE_TITLE						= "Adaptive AUTOSAR Project";
	String WIZARD_MAIN_PAGE_MESSAGE						= "Create a new Adaptive AUTOSAR project.";
	String WIZARD_ARXML_GROUP_NAME	 					= "ARXML options";
//	String WIZARD_PACKAGE_LABEL 						= "Package name: ";
	String WIZARD_PREDEFINED_ARXML_BUTTON 				= "Create a predefined ARXML";
//	String WIZARD_PACKAGE_ERROR_MESSAGE					= "Package name must be specified";
	
	// 프로젝트 마법사 두번째 페이지
	String WIZARD_SECOND_PAGE_TITLE						= "User-defined ARXML";
	String WIZARD_SECOND_PAGE_MESSAGE					= "Select the ARXML files to import from local system.";
	String WIZARD_INPORT_ARXML_BUTTON	 				= "Import user-defined ARXML";
	String WIZARD_INPORT_ARXML_ADD_BUTTON	 			= "Add...";
	String WIZARD_INPORT_ARXML_REMOVE_BUTTON 			= "Remove";
	String WIZARD_INPORT_ARXML_LIST_LABEL 				= "Selected ARXML files:";
	String[] WIZARD_INPORT_ARXML_TABLE_COLUMN			= {"Name", "Location"};
	
	// 대화창을 통하여 추가되는 엘리먼트 추가용 대화창
	String DIALOG_WINDOW_TITLE_NEW_ELEMENT				= "New %s";
	String DIALOG_TITLE_NEW_ELEMENT						= "%s";
	String DIALOG_MESSAGE_NEW_ELEMENT					= "Create a new %s";
	
	String DIALOG_FILTER_INITIAL_TEXT					= "package filter text";
	String DIALOG_PACKAGE_NAME_TEXT						= "Package name: ";
	String DIALOG_SHORT_NAME_TEXT						= "Short name: ";
}
