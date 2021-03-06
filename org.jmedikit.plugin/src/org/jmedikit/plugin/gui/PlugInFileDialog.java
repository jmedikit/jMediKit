package org.jmedikit.plugin.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Der FileDialog dient zum laden von Dateien
 * 
 * @author rkorb
 *
 */
public class PlugInFileDialog implements IPlugInDialogItem{

	private String name;
	
	private String value;
	
	/**
	 * Erzeugt ein Formularelement
	 * 
	 * @param name Ein innerhalb des Dialogs eindeutiger Name
	 * @param defaultValue Standardwert des Elements
	 */
	public PlugInFileDialog(String name, String defaultValue) {
		this.name = name;
		value = defaultValue;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object o) {
		value = (String)o;
	}

	@Override
	public void getSWTObject(Composite parent) {
		Composite c = new Composite(parent, SWT.BORDER);
		GridLayout grid = new GridLayout(1, false);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true, 0, 0);
		c.setLayout(grid);
		c.setLayoutData(data); 
		
		Label label = new Label(c, SWT.NONE);
		GridData labelData = new GridData();
		labelData.widthHint = 100;
		label.setLayoutData(labelData);
		label.setText(name);
		
		final Composite dialogContainer = new Composite(c, SWT.NONE);
		dialogContainer.setLayout(new GridLayout(2, false));
		dialogContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
		Button open = new Button(dialogContainer, SWT.NONE);
		open.setText("�ffnen");
		GridData openData = new GridData();
		openData.widthHint = 100;
		open.setLayoutData(openData);
		
		final Text text = new Text(dialogContainer, SWT.BORDER);
		text.setText(value);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
		open.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog(dialogContainer.getShell());
				String path = dialog.open();
				value = path;
				text.setText(value);
			}
		});
		
		
		/*final Button check = new Button(c, SWT.CHECK);
		GridData checkData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		check.setLayoutData(checkData);
		check.setSelection(value);
		
		
		check.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				value = check.getSelection();
			}
		});*/
	}

}
