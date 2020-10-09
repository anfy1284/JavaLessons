package ru.geekbrains.java_two.lesson_d.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));

    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    private final String logFileName = "log.txt";

    private FileWriter logFileWriter;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { // Event Dispatching Thread
                new ClientGUI();
            }
        });
    }

    public void readLogFile() throws IOException {
        File file = new File(logFileName);
        if (!file.exists()) file.createNewFile();

        String logText = new String(Files.readAllBytes(Paths.get(logFileName)));
        log.setText(logText);
        openLogFileWriter();
        appendLog(logText);
    }

    public void openLogFileWriter() throws IOException {
        File file = new File(logFileName);
        logFileWriter = new FileWriter(file);
    }

    public void appendLog(String text) {
        try {
            logFileWriter.write(text);
            logFileWriter.flush();
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }

    }

    private void sendMessage() {
        String text = tfMessage.getText();
        if (text.isBlank()) return;
        String line = tfLogin.getText() + ": " + tfMessage.getText() + "\n";
        log.append(line);
        appendLog(line);
        tfMessage.setText("");
    }

    private void setFocusOnMessageInput() {
        tfMessage.grabFocus();
    }

    private void afterAnyEventAction(ActionEvent e) { //Аргумент на будущее
        setFocusOnMessageInput();
    }

    private ClientGUI() {
        try {
            readLogFile();
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }

        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5",
                "user_with_an_exceptionally_long_name_in_this_chat"};
        userList.setListData(users);
        log.setEditable(false);
        scrollUser.setPreferredSize(new Dimension(150, 0));
        cbAlwaysOnTop.addActionListener(this);
        tfMessage.addActionListener(this);
        btnSend.addActionListener(this);

        btnSend.setFocusable(false);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);
        setVisible(true);
        setFocusOnMessageInput();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
        afterAnyEventAction(e);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        showException(t, e);
    }

    public void showException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = "Exception in " + t.getName() + " " +
                e.getClass().getCanonicalName() + ": " +
                e.getMessage() + "\n\t at " + ste[0];
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }


}