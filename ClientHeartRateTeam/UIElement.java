package ClientHeartRateTeam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

public class UIElement extends JPanel implements Observer, ActionListener{
	private ClientSubscriber subscriber;
	private final ExecutorService service;
	
	private JTextPane dataPane = new JTextPane();
	private JTextField ipAddress = new JTextField();
	private JTextField port = new JTextField();
	private JButton connect = new JButton();

	UIElement(ClientSubscriber subscriber) {

		service = Executors.newCachedThreadPool();

		this.setBackground(Color.WHITE);
		this.setLayout(new FlowLayout());
		
		this.add(createMainPanel());
		this.add(createRightButtonGroup());

		this.subscriber = subscriber;
	}

	public ExecutorService getService() {
		return service;
	}

	public ClientSubscriber getSubscriber() {
		return subscriber;
	}
	
	private Component createRightButtonGroup() {
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.WHITE);

		this.ipAddress.setText("localhost");
		this.port.setText("PORT");
		this.connect.setText("Connect");

		buttons.add(ipAddress);
		buttons.add(port);
		buttons.add(connect);
		buttons.setLayout(new GridLayout(3, 1));

		connect.setPreferredSize(new Dimension(100, 20));
		connect.addActionListener(this);

		return buttons;
	}

	private Component createMainPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setPreferredSize(new Dimension(400, 150));
		panel.setBackground(Color.WHITE);

		this.dataPane.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.dataPane.setEnabled(false);
		this.dataPane.setEditable(true);

		JScrollPane scrollPane = new JScrollPane(this.dataPane);
		panel.add(scrollPane);
		return panel;

	}

	private boolean isPortValid() {
		try {
			return Integer.parseInt(port.getText()) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void close() {
		subscriber.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(connect.getText().equals("Connect")) {
			if (!isPortValid()) {
				JOptionPane.showMessageDialog(new JPanel(), "Invalid PORT", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			dataPane.setText("");
			connect.setText("Stop");
			subscriber.setIp(ipAddress.getText());
			subscriber.setPort(Integer.parseInt(port.getText()));
			service.submit(subscriber);
			subscriber.addObserver(this);
		} else {
			close();
			connect.setText("Connect");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		String data = ((ClientSubscriber) o).getObject().toString();
		if (data.compareTo("FIN") != 0) {
			dataPane.setText(dataPane.getText() + "\n" + data);
			this.getParent().revalidate();
			this.getParent().repaint();
		}
		else {
			close();
			connect.setText("Connect");
		}
	}
}
