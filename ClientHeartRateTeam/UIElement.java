package ClientHeartRateTeam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import HeartRate.Gui;

public class UIElement extends JPanel implements ActionListener{
	
	private JTextPane dataPane = new JTextPane();
	private JTextField ipAddress = new JTextField();
	private JTextField port = new JTextField();
	private JButton connect = new JButton();
	
	
	public  UIElement() {
		this.setBackground(Color.WHITE);
		this.setLayout(new FlowLayout());
		
		this.add(createMainPanel());
		this.add(createRightButtonGroup());
	}
	
	Component createRightButtonGroup() {
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.WHITE);

		this.ipAddress.setText("localhost");
		this.port.setText("PORT");
		this.connect.setText("Connect");

		buttons.add(ipAddress);
		buttons.add(port);
		buttons.add(connect);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		
		return buttons;
	}
	
	
	
	Component createMainPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(Color.WHITE);

		this.dataPane.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.dataPane.setEnabled(false);
		this.dataPane.setEditable(true);
		this.dataPane.setPreferredSize(new Dimension(400, 150));

		JScrollPane scrollPane = new JScrollPane(this.dataPane);
		panel.add(scrollPane);

		return panel;

	}
	
	public JTextPane getDataPane() {
		return dataPane;
	}

	public void setDataPane(JTextPane dataPane) {
		this.dataPane = dataPane;
	}

	public JTextField getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(JTextField ipAddress) {
		this.ipAddress = ipAddress;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}

	public JButton getConnect() {
		return connect;
	}

	public void setConnect(JButton connect) {
		this.connect = connect;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
}
