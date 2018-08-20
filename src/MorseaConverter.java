
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.tools.OptionChecker;

public class MorseaConverter extends JFrame implements KeyListener {

	private JButton convertButton, exitButton, cleanButton;
	private JLabel labelEnter, labelResult, labelConvert, labelClean, labelClose;
	private JCheckBox switchEnter, lineWrap;
	private static JCheckBoxMenuItem itemChConfirmExit;
	private JTextArea fieldEntry, fieldExit;
	private JMenuBar menuBar;
	private JMenu menuHelp, menuFile, menuOptions;
	private JMenuItem itemInfo, itemOpen, itemWrite, itemExit;
	private boolean optionClose, optionWrap, optionEnterButton;
	private boolean loadedClosing, loadedWraping, loadedEnter;

	private RememberOptions rememberOptions = new RememberOptions();

	private String convertResult;

	public void setCheckBox() {
		rememberOptions.checkFile();

		optionClose = rememberOptions.getClosingSettings();
		optionWrap = rememberOptions.getWrapingSettings();
		optionEnterButton = rememberOptions.getEnterSettings();

		itemChConfirmExit.setSelected(optionClose);
		switchEnter.setSelected(optionEnterButton);
		lineWrap.setSelected(optionWrap);

	}

	public void getCurrentSettingsBeforeClosing() {

		loadedClosing = rememberOptions.getClosingSettings();
		loadedWraping = rememberOptions.getWrapingSettings();
		loadedEnter = rememberOptions.getEnterSettings();
	}

	public MorseaConverter() {

		setSize(500, 500);
		setTitle("Konwerter kodu Morse'a");
		setLocationRelativeTo(null);
		setLayout(null);

		ActionMap actionMap = new ActionMap();

		menuBar = new JMenuBar();
		menuHelp = new JMenu("Pomoc");
		menuFile = new JMenu("Plik");
		menuOptions = new JMenu("Opcje");

		itemChConfirmExit = new JCheckBoxMenuItem("Czy potwierdzaæ zamykanie?");
		menuOptions.add(itemChConfirmExit);

		itemChConfirmExit.addActionListener(actionEvent -> {
			if (itemChConfirmExit.isSelected())
				rememberOptions.setOptionsClose("true");

			else if (!itemChConfirmExit.isSelected())
				rememberOptions.setOptionsClose("false");
		});

		itemInfo = new JMenuItem("Informacje");
		itemInfo.addActionListener(actionEvent -> {

			String wiadomosc = "Konwerter kodu Morse'a na alfabet, oraz z alfabetu na kod Morse'a \n Autor: Pawe³ Rednowski";

			JOptionPane.showMessageDialog(this, wiadomosc, "O programie...", 1);
		});

		itemOpen = new JMenuItem("Otwórz");
		itemOpen.addActionListener(actionEvent -> {

			JFileChooser openFileWindow = new JFileChooser();
			openFileWindow.setApproveButtonText("Otwórz");
			openFileWindow.setDialogTitle("Otwórz...");

			if (openFileWindow.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				File fileOpen = openFileWindow.getSelectedFile();

				try {
					Scanner readFile = new Scanner(fileOpen);
					while (readFile.hasNext()) {
						fieldEntry.append(readFile.nextLine() + "\n");
					}
				} catch (FileNotFoundException e1) {

					JOptionPane.showMessageDialog(this, "B³¹d odczytu pliku", "B³¹d!", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		itemWrite = new JMenuItem("Zapis");
		itemWrite.addActionListener(actionEvent -> {

			JFileChooser writeFileWindow = new JFileChooser();
			writeFileWindow.setDialogTitle("Zapisz...");

			if (writeFileWindow.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

				File writeFile = writeFileWindow.getSelectedFile();

				try {
					PrintWriter writeToTheFile = new PrintWriter(writeFile);
					Scanner readExitField = new Scanner(fieldExit.getText());

					while (readExitField.hasNext()) {
						writeToTheFile.println(readExitField.nextLine());
					}

					writeToTheFile.close();

				} catch (FileNotFoundException e1) {

					JOptionPane.showMessageDialog(this, "B³¹d zapisu pliku", "B³¹d!", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		itemExit = new JMenuItem("Wyjœcie");
		itemExit.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
		itemExit.addActionListener(actionEvent -> {

			if (itemChConfirmExit.isSelected()) {

				int answer = JOptionPane.showConfirmDialog(this, "Czy napewno wyjœæ z programu?",
						"Wyjœcie z programu", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					dispose();
				}
			} else
				dispose();
		});

		setJMenuBar(menuBar);

		menuBar.add(menuFile);
		menuBar.add(menuOptions);

		menuFile.add(itemOpen);
		menuFile.add(itemWrite);
		menuFile.addSeparator();
		menuFile.add(itemExit);

		menuBar.add(Box.createHorizontalStrut(300));
		menuBar.add(menuHelp);
		menuHelp.add(itemInfo);

		convertButton = new JButton("Przet³umacz");
		convertButton.setBounds(185, 165, 130, 30);
		add(convertButton);
		convertButton.addActionListener(actionEvent -> {
			MorseaCode convertEngine = new MorseaCode();
			if (fieldEntry.getText().length() == 0 || fieldEntry.getText().matches("[\n ]+")) {
				fieldExit.setText("WprowadŸ dane");
			}

			else {
				convertEngine.ustawDaneUzytkownika((fieldEntry.getText() + "\ne"));
				convertEngine.handlingNewLine();

				fieldExit.setText(
						convertEngine.pobierzWynik().substring(0, convertEngine.pobierzWynik().length() - 3));
			}

		});

		cleanButton = new JButton("Czyœæ");
		cleanButton.setBounds(185, 320, 130, 30);
		add(cleanButton);
		cleanButton.addActionListener(actionEvent -> {

			fieldEntry.setText("");
			fieldExit.setText("");

		});

		exitButton = new JButton("Wyjœcie");
		exitButton.setBounds(185, 400, 130, 30);
		add(exitButton);
		exitButton.addActionListener(actionEvent -> {

			if (itemChConfirmExit.isSelected()) {

				int answer = JOptionPane.showConfirmDialog(this, "Czy napewno wyjœæ z programu?",
						"Wyjœcie z programu", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					dispose();
				}
			} else
				dispose();
		});

		labelEnter = new JLabel();
		labelEnter.setBounds(10, 20, 300, 20);
		labelEnter.setText("WprowadŸ kod Morse'a, lub wpisz litery");
		add(labelEnter);

		labelResult = new JLabel();
		labelResult.setBounds(10, 190, 200, 20);
		labelResult.setText("Wynik t³umaczenia:");
		add(labelResult);

		labelConvert = new JLabel();
		labelConvert.setBounds(320, 165, 45, 30);
		labelConvert.setText("ctrl+k");
		labelConvert.setForeground(Color.gray);
		add(labelConvert);

		labelClean = new JLabel();
		labelClean.setBounds(320, 320, 45, 30);
		labelClean.setText("ctrl+u");
		labelClean.setForeground(Color.gray);
		add(labelClean);

		labelClose = new JLabel();
		labelClose.setBounds(320, 400, 130, 30);
		labelClose.setText("ctrl+w");
		labelClose.setForeground(Color.gray);
		add(labelClose);

		switchEnter = new JCheckBox("Konwertuj naciskaj¹c ENTER");
		switchEnter.setBounds(7, 135, 300, 20);
		add(switchEnter);
		switchEnter.addKeyListener(this);
		switchEnter.addActionListener(actionEvent -> {

			if (switchEnter.isSelected())
				rememberOptions.setOptionsEnter("true");

			else if (!switchEnter.isSelected())
				rememberOptions.setOptionsEnter("false");
		});

		lineWrap = new JCheckBox("Zawijaj wiersze");
		lineWrap.setBounds(7, 160, 150, 20);
		add(lineWrap);
		lineWrap.addActionListener(actionEvent -> {

			if (lineWrap.isSelected()) {

				rememberOptions.setOptionsWrap("true");

				fieldEntry.setLineWrap(true);
				fieldExit.setLineWrap(true);
			}

			else if (!lineWrap.isSelected()) {

				rememberOptions.setOptionsWrap("false");

				fieldEntry.setLineWrap(false);
				fieldExit.setLineWrap(false);
			}

		});

		fieldEntry = new JTextArea();
		fieldEntry.addKeyListener(this);

		InputMap mapaKlawiatury = convertButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		mapaKlawiatury.put(KeyStroke.getKeyStroke("ctrl K"), "ctrlK");
		mapaKlawiatury.put(KeyStroke.getKeyStroke("ctrl U"), "ctrlU");
		mapaKlawiatury.put(KeyStroke.getKeyStroke("ctrl W"), "ctrlW");

		actionMap.put("ctrlK", actionConvert);
		actionMap.put("ctrlU", actionClean);
		actionMap.put("ctrlW", actionExit);

		convertButton.setActionMap(actionMap);
		cleanButton.setActionMap(actionMap);
		exitButton.setActionMap(actionMap);

		JScrollPane scrollPaneEnter = new JScrollPane(fieldEntry);
		scrollPaneEnter.setBounds(10, 50, 466, 80);
		add(scrollPaneEnter);

		fieldExit = new JTextArea();

		JScrollPane scrollPaneExit = new JScrollPane(fieldExit);
		scrollPaneExit.setBounds(10, 220, 466, 80);
		add(scrollPaneExit);

		fieldEntry.setLineWrap(true);
		fieldExit.setLineWrap(true);

	}

	public static void main(String[] args) {

		MorseaConverter frame = new MorseaConverter();

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		frame.setCheckBox();

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				if (itemChConfirmExit.isSelected()) {

					int exit = JOptionPane.showConfirmDialog(null, "Czy na pewno wyjœæ z programu?",
							"PotwierdŸ zamykanie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (exit == JOptionPane.YES_OPTION) {

						frame.getCurrentSettingsBeforeClosing();

						if (frame.loadedClosing == true && frame.loadedWraping == true && frame.loadedEnter == false)
							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						else if (frame.optionClose == frame.loadedClosing
								&& frame.optionWrap == frame.loadedWraping
								&& frame.optionEnterButton == frame.loadedEnter) {

							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						}

						else {
							frame.rememberOptions.saveOptions();
							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						}

					}

				} else {

					frame.getCurrentSettingsBeforeClosing();

					if (frame.loadedClosing == true && frame.loadedWraping == true && frame.loadedEnter == false)
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					else if (frame.optionClose == frame.loadedClosing && frame.optionWrap == frame.loadedWraping
							&& frame.optionEnterButton == frame.loadedEnter) {

						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					}

					else {
						frame.rememberOptions.saveOptions();
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					}
				}
			}

		});

	}

	Action actionConvert = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			convertButton.doClick();
		}
	};

	Action actionClean = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			cleanButton.doClick();
		}
	};

	Action actionExit = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			exitButton.doClick();

		}
	};

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.isShiftDown() && (e.getKeyCode() == KeyEvent.VK_ENTER)) {

			fieldEntry.append("\n");

		}

		else if (e.getKeyCode() == KeyEvent.VK_ENTER && switchEnter.isSelected()) {
			fieldEntry.setEditable(false);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER && switchEnter.isSelected() && (e.isShiftDown() == false)) {

			convertButton.doClick();
			fieldEntry.setEditable(true);

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
