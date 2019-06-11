package kr.co.rtst.autosar.ap4x.ide.externalservice;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

public interface IARObjectEventListener {
	
	int EVENT_DOUBLECLICK_ON_NAVIGATOR			= 1;
	
	Object handle(int event, ARObject inputObject);

}
