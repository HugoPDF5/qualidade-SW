package util;


import javax.swing.JButton;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ButtonColumnProduct implements Serializable {
	private int mnemonic;
	private JButton renderButton;

	public int getMnemonic() {
		return mnemonic;
	}

	public JButton getRenderButton() {
		return renderButton;
	}

	public void setRenderButton(JButton renderButton) {
		this.renderButton = renderButton;
	}

	/**
	* The mnemonic to activate the button when the cell has focus
	* @param mnemonic  the mnemonic
	*/
	public void setMnemonic(int mnemonic, JButton thisEditButton) {
		this.mnemonic = mnemonic;
		renderButton.setMnemonic(mnemonic);
		thisEditButton.setMnemonic(mnemonic);
	}
}