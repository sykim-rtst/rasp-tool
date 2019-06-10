package kr.co.rtst.autosar.ap4x.editor.listener;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class PositiveDecimalVerifier implements VerifyListener {

	@Override
	public void verifyText(VerifyEvent e) {
		String completeText = "";
		if(e.getSource() instanceof Text){
			completeText = ((Text)e.getSource()).getText();
		}else if(e.getSource() instanceof Combo){
			completeText = ((Combo)e.getSource()).getText();
		}
		completeText += e.text;
		if(completeText.startsWith("0x")){ // /[\dA-Fa-f]+/
//			e.doit = completeText.matches("[0x\\dA-Fa-f]*"); //true;
			e.doit = completeText.substring(2).matches("[\\dA-Fa-f]*"); //true;
		}else{
			e.doit = completeText.matches("[\\d]*"); // 10진수
		}
	}

}
