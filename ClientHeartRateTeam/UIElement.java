package ClientHeartRateTeam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Client.Subscriber;

public class UIElement extends JPanel implements Observer, ActionListener{

	private ClientSubscriber subscriber;
	
	private JTextPane dataPane = new JTextPane();
	private JTextField ipAddress = new JTextField();
	private JTextField port = new JTextField();
	private JButton connect = new JButton();
	private final ExecutorService service;
	
	public  UIElement(ClientSubscriber subscriber) {

		service = Executors.newCachedThreadPool();

		this.setBackground(Color.WHITE);
		this.setLayout(new FlowLayout());
		
		this.add(createMainPanel());
		this.add(createRightButtonGroup());

		this.subscriber = subscriber;
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
		connect.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(connect.isEnabled()){
			connect.setEnabled(false);
			subscriber.setIp(ipAddress.getText());
			subscriber.setPort(Integer.parseInt(port.getText()));
			service.submit(subscriber);
			subscriber.addObserver(this);
		} else{
			shutdown();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		String data = ((ClientSubscriber) o).getObject().toString();
		dataPane.setText(dataPane.getText() + "\n" + data);
		this.getParent().revalidate();
		this.getParent().repaint();
		if (data.compareTo("FIN") != 0) {

		}
		else {
			close();
			connect.setEnabled(true);
		}
	}

	private void close() {
		System.out.println("clossing ....... +++++++");
		subscriber.stop();
	}

	private void shutdown() {
		subscriber.stop();
		service.shutdown();
		try {
			if (!service.awaitTermination(10, TimeUnit.SECONDS)) {
				service.shutdownNow();
			}
		} catch (InterruptedException ex) {
		}
	}
}
