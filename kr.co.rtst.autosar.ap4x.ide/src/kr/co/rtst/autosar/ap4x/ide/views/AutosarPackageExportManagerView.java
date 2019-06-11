package kr.co.rtst.autosar.ap4x.ide.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class AutosarPackageExportManagerView extends ViewPart {
	
	public static final String ID = "kr.co.rtst.autosar.ap4x.ide.views.AutosarPackageExportManager";

	public AutosarPackageExportManagerView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@Override
	public void setFocus() {

	}

}
