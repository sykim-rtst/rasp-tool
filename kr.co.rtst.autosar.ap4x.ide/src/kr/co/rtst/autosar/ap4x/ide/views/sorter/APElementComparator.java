package kr.co.rtst.autosar.ap4x.ide.views.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;

public class APElementComparator extends ViewerComparator {

	
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if(e1 instanceof IAPVirtualElement || e2 instanceof IAPVirtualElement) {
			if(e1 instanceof IAPVirtualElement && e2 instanceof IAPVirtualElement) {
				return Integer.compare(((IAPVirtualElement)e1).getIndex(), ((IAPVirtualElement)e2).getIndex());
			} else if(e1 instanceof IAPVirtualElement) {
				return 1;
			} else {
				return -1;
			}
		}
		return super.compare(viewer, e1, e2);
	}

}
