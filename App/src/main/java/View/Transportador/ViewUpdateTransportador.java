package View.Transportador;

import Socket.Client;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class ViewUpdateTransportador extends JFrame {

    private final JTextField idField;
    private final JTextField idDepField;
    private final JTextField cpfField;
    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField phoneField;
    private final JTextField carregamentoField;

    public ViewUpdateTransportador(Client client) {

        idField = new JTextField(20);
        idField.setVisible(false);
        idDepField = new JTextField(20);
        idDepField.setVisible(false);
        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(20);
        cpfField.setFont(cpfField.getFont().deriveFont(16f));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.setFont(nameField.getFont().deriveFont(16f));
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);
        addressField.setFont(addressField.getFont().deriveFont(16f));
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(20);
        phoneField.setFont(phoneField.getFont().deriveFont(16f));
        JLabel carregamentoLabel = new JLabel("Carregamento:");
        carregamentoField = new JTextField(20);
        carregamentoField.setFont(carregamentoField.getFont().deriveFont(16f));

        JButton findButton = new JButton("Search");
        findButton.setFont(findButton.getFont().deriveFont(16f));
        JButton registerButton = new JButton("Register");
        registerButton.setEnabled(false);

        findButton.addActionListener(e -> {
            String txt = "transportador;GET;" + cpfField.getText() + ";";
            try {
                String response = client.write(txt);
                JSONObject myjson;
                try {
                    myjson = new JSONObject(response);
                } catch (JSONException je) {
                    cpfField.setText("Não encontrado!");
                    myjson = new JSONObject("{'nome':'','endereco':'','telefone':'','carregamento':'', 'departamento':''}");
                }
                System.out.println(myjson.toString());
                if(!myjson.has("departamento")){
                    idDepField.setText("");
                    idField.setText("");
                    nameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");
                    carregamentoField.setText("");
                    registerButton.setEnabled(false);
                    showMessageDialog(this, "Transportador de CPF '" + cpfField.getText() + "' não encontrado");
                }else{
                idDepField.setText(myjson.get("departamento").toString());
                idField.setText(myjson.get("id").toString());
                nameField.setText(myjson.get("nome").toString());
                addressField.setText(myjson.get("endereco").toString());
                phoneField.setText(myjson.get("telefone").toString());
                carregamentoField.setText(myjson.get("carregamento").toString());
                registerButton.setEnabled(true);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        registerButton.setFont(registerButton.getFont().deriveFont(16f));

        registerButton.addActionListener(e -> {
            String txt = "transportador;UPDATE;" + cpfField.getText() + ";" + nameField.getText() + ";"
                    + addressField.getText() + ";" + phoneField.getText() + ";" + carregamentoField.getText() + ";" + idDepField.getText() + ";" + idField.getText() + ";";
            try {
                showMessageDialog(this, client.write(txt));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(cpfLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(cpfField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(carregamentoLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(carregamentoField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(findButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        getContentPane().setName("Atualizar tranportadores");
        add(formPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}
