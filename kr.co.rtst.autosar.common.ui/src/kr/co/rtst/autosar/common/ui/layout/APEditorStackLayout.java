package kr.co.rtst.autosar.common.ui.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class APEditorStackLayout extends StackLayout {

	@Override
	protected boolean flushCache(Control control) {
		return true;
	}
	
	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
//		return super.computeSize(composite, wHint, hHint, flushCache);
		Control children[] = composite.getChildren();
		int maxWidth = 0;
		int maxHeight = 0;
		for (int i = 0; i < children.length; i++) {
			Point size = children[i].computeSize(wHint, hHint, flushCache);
			maxWidth = Math.max(size.x, maxWidth);
			maxHeight = Math.max(size.y, maxHeight);
		}
		int width = maxWidth + 2 * marginWidth;
		int height = maxHeight + 2 * marginHeight;
		if (wHint != SWT.DEFAULT) width = wHint;
		if (hHint != SWT.DEFAULT) height = hHint;
		return new Point(width, height);
	}
	
	@Override
	protected void layout(Composite composite, boolean flushCache) {
//		super.layout(composite, flushCache);
		Control children[] = composite.getChildren();
		Rectangle rect = composite.getClientArea();
		rect.x += marginWidth;
		rect.y += marginHeight;
		rect.width -= 2 * marginWidth;
		rect.height -= 2 * marginHeight;
		for (int i = 0; i < children.length; i++) {
			children[i].pack();
			children[i].setBounds(rect.x, children[i].getBounds().y, rect.width, children[i].getBounds().height);
//			children[i].setBounds(rect);
			children[i].setVisible(children[i] == topControl);
		}
	}
	
}
