package usp.ime.line.ivprog.view.utils;

/**
 * L2FProd.com Common Components 7.3 License.
 *
 * Copyright 2005-2007 L2FProd.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

public class BlueishButtonUI extends BasicButtonUI {

	private static Color blueishBackgroundOver = new Color(224, 232, 246);
	private static Color blueishBorderOver = new Color(152, 180, 226);

	private static Color blueishBackgroundSelected = new Color(193, 210, 238);
	private static Color blueishBorderSelected = new Color(49, 106, 197);
	private static MouseListener handCursorListener = new HandCursor();

	private static Rectangle viewRect = new Rectangle();
	private static Rectangle textRect = new Rectangle();
	private static Rectangle iconRect = new Rectangle();

	protected int dashedRectGapX;
	protected int dashedRectGapY;
	protected int dashedRectGapWidth;
	protected int dashedRectGapHeight;
	private Color focusColor;

	public BlueishButtonUI() {
		super();
		dashedRectGapX = UIManager.getInt("ButtonUI.dashedRectGapX");
		dashedRectGapY = UIManager.getInt("ButtonUI.dashedRectGapY");
		dashedRectGapWidth = UIManager.getInt("ButtonUI.dashedRectGapWidth");
		dashedRectGapHeight = UIManager.getInt("ButtonUI.dashedRectGapHeight");
		focusColor = new Color(100, 100, 255);
	}

	protected void installListeners(AbstractButton b) {
		super.installListeners(b);
		b.addMouseListener(handCursorListener);
	}

	protected void uninstallListeners(AbstractButton b) {
		super.uninstallListeners(b);
		b.removeMouseListener(handCursorListener);
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		AbstractButton button = (AbstractButton) c;
		button.setRolloverEnabled(true);
		button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	    button.setOpaque(false);
	}

	public void paint(Graphics g, JComponent c) {
		AbstractButton button = (AbstractButton) c;
		if (button.getModel().isRollover() || button.getModel().isArmed()
				|| button.getModel().isSelected()) {
			Color oldColor = g.getColor();
			if (button.getModel().isSelected()) {
				g.setColor(blueishBackgroundSelected);
			} else {
				g.setColor(blueishBackgroundOver);
			}
			if (button.getModel().isSelected()) {
				g.setColor(blueishBorderSelected);
			} else {
				g.setColor(blueishBorderOver);
			}
			g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
			g.setColor(oldColor);
		}

		if (button.getText() != null && !button.getText().equals("")) {
			View v = (View) c.getClientProperty(BasicHTML.propertyKey);
			if (v != null) {
				textRect.x += getTextShiftOffset();
				textRect.y += getTextShiftOffset();
				v.paint(g, textRect);
				textRect.x -= getTextShiftOffset();
				textRect.y -= getTextShiftOffset();
			}
		}
		if (button.isFocusPainted() && button.hasFocus()) {
			paintFocus(g, button, viewRect, textRect, iconRect);
		}
		super.paint(g, c);
	}

	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect,
			Rectangle textRect, Rectangle iconRect) {
		if (b.getParent() instanceof JToolBar) {
			return;
		}
		int width = b.getWidth();
		int height = b.getHeight();
		g.setColor(focusColor);
		BasicGraphicsUtils.drawDashedRect(g, dashedRectGapX, dashedRectGapY,
				width - dashedRectGapWidth, height - dashedRectGapHeight);
	}

	static class HandCursor extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			e.getComponent().setCursor(
					Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getDefaultCursor());
		}
	}

}
