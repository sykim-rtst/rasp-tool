package kr.co.rtst.autosar.ap4x.ide.wizards.job;

import org.eclipse.core.runtime.IStatus;

public interface IAPMargeAndSaveJobObserver {
	
	void notifyJobFinish(IStatus status);

}
