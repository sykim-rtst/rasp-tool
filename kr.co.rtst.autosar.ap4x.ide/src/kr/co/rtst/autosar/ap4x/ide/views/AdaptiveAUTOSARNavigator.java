package kr.co.rtst.autosar.ap4x.ide.views;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import kr.co.rtst.autosar.ap4x.core.model.observer.APModelChangeActionHandler;
import kr.co.rtst.autosar.ap4x.core.model.observer.IAPModelChangeActionObserver;
import kr.co.rtst.autosar.ap4x.ide.AP4xIDEActivator;
import kr.co.rtst.autosar.ap4x.ide.externalservice.IARObjectEventListener;
import kr.co.rtst.autosar.ap4x.ide.externalservice.ARObjectEventNotifier;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class AdaptiveAUTOSARNavigator extends CommonNavigator implements IAPModelChangeActionObserver, ITabbedPropertySheetPageContributor{
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "kr.co.rtst.autosar.ap4x.ide.views.AdaptiveAUTOSARNavigator";
	
	private APElementDoubleClickHandler apElementDoubleClickHandler;
	private APElementSelectionChangedListener apElementSelectionChangedListener;
	
	@Override
	public String getContributorId() {
		return null;
	}
	
	@Override
	public void createPartControl(Composite aParent) {
		APModelChangeActionHandler.apINSTANCE.addObserver(this);
		
		apElementDoubleClickHandler = new APElementDoubleClickHandler();
		apElementSelectionChangedListener = new APElementSelectionChangedListener();
		
		// TODO Auto-generated method stub
		super.createPartControl(aParent);
		
		getCommonViewer().addDoubleClickListener(apElementDoubleClickHandler);
		getCommonViewer().addPostSelectionChangedListener(apElementSelectionChangedListener);
//		System.out.println("getComparator::"+getCommonViewer().getComparator());
//		((CommonViewerSorter)getCommonViewer().getComparator()).
////		getCommonViewer().setComparator(new APElementComparator());
//		getCommonViewer().setSorter(new APElementComparator());
	}
	
	@Override
	public void dispose() {
		APModelChangeActionHandler.apINSTANCE.removeObserver(this);
		
		getCommonViewer().removeDoubleClickListener(apElementDoubleClickHandler);
		getCommonViewer().removePostSelectionChangedListener(apElementSelectionChangedListener);
		super.dispose();
	}
	
	private class APElementDoubleClickHandler implements IDoubleClickListener{
		@Override
		public void doubleClick(DoubleClickEvent event) {
//			System.out.println("doubleClick::"+event.getSource().getClass());
			if(event.getSource() instanceof CommonViewer) {
				if(((CommonViewer)event.getSource()).getStructuredSelection().getFirstElement() instanceof ARObject) {
					ARObjectEventNotifier.getInstance().handle(IARObjectEventListener.EVENT_DOUBLECLICK_ON_NAVIGATOR, (ARObject)(((CommonViewer)event.getSource()).getStructuredSelection().getFirstElement()));					
				}
//				System.out.println("SELECTION:"+((CommonViewer)event.getSource()).getStructuredSelection().getFirstElement().getClass());
			}
		}
	}
	
	private class APElementSelectionChangedListener implements ISelectionChangedListener{
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
//			System.out.println("selectionChanged::"+event.getSource());
		}
	}
	
	@Override
	public void preModelChange() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postModelChange() {
		getCommonViewer().refresh();
	}
	
//	public static void refresh() {
//		IViewPart view = AP4xIDEActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(AdaptiveAUTOSARNavigator.ID);
//		if(view != null) {
//			AdaptiveAUTOSARNavigator pe = (AdaptiveAUTOSARNavigator)view;
//			pe.getCommonViewer().refresh();
//		}
//	}
	
}
