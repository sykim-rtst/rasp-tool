package kr.co.rtst.autosar.ap4x.editor;

import org.eclipse.ui.IStartup;

import kr.co.rtst.autosar.ap4x.editor.ide.IDEServiceHandler;
import kr.co.rtst.autosar.ap4x.ide.externalservice.ARObjectEventNotifier;

public class APEditorPluginStartup implements IStartup {

	@Override
	public void earlyStartup() {
		ARObjectEventNotifier.getInstance().addARObjectEventListener(new IDEServiceHandler());
	}

}
