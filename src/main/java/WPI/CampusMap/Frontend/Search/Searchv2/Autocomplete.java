package WPI.CampusMap.Frontend.Search.Searchv2;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

//http://stackabuse.com/example-adding-autocomplete-to-jtextfield/

public class Autocomplete implements DocumentListener {

	private static enum Mode {
		INSERT, COMPLETION
	};

	static class CustomComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
//			System.out.println(o1 + ", " + o2 + ": " + o1.toLowerCase().compareTo(o2.toLowerCase()));
			return o1.toLowerCase().compareTo(o2.toLowerCase());
		}
	}

	CustomComparator c = new CustomComparator();

	private JTextField textField;
	private final List<String> keywords;
	private Mode mode = Mode.INSERT;

	public Autocomplete(JTextField textField, List<String> keywords) {
		this.textField = textField;
		this.keywords = keywords;
		Collections.sort(keywords, c);
	}

	@Override
	public void changedUpdate(DocumentEvent ev) {
	}

	@Override
	public void removeUpdate(DocumentEvent ev) {
	}

	@Override
	public void insertUpdate(DocumentEvent ev) {
		if (ev.getLength() != 1)
			return;

		int pos = ev.getOffset();
		String content = null;
		try {
			content = textField.getText(0, pos + 1);
		} catch (BadLocationException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// Find where the word starts
		int w;
		for (w = pos; w >= 0; w--) {
			if (!Character.isLetter(content.charAt(w)) && !Character.isDigit(content.charAt(w))) {
				break;
			}
		}

		// Too few chars
		if (pos - w < 2)
			return;

		String prefix = content.substring(w + 1);
		int n = Collections.binarySearch(keywords, prefix, c);
		if (n < 0 && -n <= keywords.size()) {
			String match = keywords.get(-n - 1);
			if (match.toLowerCase().startsWith(prefix.toLowerCase())) {
				// A completion is found
				String completion = match.substring(pos - w);
				// We cannot modify Document from within notification,
				// so we submit a task that does the change later
				SwingUtilities.invokeLater(new CompletionTask(match, pos + 1));
			}
		} else {
			// Nothing found
			mode = Mode.INSERT;
		}
	}

	public class CommitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5794543109646743416L;

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (mode == Mode.COMPLETION) {
				int pos = textField.getSelectionEnd();
				StringBuffer sb = new StringBuffer(textField.getText());
				sb.insert(pos, " ");
				textField.setText(sb.toString());
				textField.setCaretPosition(pos + 1);
				mode = Mode.INSERT;
			} else {
				textField.replaceSelection("\t");
			}
		}
	}

	private class CompletionTask implements Runnable {
		private String match;
		private int position;

		CompletionTask(String match, int position) {
			this.match = match;
			this.position = position;
		}

		public void run() {
			textField.setText(match);
			textField.setCaretPosition(match.length());
			textField.moveCaretPosition(position);
			mode = Mode.COMPLETION;
		}
	}

}