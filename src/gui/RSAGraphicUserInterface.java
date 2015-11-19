package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import rsa.decrypter.RSADecrypter;
import rsa.encrypter.RSAEncrypter;
import rsa.key.RSAKey;
import rsa.key.RSAKeyGenerator;
import rsa.key.RSAPrivateKey;
import rsa.key.RSAPublicKey;

public class RSAGraphicUserInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6542178694941578277L;
	
	private JFrame frame = this;
	
	private JPanel contentPane;
	private JTextField textFieldValue1;
	private JTextField textFieldValue2;
	private JTextField textFieldPublicKey;
	private JTextField textFieldPrivateKey;
	private JTextField textFieldMessage;
	private JTextField textFieldEncrypted;
	
	private RSAKey[] keys;
	private JTextField textFieldPrivateKeyD;
	private JTextField textFieldPrivateKeyN;
	private JTextField textFieldEncryptedMessage;
	private JTextField textFieldDecryptedMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RSAGraphicUserInterface frame = new RSAGraphicUserInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RSAGraphicUserInterface() {
		setResizable(false);
		setTitle("RSA Encrypter and Decrypter");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RSAGraphicUserInterface.class.getResource("/com/sun/javafx/scene/web/skin/Bold_16x16_JFX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 424, 249);
		contentPane.add(tabbedPane);
		
		JPanel encryptPanel = new JPanel();
		tabbedPane.addTab("Encrypt", new ImageIcon(RSAGraphicUserInterface.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")), encryptPanel, null);
		encryptPanel.setLayout(null);
		
		JLabel lblValues = new JLabel("Insert two distinct prime numbers:");
		lblValues.setBounds(10, 11, 200, 14);
		encryptPanel.add(lblValues);
		
		textFieldValue1 = new JTextField();
		textFieldValue1.setToolTipText("");
		textFieldValue1.setBounds(227, 8, 86, 20);
		encryptPanel.add(textFieldValue1);
		textFieldValue1.setColumns(10);
		
		textFieldValue2 = new JTextField();
		textFieldValue2.setToolTipText("");
		textFieldValue2.setBounds(323, 8, 86, 20);
		encryptPanel.add(textFieldValue2);
		textFieldValue2.setColumns(10);
		
		JPanel panelKeys = new JPanel();
		panelKeys.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelKeys.setBounds(10, 36, 399, 65);
		encryptPanel.add(panelKeys);
		panelKeys.setLayout(null);
		
		JLabel lblKeys = new JLabel("Generated Keys:");
		lblKeys.setBounds(10, 11, 200, 14);
		panelKeys.add(lblKeys);
		
		textFieldPublicKey = new JTextField();
		textFieldPublicKey.setEditable(false);
		textFieldPublicKey.setBounds(119, 8, 270, 20);
		panelKeys.add(textFieldPublicKey);
		textFieldPublicKey.setColumns(10);
		
		textFieldPrivateKey = new JTextField();
		textFieldPrivateKey.setEditable(false);
		textFieldPrivateKey.setColumns(10);
		textFieldPrivateKey.setBounds(119, 36, 270, 20);
		panelKeys.add(textFieldPrivateKey);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(10, 112, 100, 14);
		encryptPanel.add(lblMessage);
		
		textFieldMessage = new JTextField();
		textFieldMessage.setBounds(86, 109, 323, 20);
		encryptPanel.add(textFieldMessage);
		textFieldMessage.setColumns(10);
		
		JLabel lblEncrypted = new JLabel("Encrypted:");
		lblEncrypted.setBounds(10, 140, 100, 14);
		encryptPanel.add(lblEncrypted);
		
		textFieldEncrypted = new JTextField();
		textFieldEncrypted.setEditable(false);
		textFieldEncrypted.setColumns(10);
		textFieldEncrypted.setBounds(86, 137, 323, 20);
		encryptPanel.add(textFieldEncrypted);
		
		JButton btnGenerateKeys = new JButton("Generate Keys");
		btnGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int p = Integer.valueOf(textFieldValue1.getText()),
						q = Integer.valueOf(textFieldValue2.getText());
					keys = RSAKeyGenerator.generateKeys(p,q);
					textFieldPublicKey.setText(keys[0].toString());
					textFieldPrivateKey.setText(keys[1].toString());
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Both generation values must be integers!", "Encrypt Error", 
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGenerateKeys.setBounds(10, 168, 120, 23);
		encryptPanel.add(btnGenerateKeys);
		
		JButton btnEncryptMessage = new JButton("Encrypt Message");
		btnEncryptMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for(char c : textFieldMessage.getText().toCharArray()) {
						if(c >= keys[0].getN())
							throw new IllegalArgumentException("Both generation values must be higher for this message!");
					}
					int[] encryptedMessage = 
							RSAEncrypter.encrypt(textFieldMessage.getText(),(RSAPublicKey)keys[0]);
					String encryptedMessageS = "[";
					for(int i = 0; i < encryptedMessage.length; i++) {
						encryptedMessageS += " " + encryptedMessage[i];
					}
					encryptedMessageS += " ]";
					textFieldEncrypted.setText(encryptedMessageS);
				} catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(frame, "Public and Private Keys must be generated first!", "Encrypt Error", 
							JOptionPane.ERROR_MESSAGE);
				} catch(IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(frame, ex.getMessage(), "Encrypt Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEncryptMessage.setBounds(140, 168, 139, 23);
		encryptPanel.add(btnEncryptMessage);
		
		JButton btnResetFields1 = new JButton("Reset Fields");
		btnResetFields1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				keys = null;
				textFieldValue1.setText(null);
				textFieldValue2.setText(null);
				textFieldPublicKey.setText(null);
				textFieldPrivateKey.setText(null);
				textFieldMessage.setText(null);
				textFieldEncrypted.setText(null);
			}
		});
		btnResetFields1.setBounds(289, 168, 120, 23);
		encryptPanel.add(btnResetFields1);
		
		JPanel decryptPanel = new JPanel();
		tabbedPane.addTab("Decrypt", new ImageIcon(RSAGraphicUserInterface.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")), decryptPanel, null);
		decryptPanel.setLayout(null);
		
		JLabel lblPrivateKey = new JLabel("PrivateKey (");
		lblPrivateKey.setBounds(10, 11, 70, 14);
		decryptPanel.add(lblPrivateKey);
		
		textFieldPrivateKeyD = new JTextField();
		textFieldPrivateKeyD.setToolTipText("");
		textFieldPrivateKeyD.setBounds(90, 8, 60, 20);
		decryptPanel.add(textFieldPrivateKeyD);
		textFieldPrivateKeyD.setColumns(10);
		
		JLabel labelMiddle = new JLabel(",");
		labelMiddle.setHorizontalAlignment(SwingConstants.CENTER);
		labelMiddle.setBounds(160, 11, 20, 14);
		decryptPanel.add(labelMiddle);
		
		textFieldPrivateKeyN = new JTextField();
		textFieldPrivateKeyN.setToolTipText("");
		textFieldPrivateKeyN.setColumns(10);
		textFieldPrivateKeyN.setBounds(190, 8, 60, 20);
		decryptPanel.add(textFieldPrivateKeyN);
		
		JLabel labelEnd = new JLabel(")");
		labelEnd.setBounds(260, 11, 46, 14);
		decryptPanel.add(labelEnd);
		
		JButton btnDecryptMessage = new JButton("Decrypt Message");
		btnDecryptMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int d = Integer.valueOf(textFieldPrivateKeyD.getText()),
						n = Integer.valueOf(textFieldPrivateKeyN.getText());
					RSAPrivateKey pk = new RSAPrivateKey(d,n);
					String message = textFieldEncryptedMessage.getText();
					message = message.substring(2, message.length()-2);
					String[] messageSubArray = message.split(" ");
					int[] messageArray = new int[messageSubArray.length];
					for(int i = 0; i < messageArray.length; i++) {
						messageArray[i] = Integer.valueOf(messageSubArray[i]);
					}
					String decryptedMessage = RSADecrypter.decrypt(messageArray,pk);
					textFieldDecryptedMessage.setText(decryptedMessage);
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Both values of the Private Key must be integers!", "Decrypt Error", 
							JOptionPane.ERROR_MESSAGE);
				} catch(StringIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(frame, "Invalid message to decrypt!", "Decrypt Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDecryptMessage.setBounds(10, 169, 140, 23);
		decryptPanel.add(btnDecryptMessage);
		
		JButton btnResetFields2 = new JButton("Reset Fields");
		btnResetFields2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldPrivateKeyD.setText(null);
				textFieldPrivateKeyN.setText(null);
				textFieldEncryptedMessage.setText(null);
				textFieldDecryptedMessage.setText(null);
			}
		});
		btnResetFields2.setBounds(166, 169, 140, 23);
		decryptPanel.add(btnResetFields2);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 399, 122);
		decryptPanel.add(panel);
		panel.setLayout(null);
		
		JLabel labelEncryptedMessage = new JLabel("Encrypted Message");
		labelEncryptedMessage.setBounds(10, 11, 379, 14);
		panel.add(labelEncryptedMessage);
		
		textFieldEncryptedMessage = new JTextField();
		textFieldEncryptedMessage.setToolTipText("Format: [ 1 2 3 4 5 ]");
		textFieldEncryptedMessage.setColumns(10);
		textFieldEncryptedMessage.setBounds(10, 36, 379, 20);
		panel.add(textFieldEncryptedMessage);
		
		JLabel labelDecryptedMessage = new JLabel("Decrypted Message:");
		labelDecryptedMessage.setBounds(10, 67, 379, 14);
		panel.add(labelDecryptedMessage);
		
		textFieldDecryptedMessage = new JTextField();
		textFieldDecryptedMessage.setEditable(false);
		textFieldDecryptedMessage.setColumns(10);
		textFieldDecryptedMessage.setBounds(10, 92, 379, 20);
		panel.add(textFieldDecryptedMessage);
		
		JPanel aboutPanel = new JPanel();
		tabbedPane.addTab("About", new ImageIcon(RSAGraphicUserInterface.class.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")), aboutPanel, null);
		aboutPanel.setLayout(null);
		
		JTextArea textAbout = new JTextArea();
		textAbout.setFont(new Font("Lucida Handwriting", Font.PLAIN, 13));
		textAbout.setBackground(SystemColor.control);
		textAbout.setEditable(false);
		textAbout.setText("SOFTWARE DEVELOPED BY:\r\n  Bruno Aboim (brunoaboim@gmail.com)\r\n  ISCTE - Instituto Universit\u00E1rio de Lisboa\r\n  June 2015");
		textAbout.setBounds(10, 11, 399, 181);
		aboutPanel.add(textAbout);
	}
}
