import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;

public class MessengerClient extends Component {
    private PrintWriter out;

    private JTextArea textArea;
    private JTextField textField;

    public MessengerClient() {
        authenticate();
    }

    public void connect(String serverAddress, int serverPort) {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            initializeUI();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void initializeUI()  {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }


        JFrame frame = new JFrame("Messenger Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        textField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(e -> sendMessage());
        textField.addActionListener(e -> sendMessage());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            try {
                out.println(message);
                textField.setText("");
                textArea.append(message + "\n");
            } catch (Exception e) {
                System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
            }
        } else {
            textArea.append("Сообщение пустое!" + "\n");
        }
    }

    private void authenticate() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (User.isAuthenticate(username, password)) {
                connect("localhost", 10001);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                authenticate();
            }
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        new MessengerClient();
    }
}
